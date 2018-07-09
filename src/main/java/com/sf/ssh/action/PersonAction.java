package com.sf.ssh.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.sf.ssh.domain.Person;
import com.sf.ssh.service.PersonService;

/**
 * 描述：PersonAction，拦截所有/person请求
 * @author 80002888
 * @date   2018年4月27日
 */
@Action(value = "person")
@ParentPackage("json-default")
@Controller
public class PersonAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2904239551547604271L;

	private static Logger logger = LoggerFactory.getLogger(PersonAction.class);
	
	@Autowired
	private PersonService personService;
	
	private Long id;
	
	private String msg;
	
	private Person person;
	
	private List<Person> persons;
	
	/**
	 * 查询所有
	 * 拦截/hello/queryAll请求，返回persons的json字符串
	 *	@ReturnType	String 
	 *	@Date	2018年7月7日	下午2:11:48
	 *  @Param  @return
	 */
	@Action(value = "queryAll", results = {@Result(name = "success", type = "json", params = {"includeProperties", "success, persons.*" }) })
	public void queryAll(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(personService.queryAll())); 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 根据id查询一条数据
	 * 拦截/hello/queryById请求，返回cat
	 *	@ReturnType	String 
	 *	@Date	2018年4月27日	上午9:14:08
	 *  @Param  @return
	 */
	@Action(value = "queryById", results = {@Result(name = "success", type = "json", params = {"includeProperties", "success, person.*" }) })
	public String queryById(){
		try {
			this.person = personService.queryById(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	/**
	 * 按照id删除一条数据
	 *	@ReturnType	void 
	 *	@Date	2018年7月7日	下午3:15:44
	 *  @Param
	 */
	@Action(value = "removeById", results = {@Result(name = "success", type = "json", params = {"includeProperties", "success, msg" }) })
	public String removeById(){
		try {
			if (id == null) {
				msg = "删除失败,id为空！";
			} else {
				personService.deleteById(id);
				msg = "成功删除！";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg = "服务器发生异常" + e.getMessage() + "，删除失败";
		}
		return SUCCESS;
	}
	
	/**
	 * 添加一条数据
	 *	@ReturnType	String 
	 *	@Date	2018年7月7日	下午3:51:52
	 *  @Param  @return
	 */
	@Action(value = "insert", results = {@Result(name = "success", type = "json", params = {"includeProperties", "success, msg" }) })
	public void insert(){
		HttpServletResponse response = null;
		try {
			
			response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			if (person == null) {
				msg = "添加失败，person为空！";
			} else {
				personService.insert(person);
				msg = "添加成功！";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg = "服务器发生异常" + e.getMessage() + "，添加失败";
		} finally {
			try {
				response.getWriter().write(msg);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} 
		}
	}
	
	/**
	 * 根据主键更新
	 *	@ReturnType	String 
	 *	@Date	2018年7月9日	上午9:55:15
	 *  @Param  @return
	 */
	@Action(value = "updateById", results = {@Result(name = "success", type = "json", params = {"includeProperties", "success, msg" }) })
	public void updateById(){
		HttpServletResponse response = null;
		try {
			
			response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			if (person == null || person.getId() == null) {
				msg = "更新失败，person为空或id为空！";
			} else {
				personService.updateById(person);
				msg = "更新成功！";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg = "服务器发生异常" + e.getMessage() + "，更新失败";
		} finally {
			try {
				response.getWriter().write(msg);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} 
		}
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
