package com.example.demo.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

//import com.caiandong.model.TRole;
//
public class Utils {
//	public static Set<GrantedAuthority> fanhuiquanxian(List<TRole> list,Set<GrantedAuthority> jihe) {
//		jihe=list.stream().map(role->{return new SimpleGrantedAuthority(role.getRoleKey());})
//		.collect(Collectors.toSet());
//			return jihe;
//	}
	//把一种集合类型转换成另外一种，通过apply返回应用后的结果 
	public static <T extends Collection<E>,E,R> T zhuanhuan(List<R> list, final T jihe,Function<R, E> f) {
		list.stream().forEach(role->{jihe.add(f.apply(role));});
		return jihe;
	}
}
