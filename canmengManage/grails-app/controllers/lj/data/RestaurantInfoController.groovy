package lj.data

import grails.converters.JSON
import lj.data.RestaurantInfo
import lj.util.Result
import org.springframework.dao.DataIntegrityViolationException

class RestaurantInfoController {

    def index(){

    }
    def list() {

    }
    def edit() {

    }
    def restaurantInfoService
    def datagridService
    def datagrid = {
        def dataFormat={restaurantInfoInstance->
            [
                    'id':restaurantInfoInstance.id,
                    'name':restaurantInfoInstance.name,
                    'image':restaurantInfoInstance.image,
                    'province':restaurantInfoInstance.province,
                    'city':restaurantInfoInstance.city,
                    'area':restaurantInfoInstance.area,
                    'street':restaurantInfoInstance.street,
                    'longitude':restaurantInfoInstance.longitude,
                    'latitude':restaurantInfoInstance.latitude,
                    'phone':restaurantInfoInstance.phone,
                    'masterName':restaurantInfoInstance.masterName,
                    'userId':restaurantInfoInstance.userId,
                    'shopHoursBeginTime':restaurantInfoInstance.shopHoursBeginTime,
                    'shopHoursEndTime':restaurantInfoInstance.shopHoursEndTime,
                    'enabled':restaurantInfoInstance.enabled,
                    'cuisineId':restaurantInfoInstance.cuisineId,
                    'freight':restaurantInfoInstance.freight,
                    'freeFreight':restaurantInfoInstance.freeFreight,
                    'packId':restaurantInfoInstance.packId,
                    'imageSpaceSize':restaurantInfoInstance.imageSpaceSize,
                    'imageSpaceUsedSize':restaurantInfoInstance.imageSpaceUsedSize,
                    'verifyStatus':restaurantInfoInstance.verifyStatus,
                    'deliverRange':restaurantInfoInstance.deliverRange,
                    'averageConsume':restaurantInfoInstance.averageConsume,
                    'remark':restaurantInfoInstance.remark,
                    'description':restaurantInfoInstance.description,
            ]
        }
        def queryBlock={searchParams->
            RestaurantInfo.createCriteria().list(params){
                if(params.description)
                    ilike('description','%'+params.description+'%')
            }
        }
        render datagridService.createForJson(params,queryBlock,dataFormat) as JSON;
    }

    def save() {
        Result result = new Result();
        def restaurantInfoInstance = new RestaurantInfo(param);

        if (!restaurantInfoInstance.save(flush: true)) {
            result.success = false
            def msg = new StringBuffer()
            msg << g.message(code: 'restaurantInfo.add.fail') << "\n"
            restaurantInfoInstance.errors.allErrors.each { error ->
                msg << g.message(error: error) + "\n"
            }
            result.msg = msg.toString()
            render(contentType: 'text/html', text: result.toJson())
            return
        } else {
            result.success = true
            result.msg = g.message(code: 'restaurantInfo.add.success')
            result.obj = restaurantInfoInstance
            render(contentType: 'text/html', text: result.toJson())
        }
    }
    def editUpdHandle(){
        Result result=new Result();
        def restaurantInfoInstance=restaurantInfo.get(params.ids);
        if (!restaurantInfoInstance) {
            result.msg=g.message(code: 'restaurantInfo.not.found.message')
            result.success=false
            render(contentType:'text/html',text:result.toJson())
            return
        }
        restaurantInfoInstance.properties=params
        if(!restaurantInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'restaurantInfo.edit.fail')<<"\n"
            restaurantInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)<<"\n"
                ;
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'restaurantInfo.edit.success')
            result.obj=restaurantInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }
    }
    def addSaveHandle(){

        Result result=new Result()
        def restaurantInfoInstance=new RestaurantInfo(params)

        if(!restaurantInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'restaurantInfo.add.fail')<<"\n"
            restaurantInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)+"\n"
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'restaurantInfo.add.success')
            result.obj=restaurantInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def del(){
        Result result=new Result()
        try {
            restaurantInfo.executeUpdate("delete from restaurantInfo where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'restaurantInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'restaurantInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def verify(){
        Result result=new Result()
        try {
            RestaurantInfo.executeUpdate("update RestaurantInfo set verifyStatus = 1 where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'restaurantInfo.verify.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'restaurantInfo.verify.fail')
            render(contentType:'text/html',text:result.toJson())
        }
    }
}
