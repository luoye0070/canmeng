package lj.cms.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;


public class ITBCListAdapter extends BaseAdapter {
private AsyncImageLoader asyncImageLoader;
	//SpannableString
	Activity contex=null;
	ListView listView=null;
	ArrayList<HashMap<String,Object>> list=null;
	int itemLayoutId=0;
	String [] hashKeys=null;
	int [] viewIds=null;
	
	SoftReference<Bitmap> srBitmap=null;
	
	public ITBCListAdapter(Activity contex, ListView listView, ArrayList<HashMap<String, Object>> list,
			int itemLayoutId, String[] hashKeys, int[] viewIds) {
		super();
		asyncImageLoader = new AsyncImageLoader();
		
		this.contex = contex;
		this.listView=listView;
		this.list = list;
		this.itemLayoutId = itemLayoutId;
		this.hashKeys = hashKeys;
		this.viewIds = viewIds;
		this.srBitmap=new SoftReference<Bitmap>(BitmapFactory.decodeResource(this.contex.getResources(), R.drawable.default_img));
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ArrayList<View> viewList=null;
		if(convertView == null){
			convertView = contex.getLayoutInflater().inflate(itemLayoutId, null);
			viewList=new ArrayList<View>();
			for (int i = 0; i < viewIds.length; i++) {
				View itemView=convertView.findViewById(viewIds[i]);
				viewList.add(itemView);
			}
			convertView.setTag(viewList);
		}
		else
		{
			viewList=(ArrayList<View>)convertView.getTag();
		}
		if(viewList!=null)
		{
			//设置各控件的值
			int i_vs=viewList.size();
			for (int i = 0; i < i_vs; i++) {
				View itemView=viewList.get(i);
				if(itemView instanceof TextView)
				{
					if(itemView instanceof Button)
					{
						if(itemView instanceof CheckBox){
							itemView=(CheckBox)itemView;
							if(list.get(position).get(hashKeys[i]) instanceof LvItemCbAttribute)
								((CheckBox) itemView).setChecked(((LvItemCbAttribute)list.get(position).get(hashKeys[i])).isChecked);
							if(list.get(position).get(hashKeys[i]) instanceof OnClickListener)
								((CheckBox) itemView).setOnClickListener((OnClickListener) list.get(position).get(hashKeys[i]));
						}
						else{
							itemView=(Button)itemView;
							if(list.get(position).get(hashKeys[i]) instanceof OnClickListener)
							{
								if(list.get(position).get(hashKeys[i]) instanceof LvItemBtAttribute)
								{
									LvItemBtAttribute lvibtonclickListener=(LvItemBtAttribute)list.get(position).get(hashKeys[i]);
									if(lvibtonclickListener.btTxt!=null)
										((Button)itemView).setText(lvibtonclickListener.btTxt);
									if(lvibtonclickListener.btBg!=null)
										((Button)itemView).setBackgroundDrawable(lvibtonclickListener.btBg);
									if(lvibtonclickListener.btV!=-1)
										((Button)itemView).setVisibility(lvibtonclickListener.btV);
								}
								itemView.setOnClickListener((OnClickListener)list.get(position).get(hashKeys[i]));
							}
						}
					}
					else
					{
						itemView=(TextView)itemView;
						((TextView) itemView).setText((SpannableString)list.get(position).get(hashKeys[i]));
					}
					
				}
				else if(itemView instanceof ImageView)
				{
					itemView=((ImageView)itemView);
					String imageUrl=(String)list.get(position).get(hashKeys[i]);
					itemView.setTag(imageUrl);
					Bitmap cachedImage = asyncImageLoader.loadDrawable(imageUrl, new AsyncImageLoader.ImageCallback() {
			            public void imageLoaded(Bitmap imageBitmap, String imageUrl) {
			                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
			                if (imageViewByTag != null&&imageBitmap!=null) {
			                    imageViewByTag.setImageBitmap(imageBitmap);
			                }
			            }
			        });
					if (cachedImage != null) {
						((ImageView) itemView).setImageBitmap(cachedImage);
					}
					else
					{//设置默认图片
						Bitmap bitmap=BitmapFactory.decodeResource(this.contex.getResources(), R.drawable.default_img);
						((ImageView) itemView).setImageBitmap(bitmap);
						//((ImageView) itemView).setImageBitmap(srBitmap.get());
					}
				}
	
			}
		}
		System.out.println("zdyAdapter:"+position);
		return convertView;
	}

}


//class AsyncImageLoader {
//
//	 private HashMap<String, SoftReference<Bitmap>> imageCache;
//	  
//	     public AsyncImageLoader() {
//	    	 imageCache = new HashMap<String, SoftReference<Bitmap>>();
//	     }
//	  
//	     public Bitmap loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
//	         if (imageCache.containsKey(imageUrl)) {
//	             SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
//	             Bitmap bitmap = softReference.get();
//	             if (bitmap != null) {
//	                 return bitmap;
//	             }
//	         }
//	         final Handler handler = new Handler() {
//	             public void handleMessage(Message message) {
//	                 imageCallback.imageLoaded((Bitmap) message.obj, imageUrl);
//	             }
//	         };
//	         new Thread() {
//	             @Override
//	             public void run() {
//	                 Bitmap bitmap = loadImageFromUrl(imageUrl);
//	                 imageCache.put(imageUrl, new SoftReference<Bitmap>(bitmap));
//	                 Message message = handler.obtainMessage(0, bitmap);
//	                 handler.sendMessage(message);
//	             }
//	         }.start();
//	         return null;
//	     }
//	  
//		public static Bitmap loadImageFromUrl(String url) {
//			if(url.equals("")||url==null)
//				return null;
//			System.out.println("url:"+url);
//			Bitmap reBitmap=null;
//			String imgName=getImgName(url);
//			String imgPath=AppConstant.DirNames.IMGDIR_NAME+imgName;
//			String urlStr=AppConstant.UrlStrs.URL_HOST+url;
//			FileReadWrite frw=new FileReadWrite();
//			if(!frw.isFileExist(imgPath))
//			{//图片文件不存在，需要从服务器下载
//				HttpConnectionHelper httpConnHelper=new HttpConnectionHelper();
//				int recode=httpConnHelper.downloadImg(urlStr, null,imgName, AppConstant.DirNames.IMGDIR_NAME);
//				if(recode!=AppConstant.VisitServerResultCode.RESULT_CODE_OK)
//				{//下载图片失败，将文件路径置空
//					//continue;
//					imgPath=null;
//				}
//			}
//			if(imgPath!=null)
//			{
//				FileReadWrite frwbm=new FileReadWrite();
//				String imgPathQ=frwbm.getSDCardRoot()+imgPath;
//				BitmapFactory.Options options = new BitmapFactory.Options();
//		        options.inJustDecodeBounds = true;
//		        // 获取这个图片的宽和高
//		        Bitmap bitmap = BitmapFactory.decodeFile(imgPathQ, options); //此时返回bm为空
//		        options.inJustDecodeBounds = false;
//		         //计算缩放比
//		        int be = (int)(options.outHeight / (float)200);
//		        if (be <= 0)
//		            be = 1;
//		        options.inSampleSize = be;
//		        //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
//		        bitmap=BitmapFactory.decodeFile(imgPathQ,options);
//		        reBitmap=bitmap;
//			}
//			return reBitmap;
//		}
//		
//		//获取文件名
//		public static String getImgName(String fileName)
//		{
//			String imgName=fileName;
//			int index=fileName.lastIndexOf("/");
//			if(index!=-1)
//			{
//				imgName=fileName.substring(index+1);
//			}
//			return imgName;
//		}
//		
//	     public interface ImageCallback {
//	         public void imageLoaded(Bitmap imageBitmap, String imageUrl);
//	     }
//
//}
