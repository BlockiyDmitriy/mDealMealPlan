using Business_Logic_Layer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data_Access_Layer
{
    class ReadWriteDb : IReadWriteDb
    {
        /// <summary>
        /// Add category in data base
        /// </summary>
        /// <param name="name"></param>
        public void AddCategory(string name)
        {
            throw new NotImplementedException();
        }
        /// <summary>
        /// Add product in data base
        /// </summary>
        /// <param name="name"></param>
        /// <param name="gramms"></param>
        /// <param name="protein"></param>
        /// <param name="fats"></param>
        /// <param name="carbs"></param>
        /// <param name="calories"></param>
        public void AddProduct(string name, double gramms, double protein, double fats, double carbs, double calories)
        {
            throw new NotImplementedException();
        }
        /// <summary>
        /// Get all values from data base
        /// </summary>
        public void GetAll()
        {
            throw new NotImplementedException();
        }
    }
}
