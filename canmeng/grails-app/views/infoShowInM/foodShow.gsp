<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-12-8
  Time: 下午1:54
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>菜谱详情</title>
</head>
<body>
<!--菜单信息-->
<!--图片-->
<div class="fieldcontain ${hasErrors(bean: foodInfo, field: 'image', 'error')} ">
    <label for="imageLabel">
        <g:message code="foodInfo.image.label" default="Image" />
    </label>
    <img id="imageLabel" src="${createLink(controller: "imageShow",action: "downloadThumbnail",params: [imgUrl:foodInfo?.image])}"/>
</div>
<!--名称-->
<div class="fieldcontain ${hasErrors(bean: foodInfo, field: 'name', 'error')} required">
    <label for="nameLabel">
        <g:message code="foodInfo.name.label" default="Name" />
        <span class="required-indicator">*</span>
    </label>
    <label id="nameLabel" maxlength="32" required="" value="">${foodInfo?.name}</label>
</div>
<!--价格-->
<div class="fieldcontain ${hasErrors(bean: foodInfo, field: 'price', 'error')} required">
    <label for="priceLabel">
        <g:message code="foodInfo.price.label" default="Price" />
        <span class="required-indicator">*</span>
    </label>
    <label id="priceLabel" value="" required="">${fieldValue(bean: foodInfo, field: 'price')}</label>
</div>
<!--原价-->
<div class="fieldcontain ${hasErrors(bean: foodInfo, field: 'originalPrice', 'error')} ">
    <label for="originalPriceLabel">
        <g:message code="foodInfo.originalPrice.label" default="Original Price" />

    </label>
    <label id="originalPriceLabel" value="">${fieldValue(bean: foodInfo, field: 'originalPrice')}</label>
</div>
<!--详细信息-->
<div class="fieldcontain ${hasErrors(bean: foodInfo, field: 'description', 'error')} ">
    <label for="description">
        <g:message code="foodInfo.description.label" default="description" />
    </label>
    <label id="description" value="">${fieldValue(bean: foodInfo, field: 'description')}</label>
</div>
</body>
</html>