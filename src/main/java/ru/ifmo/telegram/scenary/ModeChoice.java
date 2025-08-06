package ru.ifmo.telegram.scenary;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.ifmo.telegram.scenary.ConsoleEmulator.ConsoleEmulator;

import java.util.Locale;

public class ModeChoice implements ScenaryElement{
    private TelegramClient telegramClient;
    private String api;
    private long chatid;

    public ModeChoice(String api, long chatid) {
        this.api = api;
        telegramClient = new OkHttpTelegramClient(api);
        SendMessage message = SendMessage.builder().chatId(chatid).text("выбор режима").build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("ошибка отправки сообщения в ModeChoice constructor");
            e.printStackTrace();
        }
        //TODO кнопки
    }

    @Override
    public ScenaryElement getnext(Update update) {
        Message message = update.getMessage();
        String text = message.getText().toLowerCase(Locale.ROOT);

        return new ConsoleEmulator(api, update.getMessage().getChatId());
        //TODO выбор режима
    }
}
