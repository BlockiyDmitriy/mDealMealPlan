using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Service.Models
{
    public class ProductModel
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public double Gramms { get; set; }
        public double Protein { get; set; }
        public double Fats { get; set; }
        public double Carbs { get; set; }
        public double Calories { get; set; }
        public virtual CategoryModel CategoryModel { get; set; }
    }
}
