package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.web.service.ItemService;
import com.taotao.web.vo.ItemVo;

@Controller
@RequestMapping("item")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public ModelAndView showDetail(@PathVariable("itemId")Long itemId){
		ModelAndView mv = new ModelAndView("item");
		ItemVo itemVO = itemService.queryItemByItemId(itemId);
		mv.addObject("item", itemVO);
		
		ItemDesc itemDesc = itemService.queryItemDescByItemId(itemId);
		mv.addObject("itemDesc", itemDesc);
		
		ItemParamItem itemParamItem = itemService.queryItemParamItemByItemId(itemId);
		mv.addObject("itemParam", itemParamItem.getParamData());
		return mv;
	}
}