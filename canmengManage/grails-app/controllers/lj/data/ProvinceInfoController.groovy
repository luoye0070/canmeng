package lj.data

import grails.converters.JSON
import lj.util.Result
import org.springframework.dao.DataIntegrityViolationException

class ProvinceInfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def edit() {

    }
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [provinceInfoInstanceList: ProvinceInfo.list(params), provinceInfoInstanceTotal: ProvinceInfo.count()]
    }

    def create() {
        [provinceInfoInstance: new ProvinceInfo(params)]
    }

    def datagridService
    def datagrid = {
        def dataFormat={provinceInfoInstance->
            [
                    'id':provinceInfoInstance.id,
                    'province':provinceInfoInstance.province,

            ]
        }
        def queryBlock={searchParams->
            ProvinceInfo.createCriteria().list(params){
                if(params.city)
                    ilike('city','%'+params.city+'%')
            }
        }
        render datagridService.createForJson(params,queryBlock,dataFormat) as JSON;
    }
    def save() {
        def provinceInfoInstance = new ProvinceInfo(params)
        if (!provinceInfoInstance.save(flush: true)) {
            result.success = false
            def msg = new StringBuffer()
            msg << g.message(code: 'provinceInfo.add.fail') << "\n"
            provinceInfoInstance.errors.allErrors.each { error ->
                msg << g.message(error: error) + "\n"
            }
            result.msg = msg.toString()
            render(contentType: 'text/html', text: result.toJson())
            return
        }

    }

    def editUpdHandle(){
        Result result=new Result();
        def provinceInfoInstance=ProvinceInfo.get(params.ids);
        if (!provinceInfoInstance) {
            result.msg=g.message(code: 'provinceInfo.not.found.message')
            result.success=false
            render(contentType:'text/html',text:result.toJson())
            return
        }
        provinceInfoInstance.properties=params
        if(!provinceInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'provinceInfo.edit.fail')<<"\n"
            provinceInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)<<"\n"
                ;
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'provinceInfo.edit.success')
            result.obj=provinceInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }
    }
    def addSaveHandle(){

        Result result=new Result()
        def provinceInfoInstance=new ProvinceInfo(params)

        if(!provinceInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'provinceInfo.add.fail')<<"\n"
            provinceInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)+"\n"
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'provinceInfo.add.success')
            result.obj=provinceInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def del(){
        Result result=new Result()
        try {
            ProvinceInfo.executeUpdate("delete from ProvinceInfo where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'provinceInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'provinceInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
}
