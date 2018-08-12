package org.deadeternity.telegrambot.telegram;

import org.deadeternity.telegrambot.Entities.CreepyPaste;
import org.deadeternity.telegrambot.Entities.User;
import org.deadeternity.telegrambot.Services.CreepyPasteService;
import org.deadeternity.telegrambot.Services.UserService;
import org.deadeternity.telegrambot.parsers.MrakopediaParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CreepyBot extends TelegramLongPollingBot{
    private String token;
    private String botName;

    public CreepyBot() {
        Properties properties = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            properties.load(input);
            this.token = properties.getProperty("botapikey");
            this.botName = properties.getProperty("botname");

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onUpdateReceived(Update update) {
        SendMessage sender = new SendMessage();
        Message message;
        if(update.hasMessage() && (message = update.getMessage()).hasText()) {
            System.out.println(message.getText());

            User user = UserService.getUserByMessage(message);

            if(message.getText().equals("/random")) {
                CreepyPaste paste;
                while(true) {
                    paste = MrakopediaParser.getRandomPasteText();

                    CreepyPasteService.tryToGetPaste(paste);
                    if (!user.getReadedPaste().contains(paste)) {
                        user.addReadedPaste(paste);
                        UserService.updateUserInformation(user);
                        break;
                    } else {
                        System.out.println("already readed");
                    }

                }

                sender = new SendMessage();
                sender.setChatId(user.getUid());
                sender.enableHtml(true);
                sender.setText(paste.getMessageView());

                try {
                    execute(sender);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
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
