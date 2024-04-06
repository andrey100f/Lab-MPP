using System.Collections.Generic;
using System.Data;

namespace ConcertPro.DbUtils
{
    public static class DbUtils
    {
        private static IDbConnection _instance;


        public static IDbConnection GetConnection(IDictionary<string,string> props)
        {
            if (_instance == null || _instance.State == ConnectionState.Closed)
            {
                _instance = GetNewConnection(props);
                _instance.Open();
            }
            return _instance;
        }

        private static IDbConnection GetNewConnection(IDictionary<string,string> properties)
        {
            return ConnectionFactory.GetInstance().CreateConnection(properties);
        }
    }
}
