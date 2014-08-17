package lj.order.staff

import lj.data.AddressInfo
import lj.enumCustom.ReCode

class StaffCommonService {

    //从地址库中匹配出相近地址
    def getAddress(def params){
        String phone=params.phone;
        String linkManName=params.linkManName;
        AddressInfo addressInfo=null;
        if(phone&&linkManName){
            addressInfo=AddressInfo.findByPhoneAndLinkManName(phone,linkManName);
        }else if (linkManName){
            addressInfo=AddressInfo.findByLinkManName(linkManName);
        }else if(phone){
            addressInfo=AddressInfo.findByPhone(phone);
        }
        if(addressInfo){
            return [recode:ReCode.OK,addressInfo:addressInfo];
        }else{
            return [recode:ReCode.NO_RESULT];
        }

    }



}
