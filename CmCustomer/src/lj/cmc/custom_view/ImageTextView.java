package lj.cmc.custom_view;

import java.util.ArrayList;

import lj.cmc.R;
import lj.cmc.adapter.AsyncImageLoaderEnhance;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageTextView extends LinearLayout {
	
	private ImageView iv=null;
	private TextView tv=null;
	AsyncImageLoaderEnhance asyncImageLoader=null;
	Context context=null;
//	ArrayList<Bitmap> bitmapList=null;
	
	private boolean imgbehind=false;
	private int tv_textColor=-1;
	private int tv_bgColor=-1;
	private int iv_bgColor=-1;
	private float tv_textSize=-1f;
	
	public ImageTextView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	public ImageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub		
		initView(context, attrs);
	}
	public ImageTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub	
		initView(context, attrs);
	}
	private void initView(Context context, AttributeSet attrs){
//		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom_view_itvParams);
//		try {
//			//imgbehind = a.getBoolean(R.attr.layout_imageBehind, false);
//			imgbehind = a.getBoolean(R.styleable.custom_view_itvParams_layout_imageBehind, false);
//			tv_textColor=a.getColor(R.styleable.custom_view_itvParams_tv_textColor, -1);
//			tv_bgColor=a.getColor(R.styleable.custom_view_itvParams_tv_bgColor, -1);
//			iv_bgColor=a.getColor(R.styleable.custom_view_itvParams_iv_bgColor, -1);
//			tv_textSize=a.getDimension(R.styleable.custom_view_itvParams_tv_textSize, -1f);
//		} finally {
//			a.recycle();
//		}
//		// 导入布局   
//		if(imgbehind)
//			LayoutInflater.from(context).inflate(R.layout.custom_view_imgtxtview_imgbehind, this, true);
//		else
//			LayoutInflater.from(context).inflate(R.layout.custom_view_imgtxtview, this, true);
//		
//		iv=(ImageView) findViewById(R.id.cv_itv_iv);
//		tv=(TextView) findViewById(R.id.cv_itv_tv);
//		//设置控件属性
//		if(tv_textColor!=-1){
//			tv.setTextColor(tv_textColor);
//		}
//		if(tv_bgColor!=-1){
//			tv.setBackgroundColor(tv_bgColor);
//		}
//		if(iv_bgColor!=-1){
//			iv.setBackgroundColor(iv_bgColor);
//		}
//		if(tv_textSize!=-1f){
//			tv.setTextSize(tv_textSize);
//		}
//		asyncImageLoader=new AsyncImageLoaderEnhance();
//		this.context=context;
	}
	/**  
     * 设置图片资源  
     */   
    public void setImageResource(String imageUrl) { 
//    	if(bitmapList==null)
//		{
//			bitmapList=new ArrayList<Bitmap>();
//		}
		iv.setTag(imageUrl);
		Bitmap cachedImage = asyncImageLoader.loadDrawable(imageUrl, new AsyncImageLoaderEnhance.ImageCallback() {
            public void imageLoaded(Bitmap imageBitmap, String imageUrl) {
                ImageView imageViewByTag = (ImageView) findViewWithTag(imageUrl);
                if (imageViewByTag != null&&imageBitmap!=null) {
                    imageViewByTag.setImageBitmap(imageBitmap);
                    imageViewByTag.setAdjustViewBounds(true); 
                    //clearBitmap();
                    //bitmapList.add(imageBitmap);
                }
            }
        });
		if (cachedImage != null) {
			iv.setImageBitmap(cachedImage);
			iv.setAdjustViewBounds(true);
			//clearBitmap();
			//bitmapList.add(cachedImage);
		}
		else
		{//设置默认图片
			Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.default_img);
			iv.setImageBitmap(bitmap);
			iv.setAdjustViewBounds(true); 
			//clearBitmap();
			//bitmapList.add(bitmap);
		}  
    } 
    
    /**  
     * 设置图片资源  
     */   
    public void setImageResource(String imageUrl,int defaultImage) { 
//    	if(bitmapList==null)
//		{
//			bitmapList=new ArrayList<Bitmap>();
//		}
		iv.setTag(imageUrl);
		Bitmap cachedImage = asyncImageLoader.loadDrawable(imageUrl, new AsyncImageLoaderEnhance.ImageCallback() {
            public void imageLoaded(Bitmap imageBitmap, String imageUrl) {
                ImageView imageViewByTag = (ImageView) findViewWithTag(imageUrl);
                if (imageViewByTag != null&&imageBitmap!=null) {
                    imageViewByTag.setImageBitmap(imageBitmap);
                    imageViewByTag.setAdjustViewBounds(true); 
                    //clearBitmap();
                    //bitmapList.add(imageBitmap);
                }
            }
        });
		if (cachedImage != null) {
			iv.setImageBitmap(cachedImage);
			iv.setAdjustViewBounds(true);
			//clearBitmap();
			//bitmapList.add(cachedImage);
		}
		else
		{//设置默认图片
			Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(), defaultImage);
			iv.setImageBitmap(bitmap);
			iv.setAdjustViewBounds(true); 
			//clearBitmap();
			//bitmapList.add(bitmap);
		}  
    }   
   
    /**  
     * 设置显示的文字  
     */   
    public void setTextViewText(String text) {   
        tv.setText(Html.fromHtml(text));  
    }  
    
    /**********
     * 清理图片资源
     * ***********/
    public void clearBitmap(){
//    	if(bitmapList!=null)
//    	{
//    		for(Bitmap btmp:bitmapList){
//    			if(!btmp.isRecycled())
//    				btmp.recycle();
//    		}
//    		bitmapList.clear();
//    	}
    }
}
