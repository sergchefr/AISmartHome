package ru.ifmo.telegram.scenary;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static java.lang.Math.toIntExact;

public class Acception implements ScenaryElement{
    String api;
    TelegramClient telegramClient;
    String needAcc;

    public Acception(String api, String needAcc, long chatid) {
        this.api = api;
        telegramClient = new OkHttpTelegramClient(api);
        this.needAcc = needAcc;

        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("подтвердить")
                                        .callbackData("accept")
                                        .build(),
                                InlineKeyboardButton.builder()
                                        .text("тут ошибка!")
                                        .callbackData("negotiate")
                                        .build()
                        )).build();
        SendMessage message = SendMessage.builder()
                .chatId(chatid)
                .text(needAcc)
                .replyMarkup(markup)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("ошибка отправки сообщения в basic constructor");
        }
    }

    @Override
    public ScenaryElement getnext(Update update) {
        if(update.hasCallbackQuery()) {
            long chatid = update.getCallbackQuery().getMessage().getChatId();

            if (update.getCallbackQuery().getData().equals("accept")) {
                int messageid = update.getCallbackQuery().getMessage().getMessageId();
                EditMessageText newMessage = EditMessageText.builder().messageId(toIntExact(messageid)).text(needAcc+"\n\n<b>подтверждено</b>").chatId(chatid).parseMode("html").build();
                try {
                    telegramClient.execute(newMessage);
                    return new AIinterpreter(api, chatid);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    return new AIinterpreter(api, chatid);
                }
            }else if (update.getCallbackQuery().getData().equals("negotiate")){
                int messageid = update.getCallbackQuery().getMessage().getMessageId();
                EditMessageText newMessage = EditMessageText.builder().messageId(toIntExact(messageid)).text(needAcc+"\n\n<b>не будет выполнено</b>").parseMode("html").chatId(chatid).build();
                try {
                    telegramClient.execute(newMessage);
                    return new AIinterpreter(api, chatid);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    return new AIinterpreter(api, chatid);
                }
            }else {
                EditMessageText newMessage = EditMessageText.builder().messageId(update.getCallbackQuery().getMessage().getMessageId()).text("магии не случилось").build();
                return new AIinterpreter(api, chatid);
            }

        } else if (update.hasMessage()) {
            long chatid = update.getMessage().getChatId();
            try {
                telegramClient.execute(SendMessage.builder().chatId(chatid).text("вы не выбрали опцию, команда не будет выполнена").build());
                return new AIinterpreter(api, chatid);
            } catch (TelegramApiException e) {
                return new AIinterpreter(api, chatid);
            }
        }else{
            throw new RuntimeException("ни то, ни се");
        }
    }
}