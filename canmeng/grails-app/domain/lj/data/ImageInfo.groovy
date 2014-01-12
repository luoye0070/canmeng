package lj.data

//图片
class ImageInfo {

    //饭店ID
    long restaurantId
    //图片访问地址
    String url
    //宽
    Integer width=0;
    //高
    Integer height=0;
    //是否回收了
    Boolean isDel=false;
    //类别号
    long classId=0l;
    //上传时间
    Date uploadTime=new Date();
    //图片大小
    long size=0l;
    //存储位置 ,0本地，1百度云盘
    Integer savePlace=0;

    static constraints = {
        restaurantId(nullable:false,min: 1l);
        url(nullable: false, blank:false,maxSize:128);
        width(nullable:false,min: 0);
        height(nullable:false,min: 0);
        isDel(nullable:false);
        classId(nullable:false,min: 0l);
        uploadTime(nullable:false);
        size(nullable: false,min:0l);
        savePlace(nullable: false,inList: [0,1]);
    }
}
