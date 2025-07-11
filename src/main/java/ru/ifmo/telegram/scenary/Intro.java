package ru.ifmo.telegram.scenary;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Locale;

public class Intro implements ScenaryElement{
    private TelegramClient telegramClient;
    private String api;

    public Intro(String api) {
        this.telegramClient = new OkHttpTelegramClient(api);
        this.api = api;
    }

    @Override
    public ScenaryElement getnext(Update update) {
        Message message =update.getMessage();
        if (message.hasText()){
            String text = message.getText().toLowerCase(Locale.ROOT);
            try {
                telegramClient.execute(SendMessage.builder().chatId(message.getChatId()).text("привет! для начала работы необходимо зарегистрироваться. Для этого введите код, который указан в консолт").build());
                return new Login(api, rand6digit());
            } catch (TelegramApiException e) {
                return new Intro(api);
            }
        }
        return new Intro(api);
    }

    private String rand6digit(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append((int) (Math.random() * 10));
        }
        System.out.println(builder);
        return builder.toString();
    }
}
