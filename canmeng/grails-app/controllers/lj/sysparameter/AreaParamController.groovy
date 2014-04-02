package lj.sysparameter

import grails.converters.JSON

//系统地区参数
class AreaParamController {
    AreaParamService areaParamService;
    //查询系统定义的所有省份
    def getProvinceList(){
        def provinceList=[];
        provinceList.add([id:0,province:"请选择"]);
        def provinceListTemp=areaParamService.getProvinceList().provinceList;
        if(provinceListTemp){
            provinceListTemp.each {
                provinceList.add([id:it.id,province:it.province]);
            }
        }
        render (provinceList as JSON);
    }
    //根据省份ID查询对应的系统定义的城市列表
    def getCityList(){
        def cityList=[];
        cityList.add([id:0,city:"请选择"]);
        def cityListTemp=areaParamService.getCityList(params).cityList;
        println("cityListTemp-->"+cityListTemp);
        if(cityListTemp){
            cityListTemp.each {
                cityList.add([id:it.id,city:it.city]);
            }
        }
        println("cityList-->"+cityList);
        render (cityList as JSON);
        //render (areaParamService.getCityList(params) as JSON);
    }
    //根据城市ID查询对应的系统定义的区域列表
    def getAreaList(){
        def areaList=[];
        areaList.add([id:0,area:"请选择"]);
        def areaListTemp=areaParamService.getAreaList(params).areaList;
        if(areaListTemp){
            areaListTemp.each {
                areaList.add([id:it.id,area:it.area]);
            }
        }
        render (areaList as JSON);
        //render (areaParamService.getAreaList(params) as JSON);
    }

}
