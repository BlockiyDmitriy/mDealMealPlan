using Business_Logic_Layer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data_Access_Layer
{
    interface IReadWriteDb
    {
        void GetAll();
        void AddCategory(string name);
        void AddProduct(string name, double gramms, double protein, double fats, double carbs, double calories);

    }
}
