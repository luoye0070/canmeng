package lj.data

import grails.converters.JSON
import lj.data.PapersInfo
import lj.util.Result
import org.springframework.dao.DataIntegrityViolationException

class PapersInfoController {

    def index(){

    }
    def list() {

    }
    def edit() {
        []
    }
    def papersInfoService
    def datagridService
    def datagrid = {
        def dataFormat={papersInfoInstance->
            [
                    'id':papersInfoInstance.id,
                    'name':papersInfoInstance.name,
                    'description':papersInfoInstance.description
            ]
        }
        def queryBlock={searchParams->
            PapersInfo.createCriteria().list(params){
                if(params.name)
                    ilike('name','%'+params.name+'%')
            }
        }
        render datagridService.createForJson(params,queryBlock,dataFormat) as JSON;
    }

    def save() {
        Result result = new Result();
        def papersInfoInstance = new PapersInfo(param);

        if (!papersInfoInstance.save(flush: true)) {
            result.success = false
            def msg = new StringBuffer()
            msg << g.message(code: 'papersInfo.add.fail') << "\n"
            papersInfoInstance.errors.allErrors.each { error ->
                msg << g.message(error: error) + "\n"
            }
            result.msg = msg.toString()
            render(contentType: 'text/html', text: result.toJson())
            return
        } else {
            result.success = true
            result.msg = g.message(code: 'papersInfo.add.success')
            result.obj = papersInfoInstance
            render(contentType: 'text/html', text: result.toJson())
        }
    }
    def editUpdHandle(){
        Result result=new Result();
        def papersInfoInstance=papersInfo.get(params.ids);
        if (!papersInfoInstance) {
            result.msg=g.message(code: 'papersInfo.not.found.message')
            result.success=false
            render(contentType:'text/html',text:result.toJson())
            return
        }
        papersInfoInstance.properties=params
        if(!papersInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'papersInfo.edit.fail')<<"\n"
            papersInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)<<"\n"
                ;
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'papersInfo.edit.success')
            result.obj=papersInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }
    }
    def addSaveHandle(){

        Result result=new Result()
        def papersInfoInstance=new PapersInfo(params)

        if(!papersInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'papersInfo.add.fail')<<"\n"
            papersInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)+"\n"
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'papersInfo.add.success')
            result.obj=papersInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def del(){
        Result result=new Result()
        try {
            PapersInfo.executeUpdate("delete from papersInfo where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'papersInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'papersInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
}
