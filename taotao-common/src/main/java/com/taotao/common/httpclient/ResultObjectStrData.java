package com.taotao.common.httpclient;

public class ResultObjectStrData {

	private Integer code;
	
	private String data;
	
	private String message;
	
	private Integer status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ResultObjectStrData(Integer code, String data) {
		super();
		this.code = code;
		this.data = data;
	}

	public ResultObjectStrData() {
		super();
	}
	
	
}
