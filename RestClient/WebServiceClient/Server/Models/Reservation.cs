using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebServiceClient.Server.Models
{
    public class Reservation
    {
        public int Id { get; set; }

        public int FlightId { get; set; }

        public Flight Flight { get; set; }

        public List<Person> People { get; set; }
    }
}
