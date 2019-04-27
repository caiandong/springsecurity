package com.example.demo.serviceimp;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr.Item;
import com.example.demo.dao.MovieMapper;
import com.example.demo.mvc.MvcProperties;
import com.example.demo.mvc.SpringMvcConfig;
import com.example.demo.service.MovieService;

import com.example.demo.util.DirNameConvert;
import com.example.demo.util.FileNameFilter;
import com.example.demo.util.FileNameFilterHolder;
import com.example.demo.util.SomeThingWrap;
import com.example.demo.util.Utils;

@Service
public class MovieServiceImp implements MovieService {
	
	public static final Logger log=LoggerFactory.getLogger(MovieServiceImp.class);

	@Autowired
	private MovieMapper movieMapper;
	
	@Autowired
	private DirNameConvert dnc;

	@Autowired
	MvcProperties mvcProperties;

	@Autowired
	ApplicationContext applicationContext;
	
	public static final String AllType="all";
	
	private String Perfix;
	
	private  Set<String> MovieNameTypeSuffix=new HashSet<String>();

	private final Set<String> Dir = new HashSet<String>();
	
	private final LinkedBlockingQueue<List<String>> bkq=new LinkedBlockingQueue<List<String>>();
	
	private final Object object=new Object();
	
	private final Lock lock=new ReentrantLock();
	
	private final List<Resource> DirForResource = new CopyOnWriteArrayList<Resource>();

	private final Map<String, String> map = new ConcurrentHashMap<String, String>();

	private final List<String> list = new CopyOnWriteArrayList<>();

	public MovieServiceImp() {
		System.out.println("mother fuck");
	}

	// 此处用枚举一点都不合适，只是为了练习
	private enum MovieType {
		Mp4("mp4"), Mkv("mkv"), NoMovie("fuck");
		private String name;

		private MovieType(String s) {
			name = s;
		}

		public static Boolean HasEnum(String ss) {
			String s = ss.substring(ss.lastIndexOf(".") + 1);
			for (MovieType mt : values())
				if (mt.name.equalsIgnoreCase(s))
					return true;
			return false;
		}
	}

	private  Boolean FilterMovie(String ss,List<String> pattrn) {
		String s = ss.substring(ss.lastIndexOf(".") + 1);
		for (String mt : pattrn)
			if (mt.equalsIgnoreCase(s))
				return true;
		return false;
	}

	@PostConstruct
	public void init() {
		
		if(mvcProperties.getFilesuffix()!=null)
			MovieNameTypeSuffix.addAll(mvcProperties.getFilesuffix());
		else
			MovieNameTypeSuffix.add(AllType);
		Perfix = mvcProperties.GetMovieDirPerfix();
		dnc.setPerfix(Perfix);
		AddDirNames(Arrays.asList(mvcProperties.getMappingdirs()),MovieNameTypeSuffix);
	}

	public void AddDirNames(List<String> dirs,Set<String> pattern) {

		List<String> Checkedlist = CheckDirName(dirs);
		try {
			bkq.put(Checkedlist);
		} catch (InterruptedException e) {
			log.warn("队列已满，放弃入队");
			e.printStackTrace();
		}
		if(lock.tryLock())
		{
			Dir.clear();
			try {
				while (true) {
					
						// 去掉包含的目录
						List<String> take = bkq.poll();
						if(take==null)
							break;
						take.forEach(newdir -> {
							final Iterator<String> each = Dir.iterator();
							while (each.hasNext()) {
								String next = each.next();
								if (newdir.contains(next)) {
									return;
								} else if (next.contains(newdir))
									each.remove();
							}
							Dir.add(newdir);
						});
				
				}
				map.clear();
				Set<String> dd=pattern==null?MovieNameTypeSuffix:pattern;
				Dir.forEach(dir -> {
					File file = new File(dir);
					if (file.exists())
						RecursionGet2(name -> {
							if(CheckFilterFileName(name,dd))
								map.put(name.substring(name.lastIndexOf('/') + 1), name);
						}, file, "");
				});
				List<Resource> arrayList = new ArrayList<Resource>(Dir.size());
				for (String location : this.Dir) {
					Resource resource = applicationContext.getResource("file:" + location);
					arrayList.add(resource);
				}
				DirForResource.clear();
				this.DirForResource.addAll(arrayList);
				
				list.clear();
				list.addAll(map.keySet());
			}
			finally {
				lock.unlock();
				synchronized(object) {
					object.notifyAll();
				}
				
			}
			return ;
		}
		synchronized(object) {
			try {
				object.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean CheckFilterFileName(String name, Set<String> pattern) {
		String substring = name.substring(name.lastIndexOf('.')+1);
		return pattern.contains(substring.toLowerCase())?true:pattern.contains(AllType);
	}

	protected List<String> CheckDirName(List<String> list) {

		return list.stream()
				.filter(item->!"".equals(item))//过滤空白
				.map(in -> {
					return dnc.ConvertRealDir(in);
				}).// 去掉全路径中的/的前缀.添加后缀/
				distinct(). // 去重
				
				collect(Collectors.toList());
	}

	@Override
	public List<String> GetAllMovieNames() {
		return list;
	}

	public String FindPathByName(String name) {
		return map.get(name);
	}


	private void RecursionGet(Consumer<String> C, String dir) {
		if (dir == null || dir.isEmpty())
			return;
		File file = new File(dir);
		for (File childfile : file.listFiles()) {
			String filename = dir + "/" + childfile.getName();
			if (childfile.isFile())
				C.accept(filename);
			else
				RecursionGet(C, filename);
		}
	}

//请保证dir是一个存在的目录
	private void RecursionGet2(Consumer<String> C, File dir, String perfix) {
		for (File childfile : dir.listFiles()) {
			String filename = perfix + "/" + childfile.getName();
			if (childfile.isFile())
				C.accept(filename);
			else
				RecursionGet2(C, childfile, filename);
		}
	}


	@Override
	public List<Resource> getLocations() {

		return DirForResource;

	}

	@Override
	public List<String> GetMovieNameSuffix() {
		return movieMapper.GetAllMovieNameSuffix(10);
	}

	@Override
	public void RestoreDir() {
		init();
	}

	@Override
	public List<String> GetDir() {
		
		return new ArrayList<String>(Dir);
	}
}
