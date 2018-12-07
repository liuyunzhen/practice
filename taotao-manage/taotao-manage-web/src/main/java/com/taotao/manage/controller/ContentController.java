package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

@Controller
@RequestMapping("content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> saveContent(Content content){
		try {
			content.setId(null);
			contentService.save(content);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryContentList(
			@RequestParam("categoryId")Long categoryId,
			@RequestParam("page")Integer page,
			@RequestParam("rows")Integer rows){
		try {
			EasyUIResult easyUIResult = contentService.queryPageListByCategoryId(categoryId, page, rows);
			return ResponseEntity.ok(easyUIResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
