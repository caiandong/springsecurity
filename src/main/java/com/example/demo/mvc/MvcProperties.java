package com.example.demo.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mymvc")
public class MvcProperties {

	private String defaults;
	
	private String perfix;

	private String[] mappingdirs;
	
	private List<String> filesuffix;


	public List<String> getFilesuffix() {
		return filesuffix;
	}

	public void setFilesuffix(List<String> filesuffix) {
		this.filesuffix = filesuffix;
	}

	public String getPerfix() {
		return perfix;
	}

	public void setPerfix(String perfix) {
		this.perfix = perfix;
	}

	public String[] getMappingdirs() {
		return mappingdirs;
	}

	public void setMappingdirs(String[] mappingdirs) {
		this.mappingdirs = mappingdirs;
	}

	public void setDefaults(String defaults) {
		this.defaults = defaults;
	}

	
	public String getDefaults() {
		return defaults;
	}

	public String[] GetMovieDirMapping() {
		return mappingdirs;
	}

	public String GetMovieDirPerfix() {
		return perfix;
	}

}
