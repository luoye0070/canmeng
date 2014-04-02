package lj.sysparameter

import grails.converters.JSON
import lj.data.AreaInfo
import lj.data.CityInfo
import lj.data.ProvinceInfo
import lj.enumCustom.ReCode
import lj.util.WebUtilService

import javax.servlet.http.Cookie

//地区系统参数
class AreaParamService {
    WebUtilService webUtilService;
    //查询系统定义的所有省份
    def getProvinceList(){
        def provinceList= ProvinceInfo.all;
        if(provinceList){
            return [recode: ReCode.OK,provinceList:provinceList];
        }
        else{
            return [recode:ReCode.NO_RESULT];
        }
    }
    //根据省份ID查询对应的系统定义的城市列表
    def getCityList(def params){
        if(!params){
            return [recode: ReCode.ERROR_PARAMS];// 参数错误
        }
        long provinceId=lj.Number.toLong(params.provinceId);
        def cityList=CityInfo.findAllByProvinceId(provinceId);
        if(cityList){
            return [recode: ReCode.OK,cityList:cityList];
        }
        else{
            return [recode:ReCode.NO_RESULT];
        }
    }
    //根据城市ID查询对应的系统定义的区域列表
    def getAreaList(def params){
        if(!params){
            return [recode: ReCode.ERROR_PARAMS];// 参数错误
        }
        long cityId=lj.Number.toLong(params.cityId);
        def areaList=AreaInfo.findAllByCityId(cityId);
        if(areaList){
            return [recode: ReCode.OK,areaList:areaList];
        }
        else{
            return [recode:ReCode.NO_RESULT];
        }
    }
    //根据城市ID查询对应的系统定义的城市
    def getCity(def params){
        if(!params){
            return [recode: ReCode.ERROR_PARAMS];// 参数错误
        }
        long cityId=lj.Number.toLong(params.cityId);
        CityInfo cityInfo=CityInfo.findById(cityId);
        if(cityInfo){
            return [recode: ReCode.OK,cityInfo:cityInfo];
        }
        else{
            return [recode:ReCode.NO_RESULT];
        }
    }
    //根据cookie或IP获取城市
    def getCityByCookieOrIp(){
        def request=webUtilService.getRequest();
        Cookie[] cookies=request.cookies;
        if(cookies==null||cookies.length<=0){//cookie中无数据 ,根据IP获取，并存入cookie
            String cityId=1+"";
            String cityName="成都";
            return [recode: ReCode,cityIdAndName:[cityId:cityId,cityName:cityName] ];
        }
        else{//从cookie中取出数据
            String cityId=null;
            String cityName=null;
            for (Cookie cookie:cookies){
                if(cookie.getName()=="cityId"){
                    cityId=cookie.getValue();
                }else if (cookie.getName()=="cityName"){
                    cityName=URLDecoder.decode(cookie.getValue(),"utf-8");
                }
            }
            if(!cityId||!cityName){//根据IP获取，并存入cookie
                cityId=1+"";
                cityName="成都";
            }
            //返回
            return [recode: ReCode,cityIdAndName:[cityId:cityId,cityName:cityName] ];
        }
    }
}
