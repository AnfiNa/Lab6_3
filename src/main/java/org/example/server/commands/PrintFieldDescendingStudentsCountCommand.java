package org.example.server.commands;

import org.example.common.studyGroupClasses.StudyGroup;
import org.example.server.CollectionManager;

/**
 * Класс, реализующий команду print_field_descending_distance,
 * которая выводит значения поля distance элементов коллекции в порядке убывания.
 */

public class PrintFieldDescendingStudentsCountCommand extends BaseCommand {

    public PrintFieldDescendingStudentsCountCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager);
    }

    /**
     * Метод, реализующий логику  команды print_field_descending_distance.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public String execute(String[] commandParts, StudyGroup group) {
        return manager.printDescendingStudentsCount();
    }
}
