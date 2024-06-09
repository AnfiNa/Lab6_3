package org.example.client;

import org.example.client.validators.*;
import org.example.common.Responses.Response;
import org.example.common.exceptions.ExitException;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.Requests.Request;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Client {

    private final Map<String, BaseValidator> validators;


    private int depth = 0;

    public Client() {
        validators = new HashMap<>() {
            {
                put("help", new NoArgumentsValidator());
                put("info", new NoArgumentsValidator());
                put("show", new NoArgumentsValidator());
                put("add", new AddValidator());
                put("update", new UpdateByIdValidator());
                put("remove_by_id", new OneIntArgValidator());
                put("clear", new NoArgumentsValidator());
                put("execute_script", new ExecuteScriptValidator());
                put("exit", new ExitValidator());
                put("add_if_max", new AddValidator());
                put("add_if_min", new AddValidator());
                put("remove_lower", new AddValidator());
                put("count_greater_than_distance", new OneIntArgValidator());
                put("count_less_than_transferred_students", new OneIntArgValidator());
                put("print_field_descending_students_count", new NoArgumentsValidator());
            }
        };
    }
    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            TCPManager.getInstance().tryConnection();
            System.out.println("Приветствую вас в программе для работы с коллекцией StudyGroup! Введите help для получения списка команд");
            while (true) {
                try {
                    Request request = lineToRequest(sc.nextLine());
                    handleRequest(request);
                } catch (ExitException e) {
                    System.out.println("Выход из программы...");
                    break;
                } catch (NoSuchElementException e) {
                    System.err.println("Достигнут конец ввода, завершение работы программы...");
                    System.exit(130);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Request lineToRequest(String line) throws ExitException, NoSuchElementException {
        if (line.isEmpty()) {
            return null;
        }

        ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(line.trim().split(" ")));

        String commandName = commandParts.get(0).toLowerCase();
        commandParts.remove(0);

        try {
            BaseValidator.checkIsValidCommand(commandName, validators.keySet());
            BaseValidator validator = validators.get(commandName);
            if (validator.getNeedParse()) {
                return validator.validate(commandName, commandParts.toArray(new String[0]), depth > 0);
            }
            return validator.validate(commandName, commandParts.toArray(new String[0]));
        } catch (UnknownCommandException e) {
            System.out.println("Неизвестная команда, напишите help для просмотра доступных");
        }
        return null;
    }

    public void handleRequest(Request request) {
        if (request == null) return;
        try {
            if (request.getCommand().equals("execute_script")) {
                handleExecuteScript(request);
            } else {
                executeCommand(request);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при отправке запроса: " + e.getMessage());
        }
    }

    public void handleExecuteScript(Request request) {
        String filename = request.getArgs()[0];
        try (FileReader reader = new FileReader(filename)) {
            Scanner fileScanner = new Scanner(reader);
            ++depth;
            while (fileScanner.hasNextLine()) {
                Request newRequest = lineToRequest(fileScanner.nextLine());
                handleRequest(newRequest);
            }
            --depth;
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }


    private void printResponse(Response response) throws IOException {
        // Display the response received from the main.server
        System.out.println(response.getOutput());
        System.out.println("-----------------------------------\n");
    }

    public void executeCommand(Request request) throws IOException {
        var response = TCPManager.getInstance().sendAndGetResponse(request);
        printResponse(response);
    }
}
