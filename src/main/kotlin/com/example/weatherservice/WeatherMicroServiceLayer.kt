import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class WeatherService(
    private val restTemplate: RestTemplate
) {
    val apiKey = "294453e70380ebba19aa7a1e8db77e4b"
    val baseUrl = "https://api.openweathermap.org/data/3.0/onecall"


}