<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
        var loginDialog;
        var loginInputForm;

        $(function() {

                var formParam = {
                        url : '${resource()}/adminInfo/login',
                        success : function(data) {

                                //console.log(data);

                                var d = $.parseJSON(data);
                                if (d.type==1) {
                                        loginDialog.dialog('close');

                                        $('#sessionInfoDiv').html(sy.fs('<a href="#"  onclick="logout(true);">退出系统</a>[<strong>{0}</strong>]，欢迎你！', d.username));

                                        $.messager.show({
                                          msg : '登陆成功!',
                                          title : '提示'
                                        });

                                        ctrlTree.tree({url : '${resource()}/tmenu/treegrid?b=true'});

                                }else if(d.type==2){
                                   loginDialog.dialog('close');
                                   $.messager.show({
                                        msg : '用户已经登陆!',
                                        title : '提示'
                                   });

                                   ctrlTree.tree({url : '${resource()}/tmenu/treegrid?b=true'});

                                }else{
                                   $.messager.show({
                                        msg : '登陆失败',
                                        title : '提示'
                                  });
                                }
                        }
                };

                loginInputForm = $('#loginInputForm').form(formParam);

                loginDialog = $('#loginDialog').show().dialog({
                        closable : false,
                        title : '登录',
                        modal : true,
                        buttons : [  {
                                text : '登录',
                                handler : function() {
                                       loginInputForm.submit();
                                }
                        } ],
                        onOpen : function() {
                                var t = $(this);
                                window.setTimeout(function() {
                                        t.find('input[name=username]').focus();
                                }, 0);
                        }
                });


                $('form input').bind('keyup', function(event) {/* 增加回车提交功能 */
                        if (event.keyCode == '13') {
                                $(this).submit();
                        }
                });
        });

        function flashOnlineDatagrid() {/*刷新在线列表*/

        }
</script>
<div id="loginDialog" style="width:250px;height:180px;display: none;overflow: hidden;">
  <div align="center">
    <form id="loginInputForm" method="post">
      <table>
        <tr>
          <th style="text-align: right;">登录名</th>
          <td><input name="username" class="easyui-validatebox" data-options="required:true,missingMessage:'请填写登录名称'" value="admin" /></td>
        </tr>
        <tr>
          <th style="text-align: right;">密码</th>
          <td><input name="password" type="password" class="easyui-validatebox" data-options="required:true,missingMessage:'请填写登录密码'" value="111111" style="width: 150px;" /></td>
        </tr>
      </table>
    </form>
  </div>
</div>