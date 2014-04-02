package lj.data

import grails.converters.JSON
import lj.util.Result
import org.springframework.dao.DataIntegrityViolationException

class CityInfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def edit() {

    }
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cityInfoInstanceList: CityInfo.list(params), cityInfoInstanceTotal: CityInfo.count()]
    }

    def create() {
        [cityInfoInstance: new CityInfo(params)]
    }
    def datagridService
    def datagrid = {
        def dataFormat={cityInfoInstance->
            [
                    'id':cityInfoInstance.id,
                    'provinceId':cityInfoInstance.provinceId,
                    'city':cityInfoInstance.city

            ]
        }
        def queryBlock={searchParams->
            CityInfo.createCriteria().list(params){
                if(params.city)
                    ilike('city','%'+params.city+'%')
            }
        }
        render datagridService.createForJson(params,queryBlock,dataFormat) as JSON;
    }
    def save() {
        def cityInfoInstance = new CityInfo(params)
        if (!cityInfoInstance.save(flush: true)) {
            result.success = false
            def msg = new StringBuffer()
            msg << g.message(code: 'cityInfo.add.fail') << "\n"
            cityInfoInstance.errors.allErrors.each { error ->
                msg << g.message(error: error) + "\n"
            }
            result.msg = msg.toString()
            render(contentType: 'text/html', text: result.toJson())
            return
        }

    }

    def editUpdHandle(){
        Result result=new Result();
        def cityInfoInstance=CityInfo.get(params.ids);
        if (!cityInfoInstance) {
            result.msg=g.message(code: 'cityInfo.not.found.message')
            result.success=false
            render(contentType:'text/html',text:result.toJson())
            return
        }
        cityInfoInstance.properties=params
        if(!cityInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'cityInfo.edit.fail')<<"\n"
            cityInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)<<"\n"
                ;
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'cityInfo.edit.success')
            result.obj=cityInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }
    }
    def addSaveHandle(){

        Result result=new Result()
        def cityInfoInstance=new CityInfo(params)

        if(!cityInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'cityInfo.add.fail')<<"\n"
            cityInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)+"\n"
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'cityInfo.add.success')
            result.obj=cityInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def del(){
        Result result=new Result()
        try {
            cityInfo.executeUpdate("delete from cityInfo where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'cityInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'cityInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
}
