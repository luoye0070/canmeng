package lj.cms.constant;

public interface AppConstant {
	//���õ����˵���activity������
	class MNMAIndex{
		public static final int MainActivity=0;
		public static final int OrderListActivity=1;
		public static final int DishListActivity=2;
		public static final int ReserveActivity=3;
	}
	
	/***************************
	 *��������������
	 **************************/
	class VisitServerResultCode{
		public final static int RESULT_CODE_OK=0;//�ɹ�
		public final static int RESULT_CODE_NETWORKERROR=1;//�������
		public final static int RESULT_CODE_OTHERERROR=2;//��������
		public final static int RESULT_CODE_STOPBYUSER=3;//�û�ȡ������
		public final static int RESULT_CODE_SERVERERRROR=4;//����������
		public final static int RESULT_CODE_FILENOTFOUND=5;//�������Դ�Ҳ���
		
	}
	/***************************
	 *���������������ֵ
	 **************************/
	class HttpParamRe{
		public final static String PARAM_NAME="paramName";
		public final static String PARAM_VALUE="paramValue";
	}
	/***************************
	 *�������������
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
	 *��������URL��ַ
	 **************************/
	class UrlStrs{
		public final static String URL_VERSIONINFO="http://im.canmeng.com/version.txt";
		public final static String URL_UPDATEFILEPATH="http://www.canmeng.com/android/";
		public final static String URL_HOST="";
//		//���Ի���
		public final static String URL_LOGIN="http://42.96.176.228:80/staffManageAjax/staffLogin";//������Ա��¼�����ַ
		public final static String URL_CREATE_ORDER="http://42.96.176.228:80/staffAjax/createOrder";//��������
		public final static String URL_DATA_OF_DISH="http://42.96.176.228:80/staffAjax/dataOfDish";//���ص����������
		public final static String URL_ADD_DISHS="http://42.96.176.228:80/staffAjax/addDishes";//���
		public final static String URL_COMPLETE_DISH="http://42.96.176.228:80/staffAjax/completeDish";//��ɵ��
		public final static String URL_ORDER_LIST="http://42.96.176.228:80/staffAjax/orderList";//�����б�
		public final static String URL_ORDER_DETAIL="http://42.96.176.228:80/staffAjax/orderShow";//��������
		public final static String URL_CANCEL_DISH="http://42.96.176.228:80/staffAjax/cancelDish";//ȡ�����
		public final static String URL_DEL_DISH="http://42.96.176.228:80/staffAjax/delDish";//ɾ�����
		public final static String URL_AFFIRM_DISH="http://42.96.176.228:80/staffAjax/affirmDish";//ȷ�ϵ��
		public final static String URL_COMPLETE_SERVE_DISH="http://42.96.176.228:80/staffAjax/completeServe";//����ϲ�
		public final static String URL_BEGIN_COOK_DISH="http://42.96.176.228:80/staffAjax/beginCook";//����ϲ�
		public final static String URL_COMPLETE_COOK_DISH="http://42.96.176.228:80/staffAjax/completeCook";//����ϲ�
		
		public final static String URL_CANCEL_ORDER="http://42.96.176.228:80/staffAjax/cancelOrder";//ȡ������
		public final static String URL_DEL_ORDER="http://42.96.176.228:80/staffAjax/delOrder";//ɾ������
		public final static String URL_AFFIRM_VALID_ORDER="http://42.96.176.228:80/staffAjax/affirmValid";//ȷ�϶�����Ч
		public final static String URL_COMPLETE_AFFIRM_DISH_ORDER="http://42.96.176.228:80/staffAjax/completeAffirmDish";//ȷ�ϵ�����
		public final static String URL_SETTLE_ACCOUNT_ORDER="http://42.96.176.228:80/staffAjax/settleAccounts";//�ύ����
		public final static String URL_DISH_LIST="http://42.96.176.228:80/staffAjax/dishList";//����б���Ҫ��ʦ��
		
		
		public final static String URL_APPRAISE_ORDER="http://42.96.176.228:80/customerAjax/appraiseOrder";//���۶���
		public final static String URL_FOOD_DETAIL="http://42.96.176.228:80/infoShowInM/foodShow";//��Ʒ����
		public final static String URL_MY_COLLECT="http://42.96.176.228:80/userAjax/myFavorites";//�ղ��б�
		public final static String URL_DEL_COLLECT="http://42.96.176.228:80/userAjax/delFavorite";//ɾ���ղ�
		public final static String URL_SHOP_LIST="http://42.96.176.228:80/searchAjax/searchShop";//�����б�
		public final static String URL_TABLE_LIST="http://42.96.176.228:80/searchAjax/searchTablesInShop";//��λ�б�
		public final static String URL_TABLE_RESERVE="http://42.96.176.228:80/staffAjax/reserveTable";//��λԤ��
		
		public final static String URL_IMAGE_THUMBNAIL="http://42.96.176.228:80/imageShow/downloadThumbnail";//����ͼ���ص�ַ
		
		public final static String URL_GET_MESSAGE="http://42.96.176.228:80/messageAjax/getMsg";//��Ϣ����
		public final static String IP_MESSAGE_SERVER="42.96.176.228";
		public final static int PORT_MESSAGE_SERVER=5000;
		
		
//		//��������
//		public final static String URL_LOGIN="http://mobile.xjmei.com/user/login";//��¼�����ַ

		
		//���ز��Ի���
//		public final static String URL_LOGIN="http://192.168.1.201:8080/canmeng/staffManageAjax/staffLogin";//������Ա��¼�����ַ
//		public final static String URL_CREATE_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/createOrder";//��������
//		public final static String URL_DATA_OF_DISH="http://192.168.1.201:8080/canmeng/staffAjax/dataOfDish";//���ص����������
//		public final static String URL_ADD_DISHS="http://192.168.1.201:8080/canmeng/staffAjax/addDishes";//���
//		public final static String URL_COMPLETE_DISH="http://192.168.1.201:8080/canmeng/staffAjax/completeDish";//��ɵ��
//		public final static String URL_ORDER_LIST="http://192.168.1.201:8080/canmeng/staffAjax/orderList";//�����б�
//		public final static String URL_ORDER_DETAIL="http://192.168.1.201:8080/canmeng/staffAjax/orderShow";//��������
//		public final static String URL_CANCEL_DISH="http://192.168.1.201:8080/canmeng/staffAjax/cancelDish";//ȡ�����
//		public final static String URL_DEL_DISH="http://192.168.1.201:8080/canmeng/staffAjax/delDish";//ɾ�����
//		public final static String URL_AFFIRM_DISH="http://192.168.1.201:8080/canmeng/staffAjax/affirmDish";//ȷ�ϵ��
//		public final static String URL_COMPLETE_SERVE_DISH="http://192.168.1.201:8080/canmeng/staffAjax/completeServe";//����ϲ�
//		public final static String URL_BEGIN_COOK_DISH="http://192.168.1.201:8080/canmeng/staffAjax/beginCook";//����ϲ�
//		public final static String URL_COMPLETE_COOK_DISH="http://192.168.1.201:8080/canmeng/staffAjax/completeCook";//����ϲ�
//		
//		public final static String URL_CANCEL_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/cancelOrder";//ȡ������
//		public final static String URL_DEL_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/delOrder";//ɾ������
//		public final static String URL_AFFIRM_VALID_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/affirmValid";//ȷ�϶�����Ч
//		public final static String URL_COMPLETE_AFFIRM_DISH_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/completeAffirmDish";//ȷ�ϵ�����
//		public final static String URL_SETTLE_ACCOUNT_ORDER="http://192.168.1.201:8080/canmeng/staffAjax/settleAccounts";//�ύ����
//		public final static String URL_DISH_LIST="http://192.168.1.201:8080/canmeng/staffAjax/dishList";//����б���Ҫ��ʦ��
//		
//		
//		public final static String URL_APPRAISE_ORDER="http://192.168.1.201:8080/canmeng/customerAjax/appraiseOrder";//���۶���
//		public final static String URL_FOOD_DETAIL="http://192.168.1.201:8080/canmeng/infoShowInM/foodShow";//��Ʒ����
//		public final static String URL_MY_COLLECT="http://192.168.1.201:8080/canmeng/userAjax/myFavorites";//�ղ��б�
//		public final static String URL_DEL_COLLECT="http://192.168.1.201:8080/canmeng/userAjax/delFavorite";//ɾ���ղ�
//		public final static String URL_SHOP_LIST="http://192.168.1.201:8080/canmeng/searchAjax/searchShop";//�����б�
//		public final static String URL_TABLE_LIST="http://192.168.1.201:8080/canmeng/searchAjax/searchTablesInShop";//��λ�б�
//		public final static String URL_TABLE_RESERVE="http://192.168.1.201:8080/canmeng/staffAjax/reserveTable";//��λԤ��
//		
//		public final static String URL_GET_MESSAGE="http://192.168.1.201:8080/canmeng/messageAjax/getMsg";//��Ϣ����
//		
	}
	/***************************
	 *�ļ���
	 **************************/
	class FileNames{
		public final static String UPDATE_SOFTWARE_NAME="updateFile.apk";
		public final static String USER_INFO_FILE="uinfo";
	}
	/***************************
	 *�����sd�����ļ�·��
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
	 *������ת�������ݵ�����
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
	 *����״̬
	 **************************/
	class OrderStatus{
		public final static int OUT_OF_ORDER=0;//����
		public final static int ASCENDING_ORDER=1;//����
		public final static int DESCENDING_ORDER=2;//����
		/*************������ͽ���֮���л�����״̬*************/
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
	 *�㲥�¼�
	 *******************/
	class BroadcastActions{
//		public final static String GOODS_CLASS_SELECTED="rxzy.xjmei.goods_class_selected";//��Ʒ��Ŀѡ��
//		public final static String GOODS_AREA_SELECTED="rxzy.xjmei.goods_area_selected";//��Ʒ����ѡ��
	}
	
	/***********************
	 *������������ 
	 *************************/
	class SharedDataName
	{
		public final static String USER_INFO="userInfo";
	}
	/****************
	 *�������������� 
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
	 *����״̬ ����
	 ********************/
	class OrderStatusIndex{
//		public final static int NOPAY_ORDER=1;     //�������  
//		public final static int NOSEND_ORDER=2;    //����������  
//		public final static int NOVERIFY_ORDER=3;  //��ȷ���ջ�����
//		public final static int NOCOMMENT_ORDER=4; //�����۽���  
//		public final static int SUCCESS_ORDER=5;   //�ѳɹ�����  
	}
	/*****************
	 *Ӧ�ð�ȫ�����
	 ***********************/
	class AppSafety{
		public final static String USER_APPKEY="telPhone";//�û�������Ҫ��appkey
		public final static String USER_SECRET_KEY="MErhzSsJ";//�û�������Ҫ����Կ
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
	    public final static ReCode NO_RESULT=new ReCode(1,"û�м�¼");
	    public final static ReCode NO_ENOUGH_PARAMS=new ReCode(2,"û���㹻�Ĳ���");
	    public final static ReCode ORDER_NOT_EXIST=new ReCode(3,"����������");
	    public final static ReCode ORDER_APPRAISED=new ReCode(4,"�����Ѿ����۹�");
	    public final static ReCode DISH_HAVEERROR=new ReCode(5,"��˴��ڲ��ɹ�");
	    public final static ReCode CUSTOMER_RELATIONS_EXIST=new ReCode(6,"�ͻ���ϵ�Ѿ�����");
	    public final static ReCode OK=new ReCode(0,"�ɹ�");
	    public final static ReCode NOT_LOGIN=new ReCode(-1,"��û�е�¼");
	    public final static ReCode OUT_RANGE=new ReCode(-2,"���λ�ó����˸÷�����������ͷ�Χ������Ϊ����������");
	    public final static ReCode TABLE_HOLDED=new ReCode(-3,"��λ�Ѿ���ռ����ѡ������λ��");
	    public final static ReCode SAVE_FAILED=new ReCode(-4,"��������ʧ��");
	    public final static ReCode OTHER_ERROR=new ReCode(-5,"δ֪����");
	    public final static ReCode OUT_SHOPHOURS=new ReCode(-6,"����ʱ�䲻����Ӫҵʱ��֮��Ŷ");
	    public final static ReCode ERROR_PARAMS=new ReCode(-7,"�ύ�Ĳ�������");
	    public final static ReCode CANNOT_DISH=new ReCode(-8,"������ǰ״̬����Ч���²��ܵ��");
	    public final static ReCode NO_ORDER=new ReCode(-9,"����������");
	    public final static ReCode ERROR_PARTAKECODE=new ReCode(-10,"����ĵ�˲�����");
	    public final static ReCode ORDER_CANNOT_CANCEL=new ReCode(-11,"������ǰ״̬����Ч���²���ȡ������");
	    public final static ReCode BLACKLIST_CUSTOMER=new ReCode(-12,"�Ѿ����÷��������������������κβ�����");
	    public final static ReCode ORDER_CANNOT_DELETE=new ReCode(-13,"������ǰ״̬����Ч���²���ɾ������");
	    public final static ReCode USERNAME_NOT_EXIST=new ReCode(-14,"�û���������");
	    public final static ReCode PASSWORD_INCORRECT=new ReCode(-15,"�������");
	    public final static ReCode ORDER_CANNOT_UPDATE_VALID=new ReCode(-16,"������ǰ״̬����Ч���²��ܸ�������Ч��");
	    public final static ReCode ORDER_CANNOT_CAST_ACCOUNT=new ReCode(-17,"������ǰ״̬����Ч���²�������");
	    public final static ReCode DENIED_USER=new ReCode(-18,"�Ƿ��û�");
	    public final static ReCode NO_RESTAURANTINFO=new ReCode(-19,"���겻���ڻ򲻿���");
	    public final static ReCode NOT_REGISTER_RESTAURANT=new ReCode(-20,"����û��ע�᷹��");
	    public final static ReCode SHOP_NOT_ENABLED=new ReCode(-21,"����ر���");
	    public final static ReCode SHOP_VERIFY_NOT_PASS=new ReCode(-22,"�������û��ͨ��");
	    public final static ReCode SHOP_WAIT_VERIFY=new ReCode(-23,"����ȴ������");
	    public final static ReCode CANNOT_REPEAT_REGISTER_SHOP=new ReCode(-24,"�����ظ�ע�����");
	    public final static ReCode NO_POSITION=new ReCode(-25,"�������ù�����Ա��ְλ");
	    public final static ReCode SAVE_FILE_FAILED=new ReCode(-26,"�����ļ�ʧ��");
	    public final static ReCode SUBMIT_FORMAT_ERROR=new ReCode(-27,"�ύ���ݸ�ʽ����");
	    public final static ReCode NO_IMAGE_SPACE=new ReCode(-28,"û���㹻��ͼƬ�ռ���");
	    public final static ReCode NOT_A_IMAGE=new ReCode(-29,"�ϴ��Ĳ���ͼƬ");
	    public final static ReCode CUSTOMER_NOT_EXIST=new ReCode(-30,"�ͻ�������");
	    public final static ReCode EXIST_THE_SAME_STAFF=new ReCode(-31,"�Ѿ�������ͬ��¼�û����Ĺ�����Ա");
	    public final static ReCode STAFF_LOGINNAME_NOT_EXIST=new ReCode(-32,"��¼�û���������");
	    public final static ReCode NEED_CUSTOMER_ID_OR_NAME=new ReCode(-33,"���ύ�ͻ����û�ID���û���");
	    public final static ReCode NEED_RESERVE_DATE=new ReCode(-34,"Ԥ����λ��Ҫ�ύԤ��������");
	    public final static ReCode NEED_RESERVE_TIME=new ReCode(-35,"Ԥ����λ��Ҫ�ύԤ�Ƶ���ʱ��");
	    public final static ReCode ORDER_CANNOT_COMPLETE_DISH=new ReCode(-36,"������ǰ״̬����Ч���²�������ɵ�˲���");
	    public final static ReCode OUT_RESERVETYPE_TIME=new ReCode(-37,"����ʱ�䲻����Ԥ������ʱ��֮��Ŷ");
	    public final static ReCode SYSTEM_ERROR=new ReCode(-39,"ϵͳ����");
	    public final static ReCode TABLE_NOT_EXIST=new ReCode(-40,"��λ������");
	}
}
