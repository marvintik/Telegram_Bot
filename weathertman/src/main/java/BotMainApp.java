import bot.Bot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;


public class BotMainApp {

    @SneakyThrows
    public static void main(String[] args){
             ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        telegramBotsApi.registerBot(new Bot());
    }
}



