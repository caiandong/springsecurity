package com.example.demo.util;

public class DirNameConvert {
	
	//默认前缀
	private String Perfix;
	
	public void setPerfix(String perfix) {
		Perfix = perfix;
	}

	public String ConvertDir(String perfix,String in) {
		return perfix+in;
	}
	
	public String ConvertRealDir(String in) {
		return ConvertRealDir2(Perfix, in);
	}

	public String ConvertRealDir2(String perfix, String in) {
		String string=in;
		if(in.startsWith("file:"))
			string=in.substring(5);
		
		if (!string.endsWith("/"))
			string += "/";
		return string;
	}
	//相对路径
	public String ConvertRealDir(String perfix, String in) {
		String string;
		if(in.startsWith("file:"))
			string=in.substring(5);
		else
			string = perfix+in.substring(in.indexOf('/')+1);
		if (!string.endsWith("/"))
			string += "/";
		return string;
	}
}
