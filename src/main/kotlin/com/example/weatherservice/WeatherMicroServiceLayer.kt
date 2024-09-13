import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class WeatherService(
    private val restTemplate: RestTemplate
) {
    val apiKey = "ENTER YOUR KEY"
    val baseUrl = "https://api.openweathermap.org/data/3.0/onecall"


}
