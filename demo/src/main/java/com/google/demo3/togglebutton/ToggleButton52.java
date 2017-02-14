package com.google.demo3.togglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ToggleButton52 extends View{

	private ToggleState toggleState = ToggleState.Open;//开关的状态
	private Bitmap slideBg;
	private Bitmap switchBg;
	private boolean isSliding = false;
	
	private int currentX;//当前触摸点x坐标

	/**
	 * 如果你的view只是在布局文件中使用，只需要重写这个构造方法
	 * @param context
	 * @param attrs
	 */
	public ToggleButton52(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 如果你的 view需要在java代码中动态new出来，走的是这个构造方法
	 * @param context
	 */
	public ToggleButton52(Context context) {
		super(context);
	}
	
	public enum ToggleState{
		Open,Close
	}

	/**
	 * 设置滑动块的背景图片
	 * @param slideButtonBackground
	 */
	public void setSlideBackgroudResource(int slideButtonBackground) {
		slideBg = BitmapFactory.decodeResource(getResources(), slideButtonBackground);
	}

	/**
	 * 设置滑动开关的背景图片
	 * @param switchBackground
	 */
	public void setSwitchBackgroudResource(int switchBackground) {
		switchBg = BitmapFactory.decodeResource(getResources(), switchBackground);
	}

	/**
	 * 设置开关的状态
	 * @param state
	 */
	public void setToggleState(ToggleState state) {
		toggleState = state;
	}
	
	/**
	 * 设置当前控件显示在屏幕上的宽高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(switchBg.getWidth(), switchBg.getHeight());
	}
	
	/**
	 * 绘制自己显示在屏幕时的样子
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//1.绘制背景图片
		//left: 图片的左边的x坐标
		//top: 图片顶部的y坐标
		canvas.drawBitmap(switchBg, 0, 0, null);
		
		//2.绘制滑动块的图片
		if(isSliding){
			int left = currentX - slideBg.getWidth()/2;
			if(left<0)left = 0;
			if(left>(switchBg.getWidth() - slideBg.getWidth())){
				left = switchBg.getWidth() - slideBg.getWidth();
			}
			canvas.drawBitmap(slideBg, left, 0, null);
		}else {
			//此时抬起，根据state去绘制滑动块的位置
			if(toggleState==ToggleState.Open){
				canvas.drawBitmap(slideBg, switchBg.getWidth()-slideBg.getWidth(), 0, null);
			}else {
				canvas.drawBitmap(slideBg, 0, 0, null);
			}
		}
		
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		currentX = (int) event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isSliding = true;
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			isSliding = false;
			
			int centerX = switchBg.getWidth()/2;
			if(currentX>centerX){
				//open
				if(toggleState!=ToggleState.Open){
					toggleState = ToggleState.Open;
					if(listner!=null){
						listner.onToggleStateChange(toggleState);
					}
				}
			}else {
				//close
				if(toggleState!=ToggleState.Close){
					toggleState = ToggleState.Close;
					if(listner!=null){
						listner.onToggleStateChange(toggleState);
					}
				}
			}
			break;
		}
		invalidate();
		return true;
	}

	private OnToggleStateChangeListner listner;
	public void setOnToggleStateChangeListener(OnToggleStateChangeListner listner){
		this.listner = listner;
	}
	public interface OnToggleStateChangeListner{
		void onToggleStateChange(ToggleState state);
	}
}
