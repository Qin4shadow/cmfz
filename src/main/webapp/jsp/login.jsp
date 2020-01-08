<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript">
       $(function () {
           //点击切换验证码
           $("#img").click(function(){
               $("#img").prop("src","${pageContext.request.contextPath}/admin/createImg?a="+ new Date().getTime());
           });


           $("#log").click(function () {
               $.post(
                   "${pageContext.request.contextPath}/admin/login",
                   $("#loginForm").serialize(),
                   function (result) {
                      if(result.status=="200"){
                          location.href = "${pageContext.request.contextPath}/jsp/main.jsp";
                      }else {
                          $("#msg").html("<font color='red'>"+result.msg+"</font>");
                      }
                   },
                   "json"
               );
           });
       });
    </script>
</head>
<body style=" background: url(../img/3.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input id="name" type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username">
            </div>
            <div class="form-group">
                <input id="pass" type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <div class="form-group">
                <input id="code" type="text" class="form-control" placeholder="验证码" autocomplete="off" name="code">
                <img id="img"  src="${pageContext.request.contextPath }/admin/createImg" alt="" style="width: 40%">
            </div>
            <span id="msg"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default form-control">注册</button>
            </div>

        </div>
        </form>
    </div>
</div>
</body>
</html>
