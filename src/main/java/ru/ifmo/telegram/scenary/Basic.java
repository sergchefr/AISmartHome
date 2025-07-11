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
import ru.ifmo.console.CommandManagerImpl;
import ru.ifmo.console.Icommand;
import ru.ifmo.neuro.NeuroController;
import ru.ifmo.neuro.RecognitionException;

import static java.lang.Math.toIntExact;

public class Basic implements ScenaryElement{
    private TelegramClient telegramClient;
    private String api;
    long chatid;

    public Basic(String api, long chatid) {
        telegramClient = new OkHttpTelegramClient(api);
        this.chatid = chatid;
        this.api=api;
    }

    @Override
    public ScenaryElement getnext(Update update) {
        if (update.hasMessage()&&update.getMessage().hasText()){
            try {
                String command = NeuroController
                        .getInstance()
                        .getCommand(update.getMessage().getText());

                String interpretation = CommandManagerImpl.getInstance().getInterpretation(command);
                //TODO необходима поддержка сценариев
                String text = "Mistral предлагает:\n"+interpretation;
                return new Acception(api,text, chatid);

            } catch (RecognitionException e) {

                try {
                    SendMessage message = SendMessage.builder().chatId(chatid).text("Mistral не смог распознать команду").build();
                    telegramClient.execute(message);
                    return new Basic(api,chatid);
                } catch (TelegramApiException ex) {
                    ex.printStackTrace();
                    return new Basic(api,chatid);
                }

            }
        }else{
            return new Basic(api,chatid);
        }
    }
}
