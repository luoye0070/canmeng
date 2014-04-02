package canmeng

class StaffLoginFilters {
    def webUtilService;
    def filters = {
        all(controller:'*', action:'*') {
            before = {
                if(
                (controllerName.equals('staff') && (actionName in ["createOrder",'reserveTable',"orderList","orderShow","doDish","delOrder",
                        "completeDish","addDishes","cancelDish","dishList","delDish","settleAccounts","completeAffirmDish","affirmValid",
                        "affirmDish","beginCook","completeCook","index"])
                )
                ){
                    if(!webUtilService.isStaffLoggedIn()){
                        webUtilService.session['urlBackParams']=params.clone();
                        redirect(controller:'staffManage',action:'staffLogin')
                        return false
                    }
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
