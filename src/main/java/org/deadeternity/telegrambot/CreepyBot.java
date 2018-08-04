package org.deadeternity.telegrambot;

import org.deadeternity.telegrambot.mrakopedia.MrakopediaParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class CreepyBot extends TelegramLongPollingBot{
    private final String token = "593243113:AAExSQRH5_hsfctzQOuJAVSKLvmgvEbAbxs";
    private final String botName = "CreepyPasteTestBot";

    public void onUpdateReceived(Update update) {
        //TODO: command parser method
        SendMessage sender = new SendMessage();
        Message message;
        if(update.hasMessage() && (message = update.getMessage()).hasText()) {
            System.out.println(message.getText());

            if(message.getText().equals("/random")) {
                CreepyPaste paste = MrakopediaParser.getRandomPasteText();
                paste.postOnTelegraph();
                sender = new SendMessage();
                sender.setChatId(message.getChatId());
                sender.enableHtml(true);
                sender.setText(paste.getMessageView());


            }
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
