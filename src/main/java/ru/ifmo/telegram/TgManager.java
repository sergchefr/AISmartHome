package ru.ifmo.telegram;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import ru.ifmo.telegram.scenary.Intro;

import java.util.ArrayList;

public class TgManager  implements TgController{
    private String apikey;
    private TgBot bot;

    public TgManager(String apikey) {
        this.apikey = apikey;
        bot = new TgBot(apikey);
        bot.setStandard_SE(new Intro(apikey));
    }

    @Override
    public void sendMessage(int userid, String message) {
    }

    @Override
    public void start() {
        try (TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication()) {
            application.registerBot(apikey, bot);

            System.out.println("бот запущен");
            Thread.currentThread().join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean getAcception(int userid, String Message) {
        return false;
    }

    @Override
    public boolean getAcception(int userid, ArrayList<String> messages) {
        return false;
    }
}
