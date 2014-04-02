<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-12-19
  Time: 下午6:50
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
  <title>城市选择</title>
    <style type="text/css">
    .mc_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }

    .mcm_top {
        width: 960px;
        height: 80px;
        margin: 20px;
        margin-top: 0px;
        border-bottom: 4px solid #FF9833;
        text-indent: 1em;
        line-height: 80px;
        font-size: 20px;
        font-weight: bolder;
        float: left;
    }

    .mcm_content {
        width: 960px;
        height: auto;
        margin: 20px;
        margin-top: 0px;
        float: left;
    }
        .mcmc_item{
            width: 960px;
            height: auto;
            float: left;
            /*margin-left: 30px;
            margin-right: 30px;*/
        }
        .mcmci_title{
            width: 960px;
            height: 30px;
            line-height: 30px;
            float: left;
            font-size: 18px;
            font-weight: bolder;
        }
        .mcmci_detail{
            width: 960px;
            height: auto;
            float: left;
        }
    .mcmci_detail li{
        /*width: 90px;*/
        width: 96px;
        height: 25px;
        line-height: 25px;
        float: left;
    }

    </style>
</head>
<body>
        <div class="mc_main">
            <div class="mcm_top">
                切换城市
            </div>

            <div class="mcm_content">
    <g:if test="${provinceList}">
        <g:each in="${provinceList}" var="provinceInfo" status="i">
            <div class="mcmc_item">
            <div class="mcmci_title">${provinceInfo.province.province}</div>
            <div class="mcmci_detail">
                <g:if test="${provinceInfo.cityList}">
                    <ul>
                    <g:each in="${provinceInfo.cityList}" var="cityInfo" status="j">
                        <li>
                            <a href="${createLink(controller: "citySelect",action: "changeCity",params: params<<[cityId:cityInfo.id])}">${cityInfo.city}</a>
                        </li>
                    </g:each>
                    </ul>
                </g:if>
            </div>
            </div>
        </g:each>
    </g:if>
        </div>
            </div>
</body>
</html>