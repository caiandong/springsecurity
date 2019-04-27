package com.example.demo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.ResourceRegionHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.accept.ServletPathExtensionContentNegotiationStrategy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.HttpResource;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;
import org.springframework.web.servlet.resource.ResourceTransformerChain;

import com.example.demo.model.ResultJson;
import com.example.demo.service.MovieService;

@Controller
public class MovieResource{
	
	private  final Log logger = LogFactory.getLog(MovieResource.class);
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private MovieService movieService;
	
	private ResourceHttpMessageConverter resourceHttpMessageConverter;
	
	private ResourceRegionHttpMessageConverter resourceRegionHttpMessageConverter;
	
	private ResourceResolverChain resolverChain;
	
	private ResourceTransformerChain transformerChain;
	
	private PathExtensionContentNegotiationStrategy contentNegotiationStrategy;
	
	public MovieResource() {
		System.out.println("fuck you");
	}
	
	//请求获取电影名，大部分是播放器请求
	@RequestMapping("/getmovienames")
	@ResponseBody
	public ResultJson FindMovieName(){
		ResultJson json = new ResultJson();
		List<String> list = movieService.GetAllMovieNames();
		List<String> nameSuffix = movieService.GetMovieNameSuffix();
		json.setListdata(list).setMessage("ok")
		.setMapdata("namesuffix", nameSuffix)
		.setMapdata("dir", movieService.GetDir());
		
		return json;
	}
	//root添加电影目录
	@RequestMapping("/root/adddir")
	public String adddir(String[] dirs,String[] suffix) {	
		HashSet<String> set = new HashSet<String>();
		if(suffix!=null) {
			set.addAll(Arrays.asList(suffix));
		}
		if (dirs != null && dirs.length>0) {
			
			movieService.AddDirNames(Arrays.asList(dirs),set);
		}

		return "redirect:/playmovie";
	}
	//root刷新电影
	@RequestMapping("/root/restore")
	public String refresh() {			
		return "redirect:/playmovie";
	}
	

	//播放器路径
	@RequestMapping("/plyr")
	public ModelAndView plry(@RequestParam("movie")String movie,ModelAndView mav) {
		mav.addObject("movie", movie);
		mav.setViewName("plyr");
		return mav;
	}
	
	@RequestMapping("/playing/{moviename}")
	public void PlayMovieResource(@PathVariable("moviename")String moviename,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = movieService.FindPathByName(moviename);
		Resource resource = getResource(request,name);
		if (resource == null) {
			logger.debug("Resource not found");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		if (new ServletWebRequest(request, response).checkNotModified(resource.lastModified())) {
			logger.trace("Resource not modified");
			return;
		}

		MediaType mediaType = getMediaType(request, resource);

		ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
		if (request.getHeader(HttpHeaders.RANGE) == null) {
			setHeaders(response, resource, mediaType);
			this.resourceHttpMessageConverter.write(resource, mediaType, outputMessage);
		}
		else {
			
			response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
			ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(request);
			try {
				List<HttpRange> httpRanges = inputMessage.getHeaders().getRange();
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				this.resourceRegionHttpMessageConverter.write(
						HttpRange.toResourceRegions(httpRanges, resource), mediaType, outputMessage);
			}
			catch (IllegalArgumentException ex) {
				response.setHeader("Content-Range", "bytes */" + resource.contentLength());
				response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
			}
		}
	}
	
	private void setHeaders(HttpServletResponse response, Resource resource, MediaType mediaType) throws IOException {
		long length = resource.contentLength();
		if (length > Integer.MAX_VALUE) {
			response.setContentLengthLong(length);
		}
		else {
			response.setContentLength((int) length);
		}

		if (mediaType != null) {
			response.setContentType(mediaType.toString());
		}
		if (resource instanceof HttpResource) {
			HttpHeaders resourceHeaders = ((HttpResource) resource).getResponseHeaders();
			resourceHeaders.forEach((headerName, headerValues) -> {
				boolean first = true;
				for (String headerValue : headerValues) {
					if (first) {
						response.setHeader(headerName, headerValue);
					}
					else {
						response.addHeader(headerName, headerValue);
					}
					first = false;
				}
			});
		}
		response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
	}

	private MediaType getMediaType(HttpServletRequest request, Resource resource) {
		
		return this.contentNegotiationStrategy.getMediaTypeForResource(resource);
	}

	private Resource getResource(HttpServletRequest request ,String path) throws IOException {
		
		Resource resource = this.resolverChain.resolveResource(request, path, movieService.getLocations());
		if (resource != null) {
			resource = this.transformerChain.transform(request, resource);
		}
		return resource;
	}

	@Autowired
	public void InitContentNegotiationStrategy(ContentNegotiationManager contentNegotiationManager) {
		Map<String, MediaType> mediaTypes = null;
		if (contentNegotiationManager != null) {
			PathExtensionContentNegotiationStrategy strategy =
					contentNegotiationManager.getStrategy(PathExtensionContentNegotiationStrategy.class);
			if (strategy != null) {
				mediaTypes = new HashMap<>(strategy.getMediaTypes());
			}
		}
		contentNegotiationStrategy= (webApplicationContext.getServletContext() != null ?
				new ServletPathExtensionContentNegotiationStrategy(webApplicationContext.getServletContext(), mediaTypes) :
				new PathExtensionContentNegotiationStrategy(mediaTypes));
		resourceHttpMessageConverter = new ResourceHttpMessageConverter();
		resourceRegionHttpMessageConverter=new ResourceRegionHttpMessageConverter();
		
		resolverChain=new DefaultResourceResolverChain(Arrays.asList(new PathResourceResolver()));
		transformerChain = new DefaultResourceTransformerChain(this.resolverChain, null);
	}
	
}

