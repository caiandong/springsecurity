package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.MovieService;

@Controller
public class MovieController {
	

	@Autowired
	private MovieService movieService;
	
	@RequestMapping("/getmovienames")
	@ResponseBody
	public List<String> FindMovieName(){
	 List<String> list = movieService.GetAllMovieNames();
	 return list;
	}
	
	@RequestMapping("/root/refreshmovie")
	public String refresh() {
		movieService.clear();
		return "redirect:/playmovie";
	}
	@RequestMapping("/plyr")
	public ModelAndView plry(@RequestParam("movie")String movie,ModelAndView mav) {
		mav.addObject("movie", movie);
		mav.setViewName("plyr");
		return mav;
	}
	@RequestMapping("/playing/{moviename}")
	public String ToPlaying(@PathVariable("moviename")String moviename) {
		String name = movieService.FindPathByName(moviename);
		String s="forward:/movie"+name;
		return s;
	}
}
