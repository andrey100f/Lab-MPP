using System;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Text.Json;
using Newtonsoft.Json;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace ConsoleApplication1
{
    internal class Program
    {
//static HttpClient client = new HttpClient();
		//pentru jurnalizarea operatiilor efectuate si a datelor trimise/primite
		static HttpClient client = new HttpClient(new LoggingHandler(new HttpClientHandler()));

		private static string URL_Base = "http://localhost:8080/api/spectacles";

		public static void Main(string[] args)
		{
			//Console.WriteLine("Hello World!");
			RunAsync().Wait();
		}


		static async Task RunAsync()
		{
			client.BaseAddress = new Uri("http://localhost:8080/api/spectacles");
			client.DefaultRequestHeaders.Accept.Clear();
			//client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("text/plain"));
			client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
			// Get the string
			//String text = await GetTextAsync("http://localhost:8080/chat/users/greeting");
			//Console.WriteLine("am obtinut {0}", text);
			//Get one user
			String id = "18";
			Console.WriteLine("Get user {0}", id);
			
			// User result1 = await GetUserAsync("http://localhost:8080/api/users/"+id);
			Spectacle result1  = await GetUserAsync("http://localhost:8080/api/spectacles/"+id);
			Console.WriteLine("Am primit {0}", result1);
			
			
			//Create a user
			// User  user = new  User{Id="test_2024",Name = "Test C#", Passwd = "test"};
			Spectacle spectacle = new Spectacle(100, "artist", "2024-05-21", "loc", 100, 50);
			Console.WriteLine("Create spectacle {0}", spectacle);
			Spectacle result = await CreateUserAsync("http://localhost:8080/api/spectacles", spectacle);
			Console.WriteLine("Am primit {0}", result);
			Console.ReadLine();
		}

		static async Task<String> GetTextAsync(string path)
		{
			String product = null;
			HttpResponseMessage response = await client.GetAsync(path);
			if (response.IsSuccessStatusCode)
			{
				product = await response.Content.ReadAsStringAsync();
			}
			return product;
		}
		static async Task<Spectacle> GetUserAsync(string path)
		{
			Spectacle product = null;
			HttpResponseMessage response = await client.GetAsync(path);
			if (response.IsSuccessStatusCode)
			{
				product = await response.Content.ReadAsAsync<Spectacle>();
			}
			return product;
		}

		
		static async Task<Spectacle> CreateUserAsync(string path, Spectacle user)
		{
			Spectacle result = null;
			HttpResponseMessage response = await client.PostAsJsonAsync(path, user);
			if (response.IsSuccessStatusCode)
			{
				result = await response.Content.ReadAsAsync<Spectacle>();
			}
			return result;
		}
	}

	public class Spectacle
	{
		[JsonProperty("spectacleId")]
		public long SpectacleId { get; set; }
		
		[JsonProperty("artistName")]
		public string ArtistName { get; set; }
		
		[JsonProperty("spectacleDate")]
		public string SpectacleDate { get; set; }
		
		[JsonProperty("spectaclePlace")]
		public string SpectaclePlace { get; set; }
		
		[JsonProperty("seatsAvailable")]
		public long SeatsAvailable { get; set; }
		
		[JsonProperty("seatsSold")]
		public long SeatsSold { get; set; }

		public Spectacle(long spectacleId, string artistName, string spectacleDate, string spectaclePlace, long seatsAvailable, long seatsSold)
		{
			SpectacleId = spectacleId;
			ArtistName = artistName;
			SpectacleDate = spectacleDate;
			SpectaclePlace = spectaclePlace;
			SeatsAvailable = seatsAvailable;
			SeatsSold = seatsSold;
		}

		public override string ToString()
		{
			return base.ToString();
		}
	}
	
	public class LoggingHandler : DelegatingHandler
    {
        public LoggingHandler(HttpMessageHandler innerHandler)
            : base(innerHandler)
        {
        }
    
        protected override async Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
        {
            Console.WriteLine("Request:");
            Console.WriteLine(request.ToString());
            if (request.Content != null)
            {
                Console.WriteLine(await request.Content.ReadAsStringAsync());
            }
            Console.WriteLine();
    
            HttpResponseMessage response = await base.SendAsync(request, cancellationToken);
    
            Console.WriteLine("Response:");
            Console.WriteLine(response.ToString());
            if (response.Content != null)
            {
                Console.WriteLine(await response.Content.ReadAsStringAsync());
            }
            Console.WriteLine();
    
            return response;
        }
    }
}