<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>TEST-SSH</title>
		<!-- 引入css -->
		<%@include file="/WEB-INF/include/css-file.jsp" %>
		<!-- 引入js -->
		<%@include file="/WEB-INF/include/js-file.jsp" %>
	</head>
	<body>
	
		<!-- hello 请求 -->
		<a href="${ctp }/hello">Hello</a>
		<br/>
		
		<!-- 展示数据表单 -->
		<table id="datagrid_01"></table>  
		
		<!-- 添加/修改数据时的对话框（表格） -->
		<div id="dialog_01" class="easyui-dialog" data-options="closed:true, width:300, height:200, modal:true" style="text-align: center; padding-top: 20px;">
			<form id="form_01" method="post" >
			    <div>   
			        <input class="easyui-validatebox" type="hidden" name="person.id" />   
			    </div>   
			    <div>   
			        <label for="name">姓名:</label>   
			        <input class="easyui-validatebox" type="text" name="person.name" data-options="required:true,validType:'length[0,10]'" />  
			    </div>
			    <br/>   
			    <div>   
			        <label for="age">年龄:</label>   
			        <input class="easyui-validatebox" type="text" name="person.age" data-options="required:true,validType:'length[0,3]'" />  
			    </div>
			    <br/>   
   			    <div>   
			        <label for="email">邮箱:</label>   
			        <input class="easyui-validatebox" type="text" name="person.email" data-options="required:true,validType:'email'" />  
			    </div>    
			    <br/>   
	   		    <div>   
	   		    	<input type="button" value="提交" id="button_01">
			    </div>  
			</form>  
		</div>  
		
	</body>
	<script type="text/javascript">
			// 页面加载完毕执行
			$(function(){
				
				// 表格初始化
				$('#datagrid_01').datagrid({    
				    url:'${ctp}/person/queryAll',
				    singleSelect:true,
				    fitColumns:true,
				    columns:[[    
				        {field:'id',title:'Id',width:100,align:'center'},    
				        {field:'name',title:'Name',width:100,align:'center'},    
				        {field:'age',title:'Age',width:100,align:'center'},    
				        {field:'email',title:'Email',width:100,align:'center'},    
				    ]],
					toolbar: [{
						iconCls: 'icon-add',
						handler: function(){
							addFunction();
						}
					},'-',{
						iconCls: 'icon-edit',
						handler: function(){
							editFunction();
						}
					},'-',{
						iconCls: 'icon-remove',
						handler: function(){
							deleteFunction();
						}
					}]
				}); 
				
				// 添加按钮
				function addFunction(){
					// 清空表单
					$('#form_01').form('clear');
					// 展示对话框和表单
					$('#dialog_01').dialog({
						closed:false,
						title:"添加"
					});
					// 设置表单属性
					$('#form_01').form({
						// 表单提交的地址
						url: '${ctp}/person/insert',
						// 提交成功的回调函数
						success:function(msg){
							$.messager.alert('提示',msg);
							$('#datagrid_01').datagrid('reload');
						}
					});
					
				}
				
				// 编辑按钮
				function editFunction(){
					// 获取选中的数据
					var selectedRow = $('#datagrid_01').datagrid('getSelected');
					if(selectedRow == null){
						$.messager.alert('警告','请先选择一条数据！');
						return;
					}
					// 清空表单
					$('#form_01').form('clear');
					// 填充表单
					$("input[name='person.id']").val(selectedRow.id);
					$("input[name='person.name']").val(selectedRow.name);
					$("input[name='person.age']").val(selectedRow.age);
					$("input[name='person.email']").val(selectedRow.email);
					// 展示对话框和表单
					$('#dialog_01').dialog({
						closed:false,
						title:"编辑"
					});
					// 设置表单属性
					$('#form_01').form({
						// 表单提交的地址
						url: '${ctp}/person/updateById',
						// 提交成功的回调函数
						success:function(msg){
							$.messager.alert('提示',msg);
							$('#datagrid_01').datagrid('reload');
						}
					});
				}
				
				// 删除按钮
				function deleteFunction(){
					// 获取选中的数据
					var selectedRow = $('#datagrid_01').datagrid('getSelected');
					if(selectedRow == null){
						$.messager.alert('警告','请先选择一条数据！');
						return;
					}
					// 获取选中id
					var id = selectedRow.id;
					// 是否确认删除
					$.messager.confirm('确认','您确认想要删除id为' + id + '记录吗？', function(yn){    
					    if (yn){
							// 发送ajax请求删除数据
							$.ajax({
								url : "${ctp}/person/removeById",
								type : "post",
								data : {
									id : id
								},
								success : function(data){
									console.log(data);
									$.messager.alert('提示',data.msg);
									$('#datagrid_01').datagrid('reload');
								}
							});
					    }
					}); 
				}
				
				// 提交按钮
				$("#button_01").click(function(){
					// 提交表单
					$("#form_01").submit();
					// 关闭对话框
					$('#dialog_01').dialog("close")
				});
				
			});
	</script>		
</html>