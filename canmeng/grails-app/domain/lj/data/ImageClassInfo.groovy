package lj.data

class ImageClassInfo {

    //饭店ID
    long restaurantId
    //名称
    String name

    static constraints = {
        restaurantId(nullable:false,min:1l);
        name(nullable: false, blank:false,maxSize:32);
    }
}
