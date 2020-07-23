package bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.api.objects.stickers.Sticker;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import service.WeatherService;
import service.impl.WeatherServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage());
      var user = update.getMessage().getFrom();
      var chatId = update.getMessage().getChatId().toString();
        System.out.println(user);
        var photo  = update.getMessage().getPhoto();
        var weatherService = new WeatherServiceImpl();
        var sticker = update.getMessage().getSticker();
        var contact = update.getMessage().getContact();
        var location = update.getMessage().getLocation();
        System.out.println(contact);
        System.out.println(location);
        if (location!=null) {
           var answerList = weatherService.getByLocation(location.getLatitude(),location.getLongitude());
            sendMsg(chatId, answerList.get(0));
            sendPhot(chatId, answerList.get(1));
        }

        if (update.getMessage().getText()!=null) {
            var text = update.getMessage().getText();
            if (text.equals("Привет")) {
                sendMsg(chatId, "Привет) Этот бот умеет отзеркаливать фото и стикеры, а также показывать погоду");
            } else if (text.equals("Погода")) {
                sendMsg(chatId, "Введите город");
            } else {
                var answerList = weatherService.getByCityName(text);
                sendMsg(chatId, answerList.get(0));
                sendPhot(chatId, answerList.get(1));}}


         if ( sticker != null) {sendStick(chatId, sticker.getFileId());}
        else if (photo != null) {
           var phot = photo.get(0).getFileId();
           sendPhot(chatId,phot);}
    }

    @SneakyThrows
    public synchronized void sendMsg(String chatId, String msg){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(msg);
        setButtons(sendMessage);
        execute(sendMessage);
    }

    @SneakyThrows
    public synchronized void sendStick(String chatId, String sticker){
        SendSticker sendSticker = new SendSticker();
        sendSticker.setSticker(sticker);
        sendSticker.setChatId(chatId);
        sendSticker(sendSticker);
    }

    @SneakyThrows
    public synchronized void sendPhot(String chatId, String photo){
        SendPhoto sendPhoto = new SendPhoto();
        if (photo.contains("https:")){
            sendPhoto.setPhoto(photo);
        } else sendPhoto.setPhoto(photo);
        sendPhoto.setChatId(chatId);
        sendPhoto(sendPhoto);
    }

    public synchronized void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();


        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Привет"));
        keyboardFirstRow.add(new KeyboardButton("Погода"));


        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Поделиться контактами").setRequestContact(true));
        keyboardSecondRow.add(new KeyboardButton("Поделиться местоположением").setRequestLocation(true));


        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    @Override
    public String getBotUsername() {
        return "Marvint_bot";
    }

    @Override
    public String getBotToken() {
        return "768271160:AAGWNAvFhDgE2MIukQYZknb7z_7901gjQO8";
    }
}
