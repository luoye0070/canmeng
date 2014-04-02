package lj.data

import grails.converters.JSON
import lj.data.UserInfo
import lj.util.Result
import org.springframework.dao.DataIntegrityViolationException

class UserInfoController {

    def index(){
    }
    def list() {

    }
    def edit() {
        []
    }
    def userInfoService
    def datagridService
    def datagrid = {
        def dataFormat={userInfoInstance->
            [
                    'id':userInfoInstance.id,
                    'userName':userInfoInstance.userName,
                    'passWord':userInfoInstance.passWord,
                    'email':userInfoInstance.email,
                    'emailEnabled':userInfoInstance.emailEnabled,
                    'phone':userInfoInstance.phone,
                    'phoneEnabled':userInfoInstance.phoneEnabled,
                    'loginTime':userInfoInstance.loginTime,
                    'question':userInfoInstance.question,
                    'answer':userInfoInstance.answer,
                    'loginIp':userInfoInstance.loginIp,
                    'enabled':userInfoInstance.enabled,
                    'defaultAddrId':userInfoInstance.defaultAddrId
            ]
        }
        def queryBlock={searchParams->
            UserInfo.createCriteria().list(params){
                if(params.userName)
                    ilike('userName','%'+params.userName+'%')
            }
        }
        render datagridService.createForJson(params,queryBlock,dataFormat) as JSON;
    }

    def save() {
        Result result = new Result();
        def userInfoInstance = new UserInfo(param);

        if (!userInfoInstance.save(flush: true)) {
            result.success = false
            def msg = new StringBuffer()
            msg << g.message(code: 'userInfo.add.fail') << "\n"
            userInfoInstance.errors.allErrors.each { error ->
                msg << g.message(error: error) + "\n"
            }
            result.msg = msg.toString()
            render(contentType: 'text/html', text: result.toJson())
            return
        } else {
            result.success = true
            result.msg = g.message(code: 'userInfo.add.success')
            result.obj = userInfoInstance
            render(contentType: 'text/html', text: result.toJson())
        }
    }
    def editUpdHandle(){
        Result result=new Result();
        def userInfoInstance=UserInfo.get(params.ids);
        if (!userInfoInstance) {
            result.msg=g.message(code: 'userInfo.not.found.message')
            result.success=false
            render(contentType:'text/html',text:result.toJson())
            return
        }
        userInfoInstance.properties=params
        if(!userInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'userInfo.edit.fail')<<"\n"
            userInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)<<"\n"
                ;
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'userInfo.edit.success')
            result.obj=userInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }
    }
    def addSaveHandle(){

        Result result=new Result()
        def userInfoInstance=new UserInfo(params)

        if(!userInfoInstance.save(flush:true)){
            result.success=false
            def msg=new StringBuffer()
            msg<<g.message(code:'userInfo.add.fail')<<"\n"
            userInfoInstance.errors.allErrors.each{error->
                msg<<g.message(error:error)+"\n"
            }
            result.msg=msg.toString()
            render(contentType:'text/html',text:result.toJson())
            return
        }else{
            result.success=true
            result.msg=g.message(code:'userInfo.add.success')
            result.obj=userInfoInstance
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def del(){
        Result result=new Result()
        try {
            userInfo.executeUpdate("delete from userInfo where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'userInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'userInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
    def verify(){
        Result result=new Result()
        try {
            userInfo.executeUpdate("update userInfo set verifyStatus = 1 where id in (${params.ids})")
            result.success=true
            result.msg=g.message(code:'userInfo.del.success')
            render(contentType:'text/html',text:result.toJson())
            return
        }
        catch (DataIntegrityViolationException e) {
            result.success=false
            result.msg=g.message(code:'userInfo.del.fail')
            render(contentType:'text/html',text:result.toJson())
        }

    }
}
