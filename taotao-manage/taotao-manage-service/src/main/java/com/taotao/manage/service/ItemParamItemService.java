package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {
	@Autowired
	private ItemParamItemMapper itemParamItemMapper;

	public Integer updateByItemId(Long itemId, String itemParams) {
		ItemParamItem record = new ItemParamItem();
		record.setParamData(itemParams);
		
		Example example = new Example(ItemParamItem.class);
		example.createCriteria().andEqualTo("itemId", itemId);
		return itemParamItemMapper.updateByExampleSelective(record, example);
	}

}
