package com.example.aspct.exceptions;

public class InvalidDeadlineException extends RuntimeException {
    private final Object formData;
    private final String formAttributeName;
    private final String viewName;


    public InvalidDeadlineException(String message, Object formData, String formAttributeName, String viewName) {
        super(message);
        this.formData = formData;
        this.formAttributeName = formAttributeName;
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public Object getFormData() {
        return formData;
    }

    public String getFormAttributeName() {
        return formAttributeName;
    }
}
