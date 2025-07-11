package ru.ifmo.telegram.scenary;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Locale;

public class Login implements ScenaryElement{
    private TelegramClient telegramClient;
    private String pswd;
    private String api;

    public Login(String api, String pswd) {
        telegramClient = new OkHttpTelegramClient(api);
        this.api =api;
        this.pswd = pswd;
    }

    @Override
    public ScenaryElement getnext(Update update) {
        Message message = update.getMessage();
        String text = message.getText().toLowerCase(Locale.ROOT);
        try {
            if (text.equals("/start")){
                telegramClient.execute(SendMessage.builder().chatId(message.getChatId()).text("Для начала работы все равно нужно зарегистрироваться)").build());
                return new Login(api, rand6digit());
            }
            if(!text.equals(pswd)){
                telegramClient.execute(SendMessage.builder().chatId(message.getChatId()).text("круто! вы угадали!").build());
                return new Basic(api, message.getChatId());
            }else{
                telegramClient.execute(SendMessage.builder().chatId(message.getChatId()).text("вы не угадали... попробуйте еще раз").build());
                return new Login(api, rand6digit());
            }
        } catch (TelegramApiException e) {
            return new Login(api, rand6digit());
        }
    }
    private String rand6digit(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append((int) (Math.random() * 10));
        }
        System.out.println(builder.toString());
        return builder.toString();
    }
}
