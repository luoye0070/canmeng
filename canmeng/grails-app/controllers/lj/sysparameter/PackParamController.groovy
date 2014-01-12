package lj.sysparameter

import grails.converters.JSON

class PackParamController {
    PackParamService packParamService
//获取所有系统定义的包装方式
    def getPackList() {
        def packList=[];
        packList.add([id:0,name:"请选择"]);
        def packListTemp=packParamService.getPackList().packList;
        if(packListTemp){
            packListTemp.each {
                packList.add([id:it.id,name:it.name]);
            }
        }
        render (packList as JSON);
    }
}
