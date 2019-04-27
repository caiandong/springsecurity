package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;



public interface MovieService {
	
	List<String> GetDir();
	List<String> GetAllMovieNames();
	String FindPathByName(String name);
	void AddDirNames(List<String> dirs,Set<String> pattern);
	List<String> GetMovieNameSuffix();
	void RestoreDir();
	List<? extends Resource> getLocations();
}
