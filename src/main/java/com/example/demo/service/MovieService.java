package com.example.demo.service;

import java.util.List;

import com.example.demo.model.MovieHref;

public interface MovieService {
	List<String> GetAllMovieNames();
	String FindPathByName(String name);
	void clear();
}
