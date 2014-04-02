package lj.data

import grails.converters.JSON
import lj.util.Result
import org.springframework.dao.DataIntegrityViolationException

class AreaInfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
    def edit() {

    }
    def list() {

    }

    def create() {
        [areaInfoInstance: new AreaInfo(params)]
    }

    def datagridService
    def datagrid = {
        def dataFormat={areaInfoInstance->
            [
                    'id':areaInfoInstance.id,
                    'provinceId':areaInfoInstance.provinceId,
                    'city':areaInfoInstance.city

            ]
        }
        def queryBlock={searchParams->
            AreaInfo.createCriteria().list(params){
                if(params.city)
                    ilike('city','%'+params.city+'%')
            }
        }
        render datagridService.createForJson(params,queryBlock,dataFormat) as JSON;
    }
    def save() {
        def areaInfoInstance = new AreaInfo(params)
        if (!areaInfoInstance.save(flush: true)) {
            result.success = false
            def msg = new StringBuffer()
            msg << g.message(code: 'AreaInfo.add.fail') << "\n"
            areaInfoInstance.errors.allErrors.each { error ->
                msg << g.message(error: error) + "\n"
            }
            result.msg = msg.toString()
            render(contentType: 'text/html', text: result.toJson())
            return
        }

    }

    def editUpdHandle(){
        Result result=new Result();
        def areaInfoInstance=AreaInfo.get(params.ids);
        if (!areaInfoInstance) {
            result.msg=g.message(code: 'AreaInfo.not.found.message')
            result.success=false
            render(contentType:'text/html',text:result.toJson())
            return
        }
        areaInfoInstance.properties=params
        if(!areaInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'AreaInfo.edit.fail')<<"\n"
            areaInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)<<"\n"
                ;
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'AreaInfo.edit.success')
            result.obj=areaInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }
    }
    def addSaveHandle(){

        Result result=new Result()
        def areaInfoInstance=new AreaInfo(params)

        if(!areaInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'AreaInfo.add.fail')<<"\n"
            areaInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)+"\n"
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'AreaInfo.add.success')
            result.obj=areaInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def del(){
        Result result=new Result()
        try {
            AreaInfo.executeUpdate("delete from AreaInfo where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'AreaInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'AreaInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
}
