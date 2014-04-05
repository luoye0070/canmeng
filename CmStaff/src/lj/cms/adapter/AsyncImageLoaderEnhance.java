package lj.cms.adapter;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import lj.cms.constant.AppConstant;
import lj.cms.filerw.FileReadWrite;
import lj.cms.internet.HttpConnectionHelper;
import lj.cms.internet.ParamCollect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

/***********************
 *异步加载图片增强版 
 ***************************/
public class AsyncImageLoaderEnhance {
	private HashMap<String, SoftReference<Bitmap>> imageCache;
	  
    public AsyncImageLoaderEnhance() {
   	 imageCache = new HashMap<String, SoftReference<Bitmap>>();
    }
 
    public Bitmap loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
        if (imageCache.containsKey(imageUrl)) {
            SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
            Bitmap bitmap = softReference.get();
            if (bitmap != null) {
                return bitmap;
            }
        }
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                imageCallback.imageLoaded((Bitmap) message.obj, imageUrl);
            }
        };
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = loadImageFromUrl(imageUrl);
                imageCache.put(imageUrl, new SoftReference<Bitmap>(bitmap));
                Message message = handler.obtainMessage(0, bitmap);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }
 
	public static Bitmap loadImageFromUrl(String url) {
		if(url==null||url.equals(""))
			return null;
		System.out.println("url:"+url);
		Bitmap reBitmap=null;
		String imgName=getImgName(url);
		String imgPath=AppConstant.DirNames.IMGDIR_NAME+imgName;
		String urlStr=AppConstant.UrlStrs.URL_IMAGE_THUMBNAIL;
		ParamCollect pc=new ParamCollect();
		pc.addOrSetParam("imgUrl", url);
		pc.addOrSetParam("width", "300");
		pc.addOrSetParam("height", "300");
		FileReadWrite frw=new FileReadWrite();
		HttpConnectionHelper httpConnHelper=new HttpConnectionHelper();
		httpConnHelper.setOuttime(5000);
		long s1=frw.getFileSize(imgPath);
		long s2=httpConnHelper.getResponseSize(urlStr, null);		
		System.out.println("图片大小比较"+s1+","+s2);
		if((s1==0)||(s2!=-1&&s1!=s2))
		{//图片文件不存在或者图片大小和网络上不一致，需要从服务器下载
			//HttpConnectionHelper httpConnHelper=new HttpConnectionHelper();
			httpConnHelper.setOuttime(10000);
			int recode=httpConnHelper.downloadImg(urlStr, pc.paramList,imgName, AppConstant.DirNames.IMGDIR_NAME,true);
			if(recode!=AppConstant.VisitServerResultCode.RESULT_CODE_OK)
			{//下载图片失败，将文件路径置空
				//continue;
				imgPath=null;
			}
		}
		if(imgPath!=null)
		{
			try{
				FileReadWrite frwbm=new FileReadWrite();
				String imgPathQ=frwbm.getSDCardRoot()+imgPath;
				BitmapFactory.Options options = new BitmapFactory.Options();
		        options.inJustDecodeBounds = true;
		        // 获取这个图片的宽和高
		        Bitmap bitmap = BitmapFactory.decodeFile(imgPathQ, options); //此时返回bm为空
		        options.inJustDecodeBounds = false;
		         //计算缩放比
		        int be = (int)(options.outHeight / (float)200);
		        if (be <= 0)
		            be = 1;
		        options.inSampleSize = be;
		        //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		        bitmap=BitmapFactory.decodeFile(imgPathQ,options);
		        reBitmap=bitmap;
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return reBitmap;
	}
	
	//获取文件名
	public static String getImgName(String fileName)
	{
		String imgName=fileName;
		int index=fileName.lastIndexOf("/");
		if(index!=-1)
		{
			imgName=fileName.substring(index+1);
		}
		return imgName.trim();
	}
	
    public interface ImageCallback {
        public void imageLoaded(Bitmap imageBitmap, String imageUrl);
    }
}
