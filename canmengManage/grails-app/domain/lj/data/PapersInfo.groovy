package lj.data
import lj.enumCustom.*
//证件
class PapersInfo {

    //名称
    String name
    //描述
    String description

    static constraints = {
        name blank:false,maxSize:64
        description nullable:true,maxSize:256
    }
}
