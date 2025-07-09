package ru.ifmo.telegram;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
    public static void main(String[] args){
        TgBot bot = new TgBot(System.getenv("TG_API_KEY"));
        try (TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication()) {
            application.registerBot(System.getenv("TG_API_KEY"), bot);

            Thread.currentThread().join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
