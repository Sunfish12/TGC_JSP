<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<jsp:useBean id="Memberbean" class="model.MemberBean" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>ShowMyMemberData</title>
</head>
<body>

	<img src='${pageContext.servletContext.contextPath}/controller/GetImage?username=${Memberbean.username}&type=MEMBER'>
	<%-- 	<img src='${pageContext.servletContext.contextPath}/controller/GetImage?username=sunfisher&type=MEMBER'> --%>
</body>
</html>