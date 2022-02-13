using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebServiceClient.Server.DTOs
{
    public class PersonDTO
    {
        public int id { get; set; }
        public string firstName { get; set; }
        public string surname { get; set; }
        public string birthDate { get; set; }

        public int reservationId { get; set; }
    }
}
