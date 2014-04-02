<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="lj.enumCustom.VerifyStatus; lj.data.PapersInfo; lj.enumCustom.ReCode" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <title>饭店认证</title>
    <style type="text/css">
    .mc_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }
    </style>
</head>
<body>
<div class="mc_main">
    <div  class="span10" style="margin-left: 10px;margin-top: 10px;">

        <g:render template="../layouts/shopMenu"/>
        <g:if test="${errors != null && errors.size() > 0}">
            <div class="alert alert-error" STYLE="color: RED">
                <g:message error="${errors.get(0)}" message=""/>
            </div>
        </g:if>
        <g:if test="${flash.message}">
            <div class="alert alert-info">
                ${flash.message}
            </div>
        </g:if>

        <a href="${createLink(controller: "authentication", action: "editAuth")}" class="btn btn-primary">新增认证</a>
        <br/>
        <g:if test="${reInfo.recode == ReCode.OK}">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>证件快照</th>
                    <th>描述</th>
                    <th>证件类型</th>
                    <th>审核状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${reInfo.authList}">
                    <tr>
                        <td><img
                                src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: it.image])}"/>
                        </td>
                        <td>${it.description}</td>
                        <td>${PapersInfo.get(it.papersId).name}</td>
                        <td>${VerifyStatus.getLabel(it.verifyStatus)}</td>
                        <td><a href="${createLink(controller: "authentication", action: "delAuth", params: [ids: it.id])}" class="btn btn-link">删除</a>
                            <a href="${createLink(controller: "authentication", action: "editAuth", params: [id: it.id])}" class="btn btn-link">修改</a>
                        </td>
                    </tr>
                </g:each>

                </tbody>
            </table>


            <ul>

            </ul>
        </g:if>
    </div>
</div>
</body>
</html>