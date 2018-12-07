package com.taotao.manage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	private final Logger logger = LoggerFactory.getLogger(ItemController.class); 
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> save(Item item, @RequestParam("desc")String desc, @RequestParam("itemParams")String itemParams){
		try {
			if (item.getPrice() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			Boolean flag = itemService.saveItem(item, desc, itemParams);
			if (flag) {
				logger.info("新建商品成功! id={}", item.getId());
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<EasyUIResult> getPageList(
			@RequestParam("page") Integer page, 
			@RequestParam(value = "rows") Integer rows){
		try {
			EasyUIResult easyUIResult = itemService.queryPageList(page, rows);
			return ResponseEntity.ok(easyUIResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc")String desc, @RequestParam("itemParams")String itemParams){
		try {
			Boolean flag = itemService.updateItem(item, desc, itemParams);
			if (flag) {
				return ResponseEntity.status(HttpStatus.OK).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
