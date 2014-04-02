import grails.converters.JSON
import lj.common.Result
import lj.data.StaffInfo
import lj.mina.server.MinaServer
import lj.mina.server.OnLineListener

class BootStrap {

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Date){
            return it?.format("yyyy-MM-dd HH:mm:ss")
        }
        MinaServer.start(5000,"UTF-8","\n","\n",null);
    }
    def destroy = {
    }
}
