<div class="navbar navbar-inner">
<ul class="nav nav-tabs">
    <g:if test="${controllerName=="shop" && actionName=="shopInfo"}">
        <li class="active"><g:link controller="shop" action="shopInfo">我的饭店</g:link></li>
    </g:if>
    <g:else>
        <li><g:link controller="shop" action="shopInfo">我的饭店</g:link></li>
    </g:else>

    <g:if test="${controllerName=="shop" && actionName=="editShopInfo"}">
        <li class="active"><g:link controller="shop" action="editShopInfo">修改饭店资料</g:link></li>
    </g:if>
    <g:else>
        <li><g:link controller="shop" action="editShopInfo">修改饭店资料</g:link></li>
    </g:else>

    <g:if test="${controllerName=="authentication" && actionName in ["authList","editAuth"]}">
        <li class="active"><g:link controller="authentication" action="authList">饭店认证</g:link></li>
    </g:if>
    <g:else>
        <li><g:link controller="authentication" action="authList">饭店认证</g:link></li>
    </g:else>

    <g:if test="${controllerName=="foodManage" && actionName in ["foodList","editFoodInfo"]}">
        <li class="active"><g:link controller="foodManage" action="foodList">菜谱管理</g:link></li>
    </g:if>
    <g:else>
        <li><g:link controller="foodManage" action="foodList">菜谱管理</g:link></li>
    </g:else>

    <g:if test="${controllerName=="tableManage" && actionName in ["tableList","editTableInfo"]}">
        <li class="active"><g:link controller="tableManage" action="tableList">桌位管理</g:link></li>
    </g:if>
    <g:else>
        <li><g:link controller="tableManage" action="tableList">桌位管理</g:link></li>
    </g:else>

    <g:if test="${controllerName=="imageSpace" && actionName in ["imageList","upload","editImageClass","upload","imageList","editImageClass","imageClassList"]}">
        <li class="active"><g:link controller="imageSpace" action="imageList">图片空间</g:link></li>
    </g:if>
    <g:else>
        <li><g:link controller="imageSpace" action="imageList">图片空间</g:link></li>
    </g:else>

    <g:if test="${controllerName=="staffManage" && actionName in ["staffList","editStaffInfo"]}">
        <li class="active"><g:link controller="staffManage" action="staffList">工作人员管理</g:link></li>
    </g:if>
    <g:else>
        <li><g:link controller="staffManage" action="staffList">工作人员管理</g:link></li>
    </g:else>

    <g:if test="${controllerName=="customerRelations" && actionName in ["crList","editCr"]}">
        <li class="active"><g:link controller="customerRelations" action="crList">客户关系</g:link></li>
    </g:if>
    <g:else>
        <li><g:link controller="customerRelations" action="crList">客户关系</g:link></li>
    </g:else>

    <g:if test="${controllerName=="shop" && actionName in ["editReserveType"]}">
        <li class="active"><g:link controller="shop" action="editReserveType">预定类型设置</g:link></li>
    </g:if>
    <g:else>
        <li><g:link controller="shop" action="editReserveType">预定类型设置</g:link></li>
    </g:else>

</ul>
</div>