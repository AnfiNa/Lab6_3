package org.example.common.Responses;

import java.io.Serializable;

public class Response implements Serializable {

    String output;

    public Response(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Responce{" +
                "output='" + output + '\'' +
                '}';
    }
}
