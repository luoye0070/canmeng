package canmeng

import lj.enumCustom.ReCode
import lj.shop.ShopService

class ShopEditFilters {
    def webUtilService;
    ShopService shopService;
    def filters = {
        all(controller: '*', action: '*') {
            before = {
                println "ShopEditFilters:"+controllerName+";"+actionName+";"+params
                if(
                        (controllerName.equals('authentication') && (actionName in ['authList','editAuth']))
                        ||(controllerName.equals('shop') && (actionName in ['editShopInfo','editReserveType','shopInfo']))
                ){
                    println "ShopManageFilters:-------------------"
                    if(!webUtilService.isLoggedIn()){
                        webUtilService.session['urlBackParams']=params.clone();
                        redirect(controller:'user',action:'login')
                        return false
                    }
                    if(shopService.getShopInfo(null).recode==ReCode.NOT_REGISTER_RESTAURANT){//店铺没有注册
                        redirect(controller:'shop',action:'shopRegister');
                        return false
                    }
                }

                return true
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
