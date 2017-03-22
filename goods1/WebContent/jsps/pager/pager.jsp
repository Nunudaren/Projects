<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function _go() {
		var currPage = $("#pageCode").val();//获取文本框中的当前页码
		if(!/^[1-9]\d*$/.test(currPage)) {//对当前页码进行整数校验
			alert('请输入正确的页码！');
			return;
		}
		if(currPage > ${pb.totalPage}) {//判断当前页码是否大于最大页
			alert('请输入正确的页码！');
			return;
		}
		location = "${pb.url }&currPage=" + currPage;//注意""号
	}
</script>


<div class="divBody">
  <div class="divContent">
    <%--上一页 --%>
<c:choose>
	<c:when test="${pb.currPage eq 1 }"><span class="spanBtnDisabled">上一页</span></c:when>
	<c:otherwise><a href="${pb.url }&currPage=${pb.currPage-1}" class="aBtn bold">上一页</a></c:otherwise>
</c:choose> 

<%--通过给出的当前页码来计算页码列表的开始和结束位置 --%>       
    <%-- 计算begin和end --%>
      <%-- 如果总页数<=6，那么显示所有页码，即begin=1 end=${pb.tp} --%>
        <%-- 设置begin=当前页码-2，end=当前页码+3 --%>
          <%-- 如果begin<1，那么让begin=1 end=6 --%>
          <%-- 如果end>最大页，那么begin=最大页-5 end=最大页 --%>
<c:choose>
	<c:when test="${pb.totalPage <= 6 }">
		<c:set var="begin" value="1"/>
		<c:set var="end" value="${pb.totalPage }"/>
	</c:when>
	<c:otherwise>
		<c:set var="begin" value="${pb.currPage - 2 }"/>
		<c:set var="end" value="${pb.currPage + 3 }"/>
		<c:if test="${begin < 1 }">
			<c:set var="begin" value="1"/>
			<c:set var="end" value="6"/>
		</c:if>
		<c:if test="${end > pb.totalPage }">
			<c:set var="begin" value="${pb.totalPage - 5 }"/>
			<c:set var="end" value="${pb.totalPage }"/>
		</c:if>
	</c:otherwise>
</c:choose>       
    
    <%-- 显示页码列表 --%>
    <c:forEach begin="${begin }" end="${end }" var="i">
    	<c:choose>
    		<c:when test="${i eq pb.currPage }">
            	<span class="spanBtnSelect">${i }</span>  <%--不可以点击的 --%>
    		</c:when>
    		<c:otherwise>
    			<a href="${pb.url }&currPage=${i}" class="aBtn">${i }</a> <%--可以点击，进行超链接 --%>
    		</c:otherwise>
    	</c:choose>
    </c:forEach>
    	
    
    <%-- 显示点点点 --%>
    <c:if test="${end < pb.totalPage }">
    	<span class="spanApostrophe">...</span> 
    </c:if>

    
     <%--下一页 --%>
<c:choose>
	<c:when test="${pb.currPage eq pb.totalPage }"><span class="spanBtnDisabled">下一页</span></c:when>
	<c:otherwise><a href="${pb.url }&currPage=${pb.currPage+1}" class="aBtn bold">下一页</a></c:otherwise>
</c:choose>
        
         
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    
    <%-- 共N页 到M页 --%>
    <span>共${pb.totalPage }页</span>
    <span>到</span>
    <input type="text" class="inputPageCode" id="pageCode" value="${pb.currPage }"/>
    <span>页</span>
    <a href="javascript:_go();" class="aSubmit">确定</a>
  </div>
</div>