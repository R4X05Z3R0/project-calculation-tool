package com.example.aspct.exceptions;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateKey(DuplicateKeyException ex, Model model){
        ErrorDTO errorDTO = new ErrorDTO(
                HttpStatus.CONFLICT.value(), "Duplicate Entry",
                "An item with this identical name or unique identifier already exists. Please try a different value."
        );

        return "error/all-error";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTypeMismatchException(MethodArgumentTypeMismatchException ex, Model model) {

        ErrorDTO errorDetails = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request / Invalid Parameter",
                "The URL you requested contains an invalid format or parameter. Please enter a valid web address."
        );

        model.addAttribute("errorDetails", errorDetails);
        return "error/all-error";
    }

}
