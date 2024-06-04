package tech.danielokoronkwo.workflexassessmentbackend.common.dtos;

import java.util.List;

public class SuccessResponseDto {
    private boolean status;
    private String message;
    private List<String> errors = null;
    private Object data;


    public SuccessResponseDto(boolean status, String message, List<String> errors, Object data) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
