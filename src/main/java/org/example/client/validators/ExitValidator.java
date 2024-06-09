package org.example.client.validators;

import org.example.common.Requests.Request;
import org.example.common.exceptions.ExitException;

public class ExitValidator extends BaseValidator {

    public Request validate(String command, String[] args) {
        throw new ExitException();
    }
}
