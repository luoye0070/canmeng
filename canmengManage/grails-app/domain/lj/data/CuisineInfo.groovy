package lj.data

//菜系
class CuisineInfo {

    //名称
    String name
    //描述
    String description

    static constraints = {
        name blank:false,maxSize:16
        description nullable:true,maxSize:256
    }
}
