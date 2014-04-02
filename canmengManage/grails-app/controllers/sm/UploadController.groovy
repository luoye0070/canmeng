package sm
import java.text.SimpleDateFormat
import java.util.UUID
import org.springframework.util.FileCopyUtils
import grails.converters.JSON
class UploadController {


    //上传文件
    def index() {
        def config=grailsApplication.config
        String savePath=servletContext.getRealPath("/")+"/"+config.xheditor.uploadDirectory+"/"
        String saveUrl="/"+config.xheditor.uploadDirectory+"/"
        Long uploadFileMaxSize=Long.valueOf(config.xheditor.uploadFileMaxSize)
        String  uploadFileExts=config.xheditor.uploadFileExts
        String uploadFieldName=config.xheditor.uploadFieldName

        println savePath
        println saveUrl
        String contentDisposition=request.getHeader("Content-Disposition")

        def uploadSuccess=[err:'']
        def uploadError=[err:'',msg:'']
        if(contentDisposition!=null){//HTML5拖拽上传文件
            Long fileSize=Long.valueOf(request.getHeader("Content-Length"))
            String fileName=contentDisposition.substring(contentDisposition.lastIndexOf("filename=\""))
            fileName = fileName.substring(fileName.indexOf("\"") + 1)
            fileName = fileName.substring(0, fileName.indexOf("\""))


            def inputStream
            try{
                inputStream=request.getInputStream()
            }catch(IOException e){
                uploadError.err="上传文件出错!"
                render uploadError as JSON
                return
            }

            if(!inputStream){
                uploadError.err="您没有上传任何文件"
                render uploadError as JSON
                return
            }

            if(fileSize>uploadFileMaxSize){
                uploadError.err="上传文件超出限制大小！"
                uploadError.msg=fileName
                render uploadError as JSON
                return
            }

            // 检查文件扩展名
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase()
            if(!uploadFileExts.split(",").contains(fileExt)){
                uploadError.err="上传文件扩展名是不允许的扩展名。\n只允许" + uploadFileExts + "格式！"
                render uploadError as JSON
                return
            }

            savePath+=fileExt+"/"
            saveUrl+=fileExt+"/"

            SimpleDateFormat yearDf=new SimpleDateFormat('yyyy')
            SimpleDateFormat monthDf=new SimpleDateFormat('MM')
            SimpleDateFormat dateDf=new SimpleDateFormat('dd')
            Date date=new Date()
            String ymd=yearDf.format(date)+"/"+monthDf.format(date) + "/" + dateDf.format(date) + "/"
            savePath+=ymd
            saveUrl+=ymd

            File uploadDir=new File(savePath)
            if(!uploadDir.exists())
            uploadDir.mkdirs()

            String newFileName=UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt
            File uploadedFile=new File(savePath,newFileName)

            try{
                FileCopyUtils.copy(inputStream, new FileOutputStream(uploadedFile))
            }catch(FileNotFoundException e){
                uploadError.err="上传文件出错!"
                render uploadError as JSON
                return
            }catch(IOException e){
                uploadError.err="上传文件出错!"
                render uploadError as JSON
                return
            }


            uploadSuccess.msg=[url:request.getContextPath()+saveUrl+newFileName,localfile:fileName,id:0]
            render uploadSuccess as JSON
            return
        }

        def files=request.getFiles(uploadFieldName)
        def fileNames=request.getFileNames(uploadFieldName)

        if(!files || files.length<1){
            uploadError.err="您没有上传任何文件!"
            render uploadError as JSON
            return
        }

        for(int i=0;i<files.length;i++){
            File file=files[i]
            String fileName=fileNames[i]

            if(file.length()>uploadFileMaxSize){
                uploadError.err="上传文件超出限制大小！"
                uploadError.msg=fileName
                render uploadError as JSON
                return
            }

            // 检查文件扩展名
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase()
            if(!uploadFileExts.split(",").contains(fileExt)){
                uploadError.err="上传文件扩展名是不允许的扩展名。\n只允许" + uploadFileExts + "格式！"
                render uploadError as JSON
                return
            }


            savePath+=fileExt+"/"
            saveUrl+=fileExt+"/"

            SimpleDateFormat yearDf=new SimpleDateFormat('yyyy')
            SimpleDateFormat monthDf=new SimpleDateFormat('MM')
            SimpleDateFormat dateDf=new SimpleDateFormat('dd')
            Date date=new Date()
            String ymd=yearDf.format(date)+"/"+monthDf.format(date) + "/" + dateDf.format(date) + "/"
            savePath+=ymd
            saveUrl+=ymd

            File uploadDir=new File(savePath)
            if(!uploadDir.exists())
            uploadDir.mkdirs()

            String newFileName=UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt
            File uploadedFile=new File(savePath,newFileName)

            try{
                FileCopyUtils.copy(inputStream, new FileOutputStream(uploadedFile))
            }catch(FileNotFoundException e){
                uploadError.err="上传文件出错!"
                render uploadError as JSON
                return
            }catch(IOException e){
                uploadError.err="上传文件出错!"
                render uploadError as JSON
                return
            }


            uploadSuccess.msg=[url:request.getContextPath()+saveUrl+newFileName,localfile:fileName,id:0]

        }

        render uploadSuccess as JSON

    }
}
