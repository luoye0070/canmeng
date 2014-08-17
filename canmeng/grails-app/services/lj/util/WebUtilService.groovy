package lj.util

import lj.data.ClientInfo
import lj.data.StaffInfo
import lj.data.UserInfo
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.web.context.request.RequestContextHolder

import javax.servlet.http.Cookie

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

    //设置客户端
    void setClient(long clientId){
        session.clientId=clientId;
    }
    long getClientId(){
        return lj.Number.toLong(session.clientId);
    }
    //获取客户端
    ClientInfo getClient(){
        long clientId=lj.Number.toLong(session.clientId);
        ClientInfo clientInfo=ClientInfo.get(clientId);
        return clientInfo
    }
    //判断客户端是否登录
    boolean isClientLoggedIn(){
        return (session.clientId!=null&&session.clientId!=0l)
    }


    //设置用户
    void setUser(UserInfo userInfo){
        session.userId=userInfo.id;
    }
    //获取用户
    UserInfo getUser(){
        long userId=lj.Number.toLong(session.userId);
        UserInfo userInfo=UserInfo.get(userId);
        return userInfo;
    }
    //判定用户是否登录
    Boolean isLoggedIn(){
        return (session.userId!=null)
    }

    void clearSession()
    {
        session.invalidate();
    }

    //获取session
    public GrailsHttpSession getSession(){
        GrailsWebRequest request=RequestContextHolder.currentRequestAttributes()
        return request.session
    }

//    //获取request
//    public GrailsWebRequest getRequest(){
//        return RequestContextHolder.currentRequestAttributes()
//    }
    //获取Request object
    def getRequest(){
        def webUtils = WebUtils.retrieveGrailsWebRequest()
        webUtils.getCurrentRequest()
    }

//获取 the Response object
    def getResponse(){
        def webUtils = WebUtils.retrieveGrailsWebRequest()
        webUtils.getCurrentResponse()
    }

//Getting the ServletContext object
    def getServletContext(){
        def webUtils = WebUtils.retrieveGrailsWebRequest()
        webUtils.getServletContext()
    }



    //设置工作人员
    def setStaff(StaffInfo staffInfo){
        session.staffId=staffInfo.id;
    }
    //获取工作人员ID
    def getStaffId(){
        long staffId=lj.Number.toLong(session.staffId);
        return staffId;
    }
    //获取工作人员
    def getStaff(){
        long staffId=lj.Number.toLong(session.staffId);
        StaffInfo staffInfo=StaffInfo.get(staffId);
        return staffInfo;
    }
    //判定工作人员是否登录
    Boolean isStaffLoggedIn(){
        return (session.staffId!=null)
    }

    /**
     * 获取客户端ip
     * <p>获取客户端ip</p>
     * @author 刘兆国
     * @param
     * @return
     * @Date: 2013-11-28
     * @Time: 上午11: 43
     */
    public String getClientIp(){
        def request = getRequest();
        def clientIp=""
        /*****获取外网ip********/
        if (request.getHeader("X-Forwarded-For"))
            clientIp = request.getHeader("X-Forwarded-For")
        else if(request.getHeader("X-Real-IP"))
            clientIp = request.getHeader("X-Real-IP")
        else
            clientIp = "0.0.0.0"

        /********获取内网ip**********/
        def vcip= request.getRemoteAddr()
        if(vcip==null||vcip==""){
            clientIp=clientIp+"."+"0.0.0.0"
        }else{
            clientIp=clientIp+"."+vcip
        }
        //println "clientIp: "+clientIp
        return clientIp;
    }

    //写入cookie
    def writeCookie(String name,String value,int day){
        Cookie cookie=new Cookie(name,value.encodeAsURL());
        cookie.setMaxAge(3600*24*day);
        cookie.setPath("/");
        //cookie.setDomain("");
        def response=getResponse();
        response.addCookie(cookie);
    }
    //读取cookie
    def readCookie(String name){
        String value=null;
        def request=getRequest();
        Cookie[] cookies=request.getCookies();
        for (Cookie cookie:cookies){
            if(cookie.name==name){
                value=URLDecoder.decode(cookie.value,"UTF-8")
                break;
            }
        }
        return value;
    }
    //获取sessionID
    def getSessionId(){
        return session.id;
    }
}
