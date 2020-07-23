package service.impl;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import model.WeatherEntity;
import org.springframework.stereotype.Service;
import service.WeatherService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static service.ApiConstant.*;

@Service
public class WeatherServiceImpl implements WeatherService {

   // @Value("${api.key}")
    private String apiKey = "&key=7c992eb9b9c6419c83940fea5909bcf7";
    private String lang = "ru";
    private String answer;
    private String icon;

    @SneakyThrows
    public List<String> getByCityName(String city) throws IOException, InterruptedException {
        validCityName(city);

        var gson = new Gson();

        var httpClient = HttpClient.newBuilder().build();

        var request = HttpRequest.newBuilder().GET().uri(URI.create(GET_WEATHER_BY_CITY_URL + city + apiKey +"&lang=" + lang)).build();

        var  response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

          WeatherResponse weathers = gson.fromJson(response.body(), WeatherResponse.class);



         if (weathers==null) {
             answer = "Город с названием " + city + " не найден";
             icon = "https://99px.ru/sstorage/86/2016/02/mid_10484_4446.jpg";
         } else {  WeatherEntity weatherEntity = weathers.data[0];
             answer = "Город: " + city + ", Код страны:" + weatherEntity.getCountry_code() + ", Температура: " + weatherEntity.getTemp() + "C, " + weatherEntity.getWeather().getDescription();
         icon =  GET_ICON+ weatherEntity.getWeather().getIcon() + ".png";
         }
        var answerList = List.of(answer, icon);

        return answerList;
    }

    @SneakyThrows
    public List<String> getByLocation(Float lat, Float lon) throws IOException, InterruptedException {

        var gson = new Gson();

        var httpClient = HttpClient.newBuilder().build();

        var request = HttpRequest.newBuilder().GET().uri(URI.create(GET_WEATHER + "lat=" +lat+ "&lon=" +lon + apiKey +"&lang=" + lang)).build();

        var  response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        WeatherResponse weathers = gson.fromJson(response.body(), WeatherResponse.class);
         WeatherEntity weatherEntity = weathers.data[0];
        String answer = "Город: " + weatherEntity.getCity_name() + ", Код страны:" + weatherEntity.getCountry_code() + ", Температура: " + weatherEntity.getTemp() + "C, " + weatherEntity.getWeather().getDescription();
        icon =  GET_ICON+ weatherEntity.getWeather().getIcon() + ".png";
        var answerList = List.of(answer, icon);

        return answerList;

    }


    class WeatherResponse{
        WeatherEntity[] data;
    }
}
