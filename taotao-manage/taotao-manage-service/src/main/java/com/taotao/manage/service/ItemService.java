package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item>  {
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	public Boolean saveItem(Item item, String desc, String itemParams){
		item.setStatus(1);
		item.setId(null);
		Integer num = super.save(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		Integer num2 = itemDescService.save(itemDesc);
		
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setItemId(item.getId());
		itemParamItem.setParamData(itemParams);
		Integer num3 = itemParamItemService.save(itemParamItem);
		return num.intValue() == 1 && num2.intValue() == 1 && num3.intValue() == 1;
	}

	public EasyUIResult queryPageList(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		
		Example example = new Example(Item.class);
		example.setOrderByClause("updated desc");
		List<Item> selectByExample = itemMapper.selectByExample(example);
		PageInfo<Item> pageInfo = new PageInfo<Item>(selectByExample);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

	public Boolean updateItem(Item item, String desc) {
		item.setCreated(null);
		item.setStatus(null);
		Integer count1 = super.updateSelective(item);
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		Integer count2 = itemDescService.updateSelective(itemDesc);
		return count1 == 1 && count2 == 1;
	}

}
