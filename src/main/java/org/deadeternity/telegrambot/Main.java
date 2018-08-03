package org.deadeternity.telegrambot;

import org.deadeternity.telegrambot.mrakopedia.MrakopediaParser;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {

        CreepyPaste p =  MrakopediaParser.getRandomPasteText();

        System.out.println(p.postOnTelegraph());



        ApiContextInitializer.init();

        TelegramBotsApi api = new TelegramBotsApi();

        try {
            api.registerBot(new CreepyBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
