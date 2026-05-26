package com.example.aspct.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String resourceNotFound(ResourceNotFoundException ex, Model model){
        model.addAttribute("notFoundMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String somethingWentWrongSomewhere(Exception ex, Model model){
        model.addAttribute("serverError", ex.getMessage());

        return "error/500";
    }


    @ExceptionHandler(InvalidDeadlineException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidDeadline(InvalidDeadlineException ex, Model model){
        model.addAttribute(ex.getFormAttributeName(), ex.getFormData()); //To use the previous data to rebuild the form after return to creation
        model.addAttribute("invalidDeadline", ex.getMessage()); //Uhhh... binding the error message to the create form when triggered

        return ex.getViewName();//The name of the create page to return to
    }
}
