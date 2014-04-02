package lj.canmengMange
import lj.data.*
class CuisineInfoService {

    def serviceMethod() {

    }
    def list(def params){
       if(!params.max) {
           params.max = 10
       }
        if(!params.offset) {
           params.offset = 0
        }
        return [cuisineInfoList:CuisineInfo.createCriteria().list(params){
            if(params.name)
                ilike('name','%'+params.name+'%')
        },totalCount:CuisineInfo.createCriteria().count(){
            if(params.name)
                ilike('name','%'+params.name+'%')
        }]
    }
    def edit(def params){
       CuisineInfo cuisineInfo=null;
       //修改状态
       if(params.id){
           cuisineInfo=CuisineInfo.get(params.id);
       }
       //添加状态
       else{
           cuisineInfo=new CuisineInfo();
       }
        cuisineInfo.name = params.name1;
        cuisineInfo.description = params.description1;
        return cuisineInfo.save(flush: true);
    }
}
