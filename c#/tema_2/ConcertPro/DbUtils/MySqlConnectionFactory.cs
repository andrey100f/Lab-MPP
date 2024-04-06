using System.Collections.Generic;
using System.Data;
using log4net;
using MySqlConnector;

namespace ConcertPro.DbUtils
{
    public class MySqlConnectionFactory : ConnectionFactory
    {
        private static readonly ILog Log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod()?.DeclaringType);
        
        public override IDbConnection CreateConnection(IDictionary<string, string> properties)
        {
            var connectionString = properties["ConnectionString"];
            Log.Info("MySql ---se deschide o conexiune la  ..." +  connectionString);
			
            return new MySqlConnection(connectionString);
        }
    }
}
