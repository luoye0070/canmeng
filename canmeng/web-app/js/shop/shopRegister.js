/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-31
 * Time: 下午9:25
 * To change this template use File | Settings | File Templates.
 */
$(document).ready(function(){
    //加载城市列表
    $("#province").change(function(){
        var provinceId=$(this).val();
        var provinceHidden=$("#provinceHidden");//省份
        var txt= $(this).children("option[value='"+provinceId+"']").text();
        //alert(txt) ;
        provinceHidden.val(txt);
        //alert(provinceId);
        //异步查询省份对应的城市
        var rand=Math.random();
        var url=$("#cityUrl").val()+"?provinceId="+provinceId+"&rand="+rand;
        $.get(url,function(data,status){
            //alert("Data: " + data.length + "\nStatus: " + status);
            $("#city").html("");
            if(data){
                for(i=0;i<data.length;i++){
                    $("#city").append("<option value='"+data[i].id+"'>"+data[i].city+"</option>");
                }
            }
        });
    });
    //加载区域列表
    $("#city").change(function(){
        //alert("ddd");
        var cityId=$(this).val();
        var cityHidden=$("#cityHidden");//城市
        var txt= $(this).children("option[value='"+cityId+"']").text();
        //alert(txt) ;
        cityHidden.val(txt);
        //alert(cityId);
        //异步查询城市对应的区域
        var rand=Math.random();
        var url=$("#areaUrl").val()+"?cityId="+cityId+"&rand="+rand;
        $.get(url,function(data,status){
            //alert("Data: " + data.length + "\nStatus: " + status);
            $("#area").html("");
            if(data){
                for(i=0;i<data.length;i++){
                    $("#area").append("<option value='"+data[i].id+"'>"+data[i].area+"</option>");
                }
            }
        });
    });
    //选择区域
    $("#area").change(function(){
        var areaId=$(this).val();
        $("#areaId").val(areaId);
        var areaHidden=$("#areaHidden");//区域
        var txt= $(this).children("option[value='"+areaId+"']").text();
        //alert(txt) ;
        areaHidden.val(txt);
    });

    $("#verifyStatusButton").click(function(event){
        $("#verifyStatusHidden").val(0);
        //$("#verifyStatusHidden").attr("name","verifyStatus");
        $("#verifyStatus").text("等待审核");
    });
});
