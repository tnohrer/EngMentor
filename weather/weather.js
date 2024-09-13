import { LightningElement, track } from 'lwc';
import getWeather from '@salesforce/apex/WeatherService.getWeather';

export default class WeatherComponent extends LightningElement {
    @track city = '';
    @track weatherData = null;

    handleCityChange(event) {
        this.city = event.target.value;
    }

    async handleGetWeather() {
        try {
            const data = await getWeather({ city: this.city });
            console.log('Raw data from Apex:', data);
            this.weatherData = JSON.parse(data).map((item, index) => ({
                id: index,
                temperature: item.temp,
                humidity: item.humidity,
                description: item.description
            }));
            console.log('Parsed weather data:', this.weatherData);
        } catch (error) {
            console.error('Error fetching weather data:', error);
            this.weatherData = [{ id: 0, temperature: 'N/A', humidity: 'N/A', description: 'Error fetching weather data.' }];
        }
    }
}