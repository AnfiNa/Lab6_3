package org.example.server.commands;

import org.example.common.studyGroupClasses.StudyGroup;
import org.example.server.CollectionManager;

/**
 * Класс, предназначенный для удаления элемента коллекции по его id.
 */

public class RemoveByIdCommand extends BaseCommand {

    public RemoveByIdCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager);
    }

    /**
     * Метод, удаляющий элемент коллекции по его id.
     *
     * @param commandParts массив, содержащий название аргументы команды
     */

    public String execute(String[] commandParts, StudyGroup group) {
        long id = Long.parseLong(commandParts[0]);
        return manager.removeById(id);
    }
}
