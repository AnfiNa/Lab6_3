package org.example.client.validators;

import org.example.common.Requests.Request;
import org.example.common.Requests.RequestBuilder;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.exceptions.WrongArgumentsException;
import org.example.common.studyGroupClasses.StudyGroup;

import java.util.Set;

public abstract class BaseValidator implements IValidator {

    protected boolean needParse = false;

    public Request validate(String command, String[] args) {
        return new RequestBuilder().setCommand(command).setArgs(args).build();
    }

    public Request validate(String command, String[] args, StudyGroup group) {
        return new RequestBuilder().setCommand(command).setArgs(args).setStudyGroup(group).build();
    }
     public Request validate(String command, String[] args, boolean parse) {
        return new RequestBuilder().setCommand(command).setArgs(args).build();
    }

    /**
     * Проверка на валидность введенной команды.
     *
     * @param command введенная команда
     * @param validCommands список всех доступных команд, кроме execute_script и exit
     * @throws UnknownCommandException исключение, если команда не найдена
     */
    public static void checkIsValidCommand(String command, Set<String> validCommands) throws UnknownCommandException {
        if (!command.equals("execute_script") && !validCommands.contains(command.toLowerCase())) {
            throw new UnknownCommandException(command);
        }
    }

    /**
     * Проверка на отсутствие аргументов команды.
     * @param commandParts массив, содержащий команду и аргументы
     * @throws WrongArgumentsException исключение, если количество аргументов неверное
     */
    public static void checkIfNoArguments(String commandName, String[] commandParts) throws WrongArgumentsException {
        if (commandParts.length != 0) {
            throw new WrongArgumentsException("Команда %s не принимает аргументы".formatted(commandName));
        }
    }

    /**
     * Проверка на наличие ровно 1 аргумента команды.
     * @param commandParts массив, содержащий команду и аргументы
     * @throws WrongArgumentsException исключение, если количество аргументов неверное
     */
    public static void checkIfOneArgument(String commandName, String[] commandParts) throws WrongArgumentsException {
        if (commandParts.length != 1) {
            throw new WrongArgumentsException("Команда %s принимает ровно 1 аргумент".formatted(commandName));
        }
    }

    /**
     * Проверка на наличие ровно 2 аргументов команды.
     * @param commandParts массив, содержащий команду и аргументы
     * @throws WrongArgumentsException исключение, если количество аргументов неверное
     */
    public static void checkIfTwoArguments(String commandName, String[] commandParts) throws WrongArgumentsException {
        if (commandParts.length != 2) {
            throw new WrongArgumentsException("Команда %s принимает ровно 2 аргумента".formatted(commandName));
        }
    }




    public boolean getNeedParse() {
        return needParse;
    }
}
