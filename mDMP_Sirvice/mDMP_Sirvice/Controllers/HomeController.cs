using Data_Access_Layer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace mDMP_Sirvice.Controllers
{
    public class HomeController : Controller
    {
        ConnectDbContext db = new ConnectDbContext();
        /// <summary>
        /// Получение данных
        /// </summary>
        /// <returns></returns>
        public ActionResult Index()
        {
            ViewBag.Title = "Home Page";

            return View(db.Category);
        }
    }
}
