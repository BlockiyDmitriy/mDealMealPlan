using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Microsoft.EntityFrameworkCore;

using Service.Models;

namespace Service.Controllers
{
    // https://metanit.com/sharp/aspnet5/12.3.php
    public class HomeController : Controller
    {
        private ConnectDbContext _db;
        public HomeController(ConnectDbContext context)
        {
            _db = context;
        }
        public async Task<IActionResult> Index()
        {
            return new JsonResult(await _db.CategoryModel.ToListAsync());
        }
        /// <summary>
        /// Получение всех категорий
        /// </summary>
        /// <returns></returns>
        public async Task<IActionResult> GetCategory()
        {
            return new JsonResult(await _db.CategoryModel.ToListAsync());
        }
        /// <summary>
        /// Добавление категории
        /// </summary>
        /// <param name="category"></param>
        /// <returns></returns>
        [HttpPost]
        public async Task<IActionResult> Index(CategoryModel category)
        {
            _db.CategoryModel.Add(category);
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
                CategoryModel category = await _db.CategoryModel.FirstOrDefaultAsync(p => p.Id == id);
                if (category != null)
                    return new JsonResult(category);
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
                CategoryModel category = await _db.CategoryModel.FirstOrDefaultAsync(p => p.Id == id);
                if (category != null)
                    return new JsonResult(category);
            }
            return NotFound();
        }
        [HttpPost]
        public async Task<IActionResult> Edit(CategoryModel category)
        {
            _db.CategoryModel.Update(category);
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
                CategoryModel category = await _db.CategoryModel.FirstOrDefaultAsync(p => p.Id == id);
                if (category != null)
                    return new JsonResult(category);
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
                CategoryModel category = await _db.CategoryModel.FirstOrDefaultAsync(p => p.Id == id);
                if (category != null)
                {
                    _db.CategoryModel.Remove(category);
                    await _db.SaveChangesAsync();
                    return RedirectToAction("Index");
                }
            }
            return NotFound();
        }
    }
}