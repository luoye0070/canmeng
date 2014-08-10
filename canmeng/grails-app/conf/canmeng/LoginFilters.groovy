package canmeng

class LoginFilters {

    def webUtilService

    def filters = {
        loginCheck(controller:'*', action:'*') {
            before = {
                if(
                    (controllerName.equals('user') && (actionName in ['viewUserInfo','changePsd','emailAuth','securityQuestion','phoneAuth','userAddresses','myFavorites']))
                    ||(controllerName.equals('shop') && (actionName in ['shopRegister']))
                    ||(controllerName.equals('customer') && (actionName in ["createOrder",'reserveTable',"orderList","orderShow","doDish","delOrder",
                            "completeDish","addDishes","cancelDish","dishList","delDish","appraiseOrder"]))
                    ||(controllerName.equals('cartOfCustomer') && (actionName in ['cartCheckout']))
                ){
                    if(!webUtilService.isLoggedIn()){
                        webUtilService.session['urlBackParams']=params.clone();
                        redirect(controller:'user',action:'login')
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
