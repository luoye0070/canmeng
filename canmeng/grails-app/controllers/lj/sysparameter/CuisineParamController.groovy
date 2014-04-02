package lj.sysparameter

import grails.converters.JSON
//菜系相关系统参数
class CuisineParamController {
    CuisineParamService cuisineParamService;
    def getCuisineList() {
        def cuisineList=[];
        cuisineList.add([id:0,name:"请选择"]);
        def cuisineListTemp=cuisineParamService.getCuisineList().cuisineList;
        if(cuisineListTemp){
            cuisineListTemp.each {
                cuisineList.add([id:it.id,name:it.name]);
            }
        }
        render (cuisineList as JSON);
    }
}
