package com.taotao.manage.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice(basePackages = {"com.taotao.manage.controller.api"})
public class JSONPController extends AbstractJsonpResponseBodyAdvice{

    public JSONPController(){
        super("callback","jsonp");
    }
}
