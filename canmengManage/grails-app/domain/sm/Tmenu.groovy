package sm

class Tmenu {

    Long pid
    String pname
    String cname
    String curl
    String iconCls
    Integer cseq
    static constraints = {
        pid nullable:true
        pname maxSize:100,blank:true,nullable:true
        cname maxSize:100,blank:true,nullable:true
        curl maxSize:100,blank:true,nullable:true
        iconCls maxSize:50,blank:true,nullable:true
        cseq nullable:true
    }
}
