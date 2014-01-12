<%@ page import="lj.data.StaffInfo" %>


<div class="control-group">
    <label class="control-label" for="loginName"><g:message code="staffInfo.loginName.label" default="Login Name" />
        <span class="required-indicator">*</span>
    </label>
    <div class="controls">
        <g:textField name="loginName" maxlength="32" required="" value="${staffInfoInstance?.staffInfo?.loginName}"/>
    </div>
</div>

<div class="control-group">
    <label class="control-label" for="passWord"><g:message code="staffInfo.passWord.label" default="Pass Word" />
        <span class="required-indicator">*</span>
    </label>
    <div class="controls">
        <g:passwordField name="passWord" maxlength="128" required="" value=""/>
    </div>
</div>


<div class="control-group">
    <label class="control-label" for="name"><g:message code="staffInfo.name.label" default="Name" />
    </label>
    <div class="controls">
        <g:textField name="name" maxlength="32" value="${staffInfoInstance?.staffInfo?.name}"/>
    </div>
</div>



<div class="control-group">
    <label class="control-label" for="staffPositionInfo"> <g:message code="staffInfo.staffPositionInfo.label" default="Is Online" />
    </label>
    <div class="controls">
        <g:checkBoxGroup id="staffPositionInfo" name="positionTypes" checkGroup="${positionTypes}" checkValues="${staffInfoInstance?.positionFullList}"></g:checkBoxGroup>
    </div>
</div>