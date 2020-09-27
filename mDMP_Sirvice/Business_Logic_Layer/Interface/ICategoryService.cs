using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business_Logic_Layer.Interface
{
    public interface ICategoryService
    {
        List<Category> GetAll();
        string GetAllCategory();
        Category GetIdCategory(int id);
        void Replace();
        void SaveChang();
        void Delete();
        //void UpdateDb();
    }
}
