package org.example.common.studyGroupClasses;

import java.util.Comparator;

public class StudyGroupStudentCountComparator implements Comparator<StudyGroup> {

    @Override
    public int compare(StudyGroup o1, StudyGroup o2) {
        return (int) (o2.getStudentsCount() - o1.getStudentsCount());
    }
}
