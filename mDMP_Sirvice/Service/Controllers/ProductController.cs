using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

using Service.Models;

namespace Service.Controllers
{ //https://metanit.com/sharp/aspnet5/12.3.php
    public class ProductController : Controller
    {
        private ConnectDbContext _db;
        public ProductController(ConnectDbContext db)
        {
            this._db = db;
        }
        public async Task<IActionResult> Index()
        {
            return new JsonResult(await _db.ProductModel.ToListAsync());
        }
        /// <summary>
        /// Получение всех продуктов
        /// </summary>
        /// <returns></returns>
        public async Task<IActionResult> GetProduct()
        {
            return View(await _db.ProductModel.ToListAsync());
        }
        /// <summary>
        /// Добавление прдукта
        /// </summary>
        /// <param name="product"></param>
        /// <returns></returns>
        [HttpPost]
        public async Task<IActionResult> Index(ProductModel product)
        {
            _db.ProductModel.Add(product);
            await _db.SaveChangesAsync();
            return RedirectToAction("Index");
        }
        /// <summary>
        /// Получение информации о определённом элементе
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public async Task<IActionResult> Details(int? id)
        {
            if (id != null)
            {
                ProductModel product = await _db.ProductModel.FirstOrDefaultAsync(p => p.Id == id);
                if (product != null)
                    return View(product);
            }
            return NotFound();
        }
        /// <summary>
        /// Редактирование определенного элемента
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public async Task<IActionResult> Edit(int? id)
        {
            if (id != null)
            {
                ProductModel product = await _db.ProductModel.FirstOrDefaultAsync(p => p.Id == id);
                if (product != null)
                    return View(product);
            }
            return NotFound();
        }
        [HttpPost]
        public async Task<IActionResult> Edit(ProductModel product)
        {
            _db.ProductModel.Update(product);
            await _db.SaveChangesAsync();
            return RedirectToAction("Index");
        }
        // Объект извлекается из базы данных и передаётся в представление
        [HttpGet]
        [ActionName("Delete")]
        public async Task<IActionResult> ConfirmDelete(int? id)
        {
            if (id != null)
            {
                ProductModel product = await _db.ProductModel.FirstOrDefaultAsync(p => p.Id == id);
                if (product != null)
                    return View(product);
            }
            return NotFound();
        }
        /// <summary>
        /// Удаление элемента по id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpPost]
        public async Task<IActionResult> Delete(int? id)
        {
            if (id != null)
            {
                ProductModel product = await _db.ProductModel.FirstOrDefaultAsync(p => p.Id == id);
                if (product != null)
                {
                    _db.ProductModel.Remove(product);
                    await _db.SaveChangesAsync();
                    return RedirectToAction("Index");
                }
            }
            return NotFound();
        }
    }
}
