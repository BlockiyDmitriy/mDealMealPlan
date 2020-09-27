using Business_Logic_Layer.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business_Logic_Layer
{
    public class Category
    {
        public string Name { get; set; }
        public List<Product> Products { get; set; }

    }
}
