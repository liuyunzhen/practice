package com.taotao.sso.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.httpclient.ResultObjectMapData;
import com.taotao.common.httpclient.ResultObjectStrData;
import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@RequestMapping("user")
@Controller
public class UserController {
	private static final String COOKIENAME = "TT_TOKEN";
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register(){
		return "register";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value = "check/{param}/{type}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> check(@PathVariable("param")String param, 
			@PathVariable("type")Integer type){
		Boolean flag = userService.check(param, type);
		if (flag == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		//兼容前端逻辑
		return ResponseEntity.ok(!flag);
	}
	
	@RequestMapping(value = "doRegister", method = RequestMethod.POST)
	@ResponseBody
	public ResultObjectStrData doRegister(@Validated User user, BindingResult bindingResult){
		ResultObjectStrData resultObject = new ResultObjectStrData();
		if (bindingResult.hasErrors()) {
			List<String> msgs = new ArrayList<String>();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError objectError : errors) {
				msgs.add(objectError.getDefaultMessage());
			}
			resultObject.setStatus(HttpStatus.BAD_REQUEST.value());
			resultObject.setData(StringUtils.join(msgs, "|"));
			return resultObject;
		}
		
		Boolean flag = userService.insert(user);
		if (flag) {
			resultObject.setStatus(200);
		} else{
			resultObject.setStatus(500);
			resultObject.setMessage("用户注册出错!");
		}
		return resultObject;
	} 
	
	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	@ResponseBody
	public ResultObjectStrData doLogin(@RequestParam("username")String username, 
			@RequestParam("password")String password,
			HttpServletRequest request, HttpServletResponse response){
		ResultObjectStrData resultObject = new ResultObjectStrData();
		try {
			String token = userService.login(username, password);
			if (StringUtils.isEmpty(token)) {
				resultObject.setStatus(HttpStatus.BAD_REQUEST.value());
			} else{
				resultObject.setStatus(HttpStatus.OK.value());
				CookieUtils.setCookie(request, response, COOKIENAME, token);
			}
		} catch (Exception e) {
			resultObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			e.printStackTrace();
		}
		return resultObject;
	}
}
