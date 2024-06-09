package org.example.common.studyGroupClasses;

import org.example.common.exceptions.BuildObjectException;

import java.io.Serializable;
import java.util.Date;
/**
 * Class for Person org.example.common.objects
 */
public class Person implements Serializable {

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Date birthday; //Поле может быть null
    private EyeColor eyeColor; //Поле может быть null
    private HairColor hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null

    public Person() {

    }

    public Person(String name, EyeColor eyeColor, HairColor hairColor, Country nationality, Date birthday) throws BuildObjectException {
        if (name == null || nationality == null) {
            throw new NullPointerException();
        } else if (name.length() == 0) {
            throw new BuildObjectException("The input data is incorrect, the object cannot be created");
        } else {
            this.name = name;
            this.eyeColor = eyeColor;
            this.hairColor = hairColor;
            this.nationality = nationality;
            this.birthday = birthday;
        }
    }
    public Person(String name, Country nationality) throws BuildObjectException {
        if (name == null || nationality == null) {
            throw new NullPointerException();
        } else if (name.length() == 0) {
            throw new BuildObjectException("The input data is incorrect, the object cannot be created");
        } else {
            this.name = name;
            this.nationality = nationality;
        }
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setEyeColor(EyeColor eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setName(String name) {
        if (name.length() == 0) {
            this.name = name;
        }
    }

    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    @Override
    public String toString() {
        return "Имя: " + name +
                ", национальность: " + String.valueOf(nationality) +
                ", дата рождения: " + String.valueOf(birthday) +
                ", цвет волос: " + String.valueOf(hairColor) +
                ", цвет глаз: " + String.valueOf(eyeColor);
    }

    public String getName() {
        return name;
    }

    public Country getNationality() {
        return nationality;
    }

    public EyeColor getEyeColor() {
        return eyeColor;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    public Date getBirthday() {
        return birthday;
    }
}
