public with sharing class WeatherService {
    @AuraEnabled(cacheable=true)
    public static String getWeather(String city) {
        Http http = new Http();
        HttpRequest request = new HttpRequest();
        request.setEndpoint('http://localhost:8080/weathers?city=' + city);
        request.setMethod('GET');
        
        // Add logging
        System.debug('Making HTTP callout to: ' + request.getEndpoint());
        
        HttpResponse response;
        try {
            response = http.send(request);
            System.debug('HTTP Response: ' + response.getBody());
            return response.getBody();
        } catch (Exception e) {
            System.debug('HTTP Callout failed: ' + e.getMessage());
            throw new AuraHandledException('HTTP Callout failed: ' + e.getMessage());
        }
    }
}