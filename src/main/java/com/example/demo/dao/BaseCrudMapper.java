package com.example.demo.dao;

import java.util.List;

public interface BaseCrudMapper<T> {
	<S extends T> S save(S entity);

	<S extends T> List<S> saveAll(List<? extends S> entities);

	<S extends T> S findById(int id);

	boolean existsById(int id);

	<S extends T> List<S> findAll();

	<S extends T> List<S> findAllByIds(int[] ids);

	long count();

	void deleteById(int id);

	void delete(T entity);

	void deleteAll(List<? extends T> entities);

	void deleteAll();

}
