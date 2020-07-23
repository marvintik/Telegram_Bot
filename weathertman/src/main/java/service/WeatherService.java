package service;

import java.io.IOException;
import java.util.List;

public interface WeatherService {

    List<String> getByCityName(String city) throws IOException, InterruptedException;

    default void validCityName(String city){
//        if ( city.matches(CITY_REGEX) == false) {
//            throw new IncorrectCityName(String.format("Incorrect city name %s", city));
//        }
    }


}
