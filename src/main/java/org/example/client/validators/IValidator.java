package org.example.client.validators;

import org.example.common.Requests.Request;

public interface IValidator {

    Request validate(String command, String[] args);
}
