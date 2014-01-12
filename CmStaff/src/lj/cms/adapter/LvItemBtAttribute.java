package lj.cms.adapter;

import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.view.View.OnClickListener;

public abstract class LvItemBtAttribute implements OnClickListener {
	public String btTxt;//按钮显示文本
	public Drawable btBg;//按钮背景
	public int btV;//按钮显示方式

	public LvItemBtAttribute() {
		super();
		this.btTxt = null;
		this.btBg=null;
		this.btV = -1;
	}
	public LvItemBtAttribute(String btTxt) {
		super();
		this.btTxt = btTxt;
		this.btBg=null;
		this.btV = -1;
	}
	public LvItemBtAttribute(Drawable btBg) {
		super();
		this.btTxt=null;
		this.btBg = btBg;
		this.btV = -1;
	}
	public LvItemBtAttribute(int btV) {
		super();
		this.btTxt = null;
		this.btBg = null;
		this.btV = btV;
	}
	public LvItemBtAttribute(String btTxt, Drawable btBg) {
		super();
		this.btTxt = btTxt;
		this.btBg = btBg;
		this.btV = -1;
	}
	public LvItemBtAttribute(String btTxt, int btV) {
		super();
		this.btTxt = btTxt;
		this.btBg = null;
		this.btV = btV;
	}
	public LvItemBtAttribute(Drawable btBg, int btV) {
		super();
		this.btTxt = null;
		this.btBg = btBg;
		this.btV = btV;
	}
	public LvItemBtAttribute(String btTxt, Drawable btBg, int btV) {
		super();
		this.btTxt = btTxt;
		this.btBg = btBg;
		this.btV = btV;
	}
}
