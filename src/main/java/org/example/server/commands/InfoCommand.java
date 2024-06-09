package org.example.server.commands;

import org.example.common.studyGroupClasses.StudyGroup;
import org.example.server.CollectionManager;

/**
 * Класс, реализующий команду info, выводящую информацию о коллекции (тип, дата инициализации, количество элементов).
 */

public class InfoCommand extends BaseCommand {


    public InfoCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager);
    }

    /**
     * Метод, реализующий логику команды info
     *
     * @param commandParts название и аргументы команды
     */
    public String execute(String[] commandParts, StudyGroup group) {
        return "Тип коллекции: " + manager.getCollectionClassName() + "\n" +
                "Дата инициализации: " + manager.getInitDate() + "\n" +
                "Количество элементов: " + manager.getCollectionSize();
    }
}
