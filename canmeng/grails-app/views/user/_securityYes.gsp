<form class="form-horizontal" method="POST" id="securityQuestion_form">
    <fieldset>
        <div id="alert" class="alert alert-info">
            您已经设置过密保问题了。<br/>
            您可以更改密保问题。更改之前要输入旧密保的答案!
        </div>
        <div class="control-group">
            <label class="control-label" for="oldQuestion">旧密保问题</label>
            <div class="controls">
                <input type="text" id="oldQuestion" name="oldQuestion"  class="input-xlarge" value="${question}" readonly/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="oldAnswer">旧密保答案</label>
            <div class="controls">
                <input type="text" id="oldAnswer" name="oldAnswer"  class="input-xlarge" value=""/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="question">密保问题</label>
            <div class="controls">
                <select id="question" name="question">
                    <option value="母亲的名字" >母亲的名字</option>
                    <option value="爷爷的名字" >爷爷的名字</option>
                    <option value="我出生的城市" >我出生的城市</option>
                    <option value="我的小学老师的名字" >我的小学老师的名字</option>
                    <option value="我的个人计算机的型号" >我的个人计算机的型号</option>
                    <option value="我最喜欢的餐厅的名称" >我最喜欢的餐厅的名称</option>
                    <option value="驾驶证的后四位" >驾驶证的后四位</option>
                    <option value="自定义问题" >自定义问题</option>
                </select>
            </div>
        </div>

        <div class="control-group" id="question1_div">
            <label class="control-label" for="question1">自定义密保问题</label>
            <div class="controls">
                <input type="text" id="question1" name="question1"  class="input-xlarge" value=""/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="answer">密保答案</label>
            <div class="controls">
                <input type="text" id="answer" name="answer"  class="input-xlarge" value=""/>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <input type="button" id="submit" class="btn btn-success" value="提&nbsp;&nbsp;交"/>
            </div>
        </div>
    </fieldset>
</form>

<script type="text/javascript">
    $(document).ready(function(){
        var question1_div=$("#question1_div").hide();
        var answer=$("#answer");
        var question=$("#question").bind("change",function(){
            var s=question.children("option:selected").val();
            if(s=="自定义问题"){
                question1_div.show();
            }else{
                question1_div.hide();
                answer.val("");
            }
        });
        var alert=$("#alert");
        var submit=$("#submit").bind("click",function(){

            if($("#oldAnswer").val()==""){
                alert.addClass("alert alert-error").html("请输入旧密保答案!");
                return;
            }

            var q=question.children("option:selected").val();
            if(q=="自定义问题") {
                q=$("#question1").val();
            }
            if(q==""){
                alert.addClass("alert alert-error").html("请输入自定义问题!");
                return;
            }

            if(answer.val()==""){
                alert.addClass("alert alert-error").html("请输入新密保答案!");
                return;
            }

            $.ajax({
                url:'./ajaxSecurityQuestion',
                dataType:'json',
                data:{
                    type:1,
                    oldAnswer:$("#oldAnswer").val(),
                    question: q,
                    answer:answer.val()
                } ,success:function(data){
                    if(data.success){
                        alert.addClass("alert alert-success");
                        $("#oldAnswer").attr("readonly",true);
                        question.attr("disabled",true);
                        $("#question1").attr("readonly",true);
                        answer.attr("readonly",true);
                        submit.hide();
                        question.unbind("change");
                    }else{
                        alert.addClass("alert alert-error");
                    }
                    alert.html(data.msg);
                }   ,
                error:function(){
                    alert.addClass("alert alert-error").html("密保设置失败!");
                }
            });
        });
    });
</script>