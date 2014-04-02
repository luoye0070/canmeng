package lj.data

import grails.converters.JSON
import lj.data.AppraiseInfo
import lj.util.Result
import org.springframework.dao.DataIntegrityViolationException

class AppraiseInfoController {

    def index(){

    }
    def list() {

    }
    def edit() {
        []
    }
    def appraiseInfoService
    def datagridService
    def datagrid = {
        def dataFormat={appraiseInfoInstance->
            [
                    'id':appraiseInfoInstance.id,
                    'orderId':appraiseInfoInstance.orderId,
                    'type':appraiseInfoInstance.type,
                    'hygienicQuality':appraiseInfoInstance.hygienicQuality,
                    'serviceAttitude':appraiseInfoInstance.serviceAttitude,
                    'deliverySpeed':appraiseInfoInstance.deliverySpeed,
                    'taste':appraiseInfoInstance.taste,
                    'whole':appraiseInfoInstance.whole,
                    'content':appraiseInfoInstance.content,
                    'appraiseTime':appraiseInfoInstance.appraiseTime,
                    'userId':appraiseInfoInstance.userId,
                    'isAnonymity':appraiseInfoInstance.isAnonymity,
                    'restaurantId':appraiseInfoInstance.restaurantId,
                    'userName':appraiseInfoInstance.userName
            ]
        }
        def queryBlock={searchParams->
            AppraiseInfo.createCriteria().list(params){
                if(params.userName)
                    ilike('userName','%'+params.userName+'%')
            }
        }
        render datagridService.createForJson(params,queryBlock,dataFormat) as JSON;
    }

    def save() {
        Result result = new Result();
        def appraiseInfoInstance = new AppraiseInfo(param);

        if (!appraiseInfoInstance.save(flush: true)) {
            result.success = false
            def msg = new StringBuffer()
            msg << g.message(code: 'appraiseInfo.add.fail') << "\n"
            appraiseInfoInstance.errors.allErrors.each { error ->
                msg << g.message(error: error) + "\n"
            }
            result.msg = msg.toString()
            render(contentType: 'text/html', text: result.toJson())
            return
        } else {
            result.success = true
            result.msg = g.message(code: 'appraiseInfo.add.success')
            result.obj = appraiseInfoInstance
            render(contentType: 'text/html', text: result.toJson())
        }
    }
    def editUpdHandle(){
        Result result=new Result();
        def appraiseInfoInstance=appraiseInfo.get(params.ids);
        if (!appraiseInfoInstance) {
            result.msg=g.message(code: 'appraiseInfo.not.found.message')
            result.success=false
            render(contentType:'text/html',text:result.toJson())
            return
        }
        appraiseInfoInstance.properties=params
        if(!appraiseInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'appraiseInfo.edit.fail')<<"\n"
            appraiseInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)<<"\n"
                ;
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'appraiseInfo.edit.success')
            result.obj=appraiseInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }
    }
    def addSaveHandle(){

        Result result=new Result()
        def appraiseInfoInstance=new AppraiseInfo(params)

        if(!appraiseInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'appraiseInfo.add.fail')<<"\n"
            appraiseInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)+"\n"
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'appraiseInfo.add.success')
            result.obj=appraiseInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def del(){
        Result result=new Result()
        try {
            appraiseInfo.executeUpdate("delete from appraiseInfo where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'appraiseInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'appraiseInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
}
