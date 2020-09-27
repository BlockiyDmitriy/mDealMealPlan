using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business_Logic_Layer.Interface
{
    public interface IProductService
    {
        List<Product> GetAll();
        string GetAllCategory();
        Product GetIdProduct(int id);
        void Replace();
        void SaveChang();
        void Delete();
        //void Update();
    }
}
