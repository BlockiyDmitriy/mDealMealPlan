using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

using Business_Logic_Layer;
using Business_Logic_Layer.Interface;

namespace mDMP_Sirvice.Controllers
{
    public class CategoryController : ApiController
    {
        private ICategoryService _category = new CategoryService();
        public IHttpActionResult GetAll()
        {
            return Ok(_category.GetAll());
        }
        public IHttpActionResult GetIdCategory(int id)
        {
            return Ok(_category.GetIdCategory(id));
        }
    }
}
