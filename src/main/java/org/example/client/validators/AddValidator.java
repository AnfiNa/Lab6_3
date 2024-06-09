package org.example.client.validators;

import org.example.common.Requests.Request;
import org.example.common.exceptions.WrongArgumentsException;

public class AddValidator extends ReadValidator {

    @Override
    public Request validate(String command, String[] args, boolean parse) {
        try {
            if (parse) {
                checkIfOneArgument(command, args);
            } else {
                checkIfNoArguments(command, args);
            }
            return super.validate(command, args, parse);
        } catch (WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
