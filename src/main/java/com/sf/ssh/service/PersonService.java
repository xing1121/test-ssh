package com.sf.ssh.service;

import java.util.List;

import com.sf.ssh.domain.Person;

public interface PersonService {

	/**
	 * 根据id获取一条数据
	 *	@ReturnType	Person 
	 *	@Date	2018年4月27日	下午3:17:18
	 *  @Param  @param id
	 *  @Param  @return
	 */
	public Person queryById(Long id);
	
	/**
	 * 查询所有
	 *	@ReturnType	List<Person> 
	 *	@Date	2018年7月7日	下午2:07:20
	 *  @Param  @return
	 */
	public List<Person> queryAll();
	
	/**
	 * 插入一条
	 *	@ReturnType	void 
	 *	@Date	2018年7月7日	下午2:07:40
	 *  @Param  @param Person
	 */
	public void insert(Person person);
	
	/**
	 * 根据id更新一条数据
	 *	@ReturnType	void 
	 *	@Date	2018年7月7日	下午2:07:59
	 *  @Param  @param Person
	 */
	public void updateById(Person person);
	
	/**
	 * 根据id删除一条数据
	 *	@ReturnType	void 
	 *	@Date	2018年7月7日	下午2:08:29
	 *  @Param  @param id
	 */
	public void deleteById(Long id);
	
}
