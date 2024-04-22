using System;
using System.Collections.Generic;
using System.Data;
using ConcertPro.Dao.Model;
using log4net;

namespace ConcertPro.Dao.Repository.SpectacleRepository
{
    public class SpectacleDbRepository : ISpectacleRepository
    {
        private static readonly ILog Log = LogManager.GetLogger("SpectacleDbRepository");
        private readonly IDictionary<string, string> _properties;

        public SpectacleDbRepository(IDictionary<string, string> properties)
        {
            Log.Info("Initializing SpectacleDbRepository with properties: " + properties);
            _properties = properties;
        }
        
        public Spectacle FindOne(long id)
        {
            Log.InfoFormat("Entering FindOneSpectacle with value {0}", id);
            IDbConnection connection = DbUtils.DbUtils.GetConnection(_properties);

            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select * from spectacles where spectacleId = @spectacleId";
                IDbDataParameter spectacleIdParameter = command.CreateParameter();
                spectacleIdParameter.ParameterName = "@spectacleId";
                spectacleIdParameter.Value = id;
                command.Parameters.Add(spectacleIdParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                    {
                        long spectacleId = dataReader.GetInt64(0);
                        string artistName = dataReader.GetString(1);
                        DateTime spectacleDate = Convert.ToDateTime(dataReader.GetDateTime(2));
                        string spectaclePlace = dataReader.GetString(3);
                        long seatsAvailable = dataReader.GetInt64(4);
                        long seatsSold = dataReader.GetInt64(5);

                        Spectacle spectacle = new Spectacle(artistName, spectacleDate, spectaclePlace, seatsAvailable,
                            seatsSold);
                        spectacle.Id = spectacleId;
                        
                        Log.InfoFormat("Existing findOneSpectacle with value {0}", spectacle);
                        return spectacle;
                    }
                }
            }
            
            Log.InfoFormat("Existing findOneSpectacle with value {0}", null);
            return null;
        }

        public IEnumerable<Spectacle> FindAll()
        {
            Log.InfoFormat("find all spectacles task");
            IDbConnection connection = DbUtils.DbUtils.GetConnection(_properties);
            IList<Spectacle> spectacles = new List<Spectacle>();

            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select * from spectacles";

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long spectacleId = dataReader.GetInt64(0);
                        string artistName = dataReader.GetString(1);
                        DateTime spectacleDate = dataReader.GetDateTime(2);
                        string spectaclePlace = dataReader.GetString(3);
                        long seatsAvailable = dataReader.GetInt64(4);
                        long seatsSold = dataReader.GetInt64(5);

                        Spectacle spectacle = new Spectacle(artistName, spectacleDate, spectaclePlace, seatsAvailable,
                            seatsSold);
                        spectacle.Id = spectacleId;
                        spectacles.Add(spectacle);
                    }
                }
            }

            return spectacles;
        }

        public void Save(Spectacle entity)
        {
            throw new NotImplementedException();
        }

        public void Update(Spectacle entity, long id)
        {
            Log.InfoFormat("Update Spectacle Task {0}", entity);
            IDbConnection connection = DbUtils.DbUtils.GetConnection(_properties);

            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "update spectacles set artistName = @artistName, " +
                                      "spectacleDate = @spectacleDate, spectaclePlace = @spectaclePlace, " +
                                      "seatsAvailable = @seatsAvailable, seatsSold = @seatsSold where " +
                                      "spectacleId = @spectacleId";
                
                IDbDataParameter artistNameParam = command.CreateParameter();
                artistNameParam.ParameterName = "@artistName";
                artistNameParam.Value = entity.ArtistName;
                command.Parameters.Add(artistNameParam);
                
                IDbDataParameter spectacleDateParam = command.CreateParameter();
                spectacleDateParam.ParameterName = "@spectacleDate";
                spectacleDateParam.Value = entity.SpectacleDate;
                command.Parameters.Add(spectacleDateParam);
                
                IDbDataParameter spectaclePlaceParameter = command.CreateParameter();
                spectaclePlaceParameter.ParameterName = "@spectaclePlace";
                spectaclePlaceParameter.Value = entity.SpectaclePlace;
                command.Parameters.Add(spectaclePlaceParameter);
                
                IDbDataParameter seatsAvailableParameter = command.CreateParameter();
                seatsAvailableParameter.ParameterName = "@seatsAvailable";
                seatsAvailableParameter.Value = entity.SeatsAvailable;
                command.Parameters.Add(seatsAvailableParameter);
                
                IDbDataParameter seatsSoldParameter = command.CreateParameter();
                seatsSoldParameter.ParameterName = "@seatsSold";
                seatsSoldParameter.Value = entity.SeatsSold;
                command.Parameters.Add(seatsSoldParameter);
                
                IDbDataParameter spectacleIdParameter = command.CreateParameter();
                spectacleIdParameter.ParameterName = "@spectacleId";
                spectacleIdParameter.Value = id;
                command.Parameters.Add(spectacleIdParameter);

                int result = command.ExecuteNonQuery();
                if (result == 0)
                {
                    Log.Error("Error DB: No Spectacle Updated");
                }
                
                Log.InfoFormat("Saved {0} instances", result);
            }
        }

        public void Delete(long id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Spectacle> GetAllByDate(DateTime date)
        {
            Log.InfoFormat("findAllSpectaclesByDate task");
            IDbConnection connection = DbUtils.DbUtils.GetConnection(_properties);
            IList<Spectacle> spectacles = new List<Spectacle>();

            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select * from spectacles where spectacleDate = @spectacleDate";
                IDbDataParameter spectacleDateParameter = command.CreateParameter();
                spectacleDateParameter.ParameterName = "@spectacleDate";
                spectacleDateParameter.Value = date;
                command.Parameters.Add(spectacleDateParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long spectacleId = dataReader.GetInt64(0);
                        string artistName = dataReader.GetString(1);
                        DateTime spectacleDate = dataReader.GetDateTime(2);
                        string spectaclePlace = dataReader.GetString(3);
                        long seatsAvailable = dataReader.GetInt64(4);
                        long seatsSold = dataReader.GetInt64(5);

                        Spectacle spectacle = new Spectacle(artistName, spectacleDate, spectaclePlace, seatsAvailable,
                            seatsSold);
                        spectacle.Id = spectacleId;
                        spectacles.Add(spectacle);
                    }
                }
            }

            return spectacles;
        }
    }
}
