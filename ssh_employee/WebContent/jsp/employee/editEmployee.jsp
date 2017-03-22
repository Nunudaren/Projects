<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<body>
	<table border="0" width="600px">
		<tr>
			<td align="center" style="font-size: 24px; color: #666">员工添加</td>
		</tr>
		<tr>
			<td align="right">
			<a href="javascript:document.getElementById('saveForm').submit()">保存</a> &nbsp;&nbsp;
			<a href="javascript:history.go(-1)">退回</a>
			</td>
		</tr>
	</table>
	<br />

	<s:form id="saveForm" action="employee_update" method="post" namespace="/" theme="simple">
	<s:hidden name="eid" value="%{model.eid}"/>
	<table width="88%" border='0' class="emp_table" style="width:80%;">
		<tr>
			<td>姓名：</td>
			<!-- 用value来回显数据 -->
			<td><s:textfield name="ename" value="%{model.ename}"/></td>
			<td>性别：</td>
			<td><s:radio name="sex" list="{'男','女'}" value="%{model.sex}"/></td>
		</tr>
		<tr>
			<td>用户名：</td>
			<td><s:textfield name="username" value="%{model.username}"/></td>
			<td>密码：</td>
			<td><s:password name="password" value="%{model.password}" showPassword="true"/></td>
		</tr>
		<tr>
			<td width="10%">出生日期：</td>
			<td width="20%"><input type="text" name="birthday" value="<s:date name="model.birthday" format="yyyy-MM-dd"/>"/></td>
			<td width="10%">入职时间：</td>
			<td width="62%"><input type="text" name="joinDate" value="<s:date name="model.joinDate" format="yyyy-MM-dd"/>"/></td>
		</tr>
		

		<tr>
			<td width="10%">所属部门：</td>
			<td width="20%"><s:select name="department.did" list="list" value="%{model.department.did}" listKey="did" listValue="dname" headerKey="" headerValue="--请选择--"/></td>
			<td width="8%">编号：</td>
			<td width="62%"><s:textfield name="enumber" value="%{model.enumber}"/></td>
		</tr>
	</table>
	</s:form>

</body>
</html>