<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#guruTable").jqGrid({
            url:"${pageContext.request.contextPath}/guru/selectPageGuru",
            datatype:"json",
            colNames:["ID","姓名","头像","状态","法号"],
            colModel:[
                {name:"id",align:"center",hidden:true},
                {name:"name",align:"center",editable:true,editrules:{required:true}},
                {name:"photo",align:"center",editable: true,formatter:function(data){
                        return "<img style='width: 180px;height: 80px' src='"+data+"'>"
                    },edittype:"file",editoptions: {enctype:"multipart/form-data"}},
                {name:"status",align:"center",editable:true,formatter:function (data) {
                        if(data=="1"){
                            return "激活";
                        }else{
                            return "冻结";
                        }
                    },editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}},

                {name:"nickName",align:"center",editable:true,editrules:{required:true}},
            ],
            sortname : 'id',
            mtype : "post",
            autowidth:true,
            pager:"#guruPage",
            rowNum:3,
            rowList:[3,5,10,20],
            viewrecords:true,
            caption : "用户信息",
            multiselect:true,
            styleUI:"Bootstrap",
            height:"500px",
            editurl:"${pageContext.request.contextPath}/guru/saveGuru"
        }).jqGrid("navGrid","#guruPage",{edit: true, add: true, del: true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            {closeAfterEdit: true},
            {closeAfterAdd: true,
                afterSubmit:function (response,postData) {
                    var guruId = response.responseJSON.guruId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/guru/uploadGuru",
                        type:"post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data:{guruId:guruId},
                        fileElementId:"photo",
                        success:function (data) {
                            $("#guruTable").trigger("reloadGrid");
                        }
                    });
                    //防止页面报错
                    return postData;
                }
            },
            {closeAfterDel: true}
        );
    });

</script>

<div class="page-header">
    <h4>上师管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>上师信息</a></li>

</ul>
<div class="panel">
    <table id="guruTable"></table>
    <div id="guruPage" style="height: 30px"></div>
</div>