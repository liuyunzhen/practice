package com.taotao.manage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

@Controller
@RequestMapping("content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> queryContentCategoryList(
			@RequestParam(value="id", defaultValue="0")Long parentId){
		try {
			ContentCategory record = new ContentCategory();
			record.setParentId(parentId);
			List<ContentCategory> list = contentCategoryService.queryWhere(record);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory){
		contentCategory.setId(null);
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		contentCategoryService.save(contentCategory);
		
		ContentCategory category = contentCategoryService.queryById(contentCategory.getParentId());
		if (!category.getIsParent()) {
			category.setIsParent(true);
			contentCategoryService.updateSelective(category);
		}
		return ResponseEntity.ok(category);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory){
		contentCategoryService.updateSelective(contentCategory);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteContentCategory(ContentCategory contentCategory){
		List<Object> ids = new ArrayList<Object>();
		ids.add(contentCategory.getId());
		
		findAllSubNode(contentCategory.getId(), ids);
		contentCategoryService.deleteByIds(ContentCategory.class, "id", ids);
		
		ContentCategory record = new ContentCategory();
		record.setParentId(contentCategory.getParentId());
		List<ContentCategory> list = contentCategoryService.queryWhere(record);
		if (list == null && list.isEmpty()) {
			ContentCategory category = new ContentCategory();
			category.setId(contentCategory.getParentId());
			category.setIsParent(false);
			contentCategoryService.updateSelective(category);
		}
		return ResponseEntity.ok().build();
	}

	private void findAllSubNode(Long id, List<Object> ids) {
		ContentCategory record = new ContentCategory();
		record.setParentId(id);
		List<ContentCategory> list = contentCategoryService.queryWhere(record);
		for (ContentCategory contentCategory : list) {
			ids.add(contentCategory.getId());
			if (contentCategory.getIsParent()) {
				findAllSubNode(contentCategory.getId(), ids);
			}
		}
	}
}
