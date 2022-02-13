using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebServiceClient.Server.DTOs
{
    public class ReservationDTO
    {
        public int id { get; set; }

        public int flightId { get; set; }

        public FlightDTO flightDTO { get; set; }

        public List<PersonDTO> people { get; set; }
    }
}
