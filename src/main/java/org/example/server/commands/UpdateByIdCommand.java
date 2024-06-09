package org.example.server.commands;

import org.example.common.studyGroupClasses.StudyGroup;
import org.example.server.CollectionManager;

/**
 * Класс, объекты которого обновляют элемент коллекции по его id, заменяя его другим элементом.
 */

public class UpdateByIdCommand extends BaseCommand {

    public UpdateByIdCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager);
    }

    /**
     * Метод, реализующий логику команды update.
     *
     * @param commandParts массив, содержащий название аргументы команды
     */
    public String execute(String[] commandParts, StudyGroup group) {
        long id = Long.parseLong(commandParts[0]);
        boolean isFound = manager.findElementById(id);
        if (!isFound) {
            return "Элемент с id " + id + " не найден. Обновление не выполнено.";
        }
        group.setId(id);
        return manager.updateElementById(id, group);

    }
}
