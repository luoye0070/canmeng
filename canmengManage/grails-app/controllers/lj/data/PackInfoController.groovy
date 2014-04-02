package lj.data

import grails.converters.JSON
import lj.data.PackInfo
import lj.util.Result
import org.springframework.dao.DataIntegrityViolationException

class PackInfoController {

    def index(){

    }
    def list() {

    }
    def edit() {
        []
    }
    def packInfoService
    def datagridService
    def datagrid = {
        def dataFormat={packInfoInstance->
            [
                    'id':packInfoInstance.id,
                    'name':packInfoInstance.name,
                    'description':packInfoInstance.description
            ]
        }
        def queryBlock={searchParams->
            PackInfo.createCriteria().list(params){
                if(params.name)
                    ilike('name','%'+params.name+'%')
            }
        }
        render datagridService.createForJson(params,queryBlock,dataFormat) as JSON;
    }

    def save() {
        Result result = new Result();
        def packInfoInstance = new PackInfo(param);

        if (!packInfoInstance.save(flush: true)) {
            result.success = false
            def msg = new StringBuffer()
            msg << g.message(code: 'packInfo.add.fail') << "\n"
            packInfoInstance.errors.allErrors.each { error ->
                msg << g.message(error: error) + "\n"
            }
            result.msg = msg.toString()
            render(contentType: 'text/html', text: result.toJson())
            return
        } else {
            result.success = true
            result.msg = g.message(code: 'packInfo.add.success')
            result.obj = packInfoInstance
            render(contentType: 'text/html', text: result.toJson())
        }
    }
    def editUpdHandle(){
        Result result=new Result();
        def packInfoInstance=packInfo.get(params.ids);
        if (!packInfoInstance) {
            result.msg=g.message(code: 'packInfo.not.found.message')
            result.success=false
            render(contentType:'text/html',text:result.toJson())
            return
        }
        packInfoInstance.properties=params
        if(!packInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'packInfo.edit.fail')<<"\n"
            packInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)<<"\n"
                ;
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'packInfo.edit.success')
            result.obj=packInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }
    }
    def addSaveHandle(){

        Result result=new Result()
        def packInfoInstance=new PackInfo(params)

        if(!packInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'packInfo.add.fail')<<"\n"
            packInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)+"\n"
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'packInfo.add.success')
            result.obj=packInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def del(){
        Result result=new Result()
        try {
            PackInfo.executeUpdate("delete from packInfo where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'packInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'packInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
}
