<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-9-11
  Time: 上午12:21
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
</head>
<body>
<form action="test" enctype="multipart/form-data" method="post">
    <input type="text" name="test" value="dd"/>
    <input type="text" name="test" value="dd"/>
    <input type="file" name="imgfile"/>
    <input type="submit" value="submit"/>
    <img src="${createLink(action: "testDown",params: [id:5,width:100,height:300])}"/>
</form>
</body>
</html>