package org.example.common.studyGroupClasses;

import org.example.common.exceptions.BuildObjectException;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Main class for StudyGroup org.example.common.objects, stored in TreeSet collection
 */

public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer studentsCount; //Значение поля должно быть больше 0
    private Long transferredStudents; //Значение поля должно быть больше 0, Поле не может быть null
    private int averageMark; //Значение поля должно быть больше 0, Поле не может быть null
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Person groupAdmin; //Поле не может быть null

    public StudyGroup() {
        this.creationDate = ZonedDateTime.now();
    }

    public StudyGroup(String name, Coordinates coordinates, int studentsCount,
                      long transferredStudents, int averageMark, FormOfEducation formOfEducation,
                      Person groupAdmin) throws BuildObjectException {
        if (name == null || coordinates == null || formOfEducation == null || groupAdmin == null) {
            throw new NullPointerException();
        } else if ((int) studentsCount < 0 || (long) transferredStudents < 0 || averageMark < 0) {
            throw new BuildObjectException("The input data is incorrect, the object cannot be created");
        }
        this.name = name;
        this.creationDate = ZonedDateTime.now();
        this.averageMark = averageMark;
        this.groupAdmin = groupAdmin;
        this.coordinates = coordinates;
        this.formOfEducation = formOfEducation;
        this.studentsCount = studentsCount;
        this.transferredStudents = transferredStudents;
    }

    @Override
    public int compareTo(StudyGroup g) {
        if (g == null) {
            return 1;
        }
        int res = this.creationDate.compareTo(g.creationDate);
        if (res == 0) {
            res = this.id.compareTo(g.id);
        }
        return res;

    }

    @Override
    public String toString() {
        String result;
        result = "id: " + id +
                ", name: " + name +
                ", coordinates: " + coordinates.toString() +
                ", creationDate: " + creationDate.toString() +
                ", studentCount: " + studentsCount +
                ", transferredStudents: " + transferredStudents +
                ", averageMark: " + averageMark +
                ", formOfEducation: " + formOfEducation.toString() +
                ", groupAdmin: " + groupAdmin.toString();
        return result;
    }

    public void setCoordinates(Coordinates coordinates) throws NullPointerException {
        if (coordinates == null) {
            throw new NullPointerException();
        } else {
            this.coordinates = coordinates;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAverageMark(int averageMark) {
        if (averageMark > 0) {
            this.averageMark = averageMark;
        }
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public void setTransferredStudents(Long transferredStudents) {
        this.transferredStudents = transferredStudents;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getAverageMark() {
        return averageMark;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Long getId() {
        return id;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public Long getTransferredStudents() {
        return transferredStudents;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
