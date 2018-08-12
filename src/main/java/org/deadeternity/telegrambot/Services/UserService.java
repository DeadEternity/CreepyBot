package org.deadeternity.telegrambot.Services;

import org.deadeternity.telegrambot.DAOs.UserDAO;
import org.deadeternity.telegrambot.Entities.CreepyPaste;
import org.deadeternity.telegrambot.Entities.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UserService {
    protected UserService(){}

    public static User getUserByMessage(Message message) {
        User user;
        if((user = UserDAO.getUserByID(message.getChatId().toString())) == null) {
            user = new User();
            user.setUid(message.getChatId().toString());
            user.setUsername(message.getChat().getUserName());
            UserDAO.saveUser(user);
        }
        return user;
    }

    public static void updateUserInformation(User user) {
        UserDAO.saveUser(user);
    }

}
