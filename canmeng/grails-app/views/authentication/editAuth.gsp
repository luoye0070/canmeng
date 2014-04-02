<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-11-1
  Time: 下午9:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
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
        <a href="${createLink(controller: "authentication", action: "authList")}" class="btn btn-primary">返回认证列表</a>
        <br/>
        <form class="form-horizontal" method="post" action="editAuth" enctype="multipart/form-data">
            <fieldset>
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
            <!--id-->
                <input type="hidden" id="authId" name="authId" value="${authenticationInfo?.id?:0}"/>

                <!--认证图片-->
                <div class="control-group">
                    <label class="control-label" for="image"> <g:message code="authenticationInfo.image.label" default="image" />
                        <span class="required-indicator">*</span>
                    </label>
                    <div class="controls">
                        <g:if test="${authenticationInfo?.image}"><img src="${createLink(controller: "imageShow",action: "downloadThumbnail",params: [imgUrl:authenticationInfo?.image]) }"/></g:if>
                        <input type="file" id="image" name="imgfile"/>
                    </div>
                </div>




                <!--认证描述-->
                <div class="control-group">
                    <label class="control-label" for="description"><g:message code="authenticationInfo.description.label" default="description" />
                    </label>
                    <div class="controls">
                        <input type="text" id="description" name="description" value="${authenticationInfo?.description}"/>
                    </div>
                </div>

                <!--证件类型-->
                <div class="control-group">
                    <label class="control-label" for="papersId"><g:message code="authenticationInfo.papersId.label" default="papersId" />
                        <span class="required-indicator">*</span>
                    </label>
                    <div class="controls">
                        <select id="papersId" name="papersId" value="${authenticationInfo?.papersId}">
                            <g:each in="${papersList}" var="papers" status="i">
                                <option value="${papers.id}">${papers.name}</option>
                            </g:each>
                        </select>
                    </div>
                </div>


                <!--备注信息-->
                <div class="control-group">
                    <label class="control-label" for="remark"><g:message code="authenticationInfo.remark.label" default="remark" />
                    </label>
                    <div class="controls">
                        <input type="text" id="remark" name="remark" value="${authenticationInfo?.remark}"/>
                    </div>
                </div>

                <div  class="form-actions">
                    <a href="${createLink(controller: "authentication", action: "authList")}" class="btn send_btn">取消</a>
                    <g:submitButton name="create"  class="btn send_btn"  value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </div>
                </fieldset>
            </form>
        </div>
    </div>


</body>
</html>