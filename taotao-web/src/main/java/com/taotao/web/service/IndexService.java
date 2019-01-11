package com.taotao.web.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.common.service.APIService;
import com.taotao.manage.pojo.Content;

@Service
public class IndexService {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private APIService apiService;
	
	@Value("${TAOTAO_MANAGE_URL}")
	private String TAOTAO_MANAGE_URL;
	@Value("${INDEX_AD1_URL}")
	private String INDEX_AD1_URL;
	
	@Value("${INDEX_AD2_URL}")
	private String INDEX_AD2_URL;

	public String queryAd1() {
		try {
			String url = TAOTAO_MANAGE_URL + INDEX_AD1_URL;
			String data = apiService.doGet(url);
			if (StringUtils.isEmpty(data)) {
				return null;
			}
			JsonNode jsonNode = objectMapper.readTree(data);
			ArrayNode rows = (ArrayNode)jsonNode.get("rows");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (JsonNode row : rows) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("srcB", row.get("pic").asText());
				map.put("height", 240);
				map.put("alt", row.get("title").asText());
				map.put("width", 670);
				map.put("src", row.get("pic").asText());
				map.put("widthB", 550);
				map.put("href", row.get("url").asText());
				map.put("heightB", 240);
				
				list.add(map);
			}
			return objectMapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	public String queryAd2() {
//		try {
//			String url = TAOTAO_MANAGE_URL + INDEX_AD2_URL;
//			String data = apiService.doGet(url);
//			if (StringUtils.isEmpty(data)) {
//				return null;
//			}
//			JsonNode jsonNode = objectMapper.readTree(data);
//			ArrayNode rows = (ArrayNode)jsonNode.get("rows");
//			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//			for (JsonNode row : rows) {
//				Map<String, Object> map = new LinkedHashMap<String, Object>();
//				
//				map.put("width", 310);
//				map.put("height", 70);
//				map.put("src", row.get("pic").asText());
//				map.put("href", row.get("url").asText());
//				map.put("alt", "");
//				map.put("widthB", 210);
//				map.put("heightB", 70);
//				map.put("srcB", row.get("pic2").asText());
//				
//				list.add(map);
//			}
//			return objectMapper.writeValueAsString(list);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public String queryAd2_v2() {
		try {
			String url = TAOTAO_MANAGE_URL + INDEX_AD2_URL;
			String data = apiService.doGet(url);
			if (StringUtils.isEmpty(data)) {
				return null;
			}
			EasyUIResult result = EasyUIResult.formatToList(data, Content.class);
			List<Content> rows = (List<Content>) result.getRows();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Content content : rows) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				
				map.put("width", 310);
				map.put("height", 70);
				map.put("src", content.getPic());
				map.put("href", content.getUrl());
				map.put("alt", "");
				map.put("widthB", 210);
				map.put("heightB", 70);
				map.put("srcB", content.getPic2());
				
				list.add(map);
			}
			return objectMapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
