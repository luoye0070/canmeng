<%@ page import="lj.FormatUtil" contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <title>我的饭店</title>
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
    <div class="span10" style="margin-left: 10px;margin-top: 10px;">
        <g:render template="../layouts/shopMenu" />

        <form class="form-horizontal" method="POST" id="register_form" action="shopRegister">
            <fieldset>
                <div class="control-group">
                    <label class="control-label"> <g:message code="restaurantInfo.name.label" default="Name"/></label>

                    <div class="controls">
                        ${restaurantInfo?.name}

                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label">  <g:message code="restaurantInfo.cuisineName.label" default="Cuisine Name"/></label>

                    <div class="controls">
                        ${restaurantInfo?.cuisineName}
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label"><g:message code="restaurantInfo.masterName.label" default="Master Name"/></label>

                    <div class="controls">
                        ${restaurantInfo?.masterName}
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" >  <g:message code="restaurantInfo.phone.label" default="Phone"/></label>

                    <div class="controls">
                        ${restaurantInfo?.phone}
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label"> <g:message code="restaurantInfo.shopHoursBeginTime.label" default="Shop Hours Begin Time"/></label>

                    <div class="controls">
                        ${FormatUtil.timeFormat(restaurantInfo?.shopHoursBeginTime)}
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" > <g:message code="restaurantInfo.shopHoursEndTime.label" default="Shop Hours End Time"/></label>

                    <div class="controls">
                        ${FormatUtil.timeFormat(restaurantInfo?.shopHoursEndTime)}
                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label">  <g:message code="restaurantInfo.longitude.label" default="Longitude"/></label>

                    <div class="controls">
                        ${fieldValue(bean: restaurantInfo, field: 'longitude')}
                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label"> <g:message code="restaurantInfo.latitude.label" default="Latitude"/></label>

                    <div class="controls">
                        ${fieldValue(bean: restaurantInfo, field: 'latitude')}
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label"><g:message code="restaurantInfo.areaId.label" default="Area Id"/></label>

                    <div class="controls">
                        ${restaurantInfo?.province}

                         ${restaurantInfo?.city}

                        ${restaurantInfo?.area}

                        ${restaurantInfo?.street}
                    </div>
                </div>


            </fieldset>
        </form>


    </div>


</div>

</body>
</html>