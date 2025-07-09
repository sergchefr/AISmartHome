package ru.ifmo.telegram;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TgBot implements LongPollingSingleThreadUpdateConsumer {
    private TelegramClient telegramClient;

    public TgBot(String api) {
        telegramClient=new OkHttpTelegramClient(api);
    }

    @Override
    public void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String message_text = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();

        if (message_text.equals("/start")){
            sendmsg("Привет! Я AI помощник для умного дома. Для начала работы войдите в систему, введя код в консоли:"+ code, chat_id);
        }

    }

        public void sendmsg(String msg, long chatid){
            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chatid)
                    .text(msg)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
}
