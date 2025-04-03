import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherDetails {

    public static String getWeatherInfo(String city) throws IOException, InterruptedException {
        String apiKey = "d012fe60b0a968b0e********"; //cOLLECT YOUR ORIGINAL API KEY FIRST
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject json = new JSONObject(response.body());

        if (json.has("main")) {
            JSONObject main = json.getJSONObject("main");
            double temp = main.getDouble("temp") - 273.15; // Kelvin to Celsius er formula
            int humidity = main.getInt("humidity");
            int pressure = main.getInt("pressure");

            JSONArray weatherArray = json.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);

            String description = weather.getString("description");
            String clouds = weather.getString("main");
            String cityName = json.optString("name", city);

            return "📍 City: " + cityName + "\n" +
                    "🌡 Temperature: " + String.format("%.2f", temp) + " °C\n" +
                    "💧 Humidity: " + humidity + "%\n" +
                    "☁️ Weather: " + clouds + " (" + description + ")\n" +
                    "⚡ Pressure: " + pressure + " hPa";
        } else {
            return "❌ Not found";
        }
    }
}
