package lj.cms.adapter;

import java.util.ArrayList;

import lj.cms.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapterEnhance extends BaseAdapter  {
	private AsyncImageLoaderEnhance asyncImageLoader;
	private Context mContext;
	private Gallery gallery;
	private ArrayList<String> imgUrlList=null;
	private int viewWidth=0;
	private float gkb=0;//高宽比
	private int defaultImage=0;
	
	public ImageAdapterEnhance(Context c,Gallery g,ArrayList<String> imgUrls,int viewWidth,float gkb) { 
		this.asyncImageLoader=new AsyncImageLoaderEnhance();
		this.mContext = c; 
		this.gallery=g;
		this.imgUrlList=imgUrls;
		this.viewWidth=viewWidth;
		this.gkb=gkb;
		this.defaultImage=R.drawable.default_img;
		}
	public ImageAdapterEnhance(Context c,Gallery g,ArrayList<String> imgUrls,int viewWidth,float gkb,int defaltImage) { 
		this.asyncImageLoader=new AsyncImageLoaderEnhance();
		this.mContext = c; 
		this.gallery=g;
		this.imgUrlList=imgUrls;
		this.viewWidth=viewWidth;
		this.gkb=gkb;
		this.defaultImage=defaltImage;
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
			String imageUrl=imgUrlList.get(position);
			System.out.println(imageUrl);
			itemView.setTag(imageUrl);
			Bitmap cachedImage = asyncImageLoader.loadDrawable(imageUrl, new AsyncImageLoaderEnhance.ImageCallback() {
	            public void imageLoaded(Bitmap imageBitmap, String imageUrl) {
	                ImageView imageViewByTag = (ImageView) gallery.findViewWithTag(imageUrl);
	                if (imageViewByTag != null&&imageBitmap!=null) {
	                    imageViewByTag.setImageBitmap(imageBitmap);
	                    //imageViewByTag.setAdjustViewBounds(true); 
	                    imageViewByTag.setBackgroundColor(0xFFFFFF);
	                    imageViewByTag.setScaleType(ImageView.ScaleType.FIT_XY);
	            		//itemView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	                    imageViewByTag.setLayoutParams(new Gallery.LayoutParams(new LayoutParams(viewWidth, (int)(viewWidth*gkb))));
	                }
	            }
	        });
			if (cachedImage != null) {
				itemView.setImageBitmap(cachedImage);
				//itemView.setAdjustViewBounds(true); 
			}
			else
			{//设置默认图片
				Bitmap bitmap=BitmapFactory.decodeResource(this.mContext.getResources(), defaultImage);
				itemView.setImageBitmap(bitmap);
				//itemView.setAdjustViewBounds(true); 
				//((ImageView) itemView).setImageBitmap(srBitmap.get());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		itemView.setBackgroundColor(0xFFFFFF);
		itemView.setScaleType(ImageView.ScaleType.FIT_XY);
		//itemView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		itemView.setLayoutParams(new Gallery.LayoutParams(new LayoutParams(viewWidth, (int)(viewWidth*gkb))));
		System.out.println(viewWidth+"---"+gkb);
		return itemView; 
		}   
}
