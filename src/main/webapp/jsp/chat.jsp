<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script src="../kindeditor/kindeditor-all-min.js"></script>
    <script src="../kindeditor/lang/zh-CN.js"></script>
    <script src="../echarts/echarts.min.js"></script>
    <script src="../echarts/china.js" charset="UTF-8"></script>
    <!-- 将https协议改为http协议 -->
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-6aeb906ba81c4dc693f78caa76fe418e", //替换为您的应用appkey
        });
        goEasy.subscribe({
            channel: "cmfz", //替换为您自己的channel
            onMessage: function (message) {
                var data = JSON.parse(message.content);
                $("#textArea").append(data);
            }
        });

        function goSub() {
            var message = $("#inTest").val();
            goEasy.publish({
                channel: "cmfz", //替换为您自己的channel
                message: message, //替换为您想要发送的消息内容
            });
        }
</script>

</head>
<body>

<div class="page-header">
    <h4>聊天室</h4>
</div>

<form class="col-xs-8">
    <div class="form-group">
        <textarea id="textArea" class="form-control" rows="20"></textarea>
    </div>

    <div class="input-group">
        <input id="inTest" type="text" class="form-control" >
        <span class="input-group-btn">
        <button onclick="goSub()" class="btn btn-default" type="button">发送</button>
        </span>
    </div>
</form>
</body>
</html>