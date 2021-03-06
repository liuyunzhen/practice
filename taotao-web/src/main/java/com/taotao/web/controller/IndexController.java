package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.service.IndexService;

@Controller
public class IndexController {
	@Autowired
	private IndexService indexService;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("index");
		String indexAd1 = indexService.queryAd1();
		mv.addObject("indexAd1", indexAd1);
		
		String indexAd2 = indexService.queryAd2_v2();
		mv.addObject("indexAd2", indexAd2);
		return mv;
	}
}
