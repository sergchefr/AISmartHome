package ru.ifmo.telegram;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import ru.ifmo.telegram.scenary.Intro;

public class Main {
    public static void main(String[] args){

        String apikey = System.getenv("TG_API_KEY");
        TgBot bot = new TgBot(apikey);
        bot.setStandard_SE(new Intro(apikey));
        try (TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication()) {
            application.registerBot(apikey, bot);

            Thread.currentThread().join();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
