package com.example.demo.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class Utils {

	public static <T extends Collection<E>,E,R> T zhuanhuan(List<R> list, final T jihe,Function<R, E> f) {
		list.stream().forEach(role->{jihe.add(f.apply(role));});
		return jihe;
	}
	public static String GetMovieDirPrefix() {
		return "file:/home/kmp/windows/";
	}
}
