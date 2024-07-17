<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>jQuery-generate-qrcode</title>
    <script type="text/javascript" src="<%=request.getContextPath() %> /js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %> /js/jquery.qrcode.min.js"></script>
</head>
<body>
<h1>生成的二维码如下：</h1><br>
<div id="qrcode"></div>
<script type="text/javascript">
    jQuery('#qrcode').qrcode("https://blog.csdn.net/heyiqingsong")
</script>
</body>
</html>