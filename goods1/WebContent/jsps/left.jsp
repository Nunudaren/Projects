<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>left</title>
    <base target="body"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/left.css'/>">
<script language="javascript">

/*
 * 1.对象名必须与第一个参数相同！
   2.第二个参数是显示在菜单上的标题
 */
var bar = new Q6MenuBar("bar", "传智播客网上书城"); //Q6MenuBar：创建的一个js的类

$(function() {
	bar.colorStyle = 4;//菜单的配色样式
	bar.config.imgDir = "<c:url value='/menu/img/'/>";//小工具所需图片的路径：“+/-”号
	bar.config.radioButton=true;//多个一级分类菜单同时展开是否排斥

	/*
	各个参数注释
	1.程序设计：一级分类名称
	2.Java Javascript：二级分类名称
	3./goods1/jsps/book/list.jsp：点击二级分类之后链接到的URL
	4.body：链接的内容在哪个框架页面显示
	*/
	//只有第一次添加一级分类的时候才会创建
	//注意这里的forEach,'E'大写
<c:forEach items="${parents}" var="parent">
	<c:forEach items="${parent.children}" var="child">
		bar.add("${parent.cname}", "${child.cname}", "/goods1/BookServlet?method=findByCategory&cid=${child.cid}", "body");
	</c:forEach>
</c:forEach>


	/* bar.add("程序设计", "Java Javascript", "/goods1/jsps/book/list.jsp", "body");
	bar.add("程序设计", "JSP", "/goods1/jsps/book/list.jsp", "body");
	bar.add("程序设计", "C C++ VC VC++", "/goods1/jsps/book/list.jsp", "body");
	
	bar.add("办公室用书", "微软Office", "/goods1/jsps/book/list.jsp", "body");
	bar.add("办公室用书", "计算机初级入门", "/goods1/jsps/book/list.jsp", "body");
	
	bar.add("图形 图像 多媒体", "Photoshop", "/goods1/jsps/book/list.jsp", "body");
	bar.add("图形 图像 多媒体", "3DS MAX", "/goods1/jsps/book/list.jsp", "body");
	bar.add("图形 图像 多媒体", "网页设计", "/goods1/jsps/book/list.jsp", "body");
	bar.add("图形 图像 多媒体", "Flush", "/goods1/jsps/book/list.jsp", "body");
	
	bar.add("操作系统/系统开发", "Windows", "/goods1/jsps/book/list.jsp", "body");
	bar.add("操作系统/系统开发", "Linux", "/goods1/jsps/book/list.jsp", "body");
	bar.add("操作系统/系统开发", "系统开发", "/goods1/jsps/book/list.jsp", "body"); */
	
	$("#menu").html(bar.toString());
});
</script>
</head>
  
<body>  
  <div id="menu"></div>
</body>
</html>
