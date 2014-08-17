package canmeng

import lj.sysparameter.AreaParamService

class CommonFilters {
    AreaParamService areaParamService;
    def filters = {
        all(controller: '*', action: '*') {
            before = {

            }
            after = { Map model ->
                def reInfo=areaParamService.getCityByCookieOrIp();
                if(model!=null)
                    model<<[cityIdAndName:reInfo.cityIdAndName];
                else
                    model=[cityIdAndName:reInfo.cityIdAndName];
                //session.getAttribute("clientId");
            }
            afterView = { Exception e ->

            }
        }
    }
}
