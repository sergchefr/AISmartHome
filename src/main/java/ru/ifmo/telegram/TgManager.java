package ru.ifmo.telegram;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.util.ArrayList;

public class TgManager  implements TgController{
    private String apikey;
    private TgBot bot;

    public TgManager(String apikey) {
        apikey = System.getenv("TG_API_KEY");
        bot = new TgBot(System.getenv("TG_API_KEY"));
        try (TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication()) {
            application.registerBot(System.getenv("TG_API_KEY"), bot);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(int chatID, String message) {
        bot.sendmsg(message, chatID);
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
