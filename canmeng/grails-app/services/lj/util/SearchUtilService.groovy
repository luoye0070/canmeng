package lj.util

class SearchUtilService {

    def search= {params,queryBlock,dataFormat->
        params.max=Integer.valueOf(params.max?:10)
        params.offset==params.offset ? params.offset : 0
        params.sort=params.sort?:'id'
        params.order=params.order?:'desc'
        def dataRows=queryBlock.call(params)
        def totalRows=dataRows.totalCount
        def results=dataRows?.collect{
            dataFormat(it)
        }
        [rows:results,total:totalRows]
    }
}
