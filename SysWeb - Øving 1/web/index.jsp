<%--
  Created by IntelliJ IDEA.
  User: OlavH
  Date: 8/25/2016
  Time: 12:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<link rel="stylesheet" type="text/css" href="style.css">
  <head>
    <title>My First JSP</title>
  </head>
  <body>

  <p>

      Name: <%= request.getParameter("name") %> <br>
      Password: <%= request.getParameter("password")%> <br>
      <br>
      Plain text -.-'


  </p>
  <p><%out.println(new java.util.Date()); %></p>
  <p><script>document.write(new Date());</script></p>
  </body>
</html>
