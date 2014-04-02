package sm
import grails.converters.JSON
class TmenuSearchService {

 boolean transactional = false
    def datagridService

    def search(params){
        def dataFormat={tmenu->
            ['id':tmenu.id,_parentId:tmenu.pid,pid:tmenu.pid,pname:tmenu.pname,cname:tmenu.cname,curl:tmenu.curl,iconCls:tmenu.iconCls,cseq:tmenu.cseq]
        }

        def queryBlock={searchParams->
            Tmenu.createCriteria().list(searchParams){
                if(params.id){
                    eq('pid',searchParams.id as Long)
                }
                order("cseq", "desc")
            }
        }
        return datagridService.createForJson(params,queryBlock,dataFormat) as JSON
    }

    def tree(Tmenu tmenu,boolean recursive,session){



        TreeNode node=new TreeNode()
        node.id=tmenu.id
        node.text=tmenu.cname
        node.attributes<<[url:tmenu.curl]
        node.iconCls=tmenu.iconCls

        def childTmenus=Tmenu.createCriteria().list(){
            eq('pid',tmenu.id)
            order("cseq", "desc")

            if(session&&session.userType!='管理员'){
                    ne('cname','用户管理')
            }
        }
        def children=[]
        if(childTmenus&&childTmenus.size()>0){
            node.state="open"
            if(recursive){
                childTmenus.each{childTmenu->
                    TreeNode tn=tree(childTmenu,true,session)
                    children<<tn
                }
                node.children=children
            }
        }
        return node
    }

    def treegrid(Tmenu tmenu,Boolean b,session){


        if(tmenu){
            return tree(tmenu,b) as JSON
        }else{
            def list=Tmenu.createCriteria().list(){
                or{
                    eq('pid',0L)
                    isNull('pid')
                }

                order("cseq", "desc")
            }
            if(list.size()>0)
            return list.collect{
                tree(it,b,session)
            } as JSON
            else
            return ([[]] as JSON)
        }


    }
}
