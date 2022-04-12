package com.zj.examsystem.utils;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ControllerInitBinderHandler {
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
        //            @Override
        //            public void setAsText(String text) throws IllegalArgumentException {
        //                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        //            }
        //        });

        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        });

        //        binder.registerCustomEditor(LocalTime.class, new PropertyEditorSupport() {
        //            @Override
        //            public void setAsText(String text) throws IllegalArgumentException {
        //                setValue(LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss")));
        //            }
        //        });
    }
}
