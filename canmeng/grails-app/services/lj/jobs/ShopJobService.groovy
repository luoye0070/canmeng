package lj.jobs

import lj.data.FoodInfo
import lj.enumCustom.ReCode

class ShopJobService {

    def resetFoodSellCount() {
        try {
            def foodInfoList= FoodInfo.list();
            if(foodInfoList){
                foodInfoList.each {
                    it.totalSellCount+=it.sellCount;
                    it.sellCount=0;
                }
                return [recode:ReCode.OK];
            }
            else{
                return [recode:ReCode.NO_RESULT];
            }
        }
        catch (Exception ex){
            println("异常："+ex.message);
            return [recode: ReCode.OTHER_ERROR,error:ex.message];
        }

    }
}
