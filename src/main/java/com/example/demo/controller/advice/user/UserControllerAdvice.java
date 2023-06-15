package com.example.demo.controller.advice.user;

import com.example.demo.exception.user.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class UserControllerAdvice {

    public static final String ERROR_PAGE = "thymeleaf/error/error-page";
    public static final String ERROR_MESSAGE = "errorMessage";

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView notFoundUser(UserNotFoundException userNotFoundException) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(ERROR_MESSAGE, userNotFoundException.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ModelAndView invalidPassword(InvalidPasswordException invalidPasswordException) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(ERROR_MESSAGE, invalidPasswordException.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ModelAndView confirmPassword(PasswordMismatchException passwordMismatchException) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(ERROR_MESSAGE, passwordMismatchException.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(InvalidUserIdException.class)
    public ModelAndView invalidUserId(InvalidUserIdException invalidUserIdException) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(ERROR_MESSAGE, invalidUserIdException.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(InvalidNameException.class)
    public ModelAndView invalidUserId(InvalidNameException invalidNameException) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(ERROR_MESSAGE, invalidNameException.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ModelAndView invalidUserId(InvalidEmailException invalidEmailException) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(ERROR_MESSAGE, invalidEmailException.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(InvalidPhoneException.class)
    public ModelAndView invalidUserId(InvalidPhoneException invalidPhoneException) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(ERROR_MESSAGE, invalidPhoneException.getMessage());

        return modelAndView;
    }
}
