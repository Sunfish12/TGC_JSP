<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RegisterMember</title>
</head>
<body>
	<form enctype="multipart/form-data" action="<c:url value="/RegisterServletMB"/>"
		method="post">
		<table>
			<thead>
				<tr>
					<th>新增會員資料</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>帳號:</td>
					<td><input type="text" id="username" name="username" value="sunfisher">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>密碼:</td>
					<td><input type="password" id="pswd1" name="pswd1" value="Aa@123">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>密碼確認:</td>
					<td><input type="password" id="pswd2" name="pswd2" value="Aa@123">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>信箱:</td>
					<td><input type="text" id="email" name="email" value="sunfisher@gmail.com">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>姓氏:</td>
					<td><input type="text" id="lastname" name="lastname" value="弗利曼">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>名字:</td>
					<td><input type="text" id="firstname" name="firstname"
						value="高登">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>性別:</td>
					<td>男<input type="radio" id="gender" name="gender"
						value="男"> 女<input type="radio" id="gender"
						name="gender" value="女">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>暱稱:</td>
					<td><input type="text" id="nickname" name="nickname" value="自由之子">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>生日:</td>
					<td><input type="text" id="birthday" name="birthday" value="1990-10-10">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>身分證字號:</td>
					<td><input type="text" id="idCard" name="idCard" value="A1234567890">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>會員加入日:</td>
					<td><input type="text" id="joinDate" name="joinDate" value="2014-10-10">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>手機:</td>
					<td><input type="text" id="phone" name="phone" value="0911222333">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>地址:</td>
					<td><input type="text" id="memberAddress" name="memberAddress"
						value="新北市三重區集美街219號3樓">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td>會員圖片:</td>
					<td><input type="file" id="memberImage" name="memberImage"
						value="">
						<div style="display: inline"></div></td>
				</tr>
				<tr>
					<td><input type="submit" value="送出"><input
						type="reset" value="清除"></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>