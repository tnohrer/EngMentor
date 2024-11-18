document.addEventListener('DOMContentLoaded', () => {
    const getWeatherBtn = document.getElementById('getWeather');

    if (getWeatherBtn) {
        getWeatherBtn.addEventListener('click', async () => {
            const city = document.getElementById('city').value;
            if (!city) {
                alert('Please enter a city name');
                return;
            }

            const url = `http://localhost:8080/weathers?city=${encodeURIComponent(city)}`;
            console.log('Fetching weather data from URL:', url);

            try {
                const response = await fetch(url, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include' // Include credentials if needed
                });
                console.log('Response status:', response.status);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                console.log('Response data:', data);
                displayWeather(data);
            } catch (error) {
                console.error('Error fetching weather data:', error);
                alert('Error fetching weather data');
            }
        });
    } else {
        console.error('Element with ID "getWeather" not found');
    }
});

function displayWeather(data) {
    const weatherInfo = document.getElementById('weatherInfo');
    weatherInfo.innerHTML = '';

    const currentHour = new Date().getHours();

    // Display current hour's weather at the top
    const currentWeather = data[currentHour];
    if (currentWeather && currentWeather.temperature !== undefined && currentWeather.humidity !== undefined) {
        const temperatureF = kelvinToFahrenheit(currentWeather.temperature);
        const currentWeatherElement = document.createElement('div');
        currentWeatherElement.classList.add('current-weather');
        currentWeatherElement.innerHTML = `
            <h2>Current Weather (${formatTime(currentHour)}:00)</h2>
            <p>Temperature: ${temperatureF}°F</p>
            <p>Humidity: ${currentWeather.humidity}%</p>
            <p>Condition: ${currentWeather.description}</p>
        `;
        weatherInfo.appendChild(currentWeatherElement);
    } else {
        console.error('Current weather data not available or missing temperature/humidity');
    }

    // Create a scrollable container for the remaining hours
    const scrollContainer = document.createElement('div');
    scrollContainer.classList.add('scroll-container');

    data.forEach((weather, index) => {
        if (index !== currentHour && weather.temperature !== undefined && weather.humidity !== undefined) {
            const temperatureF = kelvinToFahrenheit(weather.temperature);
            const weatherElement = document.createElement('div');
            weatherElement.classList.add('weather-item');
            weatherElement.innerHTML = `
                <h2>Weather (${formatTime(index)}:00)</h2>
                <p>Temperature: ${temperatureF}°F</p>
                <p>Humidity: ${weather.humidity}%</p>
                <p>Condition: ${weather.description}</p>
            `;
            scrollContainer.appendChild(weatherElement);
        }
    });

    weatherInfo.appendChild(scrollContainer);
}

function kelvinToFahrenheit(kelvin) {
    return ((kelvin - 273.15) * 9/5 + 32).toFixed(2);
}

function formatTime(hour) {
    return hour < 10 ? `0${hour}` : hour;
}