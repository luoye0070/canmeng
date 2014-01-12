package lj.sysparameter

import grails.converters.JSON
import lj.enumCustom.ReCode

import javax.servlet.http.Cookie

class CitySelectController {
    AreaParamService areaParamService;
    def cityList(){
        //首先查询出省份列表
        def reInfo=areaParamService.getProvinceList();
        if(reInfo.recode!= ReCode.OK){
            render(view:"cityList",model: reInfo);
        }
        def provinceList=reInfo.provinceList.collect{
            def paramsT=[provinceId:it.id];
            def reInfo1=areaParamService.getCityList(paramsT);
            [province:it,cityList:reInfo1.cityList];
        }
        reInfo.provinceList=provinceList;
        render(view:"cityList",model: reInfo);
    }
    def changeCity(){
        def reInfo=areaParamService.getCity(params);
        if(reInfo.recode==ReCode.OK){//将城市信息写入cookie中
            Cookie c1=new Cookie("cityId",reInfo.cityInfo.id+"");
            c1.setMaxAge(3600*24*5);//设置生命周期为一个月
            //c1.setDomain("");//设置域
            c1.setPath("/");
            response.addCookie(c1);
            Cookie c2=new Cookie("cityName",URLEncoder.encode(reInfo.cityInfo.city,"utf-8"));
            c2.setMaxAge(3600*24*5);//设置生命周期为一个月
            //c2.setDomain("");//设置域
            c2.setPath("/");
            response.addCookie(c2);
        }
        //跳转到原来的界面
        //request.getc
        if(params.co&&params.ac){
            String co=params.co;
            String ac=params.ac;
            params.remove("co");
            params.remove("ac");
            params.remove("cityId");
            redirect(controller: co,action: ac,params: params);
        }
        else{
            redirect(url: "/");
        }
    }
//    def getCityAjax(){
//        Cookie[] cookies=request.cookies;
//        if(cookies==null||cookies.length<=0){//cookie中无数据 ,根据IP获取，并存入cookie
//            String cityId=1+"";
//            String cityName="成都";
//            render(contentType: "text/html",text:([cityId:cityId,cityName:cityName] as JSON)) ;
//            return;
//        }
//        else{//从cookie中取出数据
//            String cityId=null;
//            String cityName=null;
//            for (Cookie cookie:cookies){
//                if(cookie.getName()=="cityId"){
//                    cityId=cookie.getValue();
//                }else if (cookie.getName()=="cityName"){
//                    cityName=URLDecoder.decode(cookie.getValue(),"utf-8");
//                }
//            }
//            if(!cityId||!cityName){//根据IP获取，并存入cookie
//                cityId=1+"";
//                cityName="成都";
//            }
//            //返回
//            render(contentType: "text/html",text:([cityId:cityId,cityName:cityName] as JSON)) ;
//            return;
//        }
//    }
}
