package lj.data
import lj.enumCustom.*
//外卖包装方式
class PackInfo {

    //名称
    String name
    //描述
    String description

    static constraints = {
        name blank:false,maxSize:16
        description nullable:true,maxSize:256
    }
}
