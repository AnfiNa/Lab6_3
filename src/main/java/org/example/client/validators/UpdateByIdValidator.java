package org.example.client.validators;

import org.example.common.Requests.Request;
import org.example.common.exceptions.WrongArgumentsException;

public class UpdateByIdValidator extends ReadValidator {
    @Override
    public Request validate(String command, String[] args, boolean parse) {
        try {
            if (parse) {
                checkIfTwoArguments(command, args);
            } else {
                checkIfOneArgument(command, args);
            }
            Long id = Long.parseLong(args[0]);
            return super.validate(command, args, parse);
        } catch (WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть целым числом");
            return null;
        }
    }
}
