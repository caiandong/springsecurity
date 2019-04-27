package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface MovieMapper {

	@Select("SELECT fs.suffix AS suffix FROM file_suffix fs WHERE fs.type=#{id}")
	List<String> GetAllMovieNameSuffix(int type);
	
}
