package lj.shop

import lj.data.AuthenticationInfo
import lj.data.RestaurantInfo
import lj.enumCustom.ReCode
import lj.enumCustom.VerifyStatus
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile


/**
 * AuthenticationService
 * <p>店铺认证相关的服务</p>
 * @author 曹真
 * @version 1.0
 */
class AuthenticationService {
    def webUtilService;
    def shopService;
    def uploadFileService;
    def serviceMethod() {

    }

    //保存认证
    /***************
     * 保存认证
     *
     * params是传入的参数
     * 参数格式为：
     * id 认证ID，非必须,如果存在，下面的参数非必须
     * imgfile 图片文件域名称,非必须
     * description 认证描述，非必须
     * papersId 证件类型，必须
     * remark 备注，非必须
     *
     * 返回值
     * [recode: ReCode.NOT_REGISTER_RESTAURANT];用户没有注册店铺
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.NO_RESULT];认证记录不存在
     * [recode: ReCode.OK,authList:authList,totalCount:totalCount];成功
     * [recode: ReCode.NO_RESULT];没有记录
     * **********************/
    def saveAuth(def params,def request){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //取参数
            long id=lj.Number.toLong(params.id);

            //检查店铺可用性
            def reInfo=shopService.getShopInfo();
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }

            //如果上传图片
            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                CommonsMultipartFile orginalFile = (CommonsMultipartFile) multiRequest.getFile("imgfile");
                // 判断是否上传文件
                if (orginalFile != null && !orginalFile.isEmpty()) {
                    // 获取原文件名称
                    String originalFilename = orginalFile.getOriginalFilename();

                    //上传文件到文件系统
                    //String fileFullName=UploadFile.uploadToFileSystem(orginalFile.getInputStream(),originalFilename);
                    String fileFullName=uploadFileService.upload(orginalFile.getInputStream(),originalFilename);
                    if(fileFullName){
                        params.image=fileFullName;
                    }
                    else {//保存图片失败

                    }
                }
            }
            AuthenticationInfo authenticationInfo=AuthenticationInfo.get(id);
            if(authenticationInfo){// 删除原来的认证图片
                if(params.image){//删除原先的认证图片
                    uploadFileService.delete(authenticationInfo.image);
                }
            }
            else {
                authenticationInfo=new AuthenticationInfo();
            }

            // 设置认证属性
            authenticationInfo.restaurantId=reInfo.restaurantInfo.id;// 饭店ID
            if(params.description)
                authenticationInfo.description=params.description; //认证描述
            if(params.image){
                authenticationInfo.image=params.image;//认证图片
            }
            if(params.papersId)
                authenticationInfo.papersId=lj.Number.toLong(params.papersId);//认证类型
            if(params.remark)
                authenticationInfo.remark=params.remark;//备注
            authenticationInfo.verifyStatus=0;//调用该方法将重新认证

            //保存数据
            if(authenticationInfo.save(flush: true)){
                return [recode: ReCode.OK,authenticationInfo:authenticationInfo];
            }
            else{//保存失败删除上传的图片
                if(params.image){//删除原先的认证图片
                    uploadFileService.delete(authenticationInfo.image);
                }
                return [recode: ReCode.SAVE_FAILED,authenticationInfo:authenticationInfo,errors:authenticationInfo.errors.allErrors];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }
    //显示认证
    /***************
     * 删除认证
     *
     * params是传入的参数
     * 参数格式为：
     * id 认证ID，非必须
     *
     * 返回值
     * [recode: ReCode.NOT_REGISTER_RESTAURANT];用户没有注册店铺
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.NO_RESULT];认证记录不存在
     * [recode: ReCode.OK,authList:authList,totalCount:totalCount];成功
     * [recode: ReCode.NO_RESULT];没有记录
     * **********************/
    def showAuth(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //取参数
            long id=lj.Number.toLong(params.id);

            //检查店铺可用性
            def reInfo=shopService.getShopInfo();
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }

            def condition={
                eq("restaurantId",reInfo.restaurantInfo.id);
                if(id){
                    eq("id",id);
                }
            }

            def authList=AuthenticationInfo.createCriteria().list(params,condition);
            def totalCount=AuthenticationInfo.createCriteria().count(condition);
            if(authList){
                return [recode: ReCode.OK,authList:authList,totalCount:totalCount];
            }
            else{
                return [recode: ReCode.NO_RESULT];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    /***************
     * 删除认证
     *
     * params是传入的参数
     * 参数格式为：
     * ids 认证ID数组，必须
     *
     * 返回值
     * [recode: ReCode.NOT_REGISTER_RESTAURANT];用户没有注册店铺
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.NO_RESULT];认证记录不存在
     * [recode: ReCode.OK];成功
     * [recode: ReCode.ERROR_PARAMS];参数不完整
     * **********************/
    def delAuth(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            long restaurantId=0;
            //检查店铺可用性
            def reInfo=shopService.getShopInfo();
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }
            else{
                restaurantId=reInfo.restaurantInfo.id;
            }
            def idList=[];
            if(params.ids instanceof String){ //传入id
                //获取参数
                long id=lj.Number.toLong(params.ids);//认证信息ID
                idList.add(id);
            }
            else if(params.ids instanceof String[]){//传入id数组
                for(int i=0;i<params.ids.length;i++){
                    Long id=lj.Number.toLong(params.ids[i]);
                    idList.add(id);
                }
            }
            else{//参数不完整
                return [recode: ReCode.ERROR_PARAMS];
            }

            //查询出图片
            def authList=AuthenticationInfo.findAllByIdInListAndRestaurantId(idList,restaurantId);
            if(authList){
                authList.each {
                    //UploadFile.deleteFromFileSystem(it.url);
                    if(uploadFileService.delete(it.image))
                        it.delete(flush: true);
                    println("删除认证图片成功");
                }
                return [recode: ReCode.OK];
            }
            else{
                return [recode: ReCode.NO_RESULT];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }
}
