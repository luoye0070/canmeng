package canmeng

import lj.enumCustom.ReCode
import lj.shop.ShopService

class ShopManageFilters {
    def webUtilService;
    ShopService shopService;
    def filters = {
        all(controller:'*', action:'*') {
            before = {
                println "ShopManageFilters:"+controllerName+";"+actionName+";"+params
                if(
                    (controllerName.equals('foodManage') && (actionName in ['foodList','editFoodInfo','delFoodInfo']))
                    ||(controllerName.equals('tableManage') && (actionName in ['tableList','editTableInfo','delTableInfo','printTable']))
                    ||(controllerName.equals('imageSpace') && (actionName in ['upload','imageList','recycleToRecycled','recoverFromRecycled','delImage','imageClassList','editImageClass','delImageClass']))
                    ||(controllerName.equals('staffManage') && (actionName in ['editStaffInfo','staffList','delStaff']))
                    ||(controllerName.equals('customerRelations') && (actionName in ['crList','editCr','delCr']))
                ){
                    println "ShopManageFilters:-------------------"
                    if(!webUtilService.isLoggedIn()){
                        webUtilService.session['urlBackParams']=params.clone();
                        redirect(controller:'user',action:'login')
                        return false
                    }
                    def reInfo=shopService.getShopEnabled();
                    if(reInfo.recode==ReCode.NOT_REGISTER_RESTAURANT){//店铺没有注册
                        redirect(controller:'shop',action:'shopRegister');
                        return false
                    }
                    else if(reInfo.recode==ReCode.SHOP_NOT_ENABLED){ //店铺关闭
                        redirect(controller: "shop",action: "editShopInfo");
                        return false;
                    }
                    else if(reInfo.recode==ReCode.SHOP_WAIT_VERIFY){ //等待审核中
                        redirect(controller: "shop",action: "editShopInfo");
                        return false;
                    }
                    else if(reInfo.recode==ReCode.SHOP_VERIFY_NOT_PASS){ //审核审核没通过
                        redirect(controller: "shop",action: "editShopInfo");
                        return false;
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
