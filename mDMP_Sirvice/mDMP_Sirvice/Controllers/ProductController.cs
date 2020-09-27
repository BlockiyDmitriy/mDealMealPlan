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
    public class ProductController : ApiController
    {
        private IProductService _productService = new ProductService();
        public IHttpActionResult GetAll()
        {
            return Ok(_productService.GetAll());
        }
        public IHttpActionResult GetIdProduct(int id)
        {
            return Ok(_productService.GetIdProduct(id));
        }
    }
}
