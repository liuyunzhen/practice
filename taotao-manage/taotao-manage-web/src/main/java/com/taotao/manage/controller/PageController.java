package com.taotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class PageController {

	@RequestMapping("{pageName}")
	public String toPage(@PathVariable ("pageName")String pageName){
		System.out.println("==========================================");
		return pageName;
	}
}
