using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Service.Models
{
    public class ConnectDbContext : DbContext
    {
        // base class implement connect between app and db 
        /// <summary>
        /// The options parameter passes the context settings to the data context constructor
        /// </summary>
        /// <param name="options"></param>
        public ConnectDbContext(DbContextOptions<ConnectDbContext> options) : base(options)
        {
            Database.EnsureCreated();   // создаем базу данных при первом обращении
        }

        public DbSet<CategoryModel> CategoryModel { get; set; }
        public DbSet<ProductModel> ProductModel { get; set; }
    }
}
