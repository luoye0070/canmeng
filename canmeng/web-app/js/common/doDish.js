/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-18
 * Time: 下午5:25
 * To change this template use File | Settings | File Templates.
 */
(function ($) {
    var createDialog = function (data,doDishUrl,foodId) {
        if (typeof($("#ratyService")) != 'undefined') {
            $("#ratyService").remove();
        }
        var htmlStr = "";
        htmlStr += "<div id='ratyService' class='modal hide fade'>";
        htmlStr += "    <div class='modal-header'>";
        htmlStr += "        <button type='button' class='close' data-dismiss='modal'>&times;</button>";
        htmlStr += "        <h3>选择添加到的订单</h3>";
        htmlStr += "    </div><!--Modal header-->";
        htmlStr += "    <div class='modal-body'>";
        if (data.length == 0) {
            htmlStr += "    <label>在这个饭店，您还没有有效的订单哦，赶紧去创建一个吧</label>"
        }
        else {
            for (i = 0; i < data.length; i++) {
                htmlStr += "<div name='dishData' style='float: left;width: 400px'>" ;
                htmlStr += "<div name='field' style='float: left;width: 400px;height: 50px;line-height: 50px;'>"
                htmlStr += "    <input style='float: left;' type='checkbox' name='orderId' value='" + data[i].id + "'/>";
                htmlStr += "    <label style='float: left;'>" + "&nbsp;订单" + data[i].id + "&nbsp;创建时间：" + data[i].createTime + "</label>";
                htmlStr += "    <label style='float: left;'>" + "&nbsp;&nbsp;&nbsp;&nbsp;份数：" + "" + "</label>";
                htmlStr += "    <input style='float: left;width: 20px;' type='text' name='counts' value='1' disabled='disabled'/>";
                htmlStr += "</div>";
                //htmlStr += "    <div name='info' style='float: left;width: 400px;height: 50px;line-height: 50px;'></div>"
                htmlStr += "</div>"
            }
        }
        htmlStr += "    </div><!--Modal body-->";
        htmlStr += "    <div class='modal-footer'>";
        htmlStr += "        <a href='#' class='btn' data-dismiss='modal' >关闭</a>";
        htmlStr += "        <a id='submitData' href='#' class='btn btn-primary'>提交</a>";
        htmlStr += "    </div><!--Modal footer-->";
        htmlStr += "</div>";
        htmlStr+="";

        if(parent.showDialog){
            parent.showDialog(htmlStr,foodId);
            return;
        }
        var dialog = $(htmlStr);
        $("body").append(htmlStr);
        $("#ratyService").modal();
        //dialog.modal();
        //注册事件
        $("input[name='orderId']").click(function(){//选择按钮
            if($(this).attr('checked')){
                $(this).parent().children("input[name='counts']").removeAttr("disabled");
            }
            else{
                $(this).parent().children("input[name='counts']").attr("disabled",'disabled');
                $(this).parent().parent().children("div[name='info']").remove();
            }
        });
        $("#submitData").click(function(event){//数据提交按钮
            $("div[name='dishData']").each(function(){
                var checkBox=$(this).children("div[name='field']").children("input[name='orderId']");
                var textInput=$(this).children("div[name='field']").children("input[name='counts']");
                if(checkBox.attr("checked")){//提交数据
                    if(typeof($(this).children("div[name='info']"))!='undefined')
                        $(this).children("div[name='info']").remove();
                    //$('<div class="progress progress-striped active"><div class="bar" style="width: 40%;"></div></div>').appendTo($(this).children("div[name='info']"));
                    $("<div name='info' style='float: left;width: 400px;height: 50px;line-height: 50px;'>"+
                        '<img style="width: 50px;height: 50px;float: left;" src="../images/progcess.gif"/>'+
                        '<label style="width: 300px;height: 50px;">正在添加，请稍后...</label></div>').appendTo($(this));
                    var orderId= checkBox.val();
                    var counts=textInput.val();
                    var foodIds=foodId;
                    var remarks="";
                    $.ajax({
                        context:this,
                        url:doDishUrl,
                        async:false,
                        type:'post',
                        data:{'orderId':orderId,'counts':counts,'foodIds':foodIds,'remarks':remarks},
                        dataType: 'json',
                        success:function(data){
                            $(this).children("div[name='info']").html("");
                            if(data.recode.code==0){
                                $(this).children("div[name='info']").html("<label style='height: 50px;line-height:50px;color: green'>点菜"+data.recode.label+"</label>");
                            }else{
                                if(data.recode.code==5)
                                    $(this).children("div[name='info']").html("<label style='height: 50px;line-height:50px;color: red'>点菜不成功："+data.failedList[0].msg+"</label>");
                                else
                                    $(this).children("div[name='info']").html("<label style='height: 50px;line-height:50px;color: red'>点菜不成功："+data.recode.label+"</label>");
                            }
                        },
                        error:function(data){
                            $(this).children("div[name='info']").html("<label style='height: 50px;line-height:50px;color: red'>"+"未知错误"+"</label>");
                        }
                    });
                }
            });
        });
    }
    $.fn.doDish = function (options) {
        return this.each(function () {
            $(this).click(function () { //注册点击事件
                var orderListUrl = options.orderListUrl + "?rand=" + Math.random();//从orderList取出访问地址
                var doDishUrl = options.doDishUrl + "?rand=" + Math.random();
                var restaurantId = $(this).attr("restaurantId");
                var foodId = $(this).attr("foodId");
                $.getJSON(orderListUrl, {restaurantId: restaurantId}, function (data) {
                    //var dataAH=dataAnalyzeHelper(data);
                    //alert(orderListUrl+data.recode.label);
                    if (data.recode.code == 0) {//成功则显示订单选择对话框
                        createDialog(data.orderList,doDishUrl,foodId);
                    }
                    else {//显示错误信息
                        alert(data.recode.label);
                    }
                });
            });
        });
    }
})(jQuery)