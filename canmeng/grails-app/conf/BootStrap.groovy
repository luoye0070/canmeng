import grails.converters.JSON
import lj.common.Result

class BootStrap {

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Date){
            return it?.format("yyyy-MM-dd HH:mm:ss")
        }
    }
    def destroy = {
    }
}
