<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-4-6
  Time: 下午10:34
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
</head>
<body>
        ${flash.message}
      <form action="${createLink(controller: "message",action: "sendMsg")}" method="post">
          接收id：<input type="text" name="receiveId"/> <br/>
          发送类型 <select name="sendType">
                <option value="1">发给饭店</option>
          <option value="2">发给顾客</option>
          </select>                                    <br/>
          内容：<input type="text" name="content"/> <br/>
          <input type="submit" value="发送"/>
      </form>
</body>
</html>