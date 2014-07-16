package lj.shop

import lj.data.ClientInfo
import lj.data.CustomerRelations
import lj.data.FoodInfo
import lj.data.OrderInfo
import lj.data.UserInfo
import lj.enumCustom.OrderStatus
import lj.enumCustom.OrderValid
import lj.enumCustom.ReCode

class CustomerRelationsService {
    def webUtilService;
    def searchService;
    def shopService;
    def serviceMethod() {

    }
    //从订单中获取客户列表
    def getCustomerFromOrder(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //取参数
            //Long id=lj.Number.toLong(params.id);

            //检查店铺可用性
            def reInfo=shopService.getShopEnabled();
            long restaurantId=0;
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }
            else{
                restaurantId=reInfo.restaurantInfo.id;
            }
            //def orderInfoList=OrderInfo.findAllByRestaurantIdAndValidAndStatus(restaurantId,OrderValid.EFFECTIVE_VALID.code,OrderStatus.CHECKOUTED_STATUS.code);
            String sql_str="select distinct clientId from OrderInfo where clientId!=0"+
                    " and restaurantId="+restaurantId+
                    " and valid="+ OrderValid.EFFECTIVE_VALID.code+
                    " and status="+OrderStatus.CHECKOUTED_STATUS.code;
            def clientIdList=OrderInfo.executeQuery(sql_str);
            //def userList=null;
            println(clientIdList);
            if(clientIdList){
                def userList=clientIdList.collect {
                    [
                            clientId:it,
                            userName:UserInfo.get(it)?.userName
                    ];
                }
                return [recode: ReCode.OK,userList:userList];
            }
            return [recode: ReCode.NO_RESULT];
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //保存客户关系
    def save(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //取参数
            Long id=lj.Number.toLong(params.id);//非必须
            Long customerClientId=lj.Number.toLong(params.customerClientId);  //客户用户ID,非必须
            String customerUserName=params.customerUserName;//客户用户名,非必须
            int type=lj.Number.toInteger(params.type);//客户类型,必须,默认为0普通客户
            if(customerClientId==0&&(customerUserName==""||customerUserName==null)){
                return [recode: ReCode.NEED_CUSTOMER_ID_OR_NAME];
            }

            //检查店铺可用性
            def reInfo=shopService.getShopEnabled();
            long restaurantId=0;   //店铺ID
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }
            else{
                restaurantId=reInfo.restaurantInfo.id;
            }
            CustomerRelations customerRelations=null;
            if(id){
                customerRelations=CustomerRelations.get(id);
            }
            if(customerRelations){
                //修改客户类型
                customerRelations.type=type;
            }
            else{
                //检查客户关系是否已经存在
                customerRelations=CustomerRelations.findByRestaurantIdAndCustomerClientId(restaurantId,customerClientId);
                if(customerRelations){//客户关系已经存在
                    return [recode: ReCode.CUSTOMER_RELATIONS_EXIST];
                }
                //检查客户用户是否存在
                ClientInfo clientInfo=null;
                if(customerClientId)
                    clientInfo= ClientInfo.get(customerClientId);
                else if(customerUserName)
                    clientInfo=ClientInfo.findByUserName(customerUserName);
                if(clientInfo){
                    customerRelations=new CustomerRelations();
                    customerRelations.restaurantId=restaurantId;
                    customerRelations.customerClientId=clientInfo.id;
                    customerRelations.customerUserName=clientInfo.userName;
                    customerRelations.type=type;
                }
                else{
                    return [recode: ReCode.CUSTOMER_NOT_EXIST];
                }
            }
            if(customerRelations.validate()){
                if(customerRelations.save(flush: true)){
                    return [recode: ReCode.OK,customerRelations:customerRelations];
                }
                else
                    return [recode: ReCode.SAVE_FAILED,customerRelations:customerRelations,errors:customerRelations.errors.allErrors];
            }
            else{
                return [recode: ReCode.SAVE_FAILED,customerRelations:customerRelations,errors:customerRelations.errors.allErrors];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //客户关系删除
    def delete(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //检查店铺可用性
            def reInfo=shopService.getShopEnabled();
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }

            //根据ID删除记录
            if(params.ids instanceof String){ //传入id
                Long id=lj.Number.toLong(params.ids); //菜单id
                CustomerRelations.executeUpdate("delete from CustomerRelations where id=?",[id]);
            }
            else if(params.ids instanceof String[]){//传入id数组
                String sql_s= "delete from CustomerRelations where id in (0";
                for(int i=0;i<params.ids.length;i++){
                    Long id=lj.Number.toLong(params.ids[i]);
                    sql_s+=","+id;
                }
                sql_s+=")";
                CustomerRelations.executeUpdate(sql_s);
            }

            return [recode: ReCode.OK];
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //客户关系查询
    def search(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //取参数
            Long id=lj.Number.toLong(params.id);
            Long customerClientId=lj.Number.toLong(params.customerClientId);  //客户用户ID
            int type=lj.Number.toInteger(params.type);//客户类型

            if(!params.max){
                params.max=10
            }
            if(!params.offset){
                params.offset=0;
            }

            //检查店铺可用性
            def reInfo=shopService.getShopEnabled();
            long restaurantId=0;   //店铺ID
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }
            else{
                restaurantId=reInfo.restaurantInfo.id;
            }

            //根据店铺Id等查询出客户关系
            def condition={
                //加上店铺id条件
                eq("restaurantId",restaurantId);
                if(id){
                    eq("id",id);
                }
                if(customerClientId) {
                    eq("customerClientId",customerClientId);
                }
                if(params.type){
                    eq("type",type);
                }
            }

            def customerRelationsList=CustomerRelations.createCriteria().list(params,condition);
            def totalCount=CustomerRelations.createCriteria().count(condition);

            return [recode: ReCode.OK,customerRelationsList:customerRelationsList,totalCount:totalCount,params:params];

        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //获取客户关系
    def getCrInfo(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //取参数
            Long id=lj.Number.toLong(params.id);

            //检查店铺可用性
            def reInfo=shopService.getShopEnabled();
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }
            long restaurantId=reInfo.restaurantInfo.id;
            CustomerRelations customerRelations=CustomerRelations.findByRestaurantIdAndId(restaurantId,id);
            if(customerRelations)
                return [recode: ReCode.OK,customerRelations:customerRelations];
            else
                return [recode: ReCode.NO_RESULT];
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

}
