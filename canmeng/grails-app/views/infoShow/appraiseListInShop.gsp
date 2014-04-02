<%@ page import="lj.FormatUtil; lj.enumCustom.AppraiseType" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta name="layout" content="main1"/>
    <title>评价列表</title>

    <style type="text/css">
    * {
        margin: 0px;
        padding: 0px;
    }

    ul, li {
        list-style: none;
        margin: 0px;
        padding: 0px;
    }
     body{
         float: left;
     }
    .main {
        width: 960px;
        height: auto;
        margin: 0px auto;
        background-color: #ffffff;
        float: left;
    }

    .m_ssl {
        width: 960px;
        margin-top: 10px;
        margin-bottom: 10px;
        float: left;
    }

    .ms_field {
        width: 80px;
        /*height: 30px;
        line-height: 30px; */
        float: left;
    }

    .msf_label {
        width: 60px;
        /*height: 30px;
        line-height: 30px; */
        float: left;
    }

    .msf_input {
        width: 20px;
        /*height: 30px;
        line-height: 30px;*/
        float: left;
    }

    .m_list {
        width: 960px;
        height: auto;
        float: left;
    }

    .ml_u {
        width: 960px;
        height: auto;
        float: left;
        margin: 0px;
        padding: 0px;
        margin-bottom: 20px;
    }

    .ml_item {
        width: 960px;
        height: auto;
        float: left;
        margin-top: 10px;
        border-bottom: 1px dashed #CCCCCC;
    }

    .mli_left {
        width: 100px;
        height: 100px;
        float: left;
    }

    .mli_right {
        width: 850px;
        height: auto;
        float: left;
        margin-left: 10px;
        font-size: 12px;
    }

    .mlir_row {
        width: 850px;
        height: 30px;
        float: left;

    }

    .mlirr_left {
        width: 550px;
        height: 30px;
        float: left;
        font-size: 14px;
        font-weight: bold;
    }

    .mlirr_right {
        width: 300px;
        height: 30px;
        float: right;
    }

    .mlir_row_auto {
        width: : 800 px;
        height: auto;
        float: left;
        text-indent: 2em;
    }

    .ml_page {
        width: 960px;
        height: 80px;
        float: left;
        margin: 0px;
        padding: 0px;
    }

    </style>
    <script type="text/javascript">
        $(function () {
            //初始化评价类型选择
            var type = $("#typeValue").val();
            if (type ==${AppraiseType.GOOD_TYPE.code}) {
                $("#type_good").attr("checked", "checked");
            }
            else if (type ==${AppraiseType.NOTGOODNOTBAD_TYPE.code}) {
                $("#type_gab").attr("checked", "checked");
            }
            else if (type ==${AppraiseType.BAD_TYPE.code}) {
                $("#type_bad").attr("checked", "checked");
            }
            else {
                $("#type_all").attr("checked", "checked");
            }

            //切换类型
            $("input[name='type']").click(function () {
                if ($(this).val() != type) {
                    $("#ss_form").submit();
                }
            });
        });
    </script>
</head>

<body>

<div class="main">
    <div class="m_ssl">
        <form method="post" class="well form-inline" id="ss_form"
              action="${createLink(controller: "infoShow", action: "appraiseList", params: [restaurantId: params.restaurantId])}">
            <input type="hidden" id="typeValue" value="${params.type ?: "-1"}"/>

            <div class="ms_field">
                <input id="type_all" type="radio" name="type" value="-1" class="msf_input"/>
                <label class="msf_label">全部</label>
            </div>

            <div class="ms_field">
                <input id="type_good" type="radio" name="type" value="${AppraiseType.GOOD_TYPE.code}"
                       class="msf_input"/>
                <label class="msf_label">好评</label>
            </div>

            <div class="ms_field">
                <input id="type_gab" type="radio" name="type" value="${AppraiseType.NOTGOODNOTBAD_TYPE.code}"
                       class="msf_input"/>
                <label class="msf_label">中评</label>
            </div>

            <div class="ms_field">
                <input id="type_bad" type="radio" name="type" value="${AppraiseType.BAD_TYPE.code}" class="msf_input"/>
                <label class="msf_label">差评</label>
            </div>
        </form>
    </div>

    <div class="m_list">
        <g:if test="${appraiseList}">
            <ul class="ml_u">
                <g:each in="${appraiseList}" status="i" var="appraiseInfoInstance">

                    <li class="ml_item">
                        <div class="mli_left">
                            <img src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: "", width: 300, height: 300])}"/>
                        </div>

                        <div class="mli_right">
                            <div class="mlir_row">
                                <div class="mlirr_left">
                                    <g:if test="${appraiseInfoInstance?.isAnonymity}">匿名用户</g:if>
                                    <g:else>${appraiseInfoInstance?.userName}</g:else>
                                </div>

                                <div class="mlirr_right">${FormatUtil.dateTimeFormat(appraiseInfoInstance?.appraiseTime)}</div>
                            </div>

                            <div class="mlir_row"
                                 style="margin-bottom: 18px;height:12px;line-height: 12px;font-size: 12px;"><g:appraiseStars
                                    appraiseId="${appraiseInfoInstance?.id}"></g:appraiseStars></div>

                            <div class="mlir_row_auto">${appraiseInfoInstance?.content}</div>
                        </div>
                    </li>
                </g:each>
            </ul>
            <div class="ml_page">
            <white:paginate total="${totalCount ?: 0}" prev="&larr;" next="&rarr;" params="${params}"/>
            </div>
        </g:if>
        <g:else>
            <div style="margin: 0px auto;">
                <label style="text-align: center">还没有评价哦</label>
            </div>
        </g:else>
    </div>
</div>
</body>
</html>