package lj.util

import lj.data.UserInfo
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder

//web工具服务
class WebUtilService {

    static transactional = false
    static scope = "singleton"

    //在服务中使用session
    void withSession(Closure closure) {
        try {
            closure.call(session)
        }catch(IllegalStateException ise){
            log.warn("No WebRequest available")
        }
    }

    //设置用户
    def setUser(UserInfo userInfo){
        session.invalidate()
        session.user=user
    }

    //获取用户
    def getUser(){
        return session.user
    }

    def clearSession()
    {
        session.user=user
        session.invalidate()
    }

    //获取session
    public GrailsHttpSession getSession(){
        GrailsWebRequest request=RequestContextHolder.currentRequestAttributes()
        println(request.session)
        return request.session
    }

    //获取request
    public GrailsWebRequest getRequest(){
        return RequestContextHolder.currentRequestAttributes()
    }

    //判定用户是否登录
    Boolean isLoggedIn(){
        return (session.user!=null)
    }
}
