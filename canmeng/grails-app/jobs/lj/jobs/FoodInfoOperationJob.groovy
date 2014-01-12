package lj.jobs

import lj.enumCustom.ReCode



class FoodInfoOperationJob {
    private isRun = false;
    ShopJobService shopJobService;
    static triggers = {
        //simple repeatInterval: 5000l // execute job once in 5 seconds
        cron name: 'FoodInfoOperationJob', cronExpression: "0 0 1 * * ?"
    }

    def execute() {
        // execute job
        if (isRun)
            return ;
        try{
            isRun = true;
            println "重置菜谱日销量 --->"
            def reInfo=shopJobService.resetFoodSellCount(); //  重置菜谱日销量
            if(reInfo.recode!=ReCode.OK){ //
                println "重置菜谱日销量不成功:"+reInfo.recode.label;
            }
        } catch(Exception e){
            println "异常 --->" + e.message;
        }finally {
            isRun = false
        }
    }
}
