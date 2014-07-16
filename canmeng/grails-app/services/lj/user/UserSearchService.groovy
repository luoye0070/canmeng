package lj.user

import lj.data.UserInfo
import lj.util.WebUtilService

import javax.servlet.http.HttpSession
import lj.data.AddressInfo
import lj.data.FoodCollectInfo
import lj.data.FoodInfo
import lj.data.RestaurantCollectInfo
import lj.data.RestaurantInfo

//用户相关查询服务
class UserSearchService {

    WebUtilService webUtilService
    def searchUtilService

    static transactional = false

    //判定该用户是否存在
    boolean isExists(String username) {
        return (UserInfo.findByUserName(username) != null)
    }

    //查看邮件是否已经注册
    boolean emailValidate(String userName, String email) {
        def list = UserInfo.createCriteria().list {
            ne("userName", userName)
            eq("email", email)
        }

        if (list.size() == 0)
            return true;

        return false;
    }

    //查看手机是否存在
    boolean phoneValidate(String userName, String phone) {
        def list = UserInfo.createCriteria().list {
            ne("userName", userName)
            eq("phone", phone)
        }

        if (list.size() == 0)
            return true;

        return false;
    }

    //获取用户地址信息
    List getAddresses() {
        return AddressInfo.findAllByClientId(webUtilService.getClientId());
    }

    //查找我的收藏
    def getMyFavorites(def params) {

        def queryBlock
        def dataFormat
        //食品收藏
        if ("food".equals(params.type)) {
            queryBlock = {queryParams ->
                FoodCollectInfo.createCriteria().list(queryParams) {
                    eq('clientId', webUtilService.getClientId());
                }
            }

            dataFormat = {foodCollectInfo ->
                def food = FoodInfo.get(foodCollectInfo.foodId)
                if (!food) {
                    [
                            'id': foodCollectInfo.id,
                            'foodId': 0,
                            'name': '该菜谱已经不存在了',
                            'image': ''
                    ]
                } else {
                    [
                            'id': foodCollectInfo.id,
                            'foodId': foodCollectInfo.foodId,
                            'name': food.name,
                            'image': food.image,
                            'restaurantId':food.restaurantId
                    ]
                }

            }
        } else {
            queryBlock = { queryParams ->
                RestaurantCollectInfo.createCriteria().list(queryParams) {
                    eq('clientId', webUtilService.getClientId());
                }
            }

            dataFormat = {restaurantCollectInfo ->
                def restaurantInfo = RestaurantInfo.get(restaurantCollectInfo.restaurantId)
                if (!restaurantInfo) {
                    ['id':restaurantCollectInfo.id,'restaurantId':0,'name':"该饭店已经不存在了",'image':'']
                } else {
                    [
                            'id': restaurantCollectInfo.id,
                            'restaurantId': restaurantCollectInfo.restaurantId,
                            'name': restaurantInfo.name,
                            'image': restaurantInfo.image
                    ]
                }

            }
        }

        def result= searchUtilService.search(params,queryBlock,dataFormat);
        if ("food".equals(params.type)) {
            result.total=FoodCollectInfo.countByClientId(webUtilService.getClientId());
        }
        else{
            result.total=RestaurantCollectInfo.countByClientId(webUtilService.getClientId());
        }
        return result;
    }
}
