package org.example.client.validators;

import org.example.common.Requests.Request;
import org.example.common.Requests.RequestBuilder;
import org.example.common.exceptions.WrongArgumentsException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ExecuteScriptValidator extends BaseValidator {

    private Set<String> scriptFilenames;
    public Request validate(String commandName, String[] args) {
        scriptFilenames = new HashSet<>();
        try {
            checkIfOneArgument(commandName, args);
            String filename = args[0];
            if (!checkIfNoRecursion(filename)) {
                return null;
            }
            return super.validate(commandName, args);
//            return new Request(commandName, args, null);
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public boolean checkIfNoRecursion(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("execute_script")) {
                    String[] parts = line.split(" ");
                    if (parts.length > 1) {
                        String scriptFilename = parts[1];
                        if (scriptFilenames.contains(scriptFilename)) {
                            System.err.printf("Обнаружена рекурсия в файле %s, проверьте скрипт на вызов самого себя\n", filename);
                            return false;
                        }
                        scriptFilenames.add(scriptFilename);
                        return checkIfNoRecursion(scriptFilename);
                    }
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла " + filename);
            return false;

        }
    }
}