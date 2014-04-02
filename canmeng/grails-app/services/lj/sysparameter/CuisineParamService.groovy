package lj.sysparameter

import lj.data.CuisineInfo
import lj.enumCustom.ReCode

//菜系系统参数
class CuisineParamService {
    //获取所有系统定义的菜系
    def getCuisineList() {
        def cuisineList= CuisineInfo.all;
        if(cuisineList){
            return [recode: ReCode.OK,cuisineList:cuisineList];
        }
        else{
            return [recode:ReCode.NO_RESULT];
        }
    }
    //根据菜系ID获取名称
    def getCuisineById(long id){
        CuisineInfo cuisineInfo=CuisineInfo.findById(id);
        if(cuisineInfo){
            return [recode: ReCode.OK,cuisineInfo:cuisineInfo];
        }
        else{
            return [recode:ReCode.NO_RESULT];
        }
    }
}
