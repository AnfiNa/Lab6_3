package org.example.client.validators;

import org.example.common.Requests.Request;
import org.example.common.exceptions.*;
import org.example.common.studyGroupClasses.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

public class ReadValidator extends BaseValidator {

    protected boolean needParse = true;

    @Override
    public Request validate(String command, String[] args, boolean parse) {
        try {
            StudyGroup group = parse ? parseGroup(args[args.length - 1]) : readGroup();
            return super.validate(command, args, group);
        } catch (ValidationException | BuildObjectException | WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Метод для чтения маршрута из ввода пользователя
     * (нужно сначала ввести name и distance, затем будут запрошены остальные данные).
     *
     * @return прочитанный объект класса Route
     * @throws InvalidNameException    если имя маршрута некорректно
     * @throws WrongArgumentsException если введены некорректные аргументы
     */

    public StudyGroup readGroup() {
        Scanner scanner = new Scanner(System.in);
        try {

            String name = "";
            while (true) {
                try {
                    System.out.println("Enter name(not null!)(type: String) : ");
                    name = scanner.nextLine();
                    GroupDataValidator.checkName(name);
                    break;
                } catch (InvalidNameException e) {
                    System.out.println(e.getMessage());
                }
            }
            float coordX;
            while (true) {
                System.out.println("Enter coordinates_x(not null!) (type: float) : ");
                String x = scanner.nextLine();
                try {
                    coordX = Float.parseFloat(x);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("It's not float");
                }
            }
            float coordY;
            while (true) {
                System.out.println("Enter coordinates_y(not null!) (type: float) : ");
                String y = scanner.nextLine();
                try {
                    coordY = Float.parseFloat(y);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("It's not float");
                }
            }
            Coordinates coord = new Coordinates(coordX, coordY);
            int studentsCount;
            while (true) {
                System.out.println("Enter students count(not null!) (type: int > 0) : ");
                String y = scanner.nextLine();
                try {
                    studentsCount = Integer.parseInt(y);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("It's not int");
                }
            }
            long transferredStudents;
            while (true) {
                System.out.println("Enter transferred students (not null!) (type: long > 0) : ");
                String y = scanner.nextLine();
                try {
                    transferredStudents = Long.parseLong(y);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("It's not long");
                }
            }
            int averageMark;
            while (true) {
                System.out.println("Enter average mark(not null!) (type: int > 0) : ");
                String y = scanner.nextLine();
                try {
                    averageMark = Integer.parseInt(y);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("It's not int");
                }
            }


            String formOfEducation = "hgf";
            FormOfEducation education;
            while (!formOfEducation.equals("DISTANCE_EDUCATION") && !formOfEducation.equals("FULL_TIME_EDUCATION") && !formOfEducation.equals("EVENING_CLASSES")) {
                System.out.println("Enter form of education(not null!) (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES) : ");
                formOfEducation = scanner.nextLine();
            }
            education = FormOfEducation.valueOf(formOfEducation);

            String adminName = "";
            while (adminName.length() == 0) {
                System.out.println("Enter groupAdmin's name(not null!) (type: String) : ");
                adminName = scanner.nextLine();
            }

            String adminNationality = "hgf";
            Country country;
            while (true) {
                try {
                    System.out.println("Enter nationality(not null!) (USA, GERMANY, SPAIN, CHINA, JAPAN) : ");
                    adminNationality = scanner.nextLine();
                    country = Country.valueOf(adminNationality);
                    break;
                } catch (IllegalArgumentException e) {
                    System.err.println("Введите допустимое значение");
                }
            }


            Date adminBirthday = null;
            while (adminBirthday == null) {
                System.out.println("Enter birthday (type: date) : ");
                try {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String date = scanner.nextLine();
                    if (date.equals("")) {
                        break;
                    }
                    adminBirthday = df.parse(date);
                    System.out.println(adminBirthday.toString());
                } catch (ParseException e) {
                    adminBirthday = null;
                }
            }


            String eye = "hgf";
            EyeColor eyeColor;
            while (true) {
                try {
                    System.out.println("Enter eye color (RED, BLACK, ORANGE, WHITE :");
                    eye = scanner.nextLine();
                    if (eye.isEmpty()) {
                        eyeColor = null;
                        break;
                    }
                    eyeColor = EyeColor.valueOf(eye);
                    break;
                } catch (IllegalArgumentException e) {
                    System.err.println("Введите допустимое значение");
                }
            }


            String hair = "hgf";
            HairColor hairColor;
            while (true) {
                try {
                    System.out.println("Enter hair color (RED, BLACK, ORANGE, WHITE) : ");
                    hair = scanner.nextLine();
                    if (hair.isEmpty()) {
                        hairColor = null;
                        break;
                    }
                    hairColor = HairColor.valueOf(hair);
                    break;
                } catch (IllegalArgumentException e) {
                    System.err.println("Введите допустимое значение");
                }
            }

            Person admin = new Person(adminName, eyeColor, hairColor, country, adminBirthday);
            StudyGroup studyGroup = new StudyGroup(name, coord, studentsCount, transferredStudents, averageMark, education, admin);
            studyGroup.setId(0L);
            return studyGroup;
        } catch (NoSuchElementException | BuildObjectException e) {
            System.err.println("Выход из программы...");
            System.exit(130);
        }
        return null;
    }

    /**
     * Метод для чтения маршрута из строки, содержащей все необходимые поля маршрута.
     * Используется при чтении add и update команд, запущенные в execute_script.
     *
     * @param line строка, содержащая значения полей маршрута
     * @return прочитанный объект класса Route
     * @throws InvalidNameException    если имя маршрута некорректно
     * @throws WrongArgumentsException если введены некорректные аргументы
     */


    public StudyGroup parseGroup(String line) throws InvalidNameException, BuildObjectException, WrongArgumentsException, AbsentRequiredParametersException {
        String[] pairs = line.trim().replace('{', ' ').replace('}', ' ')
                .replace("\"", "").replace("'", "").split(",");
        StudyGroup group = new StudyGroup();
        Coordinates coordinates = new Coordinates();
        Person admin = new Person();
        ArrayList<String> requiredParams = new ArrayList<>(
                List.of("name, coordinatesX, coordinatesY, studentsCount, transferredStudents, averageMark, formOfEducation, adminName, adminNationality"
                        .split(", "))
        );
        int addFrom = 0;


        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length != 2) {
                throw new WrongArgumentsException("Неверное количество параметров, проверьте, что у всех переданных параметров непустое значение");
            }
            String key = keyValue[0].trim(), value = keyValue[1].trim();
            switch (key) {
                case "id":
                    group.setId(Long.parseLong(value));
                    break;
                case "name":
                    group.setName(GroupDataValidator.checkName(value));
                    break;
                case "coordinatesX":
                    coordinates.setX(Float.parseFloat(value));
                    break;
                case "coordinatesY":
                    coordinates.setY(Float.parseFloat(value));
                    break;
                case "studentsCount":
                    group.setStudentsCount(Integer.parseInt(value));
                    break;
                case "transferredStudents":
                    group.setTransferredStudents((long) GroupDataValidator.checkNotNegativeInt(value));
                    break;
                case "averageMark":
                    group.setAverageMark(GroupDataValidator.checkNotNegativeInt(value));
                    break;
                case "formOfEducation":
                    group.setFormOfEducation(FormOfEducation.valueOf(value));
                    break;
                case "adminName":
                    admin.setName(GroupDataValidator.checkName(value));
                    break;
                case "adminNationality":
                    admin.setNationality(Country.valueOf(value));
                    break;
                case "adminBirthday":
                    if (!value.isEmpty()) {
                        try {
                            admin.setBirthday(new SimpleDateFormat("MM/dd/yyyy").parse(value));
                        } catch (ParseException e) {
                            throw new WrongArgumentsException("Неверный формат даты: " + value);
                        }
                    }
                    break;
                case "eyeColor":
                    if (!value.isEmpty()) {
                        admin.setEyeColor(EyeColor.valueOf(value));
                    }
                    break;
                case "hairColor":
                    if (!value.isEmpty()) {
                        admin.setHairColor(HairColor.valueOf(value));
                    }
                    break;
                default:
                    throw new WrongArgumentsException("Лишнее поле: " + keyValue[0]);
            }
            requiredParams.remove(key);
        }
        if (!requiredParams.isEmpty()) {
            throw new AbsentRequiredParametersException("Не хватает обязательных параметров: " + requiredParams);
        }
        group.setCreationDate(ZonedDateTime.now());
        group.setGroupAdmin(admin);
        group.setCoordinates(coordinates);
        return group;
    }

    public boolean getNeedParse() {
        return needParse;
    }
}
