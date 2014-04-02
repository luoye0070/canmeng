package sm
import grails.converters.JSON
class TmenuController {

    def tmenuSearchService
    def index() {
        redirect(action:'list')
    }
    def list(){
    }

    def datagrid(){
        render tmenuSearchService.search(params)
    }

    def treegrid(){

        session.each{
            println it
        }
         Tmenu tmenu=Tmenu.get(params.id?:-1)
        if('true'==params.b)
        render tmenuSearchService.treegrid(tmenu,true,session)
        else
        render tmenuSearchService.treegrid(tmenu,false,session)
    }


}
