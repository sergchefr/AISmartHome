package ru.ifmo.telegram.scenary.ConsoleEmulator;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.ifmo.CLI.CLIclient;
import ru.ifmo.CLI.input.ComLine;
import ru.ifmo.CommandHandler.JsonPrinter;
import ru.ifmo.telegram.scenary.ScenaryElement;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class ConsoleEmulator implements ScenaryElement, ComLine {
    private TelegramClient telegramClient;
    private String api;
    private CLIclient clIclient;
    private BlockingDeque<String> answer;
    private long chatid;

    public ConsoleEmulator(String api, long chatid) {
        answer = new LinkedBlockingDeque<>();
        this.api = api;
        this.chatid = chatid;

        telegramClient = new OkHttpTelegramClient(api);

        try {
            clIclient = new CLIclient("C:\\Users\\Сергей\\IdeaProjects\\Console Commands\\src\\main\\resources\\commandVerifiers.json",this, new JsonPrinter());
            clIclient.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SendMessage message = SendMessage.builder().chatId(chatid).text("введите название команды").build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ScenaryElement getnext(Update update) {
        Message message = update.getMessage();
        String text = message.getText().toLowerCase(Locale.ROOT);
        answer.add(text);

        return this;
    }

    @Override
    public void print(String arg) throws IOException {
        SendMessage message = SendMessage.builder().chatId(chatid).text(arg).build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void println(String arg) throws IOException {
        SendMessage message = SendMessage.builder().chatId(chatid).text(arg).build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String read() throws IOException {
        try {
            return answer.takeFirst();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String read(String arg) throws IOException {
        System.out.println(arg+"   "+answer);
        SendMessage message = SendMessage.builder().chatId(chatid).text(arg).build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        try {
            return answer.takeFirst();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
