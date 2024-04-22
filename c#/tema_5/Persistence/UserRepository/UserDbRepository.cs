using System.Collections.Generic;
using System.Data;
using Model;
using Persistence.DbUtils;

namespace Persistence.UserRepository
{
    public class UserDbRepository : IUserRepository
    {
        // private static readonly ILog Log = LogManager.GetLogger("SpectacleDbRepository");
        private readonly IDictionary<string, string> _properties;

        public UserDbRepository(IDictionary<string, string> properties)
        {
            _properties = properties;
        }

        public User FindOne(long id)
        {
            throw new System.NotImplementedException();
        }

        public IEnumerable<User> FindAll()
        {
            IDbConnection connection = Persistence.DbUtils.DbUtils.GetConnection(_properties);
            IList<User> users = new List<User>();

            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select * from users";

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long userId = dataReader.GetInt64(0);
                        string username = dataReader.GetString(1);
                        string password = dataReader.GetString(2);
                        bool loggedIn = dataReader.GetBoolean(3);

                        User user = new User(username, password, loggedIn);
                        user.Id = userId;
                        users.Add(user);
                    }
                }
            }

            return users;
        }

        public void Save(User entity)
        {
            throw new System.NotImplementedException();
        }

        public void Update(User entity, long id)
        {
            // Log.InfoFormat("Update User Task {0}", entity);
            IDbConnection connection = DbUtils.DbUtils.GetConnection(_properties);

            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "update users set username = @username, password = @password, " +
                                      "loggedIn = @loggedIn where userId = @userId";
                
                IDbDataParameter usernameDataParameter = command.CreateParameter();
                usernameDataParameter.ParameterName = "@username";
                usernameDataParameter.Value = entity.Username;
                command.Parameters.Add(usernameDataParameter);
                
                IDbDataParameter passwordParameter = command.CreateParameter();
                passwordParameter.ParameterName = "@password";
                passwordParameter.Value = entity.Password;
                command.Parameters.Add(passwordParameter);
                
                IDbDataParameter loggedInParameter = command.CreateParameter();
                loggedInParameter.ParameterName = "@loggedIn";
                loggedInParameter.Value = entity.LoggedIn;
                command.Parameters.Add(loggedInParameter);
                
                IDbDataParameter userIdParameter = command.CreateParameter();
                userIdParameter.ParameterName = "@userId";
                userIdParameter.Value = id;
                command.Parameters.Add(userIdParameter);

                int result = command.ExecuteNonQuery();
                if (result == 0)
                {
                    // Log.Error("Error DB: No User Updated");
                }
                
                // Log.InfoFormat("Saved {0} instances", result);
            }
        }

        public void Delete(long id)
        {
            throw new System.NotImplementedException();
        }

        public User FindByUsernameAndPassword(string username, string password)
        {
            // Log.InfoFormat("Entering FindUserByUsernameAndPassword with values {0}, {1}", username, password);
            IDbConnection connection = DbUtils.DbUtils.GetConnection(_properties);

            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select * from users where username = @username and password = @password";
                IDbDataParameter usernameParameter = command.CreateParameter();
                usernameParameter.ParameterName = "@username";
                usernameParameter.Value = username;
                command.Parameters.Add(usernameParameter);
                
                IDbDataParameter passwordParameter = command.CreateParameter();
                passwordParameter.ParameterName = "@password";
                passwordParameter.Value = password;
                command.Parameters.Add(passwordParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                    {
                        long userId = dataReader.GetInt64(0);
                        string usernameN = dataReader.GetString(1);
                        string passwordN = dataReader.GetString(2);
                        bool loggedIn = dataReader.GetBoolean(3);

                        User user = new User(usernameN, passwordN, loggedIn);
                        user.Id = userId;
                        
                        // Log.InfoFormat("Existing FindUserByUsernameAndPassword with value {0}", user);
                        return user;
                    }
                }
            }
            
            // Log.InfoFormat("Existing FindUserByUsernameAndPassword with value {0}", null);
            return null;
        }
    }
}
