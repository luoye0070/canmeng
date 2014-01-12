package lj.sysparameter

import lj.data.PapersInfo
import lj.enumCustom.ReCode
 //系统证件参数
class PapersParamService {

    //获取系统定义的所有证件类型列表
    def getPapersList(){
        def papersList= PapersInfo.all;
        if(papersList){
            return [recode: ReCode.OK,papersList:papersList];
        }
        else{
            return [recode:ReCode.NO_RESULT];
        }
    }

}
