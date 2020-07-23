package exceptions;

public class IncorrectCityName extends RuntimeException {

    public IncorrectCityName(String msg){
        super(msg);
    }
}
