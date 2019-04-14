package com.example.demo.serviceimp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr.Item;
import com.example.demo.mvc.SpringMvcConfig;
import com.example.demo.service.MovieService;
import com.example.demo.util.Utils;

@Service
public class MovieServiceImp implements MovieService {

	private String[] Dir=Utils.GetMovieDir();
	
	private Map<String, String> map=new HashMap<String, String>();
	
	private List<String> list = new ArrayList<String>();
	
	private volatile boolean isneedrefresh=true;
	
//	@Cacheable(cacheNames="moviename")
	@Override
	public List<String> GetAllMovieNames() {
		return synchronizerefresh();
	}
	public String FindPathByName(String name) {
		return map.get(name);
	}
//	@CacheEvict(cacheNames="moviename",allEntries=true)
	public void clear() {
		isneedrefresh=true;
	}
	private void PutMap(String item) {
		map.put(item.substring(item.lastIndexOf('/')+1),item);
	}
	public  List<String> synchronizerefresh() {
		//检查是否需要刷新map
				if (isneedrefresh)
					//只允许一个线程刷新，其他线程等待
					synchronized (this) {
						if (isneedrefresh) {
							map.clear();
							list.clear();
							for(String dir:Dir)
								RecursionGet2(name->PutMap(name),new File(dir.substring(5)),"");		
							list.addAll(map.keySet());
							isneedrefresh = false;
						}
					}
				return list;
	}
private void RecursionGet(Consumer<String> C,String dir){
	if(dir==null||dir.isEmpty()) 
		return;
	File file = new File(dir);
	for(File childfile:file.listFiles()) {
		String filename=dir+"/"+childfile.getName();
		if(childfile.isFile())
			C.accept(filename);
		else
			RecursionGet(C, filename);
	}	
}
//请保证dir是一个存在的目录
private void RecursionGet2(Consumer<String> C,File dir,String perfix){
	for(File childfile:dir.listFiles()) {
		String filename=perfix+"/"+childfile.getName();
		if(childfile.isFile())
			C.accept(filename);
		else
			RecursionGet2(C, childfile,filename);
	}	
}
}
