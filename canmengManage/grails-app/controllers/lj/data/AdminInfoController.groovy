package lj.data

import grails.converters.JSON
import lj.data.*
import lj.util.Result
import org.springframework.dao.DataIntegrityViolationException

class AdminInfoController {

    def index(){

    }
    def list() {

    }
    def edit() {
        []
    }
    def adminInfoService
    def datagridService
    def datagrid = {
        def dataFormat={adminInfoInstance->
            [
                    'id':adminInfoInstance.id,
                    'username':adminInfoInstance.username,
                    'password':adminInfoInstance.password,
                    'authority':adminInfoInstance.authority
            ]
        }
        def queryBlock={searchParams->
            AdminInfo.createCriteria().list(params){
                if(params.username)
                    ilike('username','%'+params.username+'%')
            }
        }
        render datagridService.createForJson(params,queryBlock,dataFormat) as JSON;
    }

    def save() {
        Result result = new Result();
        def adminInfoInstance = new AdminInfo(param);

        if (!adminInfoInstance.save(flush: true)) {
            result.success = false
            def msg = new StringBuffer()
            msg << g.message(code: 'adminInfo.add.fail') << "\n"
            adminInfoInstance.errors.allErrors.each { error ->
                msg << g.message(error: error) + "\n"
            }
            result.msg = msg.toString()
            render(contentType: 'text/html', text: result.toJson())
            return
        } else {
            result.success = true
            result.msg = g.message(code: 'adminInfo.add.success')
            result.obj = adminInfoInstance
            render(contentType: 'text/html', text: result.toJson())
        }
    }
    def editUpdHandle(){
        Result result=new Result();
        def adminInfoInstance=adminInfo.get(params.ids);
        if (!adminInfoInstance) {
            result.msg=g.message(code: 'adminInfo.not.found.message')
            result.success=false
            render(contentType:'text/html',text:result.toJson())
            return
        }
        adminInfoInstance.properties=params
        if(!adminInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'adminInfo.edit.fail')<<"\n"
            adminInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)<<"\n"
                ;
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'adminInfo.edit.success')
            result.obj=adminInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }
    }
    def addSaveHandle(){

        Result result=new Result()
        def adminInfoInstance=new AdminInfo(params)

        if(!adminInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'adminInfo.add.fail')<<"\n"
            adminInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)+"\n"
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'adminInfo.add.success')
            result.obj=adminInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def del(){
        Result result=new Result()
        try {
            adminInfo.executeUpdate("delete from adminInfo where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'adminInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'adminInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def login(){

        def type

        def username=session.username
        if(username){
            type=2 //已经登陆
        }else{
            def success=AdminInfo.findByUsernameAndPassword(params.username,params.password)
            if(success){
                session.username=params.username
                type=1 //初次登陆成功

            }else{
                type=9 //用户名密码不正确,登陆失败
            }

        }
        def res=[type:type,username:params.username]
        render(contentType:'text/html',text:res as JSON)
    }

    def logout(){

        def type
        def username=session.username
        if(!username){
            type=0 //未登录
        }else{
            session.username=null
            type=8 //退出成功
        }

        def res=[type:type]
        render(contentType:'text/html',text:res as JSON)
    }
}
