package ru.ifmo.telegram;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.ifmo.telegram.scenary.ScenaryElement;

import java.util.HashMap;

public class TgBot implements LongPollingSingleThreadUpdateConsumer {
    private TelegramClient telegramClient;
    private HashMap <Long, ScenaryElement> users = new HashMap<>();
    private ScenaryElement standard_SE;

    public TgBot(String api) {
        telegramClient=new OkHttpTelegramClient(api);
        this.standard_SE = standard_SE;
    }

    @Override
    public void consume(Update update) {
        long chat_id;
        if (update.hasMessage()) {
            chat_id = update.getMessage().getChatId();
        }else if (update.hasCallbackQuery()){
            chat_id = update.getCallbackQuery().getMessage().getChatId();
        }else{
            throw new RuntimeException("непонято что прилетело");
        }

        if(users.containsKey(chat_id)){
            users.put(chat_id,users.get(chat_id).getnext(update));
        }else{
            users.put(chat_id, standard_SE.getnext(update));
        }




    }

        public void sendmessage(String msg, long chatid){
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

    public void setStandard_SE(ScenaryElement standard_SE) {
        this.standard_SE = standard_SE;
    }
}
