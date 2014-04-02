package lj.data
import lj.enumCustom.*
//省份信息
class ProvinceInfo {

    //省份名称
    String province
    static constraints = {
        province blank:false,maxSize:32
    }
}
