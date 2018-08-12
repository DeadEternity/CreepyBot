package org.deadeternity.telegrambot.Services;

import org.deadeternity.telegrambot.DAOs.CreepyPasteDAO;
import org.deadeternity.telegrambot.DAOs.UserDAO;
import org.deadeternity.telegrambot.Entities.CreepyPaste;
import org.deadeternity.telegrambot.Entities.User;

public class CreepyPasteService {
    protected CreepyPasteService() {}

    public static CreepyPaste tryToGetPaste(CreepyPaste creepyPaste) {
        CreepyPaste paste;
        if((paste = CreepyPasteDAO.getCreepyPasteByID(creepyPaste.getLink())) == null) {
            paste = creepyPaste;
            paste.postOnTelegraph();
            CreepyPasteDAO.savePaste(paste);
        }
        return paste;
    }
}
