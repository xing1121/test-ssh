package com.sf.ssh.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.Id;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sf.ssh.dao.BaseDao;


@SuppressWarnings("all")
public class BaseDaoImpl<T> implements BaseDao<T>{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Class<T> clazz;				// 泛型类型（实体类）
					
	private Field indexField;			// 实体类主键属性
	
	private Method indexSetterMethod;	// 实体类主键的set方法
		
	private Method indexGetterMethod;	// 实体类主键的get方法
	
	{
		// 获取 带泛型父类的 泛型类型，获取泛型真实类型
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		Type[] types = pt.getActualTypeArguments();
		clazz = (Class<T>) types[0];	
		
		// 获取泛型类的主键
		Field[] fields = clazz.getDeclaredFields();
		if (fields.length == 0) {
			throw new RuntimeException(this.getClass().getName() + "...update 方法中，实体类属性为空！"+clazz);
		}
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			if (f == null) {
				continue;
			}
            if(!f.isAccessible()){  
                f.setAccessible(true);  
            }  
			Id id = f.getAnnotation(javax.persistence.Id.class);
			if (id != null) {
				// 命中
				indexField = f;
				break;
			}
		}
		if (indexField == null || StringUtils.isBlank(indexField.getName())) {
			throw new RuntimeException(this.getClass().getName() + "...update 方法中，实体类没有指定@Id(javax.persistence.Id)主键！"+clazz);
		}
		
		// 获取泛型类的主键的get、set方法
		try {
			if (indexField.getName().length() < 2) {
				indexGetterMethod = clazz.getMethod("get"+indexField.getName().toUpperCase());
				indexSetterMethod = clazz.getMethod("set"+indexField.getName().toUpperCase(),indexField.getType());
			} else {
				indexGetterMethod = clazz.getMethod("get"+indexField.getName().substring(0, 1).toUpperCase() + indexField.getName().substring(1));
				indexSetterMethod = clazz.getMethod("set"+indexField.getName().substring(0, 1).toUpperCase() + indexField.getName().substring(1),indexField.getType());
			}
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		if (indexGetterMethod == null) {
			throw new RuntimeException(this.getClass().getName() + "...update 方法中，主键"+indexField.getName()+"没有相应的get方法！"+clazz);
		}
		if (indexSetterMethod == null) {
			throw new RuntimeException(this.getClass().getName() + "...update 方法中，主键"+indexField.getName()+"没有相应的set方法！"+clazz);
		}
	}
	
	@Override
	public void save(T t) {
		if (t == null) {
			return;
		}
		this.getSession().save(t);
	}

	@Override
	public void updateById(T t) {
		if (t == null) {
			return;
		}
		// 执行get方法获取主键
		Object id;
		try {
			id = indexGetterMethod.invoke(t, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (id == null) {
			throw new RuntimeException(this.getClass().getName() + "...update 方法中，对象的主键为空！"+t);
		}
		if (!(id instanceof Serializable)) {
			throw new RuntimeException(this.getClass().getName() + "...update 方法中，主键没有实现Serializable接口！"+t);
		}
		T t2 = this.selectById((Serializable) id);
		if (t2 == null) {
			return;
		}
		try {
			BeanUtils.copyProperties(t2, t);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.getSession().update(t2);
	}

	@Override
	public void deleteById(Serializable id) {
		if (id == null) {
			return;
		}
		T t;
		try {
			t = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (t == null) {
			throw new RuntimeException(this.getClass().getName() + "...update 方法中，无法利用clazz.newInstance()创建该类的实例！");
		}
		try {
			indexSetterMethod.invoke(t, id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.getSession().delete(t);
	}

	@Override
	public T selectById(Serializable id) {
		Object o = this.getSession().get(clazz, id);
		if (o == null) {
			return null;
		}
		return (T)o;
	}

	@Override
	public List<T> selectAll() {
		String domainName = null;
		String className = clazz.getName();
		String[] strs = className.split("\\.");
		if (strs == null || strs.length < 1) {
			domainName = className;
		} else {
			domainName = strs[strs.length - 1];
		}
		String hql = "from " + domainName;
		Query query = this.getSession().createQuery(hql);
		List<T> list = query.list();
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list;
	}
	
	/**
	 * 获取session
	 *	@ReturnType	Session 
	 *	@Date	2018年3月22日	下午3:01:13
	 *  @Param  @return
	 */
	protected Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
}
