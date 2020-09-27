using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data_Access_Layer
{
    public class Product
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public double Gramms { get; set; }
        public double Protein { get; set; }
        public double Fats { get; set; }
        public double Carbs { get; set; }
        public double Calories { get; set; }
        public virtual Category Category { get; set; }
    }
}
