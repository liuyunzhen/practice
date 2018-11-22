package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> getItemCatList(@RequestParam(value="id", defaultValue="0")Long parentId){
		try {
			ItemCat itemCat = new ItemCat();
			itemCat.setParentId(parentId);
			List<ItemCat> itemCatList = itemCatService.queryWhere(itemCat);
			if (itemCatList == null || itemCatList.isEmpty()) {
				ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(itemCatList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(value="/queryOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<ItemCat> queryOne(@PathVariable("id")Long id){
		try {
			ItemCat itemCat = new ItemCat();
			itemCat.setId(id);
			ItemCat result = itemCatService.queryOne(itemCat);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
