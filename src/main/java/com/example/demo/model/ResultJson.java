package com.example.demo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultJson {

	// 0正常，非0不正常
	private int status;
	//主要数据
	private List<?> listdata;
	//次要数据
	private Map<String, Object> mapdata=new HashMap<>();
	
	private String message;

	public int getStatus() {
		return status;
	}

	public ResultJson setStatus(int status) {
		this.status = status;
		return this;
	}

	public List<?> getListdata() {
		return listdata;
	}

	public ResultJson setListdata(List<?> listdata) {
		this.listdata = listdata;
		return this;
	}

	public Map<String, ?> getMapdata() {
		return mapdata;
	}

	public <T> ResultJson setMapdata(String key,T value) {
		this.mapdata.put(key, value);
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ResultJson setMessage(String message) {
		this.message = message;
		return this;
	}
}
