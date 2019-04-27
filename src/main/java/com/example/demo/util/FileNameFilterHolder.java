package com.example.demo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileNameFilterHolder {

	private final ThreadLocal<List<String>> threadLocal;
	
	private List<String> defs;

	public FileNameFilterHolder(List<String> s) {
		threadLocal = new ThreadLocal<List<String>>() {
			@Override
			protected List<String> initialValue() {
				List<String> list = new ArrayList<String>();
				list.addAll(defs);
				return list;
			}
		};
		defs=s;
	}

	public List<String> GetfnfArray() {
		return threadLocal.get();
	}

	public void Addfnf(String... fnf) {
		threadLocal.get().addAll(Arrays.asList(fnf));
	}

	public void Clear() {
		threadLocal.remove();
	}
}
