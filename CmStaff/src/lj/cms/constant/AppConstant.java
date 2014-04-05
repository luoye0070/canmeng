package lj.cms.constant;

public interface AppConstant {
	//引用导航菜单的activity的索引
	class MNMAIndex{
		public static final int MainActivity=0;
		public static final int OrderListActivity=1;
		public static final int DishListActivity=2;
		public static final int ReserveActivity=3;
	}
	
	/***************************
	 *访问网络结果编码
	 **************************/
	class VisitServerResultCode{
		public final static int RESULT_CODE_OK=0;//成功
		public final static int RESULT_CODE_NETWORKERROR=1;//网络错误
		public final static int RESULT_CODE_OTHERERROR=2;//其他错误
		public final static int RESULT_CODE_STOPBYUSER=3;//用户取消请求
		public final static int RESULT_CODE_SERVERERRROR=4;//服务器错误
		public final static int RESULT_CODE_FILENOTFOUND=5;//请求的资源找不到
		
	}
	/***************************
	 *访问网络参数名和值
	 **************************/
	class HttpParamRe{
		public final static String PARAM_NAME="paramName";
		public final static String PARAM_VALUE="paramValue";
	}
	/***************************
	 *访问网络参数名
	 **************************/
	class HttpRequestParamName{
		public final static String ANDROID_PHONE_FIELD_KEY="androidPhone";

		public final static String LOGIN_RESTAURANT_ID="restaurantId";
		public final static String LOGIN_USERNAME="loginName";
		public final static String LOGIN_PASSWORD="passWord";
		public final static String LOGIN_APPKEY="appkey";
		
		public final static String USER_ID="userId";
		public final static String USER_KEY="userkey";
		public final static String USER_TOKEN="usertoken";
		

	}
	/***************************
	 *访问网络URL地址
	 **************************/
	class UrlStrs{
		public final static String URL_VERSIONINFO="http://im.canmeng.com/version.txt";
		public final static String URL_UPDATEFILEPATH="http://www.canmeng.com/android/";
		public final static String URL_HOST="";
//		//测试环境
		public final static String URL_LOGIN="http://42.96.176.228:80/staffManageAjax/staffLogin";//工作人员登录请求地址
		public final static String URL_CREATE_ORDER="http://42.96.176.228:80/staffAjax/createOrder";//创建订单
		public final static String URL_DATA_OF_DISH="http://42.96.176.228:80/staffAjax/dataOfDish";//加载点菜所需数据
		public final static String URL_ADD_DISHS="http://42.96.176.228:80/staffAjax/addDishes";//点菜
		public final static String URL_COMPLETE_DISH="http://42.96.176.228:80/staffAjax/completeDish";//完成点菜
		public final static String URL_ORDER_LIST="http://42.96.176.228:80/staffAjax/orderList";//订单列表
		public final static String URL_ORDER_DETAIL="http://42.96.176.228:80/staffAjax/orderShow";//订单详情
		public final static String URL_CANCEL_DISH="http://42.96.176.228:80/staffAjax/cancelDish";//取消点菜
		public final static String URL_DEL_DISH="http://42.96.176.228:80/staffAjax/delDish";//删除点菜
		public final static String URL_AFFIRM_DISH="http://42.96.176.228:80/staffAjax/affirmDish";//确认点菜
		public final static String URL_COMPLETE_SERVE_DISH="http://42.96.176.228:80/staffAjax/completeServe";//完成上菜
		public final static String URL_BEGIN_COOK_DISH="http://42.96.176.228:80/staffAjax/beginCook";//完成上菜
		public final static String URL_COMPLETE_COOK_DISH="http://42.96.176.228:80/staffAjax/completeCook";//完成上菜
		
		public final static String URL_CANCEL_ORDER="http://42.96.176.228:80/staffAjax/cancelOrder";//取消订单
		public final static String URL_DEL_ORDER="http://42.96.176.228:80/staffAjax/delOrder";//删除订单
		public final static String URL_AFFIRM_VALID_ORDER="http://42.96.176.228:80/staffAjax/affirmValid";//确认订单有效
		public final static String URL_COMPLETE_AFFIRM_DISH_ORDER="http://42.96.176.228:80/staffAjax/completeAffirmDish";//确认点菜完成
		public final static String URL_SETTLE_ACCOUNT_ORDER="http://42.96.176.228:80/staffAjax/settleAccounts";//提交算账
		public final static String URL_DISH_LIST="http://42.96.176.228:80/staffAjax/dishList";//点菜列表，主要厨师用
		
		
		public final static String URL_APPRAISE_ORDER="http://42.96.176.228:80/customerAjax/appraiseOrder";//评价订单
		public final static String URL_FOOD_DETAIL="http://42.96.176.228:80/infoShowInM/foodShow";//菜品详情
		public final static String URL_MY_COLLECT="http://42.96.176.228:80/userAjax/myFavorites";//收藏列表
		public final static String URL_DEL_COLLECT="http://42.96.176.228:80/userAjax/delFavorite";//删除收藏
		public final static String URL_SHOP_LIST="http://42.96.176.228:80/searchAjax/searchShop";//饭店列表
		public final static String URL_TABLE_LIST="http://42.96.176.228:80/searchAjax/searchTablesInShop";//桌位列表
		public final static String URL_TABLE_RESERVE="http://42.96.176.228:80/staffAjax/reserveTable";//桌位预定
		
		public final static String URL_IMAGE_THUMBNAIL="http://42.96.176.228:80/imageShow/downloadThumbnail";//缩略图下载地址
		
		public final static String URL_GET_MESSAGE="http://42.96.176.228:80/messageAjax/getMsg";//消息接收
		public final static String IP_MESSAGE_SERVER="42.96.176.228";
		public final static int PORT_MESSAGE_SERVER=5000;
		
		
//		//生产环境
//		public final static String URL_LOGIN="http://mobile.xjmei.com/user/login";//登录请求地址

		
		//本地测试环境
//		public final static String URL_LOGIN="http://192.168.1.201:8080/canmeng/staffManageAjax/staffLogin";//工作人员登录请求地址
//		public final static String URL_CREATE_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/createOrder";//创建订单
//		public final static String URL_DATA_OF_DISH="http://192.168.1.201:8080/canmeng/staffAjax/dataOfDish";//加载点菜所需数据
//		public final static String URL_ADD_DISHS="http://192.168.1.201:8080/canmeng/staffAjax/addDishes";//点菜
//		public final static String URL_COMPLETE_DISH="http://192.168.1.201:8080/canmeng/staffAjax/completeDish";//完成点菜
//		public final static String URL_ORDER_LIST="http://192.168.1.201:8080/canmeng/staffAjax/orderList";//订单列表
//		public final static String URL_ORDER_DETAIL="http://192.168.1.201:8080/canmeng/staffAjax/orderShow";//订单详情
//		public final static String URL_CANCEL_DISH="http://192.168.1.201:8080/canmeng/staffAjax/cancelDish";//取消点菜
//		public final static String URL_DEL_DISH="http://192.168.1.201:8080/canmeng/staffAjax/delDish";//删除点菜
//		public final static String URL_AFFIRM_DISH="http://192.168.1.201:8080/canmeng/staffAjax/affirmDish";//确认点菜
//		public final static String URL_COMPLETE_SERVE_DISH="http://192.168.1.201:8080/canmeng/staffAjax/completeServe";//完成上菜
//		public final static String URL_BEGIN_COOK_DISH="http://192.168.1.201:8080/canmeng/staffAjax/beginCook";//完成上菜
//		public final static String URL_COMPLETE_COOK_DISH="http://192.168.1.201:8080/canmeng/staffAjax/completeCook";//完成上菜
//		
//		public final static String URL_CANCEL_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/cancelOrder";//取消订单
//		public final static String URL_DEL_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/delOrder";//删除订单
//		public final static String URL_AFFIRM_VALID_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/affirmValid";//确认订单有效
//		public final static String URL_COMPLETE_AFFIRM_DISH_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/completeAffirmDish";//确认点菜完成
//		public final static String URL_SETTLE_ACCOUNT_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/settleAccounts";//提交算账
//		public final static String URL_DISH_LIST="http://192.168.1.201:8080/canmeng/staffAjax/dishList";//点菜列表，主要厨师用
//		
//		
//		public final static String URL_APPRAISE_ORDER="http://192.168.1.201:8080/canmeng/customerAjax/appraiseOrder";//评价订单
//		public final static String URL_FOOD_DETAIL="http://192.168.1.201:8080/canmeng/infoShowInM/foodShow";//菜品详情
//		public final static String URL_MY_COLLECT="http://192.168.1.201:8080/canmeng/userAjax/myFavorites";//收藏列表
//		public final static String URL_DEL_COLLECT="http://192.168.1.201:8080/canmeng/userAjax/delFavorite";//删除收藏
//		public final static String URL_SHOP_LIST="http://192.168.1.201:8080/canmeng/searchAjax/searchShop";//饭店列表
//		public final static String URL_TABLE_LIST="http://192.168.1.201:8080/canmeng/searchAjax/searchTablesInShop";//桌位列表
//		public final static String URL_TABLE_RESERVE="http://192.168.1.201:8080/canmeng/staffAjax/reserveTable";//桌位预定
//		
//		public final static String URL_GET_MESSAGE="http://192.168.1.201:8080/canmeng/messageAjax/getMsg";//消息接收
//		
	}
	/***************************
	 *文件名
	 **************************/
	class FileNames{
		public final static String UPDATE_SOFTWARE_NAME="updateFile.apk";
		public final static String USER_INFO_FILE="uinfo";
	}
	/***************************
	 *相对于sd卡的文件路径
	 **************************/
	class DirNames{
		public final static String APPDIR_NAME="cms/";
		public final static String UPDATE_SOFTWARE_DIR_NAME=APPDIR_NAME+"UpdateFile/";
		public final static String IMGDIR_NAME=APPDIR_NAME+"cmsImage/";
		
	}
	/********************
	 *
	 ************************/
	class ActivityResultSome{
		public final static int GOODSDETAIL_TO_GOODSATTR_REQUESTCODE=1; 
		public final static int MAKEORDER_TO_ADDRESSLIST=2;
		public final static int ADDRMANAGE_TO_EDIT=3;
		
	}
	/***************************
	 *界面跳转传送数据的名称
	 **************************/
	class IntentExtraName{
		public final static String SEARCH_CLASSIDX="searchClass";
		public final static String SEARCH_KEYWORD="searchKeyword";

		public final static String IN_LOGIN_FROM_BT="inLoginFromBt";
		
		public final static String ORDER_ID="orderId";
		public final static String PARTAKE_CODE="partakeCode";
		public final static String IS_OWNER="isOwner";
		public final static String FOOD_ID="foodId";
		public final static String RESTAURANT_ID="restaurantId";
		
	}
	/***************************
	 *排序状态
	 **************************/
	class OrderStatus{
		public final static int OUT_OF_ORDER=0;//无序
		public final static int ASCENDING_ORDER=1;//升序
		public final static int DESCENDING_ORDER=2;//降序
		/*************在升序和降序之间切换排序状态*************/
		public static int changeOrderStatus(int orderStatus)
		{
			return (orderStatus+1)%3;
		}
		public static int changeOrderStatusOA(int orderStatus)
		{
			return (orderStatus+1)%2;
		}
		public static int changeOrderStatusOD(int orderStatus)
		{
			return ((((orderStatus==0)?0:1)+1)%2==0)?0:2;
		}
	}
	
	/***************
	 *广播事件
	 *******************/
	class BroadcastActions{
//		public final static String GOODS_CLASS_SELECTED="rxzy.xjmei.goods_class_selected";//商品类目选中
//		public final static String GOODS_AREA_SELECTED="rxzy.xjmei.goods_area_selected";//商品地区选中
	}
	
	/***********************
	 *共享数据名称 
	 *************************/
	class SharedDataName
	{
		public final static String USER_INFO="userInfo";
	}
	/****************
	 *共享数据项名称 
	 **************/
	class SharedDataItemName
	{
		public final static String USER_INFO_USERID="userInfoUserId";
		public final static String USER_INFO_RESTAURANTID="userInfoRestaurantId";
		public final static String USER_INFO_USERNAME="userInfoUserName";
		public final static String USER_INFO_PASSWORD="userInfoPassword";
		public final static String USER_INFO_POSITION_TYPES="positionTypeList";
		public final static String USER_INFO_APPKEY="userInfoAppkey";
		public final static String USER_INFO_USERKEY="userInfoUserKey";
		public final static String USER_INFO_USERTOKEN="userInfoUserToken";
	}
	/**********************
	 *订单状态 索引
	 ********************/
	class OrderStatusIndex{
//		public final static int NOPAY_ORDER=1;     //待付款交易  
//		public final static int NOSEND_ORDER=2;    //待发货交易  
//		public final static int NOVERIFY_ORDER=3;  //待确认收货交易
//		public final static int NOCOMMENT_ORDER=4; //待评价交易  
//		public final static int SUCCESS_ORDER=5;   //已成功交易  
	}
	/*****************
	 *应用安全性相关
	 ***********************/
	class AppSafety{
		public final static String USER_APPKEY="telPhone";//用户中心需要的appkey
		public final static String USER_SECRET_KEY="MErhzSsJ";//用户中心需要的密钥
	}
	class ReCode{
		public int code=0;
		public String label="";
		ReCode(int code,String label){
			this.code=code;
			this.label=label;
		}
	}
	class ReCodeFinal {
	    public final static ReCode NO_RESULT=new ReCode(1,"没有记录");
	    public final static ReCode NO_ENOUGH_PARAMS=new ReCode(2,"没有足够的参数");
	    public final static ReCode ORDER_NOT_EXIST=new ReCode(3,"订单不存在");
	    public final static ReCode ORDER_APPRAISED=new ReCode(4,"订单已经评价过");
	    public final static ReCode DISH_HAVEERROR=new ReCode(5,"点菜存在不成功");
	    public final static ReCode CUSTOMER_RELATIONS_EXIST=new ReCode(6,"客户关系已经存在");
	    public final static ReCode OK=new ReCode(0,"成功");
	    public final static ReCode NOT_LOGIN=new ReCode(-1,"您没有登录");
	    public final static ReCode OUT_RANGE=new ReCode(-2,"你的位置超出了该饭店的外卖配送范围，不能为您创建订单");
	    public final static ReCode TABLE_HOLDED=new ReCode(-3,"桌位已经被占，请选择别的桌位吧");
	    public final static ReCode SAVE_FAILED=new ReCode(-4,"保存数据失败");
	    public final static ReCode OTHER_ERROR=new ReCode(-5,"未知错误");
	    public final static ReCode OUT_SHOPHOURS=new ReCode(-6,"到店时间不能在营业时间之外哦");
	    public final static ReCode ERROR_PARAMS=new ReCode(-7,"提交的参数错误");
	    public final static ReCode CANNOT_DISH=new ReCode(-8,"订单当前状态或有效性下不能点菜");
	    public final static ReCode NO_ORDER=new ReCode(-9,"订单不存在");
	    public final static ReCode ERROR_PARTAKECODE=new ReCode(-10,"错误的点菜参与码");
	    public final static ReCode ORDER_CANNOT_CANCEL=new ReCode(-11,"订单当前状态或有效性下不能取消订单");
	    public final static ReCode BLACKLIST_CUSTOMER=new ReCode(-12,"已经被该饭店加入黑名单，不能做任何操作了");
	    public final static ReCode ORDER_CANNOT_DELETE=new ReCode(-13,"订单当前状态或有效性下不能删除订单");
	    public final static ReCode USERNAME_NOT_EXIST=new ReCode(-14,"用户名不存在");
	    public final static ReCode PASSWORD_INCORRECT=new ReCode(-15,"密码错误");
	    public final static ReCode ORDER_CANNOT_UPDATE_VALID=new ReCode(-16,"订单当前状态或有效性下不能更改其有效性");
	    public final static ReCode ORDER_CANNOT_CAST_ACCOUNT=new ReCode(-17,"订单当前状态或有效性下不能算账");
	    public final static ReCode DENIED_USER=new ReCode(-18,"非法用户");
	    public final static ReCode NO_RESTAURANTINFO=new ReCode(-19,"饭店不存在或不可用");
	    public final static ReCode NOT_REGISTER_RESTAURANT=new ReCode(-20,"您还没有注册饭店");
	    public final static ReCode SHOP_NOT_ENABLED=new ReCode(-21,"饭店关闭了");
	    public final static ReCode SHOP_VERIFY_NOT_PASS=new ReCode(-22,"饭店审核没有通过");
	    public final static ReCode SHOP_WAIT_VERIFY=new ReCode(-23,"饭店等待审核中");
	    public final static ReCode CANNOT_REPEAT_REGISTER_SHOP=new ReCode(-24,"不能重复注册店铺");
	    public final static ReCode NO_POSITION=new ReCode(-25,"必须设置工作人员的职位");
	    public final static ReCode SAVE_FILE_FAILED=new ReCode(-26,"保存文件失败");
	    public final static ReCode SUBMIT_FORMAT_ERROR=new ReCode(-27,"提交数据格式错误");
	    public final static ReCode NO_IMAGE_SPACE=new ReCode(-28,"没有足够的图片空间了");
	    public final static ReCode NOT_A_IMAGE=new ReCode(-29,"上传的不是图片");
	    public final static ReCode CUSTOMER_NOT_EXIST=new ReCode(-30,"客户不存在");
	    public final static ReCode EXIST_THE_SAME_STAFF=new ReCode(-31,"已经存在相同登录用户名的工作人员");
	    public final static ReCode STAFF_LOGINNAME_NOT_EXIST=new ReCode(-32,"登录用户名不存在");
	    public final static ReCode NEED_CUSTOMER_ID_OR_NAME=new ReCode(-33,"请提交客户的用户ID或用户名");
	    public final static ReCode NEED_RESERVE_DATE=new ReCode(-34,"预定桌位需要提交预定的日期");
	    public final static ReCode NEED_RESERVE_TIME=new ReCode(-35,"预定桌位需要提交预计到店时间");
	    public final static ReCode ORDER_CANNOT_COMPLETE_DISH=new ReCode(-36,"订单当前状态或有效性下不能做完成点菜操作");
	    public final static ReCode OUT_RESERVETYPE_TIME=new ReCode(-37,"到店时间不能在预定类型时间之外哦");
	    public final static ReCode SYSTEM_ERROR=new ReCode(-39,"系统错误");
	    public final static ReCode TABLE_NOT_EXIST=new ReCode(-40,"桌位不存在");
	}
}
