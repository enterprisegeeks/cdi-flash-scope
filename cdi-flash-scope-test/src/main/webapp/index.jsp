<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>test page</title>
        <% System.out.println("JSP Called."); %>
    </head>
    <body>
        <div>${flashBean.message}</div>
    </body>
</html>
