<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <script type="text/javascript">
        $(function () {
            $("#albumTable").jqGrid({
                url:"${pageContext.request.contextPath}/album/selectPageAlbum",
                datatype:"json",
                colNames:["ID","标题","封面","分数","作者","播音","章节数","描述","状态","创建时间"],
                colModel:[
                    {name:"id",align:"center",hidden:true},
                    {name:"title",align:"center",editable:true,editrules:{required:true}},
                    {name:"picture",align:"center",editable: true,formatter:function(data){
                            return "<img style='width: 180px;height: 80px' src='"+data+"'>"
                        },edittype:"file",editoptions: {enctype:"multipart/form-data"}},
                    {name:"score",align:"center",editable:true,editrules:{required:true}},
                    {name:"author",align:"center",editable:true,editrules:{required:true}},
                    {name:"broadcast",align:"center",editable:true,editrules:{required:true}},
                    {name:"count",align:"center",editable:true,editrules:{required:true}},
                    {name:"desc",align:"center",editable:true,editrules:{required:true}},
                    {name:"status",align:"center",editable:true,formatter:function (data) {
                            if(data=="1"){
                                return "激活";
                            }else{
                                return "冻结";
                            }
                        },editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}},
                    {name:"createDate",align:"center",editable:true,editrules:{required:true},edittype: "date"},
                ],
                sortname : 'id',
                mtype : "post",
                autowidth:true,
                pager:"#albumPage",
                rowNum:3,
                rowList:[3,5,10,20],
                viewrecords:true,
                caption : "专辑信息",
                multiselect:true,
                styleUI:"Bootstrap",
                // 开启子表格支持
                subGrid : true,
                // subgrid_id:父级行的Id  row_id:当前的数据Id
                subGridRowExpanded : function(subgrid_id, row_id) {
                    // 调用生产子表格的方法
                    // 生成表格 | 生产子表格工具栏
                    addSubgrid(subgrid_id,row_id);
                },
                // 删除表格的方法
                subGridRowColapsed : function(subgrid_id, row_id) {
                },
                height:"500px",
                editurl:"${pageContext.request.contextPath}/album/saveAlbum"
            }).jqGrid("navGrid","#albumPage",{edit: true, add: true, del: true,edittext:"编辑",addtext:"添加",deltext:"删除"},
                {closeAfterEdit: true},
                {closeAfterAdd: true,
                    afterSubmit:function (response,postData) {
                        var albumId = response.responseJSON.albumId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/album/uploadAlbum",
                            type:"post",
                            datatype: "json",
                            // 发送添加图片的id至controller
                            data:{albumId:albumId},
                            fileElementId:"picture",
                            success:function (data) {
                                $("#albumTable").trigger("reloadGrid");
                            }
                        });
                        //防止页面报错
                        return postData;
                    }
                },
                {closeAfterDel: true}
            );
        });
        function addSubgrid(subgrid_id,row_id){
            // 声明子表格Id
            var sid = subgrid_id + "table";
            // 声明子表格工具栏id
            var spage = subgrid_id + "page";
            $("#"+subgrid_id).html("<table id='" + sid + "' class='scroll'></table><div id='"+ spage +"' style='height: 50px'></div>");
            $("#"+sid).jqGrid({
                // 指定查询的url 根据专辑id 查询对应章节 row_id: 专辑id
                url:"${pageContext.request.contextPath}/chapter/selectPageChapter",
                mtype: "post",
                postData: {albumId:row_id},
                datatype:"json",
                colNames:["ID","标题","大小","时长","创建时间","操作"],
                colModel:[
                    {name:"id",align:"center",hidden:true},
                    {name:"title",align:"center",editable:true,editrules:{required:true}},
                    {name:"size",align:"center"},
                    {name:"time",align:"center"},
                    {name:"createTime",align:"center",editable:true,editrules:{required:true},edittype: "date"},
                    {name:"url",align:"center",editable:true,edittype:"file",editoptions: {enctype:"multipart/form-data"},formatter:function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"download('"+cellvalue+"')\">下载</button>&nbsp;&nbsp;";
                            button+= "<button type=\"button\" class=\"btn btn-success\" onclick=\"inline('"+cellvalue+"')\">在线播放</button>";
                            return button;
                        }},

                ],
                sortname : 'id',
                autowidth: true,
                pager:"#"+spage,
                rowNum:3,
                rowList:[3,5,10,20],
                viewrecords:true,
                caption : "专辑章节信息",
                multiselect:true,
                styleUI:"Bootstrap",
                height:"300",
                editurl:"${pageContext.request.contextPath}/chapter/saveChapter?albumId="+row_id,
            }).jqGrid("navGrid","#"+spage,{edit: true, add: true, del: true,edittext:"编辑",addtext:"添加",deltext:"删除"},
                {closeAfterEdit: true},
                {closeAfterAdd: true,
                    afterSubmit:function (response,postData) {
                        var chapterId = response.responseJSON.chapterId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chapter/uploadChapter",
                            type:"post",
                            datatype: "json",
                            // 发送添加图片的id至controller
                            fileElementId:"url",
                            data:{chapterId:chapterId},
                            success:function (data) {
                                $("#"+sid).trigger("reloadGrid");
                                $("#albumTable").trigger("reloadGrid");
                            }
                        });
                        //防止页面报错
                        return postData;
                    }
                },
                {closeAfterDel: true}
            );
        }
        function inline(cellValue) {
            $("#voice").prop("src",cellValue);
            $("#modal1").modal("show");
        }
        function download(cellValue) {
            location.href = "${pageContext.request.contextPath}/chapter/downloadChapter?url="+cellValue;
        }

    </script>
</head>
<body>
    <div class="page-header">
        <h4>专辑管理</h4>
    </div>
    <ul class="nav nav-tabs">
        <li><a>专辑信息</a></li>
    </ul>
    <table id="albumTable"></table>
    <div id="albumPage" style="height: 50px"></div>
    <%-- 模态框 --%>
    <div id="modal1" class="modal fade bs-example-modal-sm" aria-hidden="true" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
        <div class="modal-dialog modal-sm" role="document">
            <audio id="voice" src="" controls="controls">
            </audio>
        </div>
    </div>
</body>