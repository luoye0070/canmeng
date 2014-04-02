package lj.shop

import grails.converters.JSON
import lj.enumCustom.VerifyStatus

class SearchAjaxController {
    SearchService searchService;
    //饭店检索
    def searchShop(){
        params.enabled=true;
        params.verifyStatus=VerifyStatus.PASS.code;
        def reInfo=searchService.searchShop(params);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }
    //检索桌位
    def searchTablesInShop(){

        if(!params.date){
            params.date=lj.FormatUtil.dateFormat(new Date());
        }
        if(!params.reserveType){
            params.reserveType="1";
        }

        params.canReserve=true;
        println("params-->"+params);
        def reInfo=searchService.searchTable(params);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }
}
