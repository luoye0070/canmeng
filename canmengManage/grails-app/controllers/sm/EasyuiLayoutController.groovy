package sm
import grails.converters.JSON

class EasyuiLayoutController {



    def index(){

    }

    def north(){
    }

    def west(){

    }

    def center(){

    }
    def south(){

    }

    def east(){

    }

    //菜单节点
    def allTreeNode(){
        def nodes=[["id":1,"text":"Languages", "state":"closed",
         "children":[[
        "text":"Java","attributes":[url:'/portal/about']
                    ],[
        "text":"C#","attributes":[url:'/tmenu/list']
                    ],[
        "text":"bug管理","attributes":[url:'/bug/list']
                    ]]
            ]]


        render nodes as JSON
    }

}
