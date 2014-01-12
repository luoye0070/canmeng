package lj.cms.adapter;

import java.util.ArrayList;

import lj.cms.filerw.FileReadWrite;

import lj.cms.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private AsyncImageLoader asyncImageLoader;
	private Context mContext;
	private Gallery gallery;
	private ArrayList<String> imgUrlList=null;
	
	public ImageAdapter(Context c,Gallery g,ArrayList<String> imgUrls) { 
		this.asyncImageLoader=new AsyncImageLoader();
		this.mContext = c; 
		this.gallery=g;
		this.imgUrlList=imgUrls;
		}  
	public int getCount() {
		return imgUrlList.size();  
		}  
	public Object getItem(int position) {
		return position;  
		}  
	public long getItemId(int position) {
		return position;  
		} 
	public View getView(int position, View convertView, ViewGroup parent) { 
		ImageView itemView = null;
		if(convertView==null){
			itemView = new ImageView(mContext);
		}
		else{
			itemView = (ImageView) convertView;
		}
		try {
//			FileReadWrite frw=new FileReadWrite();
//			i = new ImageView(mContext);  
//			//i.setImageResource(mThumbIds[position]);
//			i.setImageBitmap(frw.getImg(imgPathList.get(position)));
//			i.setAdjustViewBounds(true); 
//			//i.setMaxHeight(120);
//			//i.setScaleType(ImageView.ScaleType.FIT_CENTER);
//			i.setLayoutParams(new Gallery.LayoutParams(128, 128)); 
//			//i.setBackgroundResource(R.drawable.x); 
			
//			FileReadWrite frw=new FileReadWrite();
//			String imgPathQ=frw.getSDCardRoot()+imgUrlList.get(position);
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
//	        int w = bitmap.getWidth();
//	        int h = bitmap.getHeight();
//	        System.out.println(w+"   "+h);
//	        i=new ImageView(mContext);
//	        i.setImageBitmap(bitmap);
//	        i.setAdjustViewBounds(true); 
//			//i.setMaxHeight(120);
//			//i.setScaleType(ImageView.ScaleType.FIT_CENTER);
//			//i.setLayoutParams(new Gallery.LayoutParams(128, 128)); 
//			//i.setBackgroundResource(R.drawable.x); 
			
			String imageUrl=imgUrlList.get(position);
			System.out.println(imageUrl);
			itemView.setTag(imageUrl);
			Bitmap cachedImage = asyncImageLoader.loadDrawable(imageUrl, new AsyncImageLoader.ImageCallback() {
	            public void imageLoaded(Bitmap imageBitmap, String imageUrl) {
	                ImageView imageViewByTag = (ImageView) gallery.findViewWithTag(imageUrl);
	                if (imageViewByTag != null&&imageBitmap!=null) {
	                    imageViewByTag.setImageBitmap(imageBitmap);
	                    imageViewByTag.setAdjustViewBounds(true); 
	                }
	            }
	        });
			if (cachedImage != null) {
				itemView.setImageBitmap(cachedImage);
				itemView.setAdjustViewBounds(true); 
			}
			else
			{//设置默认图片
				Bitmap bitmap=BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.default_img);
				itemView.setImageBitmap(bitmap);
				itemView.setAdjustViewBounds(true); 
				//((ImageView) itemView).setImageBitmap(srBitmap.get());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return itemView; 
		}   
}
