package lj.order.common

import lj.data.AreaInfo
import lj.data.CityInfo
import lj.data.ProvinceInfo

class CommonService {

    def transformAddress(def params){
        //转换地址
        ProvinceInfo provinceInfo=ProvinceInfo.get(lj.Number.toLong(params.province));
        if(provinceInfo){
            params.province=provinceInfo.province;
        }
        CityInfo cityInfo=CityInfo.get(lj.Number.toLong(params.city));
        if(cityInfo){
            params.city=cityInfo.city;
        }
        AreaInfo areaInfo=AreaInfo.get(lj.Number.toLong(params.area));
        if(areaInfo){
            params.area=areaInfo.area;
        }
    }
}
