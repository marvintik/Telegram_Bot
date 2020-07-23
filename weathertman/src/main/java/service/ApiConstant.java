package service;

public final class ApiConstant {

    public static final String CITY_REGEX = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$";
    public static final String GET_WEATHER_BY_CITY_URL = "https://api.weatherbit.io/v2.0/current?city=";
    public static final String GET_WEATHER = "https://api.weatherbit.io/v2.0/current?";
    public static final String GET_ICON = "https://www.weatherbit.io/static/img/icons/";

    public static final String API_KEY_PAROL = "&key=7c992eb9b9c6419c83940fea5909bcf7";

    public static final int OK_STATUS_CODE = 200;
    public static final int NOT_FOUND_STATUS_CODE  = 404;



    private ApiConstant(){

    }
}
