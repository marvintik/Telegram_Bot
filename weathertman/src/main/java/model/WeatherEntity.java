package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherEntity {

    private String city_name;
    private String country_code;
    private double temp;
    private Weather weather;


}
