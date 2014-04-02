/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-28
 * Time: 下午7:39
 * To change this template use File | Settings | File Templates.
 */
function foodAddToFavorite(url){
    $.get(url+"&rand="+Math.random(),function(data){
            if(data.msg){
                alert(data.msg);
            }
            else{
                alert("发生了未知错误");
            }
        });
}
function shopAddToFavorite(url){
    $.get(url+"&rand="+Math.random(),function(data){
        if(data.msg){
            alert(data.msg);
        }
        else{
            alert("发生了未知错误");
        }
    });
}
$(document).ready(function(){

});