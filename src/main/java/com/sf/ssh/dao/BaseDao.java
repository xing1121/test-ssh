package com.sf.ssh.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {

	/**
	 * 添加
	 *	@ReturnType	void 
	 *	@Date	2018年3月22日	下午3:11:22
	 *  @Param  @param t
	 */
	public void save(T t);
	
	/**
	 * 按主键修改
	 *	@ReturnType	void 
	 *	@Date	2018年3月22日	下午3:11:26
	 *  @Param  @param t
	 */
	public void updateById(T t);
	
	/**
	 * 按主键删除
	 *	@ReturnType	void 
	 *	@Date	2018年3月22日	下午3:43:13
	 *  @Param  @param t
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 根据主键查询一条
	 *	@ReturnType	T 
	 *	@Date	2018年3月22日	下午3:43:21
	 *  @Param  @param id
	 *  @Param  @return
	 */
	public T selectById(Serializable id);
	
	/**
	 * 查询所有
	 *	@ReturnType	List<T> 
	 *	@Date	2018年3月22日	下午3:43:29
	 *  @Param  @return
	 */
	public List<T> selectAll();
	
}
