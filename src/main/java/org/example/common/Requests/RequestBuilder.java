package org.example.common.Requests;

import org.example.common.studyGroupClasses.StudyGroup;

public class RequestBuilder {

    Request request;
    public RequestBuilder() {
        request = new Request();
    }

    public RequestBuilder setCommand(String command) {
        this.request.setCommand(command);
        return this;
    }

    public RequestBuilder setArgs(String[] args) {
        this.request.setArgs(args);
        return this;
    }

    public RequestBuilder setStudyGroup(StudyGroup studyGroup) {
        this.request.setStudyGroup(studyGroup);
        return this;
    }

    public Request build() {
        return request;
    }
}
