package org.example.common.studyGroupClasses;

import org.example.common.exceptions.InvalidNameException;
import org.example.common.exceptions.WrongArgumentsException;

public class GroupDataValidator {

    /**
     * Проверка валидности имени (не пустое).
     *
     * @param name имя
     * @return валидное имя
     * @throws InvalidNameException исключение, если имя невалидное
     */
    public static String checkName(String name) throws InvalidNameException {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException();
        }
        return name;
    }

    public static int checkNotNegativeInt(String studentsCount) throws WrongArgumentsException {
        try {

            int sc = Integer.parseInt(studentsCount);
            if (sc < 0) {
                throw new WrongArgumentsException("The number of students must be greater than 0");
            }
            return sc;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The number of students must be an integer");
        }
    }
}
