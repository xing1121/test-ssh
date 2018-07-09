package com.sf.ssh.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 描述：HelloAction
 * @author 80002888
 * @date   2018年4月27日
 */
public class HelloAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7339611346962862331L;

	/**
	 * 拦截/hello请求，返回到/WEB-INF/jsp/success.jsp
	 *	@ReturnType	String 
	 *	@Date	2018年4月27日	上午9:13:47
	 *  @Param  @return
	 */
	@Action(value = "hello", results = {@Result(name = "success", location = "/WEB-INF/jsp/success.jsp") })
	public String hello(){
		return SUCCESS;
	}
	
}
