function setSelectText(element, text) {

    var count = $("#" + element + " option").length;

    for (var i = 0; i < count; i++) {
        if ($("#" + element).get(0).options[i].text == text) {
            $("#" + element).get(0).options[i].selected = true;
            break;
        }
    }
}

var currentElement = null;

function updAddress(element) {
    $("#alert").removeClass("alert alert-success").removeClass("alert alert-error").html("");
    currentElement = $(element).parent().parent();
    $("form #linkManName").val(currentElement.data("linkManName"));
    setSelectText("province",currentElement.data("province"));
    //获取市

    var url=$("#cityUrl").val()+"?provinceId="+$("#province").val()+"&rand="+Math.random();;
    $.get(url,function(data,status){
        $("#city").empty();
        if(data){
            var array=new Array()
            for(i=0;i<data.length;i++){
                if(data[i].city==currentElement.data("city")){
                    array.push("<option selected='true' value='"+data[i].id+"'>"+data[i].city+"</option>");
                }else{
                    array.push("<option value='"+data[i].id+"'>"+data[i].city+"</option>");
                }
            }
            $("#city").append(array.join())

            //请求区域
            var url=$("#areaUrl").val()+"?cityId="+$("#city").val()+"&rand="+Math.random();;
            $.get(url,function(data,status){
                $("#area").empty();
                if(data){
                    var array=new Array();
                    for(i=0;i<data.length;i++){
                        if(data[i].area==currentElement.data("area")){
                            array.push("<option selected='true' value='"+data[i].id+"'>"+data[i].area+"</option>");
                        }else{
                            array.push("<option value='"+data[i].id+"'>"+data[i].area+"</option>");
                        }
                    }
                    $("#area").append(array.join())
                }
            });
        }
    });



    setSelectText("city",currentElement.data("city"));
    setSelectText("area",currentElement.data("area"));
    $("form #street").val(currentElement.data("street"));
    $("form #phone").val(currentElement.data("phone"));
    $('form input[name="defaultAddrId"]').attr("checked", currentElement.data('default') == '1');

    $("#cancel").show();
    $("#save").show();
    $("#addAddress").hide();
}

function delAddress(element) {
    currentElement = $(element).parent().parent();
    $('#myModal').modal('show');
}
//用户地址脚本
$(document).ready(function () {

    //获取已有地址
    $.ajax({
        dataType:"json",
        url:"./ajaxGetAddresses",
        success:function (data) {
            var addresses = data.addresses;

            console.log(data.defaultAddrId);
            console.log(addresses.length);
            if (!addresses || addresses == 'null')
                return;

            var len = addresses.length;
            for (var i = 0; i < len; i++) {
                var address = addresses[i];
                console.log("address" + address['linkManName']);
                var jqTr = $("<tr></tr>");
                jqTr.data("linkManName", address['linkManName']);
                jqTr.data('province', address['province']);
                jqTr.data("city", address.city);
                jqTr.data("area", address.area);
                jqTr.data("street", address.street);
                jqTr.data("phone", address.phone);
                jqTr.data("default", (data.defaultAddrId == address.id));
                jqTr.data("id", address.id)

                var row = "<th>" + address['linkManName'] + "</th><br/>";
                row += "<th>" + jqTr.data('province') + "  " + jqTr.data("city") + "  " + jqTr.data("area") + "</th><br/>"
                row += "<th>" + jqTr.data("street") + "</th><br/>";
                row += "<th>" + jqTr.data("phone") + "</th><br/>"
                row += "<th>" + (jqTr.data("default") == '1' ? "默认地址" : "") + "</th><br/>";
                row += '<th><a  class="btn btn-primary" href="#" onclick="javascript:updAddress(this)">修改</a>|<a class="btn btn-primary" href="#" onclick="javascript:delAddress(this)">删除</a></th><br/>';

                jqTr.append(row);
                $("table tbody").append(jqTr);
            }

        }

    });
    var cancelBt = $("#cancel").hide().bind("click", function () {
        alert.removeClass("alert alert-success").removeClass("alert alert-error").html("");
        currentElement = null;
        addAddressBt.show();
        $(this).hide();
        saveBt.hide();
        $("form #linkManName").val("");
        $("form #street").val("");
        $("form #phone").val("");
        $("form #province").val("");
        $("form #city").val("");
        $("form #area").val("");
        $('form input[name="defaultAddrId"]').attr("checked", false);
    });
    var saveBt = $("#save").hide().bind("click", function () {
        alert.removeClass("alert alert-success").removeClass("alert alert-error").html("");
        if (!validator.form())
            return;

        $.ajax({
            type:"post",
            dataType:'json',
            url:"./updAddress",
            data:{
                linkManName:$("form #linkManName").val(),
                province:$("form #province").children("option:selected").text(),
                city:$("form #city").children("option:selected").text(),
                area:$("form #area").children("option:selected").text(),
                street:$("form #street").val(),
                phone:$("form #phone").val(),
                areaId:$("form #area").children("option:selected").val(),
                defaultAddrId:$('form input[name="defaultAddrId"]:checked').val() ,
                id:currentElement.data("id")
            },
            success:function (data) {
                if (data.success) {


                    currentElement.data("linkManName", $("form #linkManName").val());
                    currentElement.data('province', $("form #province").children("option:selected").text());
                    currentElement.data("city", $("form #city").children("option:selected").text());
                    currentElement.data("area", $("form #area").children("option:selected").text());
                    currentElement.data("street", $("form #street").val());
                    currentElement.data("phone", $("form #phone").val());
                    currentElement.data("default", $('form input[name="defaultAddrId"]:checked').val());

                    var children=currentElement.children("th");
                    $(children[0]).html($("form #linkManName").val());
                    $(children[1]).html(currentElement.data('province')+"  "+currentElement.data("city")+"  "+currentElement.data("area"));
                    $(children[2]).html(currentElement.data("street"));
                    $(children[3]).html(currentElement.data("phone"));

                    if(currentElement.data("default")=='1'){
                        $("table tbody th:nth-child(5)").each(function(){
                            $(this).html("");
                            $(this).parent().data("default","");
                        });
                        $(children[4]).html("默认地址");
                    }


                    validator.resetForm();

                    $("form #linkManName").val("");
                    $("form #street").val("");
                    $("form #phone").val("");
                    $("form #province").val("");
                    $("form #city").val("");
                    $("form #area").val("");
                    $('form input[name="defaultAddrId"]').attr("checked", false);

                    addAddressBt.show();
                    saveBt.hide();
                    cancelBt.hide();
                    currentElement=null;
                    alert.addClass("alert alert-success");
                } else {
                    alert.addClass("alert alert-error");
                }

                alert.html(data.msg);
            },
            error:function (data) {
                alert.addClass("alert alert-error").html("新增地址失败");
            }
        });
    });

    var validator = $("#addresses_form").validate({

        rules:{
            linkManName:{required:true},
            province:{required:true},
            city:{required:true},
            phone:{isPhone:true}
        },
        messages:{
            linkManName:{required:"请填写联系人"},
            province:{required:"请选择省"},
            city:{required:"请选择市"}
        }
    });

    var alert = $("#alert");
    //新增
    var addAddressBt = $("#addAddress").bind("click", function () {
        alert.removeClass("alert alert-success").removeClass("alert alert-error").html("");

        if (!validator.form())
            return;

        $.ajax({
            type:"post",
            dataType:'json',
            url:"./addAddress",
            data:{
                linkManName:$("form #linkManName").val(),
                province:$("form #province").children("option:selected").text(),
                city:$("form #city").children("option:selected").text(),
                area:$("form #area").children("option:selected").text(),
                street:$("form #street").val(),
                phone:$("form #phone").val(),
                areaId:$("form #area").children("option:selected").val(),
                defaultAddrId:$('form input[name="defaultAddrId"]:checked').val()
            },
            success:function (data) {

                if (data.success) {

                    var jqTr = $("<tr></tr>");
                    jqTr.data("linkManName", $("form #linkManName").val());
                    jqTr.data('province', $("form #province").children("option:selected").text());
                    jqTr.data("city", $("form #city").children("option:selected").text());
                    jqTr.data("area", $("form #area").children("option:selected").text());
                    jqTr.data("street", $("form #street").val());
                    jqTr.data("phone", $("form #phone").val());
                    jqTr.data("default", $('form input[name="defaultAddrId"]:checked').val());
                    jqTr.data("id", data.id)

                    if(jqTr.data("default")=='1'){
                        $("table tbody th:nth-child(5)").each(function(){
                            $(this).html("");
                            $(this).parent().data("default","");
                        });
                    }


                    var row = "<th>" + jqTr.data("linkManName") + "</th><br/>";
                    row += "<th>" + jqTr.data('province') + "  " + jqTr.data("city") + "  " + jqTr.data("area") + "</th><br/>"
                    row += "<th>" + jqTr.data("street") + "</th><br/>";
                    row += "<th>" + jqTr.data("phone") + "</th><br/>"
                    row += "<th>" + (jqTr.data("default") == '1' ? "默认地址" : "") + "</th><br/>";
                    row += '<th><a  class="btn btn-primary" href="#" onclick="javascript:updAddress(this)">修改</a>|<a class="btn btn-primary" href="#" onclick="javascript:delAddress(this)">删除</a></th><br/>';

                    jqTr.append(row);
                    $("table tbody").append(jqTr);

                    validator.resetForm();

                    $("form #linkManName").val("");
                    $("form #street").val("");
                    $("form #phone").val("");
                    $("form #province").val("");
                    $("form #city").val("");
                    $("form #area").val("");
                    $('form input[name="defaultAddrId"]').attr("checked", false);

                    alert.addClass("alert alert-success");
                } else {
                    alert.addClass("alert alert-error");
                }

                alert.html(data.msg);
            },
            error:function (data) {
                alert.addClass("alert alert-error").html("新增地址失败");
            }
        });
    });


    $("#del_dialog").bind("click", function () {
        alert.removeClass("alert alert-success").removeClass("alert alert-error").html("");
        $.ajax({
            url:'./delAddress',
            type:'post',
            dataType:'json',
            data:{id:currentElement.data('id')},
            success:function (data) {
                $('#myModal').modal('hide');
                currentElement.empty();
                currentElement = null;
                if (data.success) {
                    alert.addClass("alert alert-success");
                } else {
                    alert.addClass("alert alert-error");
                }

                alert.html(data.msg);

            }, error:function (data) {
                currentElement = null;
                alert.addClass("alert alert-error").html("删除地址失败");
            }
        });
    });


});
