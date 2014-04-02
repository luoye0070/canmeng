<style type="text/css">
.mcml_main{
    width: 228px;
    height: auto;
    float: left;
    margin: 0px 10px;
    border: 1px solid #DBD7D8;
}
    .mcmlm_menu{
        width: 228px;
        height: auto;
        float: left;
    }
    .mcmlmm_title{
        width: 228px;
        height: 50px;
        float: left;

    }
.mcmlmm_title_ico{
    width: 30px;
    height: 30px;
    margin: 10px;
    float: left;
}
.mcmlmm_title_text{
    width: 158px;
    height: 30px;
    margin: 10px;
    line-height: 30px;
    float: left;
    font-size: 18px;
}
.mcmlmm_menu{
    width: 228px;
    height: auto;
    float: left;
}
.mcmlmm_menu li{
    width: 208px;
    height: 30px;
    margin: 5px 10px;
    line-height: 30px;
    background-color: #F4F4F4;
    float: left;
}
.mcmlmm_menu .active{
    background-color: #FDF7D7;
}
.mcmlmm_menu li a{
    margin-left: 50px;
    color: #000;
}
</style>
<div class="mcml_main">
    <div class="mcmlm_menu">
        <div class="mcmlmm_title">
            <div class="mcmlmm_title_ico" style="background-image: url(${resource(dir: "images",file: "user_info.png")});"></div>
            <div class="mcmlmm_title_text">账户管理</div>
        </div>
        <div class="mcmlmm_menu">
        <ul>
            <g:if test="${actionName=="viewUserInfo"}">
                <li class="active"><a href="#">我的资料</a></li>
            </g:if>
            <g:else>
                <li><g:link action="viewUserInfo">我的资料</g:link></li>
            </g:else>

            <g:if test="${actionName=="userAddresses"}">
                <li class="active"><a href="#">地址管理</a></li>
            </g:if>
            <g:else>
                <li><g:link action="userAddresses">地址管理</g:link></li>
            </g:else>

        </ul>
        </div>
    </div>
    <div class="mcmlm_menu">
        <div class="mcmlmm_title">
            <div class="mcmlmm_title_ico" style="background-image: url(${resource(dir: "images",file: "user_security.png")});"></div>
            <div class="mcmlmm_title_text">安全相关</div>
        </div>
        <div class="mcmlmm_menu">
            <ul>
                <g:if test="${actionName=="emailAuth"}">
                    <li class="active"><a href="#">邮箱验证</a></li>
                </g:if>
                <g:else>
                    <li><g:link action="emailAuth">邮箱验证</g:link></li>
                </g:else>

                <g:if test="${actionName=="phoneAuth"}">
                    <li class="active"><a href="#">手机验证</a></li>
                </g:if>
                <g:else>
                    <li><g:link action="phoneAuth">手机验证</g:link></li>
                </g:else>

                <g:if test="${actionName=="securityQuestion"}">
                    <li class="active"><a href="#">密保设置</a></li>
                </g:if>
                <g:else>
                    <li><g:link action="securityQuestion">密保设置</g:link></li>
                </g:else>

                <g:if test="${actionName=="changePsd"}">
                    <li class="active"><a href="#">更换密码</a></li>
                </g:if>
                <g:else>
                    <li><g:link action="changePsd">更换密码</g:link></li>
                </g:else>

            </ul>
        </div>
    </div>
    <div class="mcmlm_menu">
        <div class="mcmlmm_title">
            <div class="mcmlmm_title_ico" style="background-image: url(${resource(dir: "images",file: "user_collect.png")});"></div>
            <div class="mcmlmm_title_text">我的收藏</div>
        </div>
        <div class="mcmlmm_menu">
            <ul>
                <g:if test="${actionName=="myFavorites"&&params.type=="restaurant"}">
                    <li class="active"><a href="#">饭店收藏</a></li>
                </g:if>
                <g:else>
                    <li><a href='<g:createLink action="myFavorites" params="[type: 'restaurant']"/>'>饭店收藏</a></li>
                </g:else>

                <g:if test="${actionName=="myFavorites"&&params.type=="food"}">
                    <li class="active"><a href="#">菜谱收藏</a></li>
                </g:if>
                <g:else>
                    <li><a href='<g:createLink action="myFavorites" params="[type: 'food']"/>'>菜谱收藏</a></li>
                </g:else>

            </ul>
        </div>
    </div>
    <div class="mcmlm_menu">
        <div class="mcmlmm_title"></div>
    </div>
</div>