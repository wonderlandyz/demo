<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,inital-scale=1,user-scalable=no,minimum-scale=1.0"/>
<title>404</title>

<style>
    /* 清除内外边距 */
    body, h1, p, img,div,span, a{ /* table elements 表格元素 */
        margin: 0;
        padding: 0;
        color: #777;
        list-style: none;
        -webkit-tap-highlight-color:transparent; 
        font-family: '微软雅黑';
        
    }
    /* 重置文本格式元素 */
    a { text-decoration: none; display: inline-block; width: 100%; height: 100%; -webkit-tap-highlight-color:transparent; -webkit-user-select: none;
    -moz-user-focus: none;-moz-user-select: none; color: #000;}
    a:hover { text-decoration: none; }

    .fail_img{width: 35%;margin: 0 auto; margin-top: 30%;}
    .fail_center{text-align: center; margin-top: 40px;}
    .fail_button{width: 50%; margin: 0 auto; overflow: hidden; margin-top: 60px}
    .fail_button a{ border:1px solid #0cc486; border-radius: 5px; box-sizing: border-box; text-align: center; color: #0cc486;display: block;height: 40px; line-height: 40px}

    .error404{text-align: center;font-size: 4em; color: #0cc486}
</style>

</head>

<body>
<div id="wrap" class="screen">
  <h1 class="fail_img error404">404</h1>
  <div class="fail_center">
    <p>路径错误~</p>
  </div>
  <div class="fail_button">
    <a href="javascript:history.go(-1)">返回</a>
  </div>
</div>
</body>
</html>
