package sm

class PortalController {

    def index() {
        redirect(action:'portal')
    }

    def portal(){

    }

    def about(){
        []
    }

    def about2(){
        render "about2"
    }

    def link(){
        render ""
    }

    def repair(){
        render ""
    }

    def qun(){
        render ""
    }
}
