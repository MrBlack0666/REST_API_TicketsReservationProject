using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebServiceClient.Server.Models
{
    public class Person
    {
        public int Id { get; set; }
        public string FirstName { get; set; }
        public string Surname { get; set; }
        public DateTime BirthDate { get; set; }

        public int ReservationId { get; set; }

        public string BirthDateString
        {
            get
            {
                return BirthDate.ToShortDateString().Replace('-', '/');
            }
        }
    }
}
