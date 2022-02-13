using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebServiceClient.Server.Models
{
    public class Flight
    {
        public int Id { get; set; }
        public string To_City { get; set; }
        public string From_City { get; set; }
        public DateTime FlightDepartureDate { get; set; }
        public DateTime FlightArrivalDate { get; set; }
        public decimal Price { get; set; }
        public int NumberOfSeats { get; set; }
        public int NumberOfAvaiableSeats { get; set; }

        public string FlightDepartureDateString
        {
            get
            {
                return (FlightDepartureDate.ToShortDateString() + " " + FlightDepartureDate.ToShortTimeString()).Replace('-', '/');
            }
        }

        public string FlightArrivalDateString
        {
            get
            {
                return (FlightArrivalDate.ToShortDateString() + " " + FlightArrivalDate.ToShortTimeString()).Replace('-', '/');
            }
        }
    }
}
