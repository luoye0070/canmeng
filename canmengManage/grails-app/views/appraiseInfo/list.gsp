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
  <title>评价管理</title>

<script type="text/javascript" charset="utf-8">
    var datagrid;
    $(function() {
        datagrid = $('#datagrid').datagrid({
            url : "${resource()}/AppraiseInfo/datagrid",
            pagination : true,
            pagePosition : 'bottom',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40 ],
            fit : false,
            fitColumns : true,
            nowrap : false,
            border : false,
            idField : 'id',

            sortName : 'id',
            sortOrder : 'desc',
            frozenColumns : [ [
                {title : '编号',field : 'orderId',sortable : true,checkbox : true}
            ] ],
            columns : [ [
                {field:'name',title:'名称'},
                {field:'type',title:'类型'},
                {field:'hygienicQuality',title:'卫生质量评分'},
                {field:'serviceAttitude',title:'服务态度评分'},
                {field:'deliverySpeed',title:'送餐速度评分'},
                {field:'taste',title:'味道评分'},
                {field:'whole',title:'总体评分'},
                {field:'content',title:'评价内容'},
                {field:'appraiseTime',title:'评价时间'},
                {field:'userId',title:'评价人用户ID'},
                {field:'isAnonymity',title:'是否匿名评价'}
            ] ],
            toolbar : [
                {text:'新增',iconCls:'icon-add',handler:function(){ addEasyui(); }}, '-',
                {text:'编辑',iconCls:'icon-edit',handler:function(){ editEasyui(); }}, '-',
                {text:'删除',iconCls:'icon-remove',handler:function(){ delEasyui(); }}, '-'
            ]
        });
    });
    function addEasyui() {
        var p = parent.sy.dialog({
            title : '新增 评价',
            href : "${resource()}/AppraiseInfo/edit",
            width : 600,
            height : 280,
            buttons : [ {
                text : '新增',
                handler : function() {
                    var f = p.find('form');
                    f.form({
                        url : "${resource()}/AppraiseInfo/addSaveHandle",
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
                title : '编辑 评价信息',
                href : "${resource()}/AppraiseInfo/edit",
                width : 600,
                height : 280,
                buttons : [ {
                    text : '编辑',
                    handler : function() {
                        var f = p.find('form');
                        f.form('submit',{
                            url : "${resource()}/AppraiseInfo/editUpdHandle",
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
                        url : "${resource()}/AppraiseInfo/del",
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

</script>
</head>
<body class="easyui-layout" data-options="fit:true">
<div data-options="region:'center',border:false" style="overflow: hidden;">
    <table id="datagrid"></table>
</div>
</body>
</html>