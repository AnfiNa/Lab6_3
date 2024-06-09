package org.example.server.serverModules;

import org.example.common.studyGroupClasses.StudyGroup;
import org.example.server.CollectionManager;
import org.example.server.commands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, запускающий команды, введенные пользователем
 */

public class CommandExecutionModule {

    /**
     * Map, содержащий все команды, доступные пользователю
     */
    private final Map<String, BaseCommand> commands;

    public CommandExecutionModule(CollectionManager manager) {
        commands = new HashMap<>() {
            {
                put("info", new InfoCommand("info", "вывести информацию о коллекции", manager));
                put("show", new ShowCommand("show", "вывести все элементы коллекции", manager));
                put("add", new AddCommand("add {element}", "добавить новый элемент в коллекцию", manager));
                put("update", new UpdateByIdCommand("update id {element}", "обновить значение элемента коллекции, id которого равен заданному", manager));
                put("remove_by_id", new RemoveByIdCommand("remove_by_id id", "удалить элемент из коллекции по его id", manager));
                put("clear", new ClearCommand("clear", "очистить коллекцию", manager));
                put("execute_script", new ExecuteScriptCommand("execute_script", "считать и исполнить скрипт из указанного файла", manager));
                put("exit", new ExitCommand("exit", "Выйти из программы (без сохранения коллекции в файл)", manager));
                put("reorder", new AddIfMinCommand("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции", manager));
                put("sort", new AddIfMinCommand("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", manager));
                put("remove_lower", new RemoveLowerCommand("remove_lower {element}", "удалить из коллекции все элементы, меньшие, чем заданный", manager));
                put("min_by_id", new MinByIdCommand("min_by_id", "вывести любой объект из коллекции, значение поля id которого является минимальным", manager));
                put("count_less_than_transferred_students", new CountLessThanTransferredStudentsCommand("count_less_than_transferred_students", "вывести количество элементов, значение поля transferredStudents которых меньше заданного", manager));
                put("print_field_descending_students_count ", new PrintFieldDescendingStudentsCountCommand("print_field_descending_distance", "вывести значения поля studentsCount всех элементов в порядке убывания", manager));
            }
        };
        HelpCommand help = new HelpCommand("help", "вывести справку по доступным командам", manager);
        help.setCommands(new ArrayList<BaseCommand>(commands.values()));
        commands.put("help", help);
    }

    /**
     * Метод, обрабатывающий команду, введенную пользователем
     *
     * @return true, если команда была успешно обработана, иначе false
     */

    public String processCommand(String commandName, String[] args, StudyGroup group) {

        BaseCommand command = commands.get(commandName);
        return command.execute(args, group);
    }
}
