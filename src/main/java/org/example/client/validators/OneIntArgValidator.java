package org.example.client.validators;

import org.example.common.Requests.Request;
import org.example.common.exceptions.WrongArgumentsException;

public class OneIntArgValidator extends BaseValidator {

    public Request validate(String command, String[] args) {
        try {
            checkIfOneArgument(command, args);
            int firstArg = Integer.parseInt(args[0]);
            return super.validate(command, args);
        } catch (WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
