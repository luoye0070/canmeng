package lj.cmc.adapter;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import lj.cmc.constant.AppConstant;
import lj.cmc.filerw.FileReadWrite;
import lj.cmc.internet.HttpConnectionHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {
	private HashMap<String, SoftReference<Bitmap>> imageCache;
	  
    public AsyncImageLoader() {
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
		String urlStr=AppConstant.UrlStrs.URL_HOST+url;
		FileReadWrite frw=new FileReadWrite();
		if(!frw.isFileExist(imgPath))
		{//图片文件不存在，需要从服务器下载
			HttpConnectionHelper httpConnHelper=new HttpConnectionHelper();
			int recode=httpConnHelper.downloadImg(urlStr, null,imgName, AppConstant.DirNames.IMGDIR_NAME);
			if(recode!=AppConstant.VisitServerResultCode.RESULT_CODE_OK)
			{//下载图片失败，将文件路径置空
				//continue;
				imgPath=null;
			}
		}
		if(imgPath!=null)
		{
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
