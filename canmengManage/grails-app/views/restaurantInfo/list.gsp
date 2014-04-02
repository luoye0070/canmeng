<%--
  Created by IntelliJ IDEA.
  User: ysm
  Date: 13-7-27
  Time: 下午9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="head">
  <title>店铺管理</title>

<script type="text/javascript" charset="utf-8">
    var datagrid;
    $(function() {
        datagrid = $('#datagrid').datagrid({
            url : "${resource()}/restaurantInfo/datagrid",
            pagination : true,
            pagePosition : 'bottom',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40 ],
            fit : true,
            fitColumns : true,
            nowrap : false,
            border : false,
            idField : 'id',

            sortName : 'id',
            sortOrder : 'desc',
            frozenColumns : [ [
                {title : '编号',field : 'id',sortable : true,checkbox : true}
            ] ],
            columns : [ [
                {field:'name',title:'店名'},
                {field:'image',title:'招牌图片'},
                {field:'areaId',title:'区号'}  ,
                {field:'province',title:'省'} ,
                {field:'city',title:'市'} ,
                {field:'area',title:'区'} ,
                {field:'street',title:'街道'} ,
                {field:'longitude',title:'经度'} ,
                {field:'latitude',title:'维度'}  ,
                {field:'phone',title:'电话'}  ,
                {field:'masterName',title:'店主姓名'},
                {field:'userId',title:'用户id'}  ,
                {field:'shopHoursBeginTime',title:'营业时间起'}  ,
                {field:'shopHoursEndTime',title:'营业时间止'}  ,
                {field:'enabled',title:'开启关闭状态'}  ,
                {field:'need2Code',title:'进店吃饭是否必须扫描二维码'}  ,
                {field:'cuisineId',title:'菜系'}  ,
                {field:'freight',title:'外卖运费'} ,
                {field:'freeFreight',title:'外卖运费条件'}  ,
                {field:'packId',title:'packId'},
                {field:'imageSpaceSize',title:'图片空间大小'},
                {field:'verifyStatus',title:'审核状态'}
            ] ],
            toolbar : [
                {text:'新增',iconCls:'icon-add',handler:function(){ addEasyui(); }}, '-',
                {text:'编辑',iconCls:'icon-edit',handler:function(){ editEasyui(); }}, '-',
                {text:'删除',iconCls:'icon-remove',handler:function(){ delEasyui(); }},'-',
                {text:'审核',iconCls:'icon-edit',handler:function(){ restaurantVerify(); }}
            ]
        });
    });
    function addEasyui() {
        var p = parent.sy.dialog({
            title : '新增 店铺',
            href : "${resource()}/restaurantInfo/edit",
            width : 600,
            height : 280,
            buttons : [ {
                text : '新增',
                handler : function() {
                    var f = p.find('form');
                    f.form({
                        url : "${resource()}/restaurantInfo/addSaveHandle",
                        success : function(d) {
                            var json = $.parseJSON(d);
                            if (json.success) {
                                datagrid.datagrid('reload');
                                p.dialog('close');
                            }
                            parent.sy.messagerShow({
                                msg : json.msg,
                                title : '提示'
                            });
                        }
                    });
                    f.submit();
                }
            } ,{
                text:'取消',
                handler:function(){
                    p.dialog('close');
                }
            }],
            onLoad : function() {

            }
        });
    }

    function editEasyui() {
        var rows = datagrid.datagrid('getSelections');

        if (rows.length == 1) {
            var p = parent.sy.dialog({
                title : '编辑 店铺',
                href : "${resource()}/restaurantInfo/edit",
                width : 600,
                height : 280,
                buttons : [ {
                    text : '编辑',
                    handler : function() {
                        var f = p.find('form');
                        f.form('submit',{
                            url : "${resource()}/restaurantInfo/editUpdHandle",
                            success : function(d) {
                                var json = $.parseJSON(d);
                                if (json.success) {
                                    datagrid.datagrid('reload');
                                    p.dialog('close');
                                }
                                parent.sy.messagerShow({
                                    msg : json.msg,
                                    title : '提示'
                                });
                            }
                        });
                    }
                },{
                    text:'取消',
                    handler:function(){
                        p.dialog('close');
                    }
                } ],
                onLoad : function() {
                    var f = p.find('form');
                    f.form('load', rows[0]);
                    f.find('input[name=ids]').val(rows[0].id);
                }
            });
        } else if (rows.length > 1) {
            parent.sy.messagerAlert('提示', '同一时间只能编辑一条记录!', 'error');
        } else {
            parent.sy.messagerAlert('提示', '请选择要编辑的记录!', 'error');
        }
    }
    function delEasyui() {
        var rows = datagrid.datagrid('getSelections');
        var ids = [];
        if (rows.length > 0) {
            parent.sy.messagerConfirm('请确认', '您要删除当前所选记录?', function(r) {
                if (r) {
                    for ( var i = 0; i < rows.length; i++) {
                        ids.push(rows[i].id);
                    }
                    $.ajax({
                        url : "${resource()}/restaurantInfo/del",
                        data : {
                            ids : ids.join(',')
                        },
                        dataType : 'json',
                        success : function(d) {
                            datagrid.datagrid('load');
                            datagrid.datagrid('unselectAll');
                            parent.sy.messagerShow({
                                title : '提示',
                                msg : d.msg
                            });
                        }
                    });
                }
            });
        } else {
            parent.sy.messagerAlert('提示', '请勾选要删除的记录！', 'error');
        }
    }
    function restaurantVerify() {
        var rows = datagrid.datagrid('getSelections');
        var ids = [];
        if (rows.length > 0) {
            parent.sy.messagerConfirm('请确认', '您要审核当前所选记录?', function(r) {
                if (r) {
                    for ( var i = 0; i < rows.length; i++) {
                        ids.push(rows[i].id);
                    }
                    $.ajax({
                        url : "${resource()}/restaurantInfo/verify",
                        data : {
                            ids : ids.join(',')
                        },
                        dataType : 'json',
                        success : function(d) {
                            datagrid.datagrid('load');
                            datagrid.datagrid('unselectAll');
                            parent.sy.messagerShow({
                                title : '提示',
                                msg : d.msg
                            });
                        }
                    });
                }
            });
        } else {
            parent.sy.messagerAlert('提示', '请勾选要审核的记录！', 'error');
        }
    }
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
<div data-options="region:'center',border:false" style="overflow: hidden;">
    <table id="datagrid"></table>
</div>
</body>
</html>