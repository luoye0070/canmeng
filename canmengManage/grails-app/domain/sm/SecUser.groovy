package sm

class SecUser {
    String username
    String password='111111'
    String userType
    static constraints = {
        username blank:false
        password blank:false
    }
}
