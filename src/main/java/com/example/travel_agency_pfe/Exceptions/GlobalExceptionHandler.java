package com.example.travel_agency_pfe.Exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(Throwable.class)
//    public ModelAndView handleException(Throwable ex) {
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("message", ex.getMessage());
//        return modelAndView;
//    }
}
