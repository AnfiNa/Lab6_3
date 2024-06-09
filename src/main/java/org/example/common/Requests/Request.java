package org.example.common.Requests;

import org.example.common.studyGroupClasses.StudyGroup;

import java.io.Serializable;
import java.util.Arrays;

public class Request implements Serializable {

    private String command;

    private String[] args;

    private StudyGroup studyGroup;

//    public Request(String command, String[] args, StudyGroup studyGroup) {
//        this.command = command;
//        this.args = args;
//        this.studyGroup = studyGroup;
//    }

    Request(){

    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", args=" + Arrays.toString(args) +
                ", studyGroup=" + studyGroup +
                '}';
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }
}
