package com.example.demo.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DirNames {
	private String perfix;
	private List<String> contents;
	
	public void AddContent(String e) {
		//是否祖先目录
		if(perfix.contains(e))
			perfix=e;
		else {
			
		}
	}
	public String getPerfix() {
		return perfix;
	}

	public void setPerfix(String perfix) {
		this.perfix = perfix;
	}

	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public List<String> GetAllNames(){
		List<String> list = contents.stream().
				map(item->perfix+item).
				collect(Collectors.toList());
		return list;
	}
}
