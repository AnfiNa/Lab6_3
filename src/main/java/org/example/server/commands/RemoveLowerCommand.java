package org.example.server.commands;

import org.example.common.studyGroupClasses.StudyGroup;
import org.example.server.CollectionManager;

/**
 * Класс, предоставляющий метод для добавления элемента в коллекцию.
 * Элемент может быть как добавлен пользователем вручную, так и считан из файла или из строки скрипта.
 */
public class RemoveLowerCommand extends BaseCommand {


    public RemoveLowerCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager);
    }

    /**
     * Метод, считывающий route и его в коллекци.
     *
     * @param commandParts массив, содержащий название аргументы команды
     */

    public String execute(String[] commandParts, StudyGroup group) {
        return manager.removeIfLower(group);
    }

}
