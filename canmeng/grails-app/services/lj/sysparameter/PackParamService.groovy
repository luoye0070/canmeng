package lj.sysparameter

import lj.data.PackInfo
import lj.enumCustom.ReCode

//系统定义的包装方式
class PackParamService {
    //获取所有系统定义的包装方式
    def getPackList() {
        def packList= PackInfo.all;
        if(packList){
            return [recode: ReCode.OK,packList:packList];
        }
        else{
            return [recode:ReCode.NO_RESULT];
        }
    }
    //根据菜系ID获取名称
    def getPackById(long id){
        PackInfo packInfo=PackInfo.findById(id);
        if(packInfo){
            return [recode: ReCode.OK,packInfo:packInfo];
        }
        else{
            return [recode:ReCode.NO_RESULT];
        }
    }
}
