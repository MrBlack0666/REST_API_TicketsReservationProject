using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebServiceClient.Server.DTOs
{
    public class FlightDTO
    {
        public int id { get; set; }
        public string to_City { get; set; }
        public string from_City { get; set; }
        public string flightDepartureDate { get; set; }
        public string flightArrivalDate { get; set; }
        public decimal price { get; set; }
        public int numberOfSeats { get; set; }
        public int numberOfAvaiableSeats { get; set; }
    }
}
