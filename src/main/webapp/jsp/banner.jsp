<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript">
    $(function () {
        $("#bannerJqGrid").jqGrid({
            url:"${pageContext.request.contextPath}/banner/selectPageBanner",
            datatype:"json",
            colNames:["ID","标题","图片","超链接","创建日期","描述","状态"],
            colModel:[
                {name:"id",align:"center",hidden:true},
                {name:"title",align:"center",editable:true,editrules:{required:true}},
                {name:"url",align:"center",editable: true,formatter:function(data){
                        return "<img style='width: 180px;height: 80px' src='"+data+"'>"
                    },edittype:"file",editoptions: {enctype:"multipart/form-data"}},
                {name:"href",align:"center",editable: true},
                {name:"createDate",align:"center",editable:true,editrules:{required:true},edittype: "date"},
                {name:"desc",align:"center",editable:true,editrules:{required:true}},
                {name:"status",align:"center",editable:true,formatter:function (data) {
                        if(data=="1"){
                            return "激活";
                        }else{
                            return "冻结";
                        }
                    },editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}},
            ],
            sortname : 'id',
            mtype : "post",
            autowidth:true,
            pager:"#pager",
            rowNum:3,
            rowList:[3,5,10,20],
            viewrecords:true,
            caption : "轮播图信息",
            multiselect:true,
            styleUI:"Bootstrap",
            height:"500px",
            editurl:"${pageContext.request.contextPath}/banner/saveBanner"
        }).jqGrid("navGrid","#pager",{edit: true, add: true, del: true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            {closeAfterEdit: true},
            {closeAfterAdd: true,
                afterSubmit:function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/uploadBanner",
                        type:"post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data:{bannerId:bannerId},
                        fileElementId:"url",
                        success:function (data) {
                            $("#bannerJqGrid").trigger("reloadGrid");
                        }
                    });
                    //防止页面报错
                    return postData;
                }
            },
            {closeAfterDel: true}
        );
        //导出轮播图信息
        $("#outBanner").click(function () {
            $.ajax({
                url:"${pageContext.request.contextPath}/banner/outBannerInformation",
                type:"post",
                datatype: "json",
                success:function (data) {
                    if(data.status=="200"){
                        alert("下载成功，请到'F:\\资料\\后期项目\\day7-poiEasyExcel\\示例\\'查看");
                    }
                }
            });
        });
        //导入轮播图信息
        $("#inBanner").click(function () {
            $("#bannerForm")[0].reset();
            $("#myModal2").modal("show");
        });
        $("#subBanner").click(function () {
            $.ajaxFileUpload({
                url:"${pageContext.request.contextPath}/banner/inBannerInformation",
                type:"post",
                datatype: "json",
                fileElementId: "inputBanner",
                success:function (data) {

                }
            });
        });
        //Excel模板下载
        $("#outBannerModel").click(function () {
            $.ajax({
                url:"${pageContext.request.contextPath}/banner/outBannerModel",
                type:"post",
                datatype: "json",
                success:function (data) {
                    if(data.status=="200"){
                        alert("下载成功，请到'F:\\资料\\后期项目\\day7-poiEasyExcel\\示例\\'查看");
                    }
                }
            });
        });

    });

</script>
<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>
    <li><a id="inBanner">导入轮播图信息</a></li>
    <li><a id="outBanner">导出轮播图信息</a></li>
    <li><a id="outBannerModel">Excel模板下载</a></li>
</ul>

<div class="panel">
    <table id="bannerJqGrid"></table>
    <div id="pager" style="height: 30px"></div>
</div>
