package org.deadeternity.telegrambot;

import org.deadeternity.telegrambot.Entities.CreepyPaste;
import org.deadeternity.telegrambot.telegram.CreepyBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi api = new TelegramBotsApi();

        CreepyPaste paste = new CreepyPaste();
        paste.setLink("qwe");
        CreepyPaste paste1 = new CreepyPaste();
        paste1.setLink("qwe");
        System.out.println(paste.equals(paste1));
        List<CreepyPaste> pastes = new ArrayList<CreepyPaste>();
        pastes.add(paste);
        System.out.print(pastes.contains(paste1));


        try {
            api.registerBot(new CreepyBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
