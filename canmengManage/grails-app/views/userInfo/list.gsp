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
  <title>用户管理</title>

<script type="text/javascript" charset="utf-8">
    var datagrid;
    $(function() {
        datagrid = $('#datagrid').datagrid({
            url : "${resource()}/userInfo/datagrid",
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
                {title : '编号',field : 'id',sortable : true,checkbox : true}
            ] ],
            columns : [ [
                {field:'userName',title:'用户名'},
                {field:'passWord',title:'密码'}  ,
                {field:'email',title:'邮箱'}      ,
                {field:'emailEnabled',title:'邮箱是否通过验证'}   ,
                {field:'phone',title:'手机号码'} ,
                {field:'phoneEnabled',title:'手机号是否通过验证'} ,
                {field:'loginTime',title:'最近登录时间'} ,
                {field:'question',title:'密码找回问题'} ,
                {field:'answer',title:'密码找回问题回答'},
                {field:'loginIp',title:'最近登录ip'},
                {field:'enabled',title:'用户有效标志'}   ,
                {field:'defaultAddrId',title:'默认地址id'}


            ] ],
            toolbar : [
                {text:'新增',iconCls:'icon-add',handler:function(){ addEasyui(); }},
                {text:'编辑',iconCls:'icon-edit',handler:function(){ editEasyui(); }},
                {text:'删除',iconCls:'icon-remove',handler:function(){ delEasyui(); }},
                {text:'审核',iconCls:'icon-edit',handler:function(){ verifyEasyui(); }}
            ]
        });
    });
    function addEasyui() {
        var p = parent.sy.dialog({
            title : '新增 用户',
            href : "${resource()}/UserInfo/edit",
            width : 600,
            height : 280,
            buttons : [ {
                text : '新增',
                handler : function() {
                    var f = p.find('form');
                    f.form({
                        url : "${resource()}/UserInfo/addSaveHandle",
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
                title : '编辑 用户',
                href : "${resource()}/UserInfo/edit",
                width : 600,
                height : 280,
                buttons : [ {
                    text : '编辑',
                    handler : function() {
                        var f = p.find('form');
                        f.form('submit',{
                            url : "${resource()}/UserInfo/editUpdHandle",
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
                        url : "${resource()}/UserInfo/del",
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
    function verifyEasyui() {
        var rows = datagrid.datagrid('getSelections');
        var ids = [];
        if (rows.length > 0) {
            parent.sy.messagerConfirm('请确认', '您要审核当前所选记录?', function(r) {
                if (r) {
                    for ( var i = 0; i < rows.length; i++) {
                        ids.push(rows[i].id);
                    }
                    $.ajax({
                        url : "${resource()}/UserInfo/verify",
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