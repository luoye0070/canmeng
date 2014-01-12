package lj.cms.adapter;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import lj.cms.constant.AppConstant;
import lj.cms.filerw.FileReadWrite;
import lj.cms.internet.HttpConnectionHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoaderPure {
	private HashMap<String, SoftReference<Drawable>> imageCache;
	  
    public AsyncImageLoaderPure() {
   	 imageCache = new HashMap<String, SoftReference<Drawable>>();
    }
 
    public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
        if (imageCache.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable bitmap = softReference.get();
            if (bitmap != null) {
                return bitmap;
            }
        }
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
            	String imgName=getImgName(imageUrl);
        		String imgPath=AppConstant.DirNames.IMGDIR_NAME+imgName;
                imageCallback.imageLoaded((Drawable) message.obj, imgPath);                
//        		imageCallback.imageLoaded(imgPath, imageUrl);
            }
        };
        new Thread() {
            @Override
            public void run() {
            	Drawable bitmap = loadImageFromUrl(imageUrl);
            	if(bitmap!=null)
            	{
	                imageCache.put(imageUrl, new SoftReference<Drawable>(bitmap));
	                Message message = handler.obtainMessage(0, bitmap);
	                //Message message = handler.obtainMessage(0);
	                handler.sendMessage(message);
            	}
            }
        }.start();
        return null;
    }
 
	public static Drawable loadImageFromUrl(String url) {
		if(url==null||url.equals(""))
			return null;
		System.out.println("url:"+url);
		Drawable reBitmap=null;
		String imgName=getImgName(url);
		String imgPath=AppConstant.DirNames.IMGDIR_NAME+imgName;
		String urlStr=AppConstant.UrlStrs.URL_HOST+url;
		FileReadWrite frw=new FileReadWrite();
		HttpConnectionHelper httpConnHelper=new HttpConnectionHelper();
		httpConnHelper.setOuttime(5000);
		long s1=frw.getFileSize(imgPath);
		long s2=httpConnHelper.getResponseSize(urlStr, null);
		System.out.println("图片大小比较"+s1+","+s2);
		if((s1==0)||(s2!=-1&&s1!=s2))
		//if(!frw.isFileExist(imgPath))
		{//图片文件不存在，需要从服务器下载
			//HttpConnectionHelper httpConnHelper=new HttpConnectionHelper();
			httpConnHelper.setOuttime(10000);
			int recode=httpConnHelper.downloadImg(urlStr, null,imgName, AppConstant.DirNames.IMGDIR_NAME,true);
			if(recode!=AppConstant.VisitServerResultCode.RESULT_CODE_OK)
			{//下载图片失败，将文件路径置空
				//continue;
				imgPath=null;
				System.out.println("图片下载失败");
			}
		}
		if(imgPath!=null)
		{
			FileReadWrite frwbm=new FileReadWrite();
			if(frwbm.isFileExist(imgPath))
			{
				try{
					String imgPathQ=frwbm.getSDCardRoot()+imgPath;
		//			BitmapFactory.Options options = new BitmapFactory.Options();
		//	        options.inJustDecodeBounds = true;
		//	        // 获取这个图片的宽和高
		//	        Bitmap bitmap = BitmapFactory.decodeFile(imgPathQ, options); //此时返回bm为空
		//	        options.inJustDecodeBounds = false;
		//	         //计算缩放比
		//	        int be = (int)(options.outHeight / (float)200);
		//	        if (be <= 0)
		//	            be = 1;
		//	        options.inSampleSize = be;
		//	        //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		//	        bitmap=BitmapFactory.decodeFile(imgPathQ,options);
		//	        reBitmap=bitmap;
			        reBitmap=Drawable.createFromPath(imgPathQ);
				}
				catch(OutOfMemoryError ex){
					ex.printStackTrace();
				}
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
        public void imageLoaded(Drawable imageBitmap, String imageUrl);
        public void imageLoaded(String imgPath, String imageUrl);
    }
}
