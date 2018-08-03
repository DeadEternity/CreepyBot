package org.deadeternity.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class CreepyBot extends TelegramLongPollingBot{
    private final String token = "593243113:AAExSQRH5_hsfctzQOuJAVSKLvmgvEbAbxs";
    private final String botName = "CreepyPasteTestBot";

    public void onUpdateReceived(Update update) {
        //TODO: command parser method
        if(update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sender = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .enableMarkdown(true)
                    .setText("test");
            try {
                execute(sender);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

}
