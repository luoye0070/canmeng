<!doctype html>
<html>
  <head>
    <title>餐萌数据管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <!--     easyui控件-->
    <link id="easyuiTheme" rel="stylesheet" href="${resource()}/js/jquery-easyui-1.3.1/themes/${themeName?:'default'}/easyui.css"  type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'js/jquery-easyui-1.3.1/themes', file: 'icon.css')}" type="text/css">
  <g:javascript src="jquery-easyui-1.3.1/jquery-1.8.0.min.js"/>
  <g:javascript src="jquery-easyui-1.3.1/jquery.easyui.min.js"/>
  <g:javascript src="jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"/>

  <!--     easyui portal插件-->
  <link rel="stylesheet" href="${resource(dir: 'js/jquery-easyui-portal', file: 'portal.css')}" type="text/css">
  <g:javascript src="jquery-easyui-portal/jquery.portal.js"/>
  <!--     cookie插件-->
  <g:javascript src="jquery.cookie.js"/>
  <!--     xhEditor插件库-->
  <g:javascript src="xheditor-1.1.14/xheditor-1.1.14-zh-cn.min.js"/>
  <!--     自己定义的样式和JS扩展-->
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'syCss.css')}" type="text/css">
  <g:javascript src="syUtil.js"/>

</head>
<body class="easyui-layout">
  <div data-options="region:'north',href:'${resource()}/easyuiLayout/north'" style="height: 34px;overflow: hidden;" class="panel-body panel-body-noheader layout-body"></div>
  <div data-options="region:'west',title:'功能导航',href:'${resource()}/easyuiLayout/west'" style="width: 200px;overflow: hidden;"></div>
  <div data-options="region:'center',title:'主界面',href:'${resource()}/easyuiLayout/center'" style="overflow: hidden;"></div>
</body>
</html>
