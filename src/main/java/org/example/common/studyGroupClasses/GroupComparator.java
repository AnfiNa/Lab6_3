package org.example.common.studyGroupClasses;

import java.util.Comparator;
/**
 * This class provides a comparator for the StudyGroup class that compares two studyGroup
 * based on their natural order using the compareTo method of the StudyGroup class.
 */
public class GroupComparator implements Comparator<StudyGroup> {
    @Override
    public int compare(StudyGroup group1, StudyGroup group2) {
        if (group1 == null){
            if (group2 == null){
                return 0;
            } else{
                return -1;
            }
        }
        return group1.compareTo(group2);

    }
}
