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
        public void AddCategory(string name)
        {
            using (var context = new ConnectDbContext())
            {
                var category = new Category()
                {
                    Name = name
                };
                context.Category.Add(category);
                context.SaveChanges();

            };
        }

        public void AddProduct(string name, double gramms, double protein, double fats, double carbs, double calories)
        {
            using (var context = new ConnectDbContext())
            {
                var product = new Product()
                {
                    Name = name,
                    Gramms = gramms,
                    Protein = protein,
                    Fats = fats,
                    Carbs = carbs,
                    Calories = calories
                };
                context.Product.Add(product);
                context.SaveChanges();

            };
        }

        public void GetAll()
        {

        }
    }
}
