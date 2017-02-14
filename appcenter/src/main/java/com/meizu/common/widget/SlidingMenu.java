package com.meizu.common.widget;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;

public class SlidingMenu extends FrameLayout {
    private boolean a;
    private CustomViewAbove b;
    private CustomViewBehind c;
    private e d;
    private b e;
    private d f;
    private int g;
    private LocalActivityManager h;
    private Context i;
    private Handler j;

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public /* synthetic */ Object createFromParcel(Parcel x0) {
                return a(x0);
            }

            public /* synthetic */ Object[] newArray(int x0) {
                return a(x0);
            }

            public SavedState a(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] a(int size) {
                return new SavedState[size];
            }
        };
        private final int a;

        public SavedState(Parcelable superState, int item) {
            super(superState);
            this.a = item;
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readInt();
        }

        public int a() {
            return this.a;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.a);
        }
    }

    public interface a {
        void a(Canvas canvas, float f);
    }

    public interface b {
        void a();
    }

    public interface c {
        void a();
    }

    public interface d {
        void a(int i);

        void a(g gVar);
    }

    public interface e {
        void a();
    }

    public interface f {
        void a();
    }

    public enum g {
        OPEN,
        CLOSE
    }

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = false;
        this.g = 0;
        this.j = new Handler();
        this.i = context;
        LayoutParams behindParams = new LayoutParams(-1, -1);
        this.c = new CustomViewBehind(context);
        addView(this.c, behindParams);
        LayoutParams aboveParams = new LayoutParams(-1, -1);
        this.b = new CustomViewAbove(context);
        addView(this.b, aboveParams);
        this.b.setCustomViewBehind(this.c);
        this.c.setCustomViewAbove(this.b);
        this.b.setOnPageChangeListener(new com.meizu.common.widget.CustomViewAbove.a(this) {
            final /* synthetic */ SlidingMenu a;

            {
                this.a = r1;
            }

            public void a(int position, float positionOffset, int positionOffsetPixels) {
                if (this.a.f != null) {
                    this.a.f.a(positionOffsetPixels);
                }
            }

            public void a(int position) {
                if (position == 0 && this.a.d != null) {
                    this.a.d.a();
                } else if (position == 1 && this.a.e != null) {
                    this.a.e.a();
                }
                if (this.a.f != null) {
                    g state;
                    if (position == 1) {
                        state = g.CLOSE;
                    } else {
                        state = g.OPEN;
                    }
                    this.a.f.a(state);
                }
            }
        });
        setShadowDrawable(com.meizu.common.a.e.mz_drawer_shadow_light);
        setMenuWidthRes(com.meizu.common.a.d.mz_slidingmenu_menu_width);
    }

    public void setup(LocalActivityManager activityManager) {
        this.h = activityManager;
    }

    public void setContent(int res) {
        setContent(LayoutInflater.from(getContext()).inflate(res, null));
    }

    public void setContent(View view) {
        this.b.setContent(view);
        a();
    }

    public void setContent(Intent intent) {
        if (this.h == null) {
            throw new IllegalStateException("Did you forget to call 'public void setup(LocalActivityManager activityManager)'?");
        }
        View v = a(this.h, intent);
        if (v == null) {
            throw new IllegalStateException("get content from Activity failed!");
        }
        setContent(v);
    }

    private View a(LocalActivityManager activityManager, Intent intent) {
        View contentView = null;
        TypedArray a = getContext().getTheme().obtainStyledAttributes(new int[]{16842836});
        int background = a.getResourceId(0, 0);
        a.recycle();
        Window window = activityManager.startActivity(null, intent);
        if (window != null) {
            contentView = window.getDecorView();
        }
        contentView.setBackgroundResource(background);
        if (contentView != null) {
            contentView.setVisibility(0);
            contentView.setFocusableInTouchMode(true);
            ((ViewGroup) contentView).setDescendantFocusability(262144);
        }
        return contentView;
    }

    public View getContent() {
        return this.b.getContent();
    }

    public void setMenu(int res) {
        setMenu(LayoutInflater.from(getContext()).inflate(res, null));
    }

    public void setMenu(View v) {
        this.c.setContent(v);
    }

    public void setMenu(Intent intent) {
        if (this.h == null) {
            throw new IllegalStateException("Did you forget to call 'public void setup(LocalActivityManager activityManager)'?");
        }
        View v = a(this.h, intent);
        if (v == null) {
            throw new IllegalStateException("get content from Activity failed!");
        }
        setMenu(v);
    }

    public View getMenu() {
        return this.c.getContent();
    }

    public void setSecondaryMenu(int res) {
        setSecondaryMenu(LayoutInflater.from(getContext()).inflate(res, null));
    }

    public void setSecondaryMenu(View v) {
        this.c.setSecondaryContent(v);
    }

    public View getSecondaryMenu() {
        return this.c.getSecondaryContent();
    }

    public void setSlidingEnabled(boolean b) {
        this.b.setSlidingEnabled(b);
    }

    public void setMode(int mode) {
        if (mode == 0 || mode == 1 || mode == 2) {
            this.c.setMode(mode);
            return;
        }
        throw new IllegalStateException("SlidingMenu mode must be LEFT, RIGHT, or LEFT_RIGHT");
    }

    public void setMenuVisibleAlways(boolean b) {
        this.c.setMenuVisibleAlways(b);
    }

    public int getMode() {
        return this.c.getMode();
    }

    public void setStatic(boolean b) {
        if (b) {
            setSlidingEnabled(false);
            this.b.setCustomViewBehind(this.c);
            this.b.setCurrentItem(1);
            return;
        }
        this.b.setCurrentItem(1);
        this.b.setCustomViewBehind(this.c);
        setSlidingEnabled(true);
    }

    public void a() {
        a(true);
    }

    public void a(boolean animate) {
        this.b.setCurrentItem(1, animate);
    }

    public int getBehindOffset() {
        return ((RelativeLayout.LayoutParams) this.c.getLayoutParams()).rightMargin;
    }

    public void setMenuOffset(int i) {
        int width;
        Display display = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        try {
            Class<?>[] parameterTypes = new Class[]{Point.class};
            Point parameter = new Point();
            Display.class.getMethod("getSize", parameterTypes).invoke(display, new Object[]{parameter});
            width = parameter.x;
        } catch (Exception e) {
            width = display.getWidth();
        }
        setMenuWidth(width - i);
    }

    public void setMenuOffsetRes(int resID) {
        setMenuOffset((int) getContext().getResources().getDimension(resID));
    }

    public void setContentOffsetLeft(int offset) {
        this.g = offset;
        this.b.setAboveOffsetLeft(offset);
        ((MarginLayoutParams) this.b.getLayoutParams()).setMargins(this.g, 0, 0, 0);
        requestLayout();
    }

    public int getMenuOffset() {
        return ((RelativeLayout.LayoutParams) this.c.getLayoutParams()).rightMargin;
    }

    public int getContentOffsetLeft() {
        return this.g;
    }

    public void setContentOffsetRes(int resID) {
        setContentOffsetLeft((int) getContext().getResources().getDimension(resID));
    }

    public void setMenuWidth(int i) {
        this.c.setMenuWidth(i);
    }

    public void setMenuWidthRes(int res) {
        setMenuWidth((int) getContext().getResources().getDimension(res));
    }

    public float getBehindScrollScale() {
        return this.c.getScrollScale();
    }

    public void setBehindScrollScale(float f) {
        if (f >= 0.0f || f <= 1.0f) {
            this.c.setScrollScale(f);
            return;
        }
        throw new IllegalStateException("ScrollScale must be between 0 and 1");
    }

    public void setBehindCanvasTransformer(a t) {
        this.c.setCanvasTransformer(t);
    }

    public int getTouchModeAbove() {
        return this.b.getTouchMode();
    }

    public void setTouchModeAbove(int i) {
        if (i == 1 || i == 0 || i == 2) {
            this.b.setTouchMode(i);
            return;
        }
        throw new IllegalStateException("TouchMode must be set to eitherTOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
    }

    public void setTouchModeBehind(int i) {
        if (i == 1 || i == 0 || i == 2) {
            this.c.setTouchMode(i);
            return;
        }
        throw new IllegalStateException("TouchMode must be set to eitherTOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
    }

    public void setShadowDrawable(int resId) {
        setShadowDrawable(getContext().getResources().getDrawable(resId));
    }

    public void setShadowDrawable(Drawable d) {
        this.c.setShadowDrawable(d);
    }

    public void setSecondaryShadowDrawable(int resId) {
        setSecondaryShadowDrawable(getContext().getResources().getDrawable(resId));
    }

    public void setSecondaryShadowDrawable(Drawable d) {
        this.c.setSecondaryShadowDrawable(d);
    }

    public void setShadowWidthRes(int resId) {
        setShadowWidth((int) getResources().getDimension(resId));
    }

    public void setShadowWidth(int pixels) {
        this.c.setShadowWidth(pixels);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
        int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, width - this.g);
        int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, height);
        if (this.b != null) {
            this.b.measure(contentWidth, contentHeight);
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setFadeEnabled(boolean b) {
        this.c.setFadeEnabled(b);
    }

    public void setFadeDegree(float f) {
        this.c.setFadeDegree(f);
    }

    public void setSelectorEnabled(boolean b) {
        this.c.setSelectorEnabled(true);
    }

    public void setSelectedView(View v) {
        this.c.setSelectedView(v);
    }

    public void setSelectorDrawable(int res) {
        this.c.setSelectorBitmap(BitmapFactory.decodeResource(getResources(), res));
    }

    public void setSelectorBitmap(Bitmap b) {
        this.c.setSelectorBitmap(b);
    }

    public void setOnOpenListener(e listener) {
        this.d = listener;
    }

    public void setOnCloseListener(b listener) {
        this.e = listener;
    }

    public void setOnOpenedListener(f listener) {
        this.b.setOnOpenedListener(listener);
    }

    public void setOnClosedListener(c listener) {
        this.b.setOnClosedListener(listener);
    }

    public void setOnMenuStateChangeListener(d l) {
        this.f = l;
    }

    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.b.getCurrentItem());
    }

    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.b.setCurrentItem(ss.a());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(SlidingMenu.class.getName());
    }
}
