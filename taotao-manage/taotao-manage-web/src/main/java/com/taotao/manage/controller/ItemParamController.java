package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;

@Controller
@RequestMapping("item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
	public ResponseEntity<ItemParam> queryItemParamByItemCatId(@PathVariable("itemCatId")Long itemCatId){
		try {
			ItemParam itemParam = new ItemParam();
			itemParam.setItemCatId(itemCatId);
			ItemParam resultItemParam = itemParamService.queryOne(itemParam);
			if (resultItemParam == null) {
				return ResponseEntity.ok(null);
			}
			return ResponseEntity.ok(resultItemParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(value = "{cid}", method = RequestMethod.POST)
	public ResponseEntity<Void> saveItemParam(@PathVariable("cid")Long cid, @RequestParam("paramData")String paramData){
		try {
			ItemParam itemParam = new ItemParam();
			itemParam.setItemCatId(cid);
			itemParam.setParamData(paramData);
			Integer count = itemParamService.save(itemParam);
			if (count == 1) {
				return ResponseEntity.ok().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(value = "list", method=RequestMethod.GET)
	public ResponseEntity<EasyUIResult> getPageList(
			@RequestParam("page") Integer page, 
			@RequestParam(value = "rows") Integer rows){
		try {
			EasyUIResult easyUIResult = itemParamService.queryPageList(page, rows);
			return ResponseEntity.ok(easyUIResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
