using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WebServiceClient.Server.DTOs;
using WebServiceClient.Server.Models;

namespace WebServiceClient.Server
{
    public class ServiceResources
    {
        private static ServiceResources serverResources = null;

        private readonly IRestClient restClient;
        private List<Link> uris;

        private ServiceResources()
        {
            restClient = new RestClient("http://localhost:8080/rsiproj2/");
            uris = new List<Link>
            {
                new Link() { UriPath = "/resources/flights", RequestType = RequestType.GET },
                new Link() { UriPath = "/resources/flights", RequestType = RequestType.POST },
                new Link() { UriPath = "/resources/flights/{flightId}", RequestType = RequestType.PUT },
                new Link() { UriPath = "/resources/flights/{flightId}", RequestType = RequestType.DELETE },
                new Link() { UriPath = "/resources/flights/{flightId}", RequestType = RequestType.GET },
                new Link() { UriPath = "/resources/reservation/{reservationId}", RequestType = RequestType.GET },
                new Link() { UriPath = "/resources/reservation", RequestType = RequestType.POST },
                new Link() { UriPath = "/resources/reservation/{reservationId}", RequestType = RequestType.DELETE },
                new Link() { UriPath = "/resources/reservation/{reservationId}/pdf", RequestType = RequestType.GET }
            };
        }

        public static ServiceResources GetInstance()
        {
            if(serverResources == null)
            {
                serverResources = new ServiceResources();
            }

            return serverResources;
        }

        public List<Flight> GetFlights()
        {
            var requestGet = new RestRequest(uris[0].UriPath, DataFormat.Json);
            var responseGet = restClient.Get<List<FlightDTO>>(requestGet);

            var flights = new List<Flight>();

            if(responseGet.Data != null)
            {
                foreach(var flightDto in responseGet.Data)
                {
                    flights.Add(ConvertFlightDtoToFlight(flightDto));
                }
            }

            return flights;
        }

        public Flight AddFlight(Flight flight, User user)
        {
            var requestPost = new RestRequest(uris[1].UriPath, DataFormat.Json);

            var flightDto = ConvertFlightToFlightDto(flight);

            requestPost.AddJsonBody(flightDto);
            requestPost.AddHeader("Authorization", "Basic " + CreateCredenctials(user));
            var responsePost = restClient.Post<FlightDTO>(requestPost);

            Flight addedFlight = null;

            if (responsePost.Data != null)
            {
                addedFlight = ConvertFlightDtoToFlight(responsePost.Data);
            }

            return addedFlight;
        }

        public bool EditFlight(Flight flight, User user)
        {
            var requestPut = new RestRequest(ReplaceCubicBrackets(uris[2].UriPath, new string[1] { flight.Id.ToString() }, new string[1] { "flightId" }), DataFormat.Json);

            var flightDto = ConvertFlightToFlightDto(flight);

            requestPut.AddJsonBody(flightDto);
            requestPut.AddHeader("Authorization", "Basic " + CreateCredenctials(user));
            var responsePut = restClient.Put<bool>(requestPut);

            return responsePut.Data;
        }

        public bool DeleteFlight(int flightId, User user)
        {
            var requestDelete = new RestRequest(ReplaceCubicBrackets(uris[3].UriPath, new string[1] { flightId.ToString() }, new string[1] { "flightId" }), DataFormat.Json);
            requestDelete.AddHeader("Authorization", "Basic " + CreateCredenctials(user));

            var responseDelete = restClient.Delete<bool>(requestDelete);

            return responseDelete.Data;
        }

        public Reservation GetReservationById(int reservationId)
        {
            var requestGet = new RestRequest(ReplaceCubicBrackets(uris[5].UriPath, new string[1] { reservationId.ToString() }, new string[1] { "reservationId" }), DataFormat.Json);
            var responseGet = restClient.Get<ReservationDTO>(requestGet);

            Reservation reservation = null;

            if (responseGet.Data != null)
            {
                reservation = ConvertReservationDtoToReservation(responseGet.Data);
            }

            return reservation;
        }

        public int AddReservation(Reservation reservation)
        {
            var requestPost = new RestRequest(uris[6].UriPath, DataFormat.Json);

            var reservationDto = ConvertReservationToReservationDTO(reservation);

            requestPost.AddJsonBody(reservationDto);
            var responsePost = restClient.Post<int>(requestPost);

            return responsePost.Data;
        }

        public bool DeleteReservation(int reservationId)
        {
            var requestDelete = new RestRequest(ReplaceCubicBrackets(uris[7].UriPath, new string[1] { reservationId.ToString() }, new string[1] { "reservationId" }), DataFormat.Json);

            var responseDelete = restClient.Delete<bool>(requestDelete);

            return responseDelete.Data;
        }

        public byte[] GetReservationConfirmationPDF(int reservationId)
        {
            var requestGet = new RestRequest(ReplaceCubicBrackets(uris[8].UriPath, new string[1] { reservationId.ToString() }, new string[1] { "reservationId" }), DataFormat.None);
            var responseGet = restClient.DownloadData(requestGet);


            return responseGet;
        }

        private Flight ConvertFlightDtoToFlight(FlightDTO flightDto)
        {
            var flightDepartureDateString = flightDto.flightDepartureDate.Substring(0, flightDto.flightDepartureDate.Length - 6);
            var flightArrivalDateString = flightDto.flightArrivalDate.Substring(0, flightDto.flightArrivalDate.Length - 6);

            var isDepartureDateParsed = DateTime.TryParse(flightDepartureDateString, out var flightDepartureDate);
            var isArrivalDateParsed = DateTime.TryParse(flightArrivalDateString, out var flightArrivalDate);

            return new Flight()
            {
                FlightArrivalDate = flightArrivalDate,
                FlightDepartureDate = flightDepartureDate,
                Id = flightDto.id,
                From_City = flightDto.from_City,
                To_City = flightDto.to_City,
                NumberOfAvaiableSeats = flightDto.numberOfAvaiableSeats,
                NumberOfSeats = flightDto.numberOfSeats,
                Price = flightDto.price
            };
        }

        private FlightDTO ConvertFlightToFlightDto(Flight flight)
        {
            return new FlightDTO()
            {
                flightArrivalDate = flight.FlightArrivalDate.ToString(),
                flightDepartureDate = flight.FlightDepartureDate.ToString(),
                id = flight.Id,
                from_City = flight.From_City,
                to_City = flight.To_City,
                numberOfAvaiableSeats = flight.NumberOfAvaiableSeats,
                numberOfSeats = flight.NumberOfSeats,
                price = flight.Price
            };
        }

        private Reservation ConvertReservationDtoToReservation(ReservationDTO reservationDto)
        {
            var people = new List<Person>();

            foreach(var personDto in reservationDto.people)
            {
                people.Add(ConvertPersonDtoToPerson(personDto));
            }

            return new Reservation()
            {
                Flight = ConvertFlightDtoToFlight(reservationDto.flightDTO),
                FlightId = reservationDto.flightId,
                Id = reservationDto.id,
                People = people
            };
        }

        private ReservationDTO ConvertReservationToReservationDTO(Reservation reservation)
        {
            List<PersonDTO> people = new List<PersonDTO>();

            foreach (Person person in reservation.People)
            {
                people.Add(ConvertPersonToPersonDto(person));
            }

            return new ReservationDTO()
            {
                flightId = reservation.FlightId,
                id = reservation.Id,
                people = people
            };
        }

        private Person ConvertPersonDtoToPerson(PersonDTO personDto)
        {
            var birthDateString =  personDto.birthDate.Substring(0, personDto.birthDate.Length - 6);
            var isBirthDateParsed = DateTime.TryParse(birthDateString, out var birthDate);

            return new Person()
            {
                Id = personDto.id,
                FirstName = personDto.firstName,
                Surname = personDto.surname,
                BirthDate = birthDate,
                ReservationId = personDto.reservationId
            };
        }

        private PersonDTO ConvertPersonToPersonDto(Person person)
        {
            return new PersonDTO()
            {
                id = person.Id,
                firstName = person.FirstName,
                surname = person.Surname,
                birthDate = person.BirthDate.ToString(),
                reservationId = person.ReservationId
            };
        }

        private string ReplaceCubicBrackets(string stringToReplace, string[] value, string[] stringsInBrackets)
        {
            if(value.Length == stringsInBrackets.Length)
            {
                int i = 0;
                foreach (string stringInBrackets in stringsInBrackets)
                {
                    stringToReplace = stringToReplace.Replace("{" + stringInBrackets + "}", value[i++]);
                }
            }

            return stringToReplace;
        }

        private string CreateCredenctials(User user)
        {
            return Convert.ToBase64String(Encoding.ASCII.GetBytes(user.UserName + ":" + user.Password));
        }
    }
}
