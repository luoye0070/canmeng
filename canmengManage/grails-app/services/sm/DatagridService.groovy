package sm

class DatagridService {

    /**
     *@param params相关参数
     *@param queryBlock取出数据的闭包逻辑
     *@param dataFormat 取出数据后具体需要组装成json的数据
     **/
    def createForJson= {params,queryBlock,dataFormat->
        def sortIndex=params.sort?:'id'
        def sortOrder=params.order?:'desc'
        def maxRows=Integer.valueOf(params.rows?:10)
        def currentPage=Integer.valueOf(params.page?:1)?:1
        def rowOffset=currentPage==1?0:(currentPage-1)*maxRows
        params.max=maxRows
        params.offset=rowOffset
        params.sort=sortIndex
        params.order=sortOrder
        def dataRows=queryBlock.call(params)
        def totalRows=dataRows.totalCount
        def numberOfPages=Math.ceil(totalRows/maxRows)
        def results=dataRows?.collect{
            dataFormat(it)
        }
        [rows:results,currpage:currentPage,total:totalRows,totalpages:numberOfPages]
    }
}
