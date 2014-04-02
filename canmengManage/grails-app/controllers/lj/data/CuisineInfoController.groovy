package lj.data
import lj.data.*
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import lj.util.Result
import lj.util.*

class CuisineInfoController {
    def index(){

    }
    def list() {

    }
    def edit() {
        []
    }
    def cuisineInfoService
    def datagridService
    def datagrid = {
        def dataFormat={cuisineInfoInstance->
            [
                    'id':cuisineInfoInstance.id,
                    'name':cuisineInfoInstance.name,
                    'description':cuisineInfoInstance.description
            ]
        }
        def queryBlock={searchParams->
            CuisineInfo.createCriteria().list(params){
                if(params.name)
                    ilike('name','%'+params.name+'%')
            }
        }
        render datagridService.createForJson(params,queryBlock,dataFormat) as JSON;
    }

    def save() {
        Result result = new Result();
        def cuisineInfoInstance = new CuisineInfo(param);

        if (!cuisineInfoInstance.save(flush: true)) {
            result.success = false
            def msg = new StringBuffer()
            msg << g.message(code: 'cuisineInfo.add.fail') << "\n"
            cuisineInfoInstance.errors.allErrors.each { error ->
                msg << g.message(error: error) + "\n"
            }
            result.msg = msg.toString()
            render(contentType: 'text/html', text: result.toJson())
            return
        } else {
            result.success = true
            result.msg = g.message(code: 'cuisineInfo.add.success')
            result.obj = cuisineInfoInstance
            render(contentType: 'text/html', text: result.toJson())
        }
    }
    def editUpdHandle(){
        Result result=new Result();
        def cuisineInfoInstance=CuisineInfo.get(params.ids);
        if (!cuisineInfoInstance) {
            result.msg=g.message(code: 'cuisineInfo.not.found.message')
            result.success=false
            render(contentType:'text/html',text:result.toJson())
            return
        }
        cuisineInfoInstance.properties=params
        if(!cuisineInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'cuisineInfo.edit.fail')<<"\n"
            cuisineInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)<<"\n"
                ;
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'cuisineInfo.edit.success')
            result.obj=cuisineInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }
    }
    def addSaveHandle(){

        Result result=new Result()
        def cuisineInfoInstance=new CuisineInfo(params)

        if(!cuisineInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'Teacher.add.fail')<<"\n"
            cuisineInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)+"\n"
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'Teacher.add.success')
            result.obj=cuisineInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def del(){
        Result result=new Result()
        try {
            CuisineInfo.executeUpdate("delete from CuisineInfo where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'cuisineInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'cuisineInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
}

