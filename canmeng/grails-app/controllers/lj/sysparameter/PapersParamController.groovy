package lj.sysparameter

import grails.converters.JSON

//证件相关系统参数
class PapersParamController {
    PapersParamService papersParamService;
    //获取系统定义的所有证件类型列表
    def getPapersList(){
        def papersList=[];
        papersList.add([id:0,name:"请选择"]);
        def papersListTemp=papersParamService.getPapersList()?.papersList;
        if(papersListTemp){
            papersListTemp.each {
                papersList.add([id:it.id,name:it.name]);
            }
        }
        render (papersList as JSON);
        //render (papersParamService.getPapersList() as JSON);
    }
}
