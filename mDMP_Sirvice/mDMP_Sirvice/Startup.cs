using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Owin;
using Owin;

[assembly: OwinStartup(typeof(mDMP_Sirvice.Startup))]

namespace mDMP_Sirvice
{
    public partial class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);

        }
        public IConfiguration Configuration { get; }
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddDbContext<MessagesContext>(options =>
            {
                options.UseSqlServer(Configuration.GetConnectionString("DbConnectionString"));
            });
        }
    }
}
