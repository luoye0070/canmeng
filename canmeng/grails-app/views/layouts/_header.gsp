<div class="m_head">
    <div class="mh_menu">
        <div class="mhm_city">
            <label>乌鲁木齐</label>
            <a href="#">切换城市</a>
        </div>
        <ul style="float: right;margin-right: 20px;">
            <% String url=request.getRequestURL();
            url=url.substring(url.lastIndexOf("/")+1);
            %>
            <li ${url==""?'class="current"':""}><g:link url="/canmeng"> 餐萌首页</g:link></li>
            <li  ${url in ["viewUserInfo","changePsd","emailAuth","phoneAuth",'securityQuestion','myFavorites','userAddresses']?'class="current"':""}><g:link controller="user" action="viewUserInfo">我的资料</g:link></li>
            <li  ${url in ["createOrder",'reserveTable',"orderList","orderShow","doDish"]?'class="current"':""}><g:link controller="customer" action="orderList">我的餐萌</g:link></li>
            <li  ${url in ["shopInfo","editShop"]?'class="current"':""}><g:link controller="shop" action="shopInfo">我的饭店</g:link></li>
            <li  ${url=="c"?'class="current"':""}><a href="about.html">联系餐萌</a></li>
            <li ${controllerName=="staff"?"class='current'":""}><g:link controller="staff" action="index">工作人员入口</g:link></li>
            <g:if test="${session && (session.clientId||session.staffInfo)}">
                <li><g:link controller="user" action="logout">退出餐萌</g:link></li>
            </g:if>
            <g:else>
                <li><g:link controller="user" action="login">登录餐萌</g:link></li>
            </g:else>
        </ul>
    </div>
    <div class="mh_center"></div>
    <div class="mh_tab">
        <ul>
            <li id="foodListTab" class="mht_selected">美食</li>
            <li id="resListTab" class="mht_unselected">餐厅</li>
        </ul>
    </div>
</div>