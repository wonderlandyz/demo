<%@page import="java.io.PrintStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page import="java.lang.Exception" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,inital-scale=1,user-scalable=no,minimum-scale=1.0"/>
<title>500</title>
</head>

<body>
<div id="wrap" class="screen">
  <h1 class="fail_img error404">500</h1>
  <div class="fail_center">
    <p>服务器正在维护,请稍后尝试。</p>
  </div>
  <div class="fail_button">
    <a href="javascript:location.reload()">重新加载</a>
    <a onclick="returnhref();">返回</a>
  </div>
</div>
</body>
</html>
