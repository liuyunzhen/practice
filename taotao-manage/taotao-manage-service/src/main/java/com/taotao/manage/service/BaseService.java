package com.taotao.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

public abstract class BaseService<T extends BasePojo> {
	
//	public abstract Mapper<T> getMapper();
	
	@Autowired
	private Mapper<T> mapper;

	public T queryById(Long id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public List<T> queryAll(){
		return mapper.select(null);
	}
	
	public T queryOne(T record){
		return mapper.selectOne(record);
	}
	
	public List<T> queryWhere(T record){
		return mapper.select(record);
	}
	
	public PageInfo<T> queryPageListByWhere(T record, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<T> recordList = mapper.select(record);
		return new PageInfo<T>(recordList);
	}
	
	public Integer save(T record){
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return mapper.insert(record);
	}
	
	public Integer saveSelective(T record){
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return mapper.insertSelective(record);
	}
	
	public Integer update(T record){
		record.setUpdated(new Date());
		return mapper.updateByPrimaryKey(record);
	}
	
	public Integer updateSelective(T record){
		record.setUpdated(new Date());
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	public Integer deleteById(Long id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer deleteByIds(Class<T> clazz, String property, List<Object> values){
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, values);
		return mapper.deleteByExample(example);
	}
	
	public Integer deleteByWhere(T record){
		return mapper.delete(record);
	}
}
