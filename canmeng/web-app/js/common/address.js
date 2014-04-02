//地址级联相关
$(document).ready(function(){

     var province=$("#province").bind("change",function(){
         var provinceId=$(this).val();
         //异步查询省份对应的城市
         var rand=Math.random();
         var url=$("#cityUrl").val()+"?provinceId="+provinceId+"&rand="+rand;
         $.get(url,function(data,status){
             city.empty();
             if(data){
                 var array=new Array()
                 for(i=0;i<data.length;i++){
                     array.push("<option value='"+data[i].id+"'>"+data[i].city+"</option>");
                 }
                 city.append(array.join())
             }
         });
     });

    var area=$("#area");
     var city=$("#city").bind("change",function(){
         var cityId=$(this).val();
         //异步查询城市对应的区域
         var rand=Math.random();
         var url=$("#areaUrl").val()+"?cityId="+cityId+"&rand="+rand;
         $.get(url,function(data,status){
             area.empty();
             if(data){
                 var array=new Array();
                 for(i=0;i<data.length;i++){
                     array.push("<option value='"+data[i].id+"'>"+data[i].area+"</option>");
                 }
                 area.append(array.join())
             }
         });
     });

});