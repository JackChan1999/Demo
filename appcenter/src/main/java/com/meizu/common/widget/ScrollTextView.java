package com.meizu.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class ScrollTextView extends View {
    private static String b = "ScrollTextView";
    private float A;
    private float B;
    private List<c> C;
    private List<e> D;
    private List<d> E;
    private Paint F;
    private h G;
    private Canvas H;
    private Bitmap I;
    private boolean J;
    boolean a;
    private Paint c;
    private int d;
    private int e;
    private float f;
    private float g;
    private int h;
    private int i;
    private float j;
    private float k;
    private g l;
    private boolean m;
    private int n;
    private int o;
    private int p;
    private i q;
    private int r;
    private f s;
    private b t;
    private Shader u;
    private Matrix v;
    private Paint w;
    private float x;
    private boolean y;
    private float z;

    public interface e {
        void a(ScrollTextView scrollTextView);

        void b(ScrollTextView scrollTextView);
    }

    public interface b {
        void a(View view, int i, int i2);

        String c(int i);
    }

    private interface h {
        void a();

        void a(int i);

        void b();

        void c();
    }

    private static class a extends Handler {
        private final WeakReference<g> a;

        public a(g scrollTextViewScroller) {
            this.a = new WeakReference(scrollTextViewScroller);
        }

        public void handleMessage(Message msg) {
            g scrollTextViewScroller = (g) this.a.get();
            if (scrollTextViewScroller != null) {
                scrollTextViewScroller.e.computeScrollOffset();
                int currY = scrollTextViewScroller.e.getCurrY();
                int delta = scrollTextViewScroller.f - currY;
                scrollTextViewScroller.f = currY;
                if (delta != 0) {
                    scrollTextViewScroller.b.a(delta);
                }
                if (Math.abs(currY - scrollTextViewScroller.e.getFinalY()) < 1) {
                    currY = scrollTextViewScroller.e.getFinalY();
                    scrollTextViewScroller.e.forceFinished(true);
                }
                if (!scrollTextViewScroller.e.isFinished()) {
                    scrollTextViewScroller.l.sendEmptyMessage(msg.what);
                } else if (msg.what == 0) {
                    scrollTextViewScroller.d();
                } else {
                    scrollTextViewScroller.b();
                }
            }
        }
    }

    public interface c {
        void a(ScrollTextView scrollTextView, int i, int i2);
    }

    public interface d {
        void a(ScrollTextView scrollTextView, int i);
    }

    private class f {
        final /* synthetic */ ScrollTextView a;
        private int b;
        private int c;
        private int d;

        public f(ScrollTextView scrollTextView) {
            this(scrollTextView, 10, 0, 9);
        }

        public f(ScrollTextView scrollTextView, int count, int validStart, int validEnd) {
            this.a = scrollTextView;
            this.b = 0;
            this.c = 0;
            this.d = 0;
            a(count, validStart, validEnd);
        }

        public String a(int index) {
            if (index < 0 || index >= a() || this.a.t == null) {
                return null;
            }
            return this.a.t.c(index);
        }

        public int a() {
            return this.d;
        }

        public f a(int count, int validStart, int validEnd) {
            this.b = validStart;
            this.c = validEnd;
            this.d = count;
            return this;
        }

        public int b() {
            return this.b;
        }

        public int c() {
            return this.c;
        }
    }

    private class g {
        final /* synthetic */ ScrollTextView a;
        private h b;
        private Context c;
        private GestureDetector d;
        private Scroller e;
        private int f;
        private float g;
        private boolean h;
        private SimpleOnGestureListener i = new SimpleOnGestureListener(this) {
            final /* synthetic */ g a;

            {
                this.a = r1;
            }

            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return true;
            }

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                this.a.f = 0;
                int maxY = this.a.a.getYScrollEnd();
                int i = (int) (-velocityY);
                this.a.e.fling(0, this.a.f, 0, i, 0, 0, this.a.a.getYScrollStart(), maxY);
                this.a.a(0);
                return true;
            }
        };
        private final int j = 0;
        private final int k = 1;
        private Handler l = new a(this);

        public g(ScrollTextView scrollTextView, Context context, h listener) {
            this.a = scrollTextView;
            this.d = new GestureDetector(context, this.i);
            this.d.setIsLongpressEnabled(false);
            this.e = new Scroller(context);
            this.b = listener;
            this.c = context;
        }

        public void a(Interpolator interpolator) {
            this.e.forceFinished(true);
            this.e = new Scroller(this.c, interpolator);
        }

        public void a(int distance, int time) {
            this.e.forceFinished(true);
            this.f = 0;
            this.e.startScroll(0, 0, 0, distance, time != 0 ? time : 400);
            a(0);
            e();
        }

        public void a() {
            this.e.forceFinished(true);
        }

        public boolean a(MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    this.g = event.getY();
                    this.e.forceFinished(true);
                    c();
                    break;
                case 2:
                    int distanceY = (int) (event.getY() - this.g);
                    if (distanceY != 0) {
                        e();
                        this.b.a(distanceY);
                        this.g = event.getY();
                        break;
                    }
                    break;
            }
            if (!this.d.onTouchEvent(event) && event.getAction() == 1) {
                d();
            }
            return true;
        }

        private void a(int message) {
            c();
            this.l.sendEmptyMessage(message);
        }

        private void c() {
            this.l.removeMessages(0);
            this.l.removeMessages(1);
        }

        private void d() {
            this.b.c();
            a(1);
        }

        private void e() {
            if (!this.h) {
                this.h = true;
                this.b.a();
            }
        }

        void b() {
            if (this.h) {
                this.b.b();
                this.h = false;
            }
        }
    }

    private class i {
        final /* synthetic */ ScrollTextView a;
        private int b;
        private int c;

        public i(ScrollTextView scrollTextView) {
            this(scrollTextView, 0, 0);
        }

        public i(ScrollTextView scrollTextView, int first, int count) {
            this.a = scrollTextView;
            this.b = first;
            this.c = count;
        }

        public int a() {
            return this.b;
        }

        public int b() {
            return (a() + c()) - 1;
        }

        public int c() {
            return this.c;
        }

        public i a(int first, int count) {
            this.b = first;
            this.c = count;
            return this;
        }
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.c = new Paint(1);
        this.d = 0;
        this.e = 5;
        this.f = 0.0f;
        this.g = 0.0f;
        this.a = false;
        this.y = true;
        this.C = new LinkedList();
        this.D = new LinkedList();
        this.E = new LinkedList();
        this.G = new h(this) {
            final /* synthetic */ ScrollTextView a;

            {
                this.a = r1;
            }

            public void a() {
                this.a.m = true;
                this.a.a();
            }

            public void a(int distance) {
                this.a.e(distance);
                int height = this.a.getHeight();
                if (this.a.n > height) {
                    this.a.n = height;
                    this.a.l.a();
                } else if (this.a.n < (-height)) {
                    this.a.n = -height;
                    this.a.l.a();
                }
            }

            public void b() {
                if (this.a.m) {
                    this.a.b();
                    this.a.m = false;
                }
                this.a.n = 0;
                this.a.invalidate();
            }

            public void c() {
                if (!this.a.a && this.a.getCurrentItem() < this.a.s.b()) {
                    this.a.c(this.a.s.b() - this.a.getCurrentItem(), 0);
                } else if (!this.a.a && this.a.getCurrentItem() > this.a.s.c()) {
                    this.a.c(this.a.s.c() - this.a.getCurrentItem(), 0);
                } else if (Math.abs(this.a.n) > 1) {
                    this.a.l.a(this.a.n, 0);
                }
            }
        };
        this.H = new Canvas();
        this.J = false;
        a(context);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollTextView(Context context) {
        super(context);
        this.c = new Paint(1);
        this.d = 0;
        this.e = 5;
        this.f = 0.0f;
        this.g = 0.0f;
        this.a = false;
        this.y = true;
        this.C = new LinkedList();
        this.D = new LinkedList();
        this.E = new LinkedList();
        this.G = /* anonymous class already generated */;
        this.H = new Canvas();
        this.J = false;
        a(context);
    }

    private void a(Context context) {
        this.l = new g(this, getContext(), this.G);
        this.k = context.getResources().getDimension(com.meizu.common.a.d.mc_picker_selected_number_size);
        this.j = context.getResources().getDimension(com.meizu.common.a.d.mc_picker_normal_number_size);
        this.f = context.getResources().getDimension(com.meizu.common.a.d.mc_picker_scroll_select_item_height);
        this.g = context.getResources().getDimension(com.meizu.common.a.d.mc_picker_scroll_normal_item_height);
        this.h = context.getResources().getColor(com.meizu.common.a.c.mc_picker_selected_color);
        this.i = context.getResources().getColor(com.meizu.common.a.c.mc_picker_unselected_color);
        this.s = new f(this);
        this.v = new Matrix();
        this.u = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, TileMode.CLAMP);
        this.w = new Paint();
        this.w.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        this.w.setShader(this.u);
        this.x = context.getResources().getDimension(com.meizu.common.a.d.mc_picker_fading_height);
        this.q = new i(this);
        this.F = new Paint();
        this.F.setAntiAlias(true);
        this.F.setColor(-3355444);
        this.c.setTextAlign(Align.CENTER);
        e();
    }

    public void a(int count) {
        a(count, this.d, 0, count - 1);
    }

    public void b(int current) {
        a(this.s.a(), current, this.s.b(), this.s.c());
    }

    public void a(int count, int current) {
        a(count, current, 0, count - 1);
    }

    void a(int count, int currentItem, int validStart, int validEnd) {
        c();
        if (count >= 0) {
            setViewAdapter(this.s.a(count, validStart, validEnd));
            int oldCurrentItem = this.d;
            if (currentItem != this.d) {
                this.d = Math.max(currentItem, validStart);
                if (this.d > validEnd || this.d >= count) {
                    this.d = Math.min(validEnd, count);
                }
            }
            if (!(oldCurrentItem == this.d || this.t == null)) {
                this.t.a(this, oldCurrentItem, this.d);
            }
            invalidate();
        }
    }

    public void setData(b dataAdapter, int currentItem, int count, int oneScreenCount) {
        setData(dataAdapter, -1.0f, currentItem, count, oneScreenCount, 0, count - 1, true);
    }

    public void setData(b dataAdapter, float lineOffset, int currentItem, int count, int oneScreenCount, int validStart, int validEnd, boolean cycleEnabled) {
        setIDataAdapter(dataAdapter);
        this.e = oneScreenCount;
        this.a = cycleEnabled;
        if (lineOffset == -1.0f) {
            this.o = getResources().getDimensionPixelSize(com.meizu.common.a.d.mc_picker_offset_y);
        } else {
            this.o = (int) (lineOffset * getContext().getResources().getDisplayMetrics().density);
        }
        if (count < this.e || validEnd + 1 < count || validStart > 0) {
            this.a = false;
        }
        a(count, currentItem, validStart, validEnd);
    }

    public void setInterpolator(Interpolator interpolator) {
        this.l.a(interpolator);
    }

    public int getVisibleItems() {
        return this.e;
    }

    public void setVisibleItems(int count) {
        this.e = count;
    }

    public f getViewAdapter() {
        return this.s;
    }

    private void setViewAdapter(f viewAdapter) {
        this.s = viewAdapter;
        invalidate();
    }

    protected void b(int oldValue, int newValue) {
        for (c listener : this.C) {
            listener.a(this, oldValue, newValue);
        }
    }

    public void a(e listener) {
        this.D.add(listener);
    }

    protected void a() {
        for (e listener : this.D) {
            listener.a(this);
        }
    }

    protected void b() {
        if (this.t != null) {
            this.t.a(this, 0, getCurrentItem());
        }
        for (e listener : this.D) {
            listener.b(this);
        }
    }

    protected void c(int item) {
        setCurrentItem(item, true);
        for (d listener : this.E) {
            listener.a(this, item);
        }
    }

    public int getCurrentItem() {
        return this.d;
    }

    public void setCurrentItem(int index, boolean animated) {
        if (this.s != null && this.s.a() != 0) {
            int itemCount = this.s.a();
            if (index < 0 || index >= itemCount) {
                if (this.a) {
                    while (index < 0) {
                        index += itemCount;
                    }
                    index %= itemCount;
                } else {
                    return;
                }
            }
            if (index == this.d) {
                return;
            }
            if (animated) {
                int itemsToScroll = index - this.d;
                if (this.a) {
                    int scroll = (Math.min(index, this.d) + itemCount) - Math.max(index, this.d);
                    if (scroll < Math.abs(itemsToScroll)) {
                        itemsToScroll = itemsToScroll < 0 ? scroll : -scroll;
                    }
                }
                c(itemsToScroll, 0);
                return;
            }
            this.n = 0;
            int old = this.d;
            this.d = index;
            b(old, this.d);
            invalidate();
        }
    }

    public void setCurrentItem(int index) {
        setCurrentItem(index, false);
    }

    public void setCyclic(boolean isCyclic) {
        this.a = isCyclic;
        invalidate();
    }

    private int getItemHeight() {
        return (int) this.g;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, (int) ((((float) (this.e - 1)) * this.g) + this.f));
    }

    public void setHorizontalOffset(int offset) {
        this.p = offset;
    }

    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = d(getWidth(), getHeight());
        if (this.J) {
            this.H.setBitmap(bitmap);
        }
        if (this.s != null && this.s.a() > 0) {
            d();
            b(this.H);
        }
        if (this.y) {
            a(this.H);
        }
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.F);
    }

    private Bitmap d(int width, int height) {
        if (this.I == null) {
            this.I = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            this.J = true;
        } else if (this.I.getWidth() == width && this.I.getHeight() == height) {
            this.J = false;
        } else {
            this.I.recycle();
            this.I = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            this.J = true;
        }
        this.I.eraseColor(0);
        return this.I;
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (4 == visibility && this.I != null) {
            this.I = null;
        }
    }

    private void a(Canvas canvas) {
        this.v.setScale(1.0f, this.x);
        this.u.setLocalMatrix(this.v);
        canvas.drawRect(0.0f, 0.0f, (float) getWidth(), this.x, this.w);
        this.v.setScale(1.0f, this.x);
        this.v.postRotate(180.0f);
        this.v.postTranslate(0.0f, (float) getHeight());
        this.u.setLocalMatrix(this.v);
        canvas.drawRect(0.0f, ((float) getHeight()) - this.x, (float) getWidth(), (float) getHeight(), this.w);
    }

    private void b(Canvas canvas) {
        int scrolloff;
        float dy = (float) (((-(((this.d - this.r) * getItemHeight()) + ((((int) this.f) - getHeight()) / 2))) + this.n) - getItemHeight());
        canvas.translate((float) this.p, dy);
        if (this.n > 0) {
            scrolloff = this.n;
        } else {
            scrolloff = getItemHeight() + this.n;
        }
        float k = (((float) scrolloff) * 1.0f) / ((float) getItemHeight());
        float yoff = dy;
        for (int i = 0; i < this.q.c(); i++) {
            dy = a(i, k);
            canvas.translate(0.0f, dy);
            yoff += dy;
            canvas.drawText(d(i), (float) (getWidth() / 2), (this.g / 2.0f) - this.B, this.c);
        }
        canvas.translate((float) (-this.p), -yoff);
    }

    private String d(int i) {
        int t = i + this.r;
        String s = this.s.a(t);
        if (t < 0) {
            s = this.a ? this.s.a(t + this.s.a()) : "";
        } else if (t >= this.s.a()) {
            s = this.a ? this.s.a(t - this.s.a()) : "";
        }
        if (s == null) {
            return "";
        }
        return s;
    }

    private float a(int index, float scale) {
        float dy = (float) getItemHeight();
        int gap = (int) (this.f - this.g);
        int selectItemId = this.e / 2;
        float k = 0.0f;
        if (index == selectItemId) {
            dy += (((float) gap) * scale) / 2.0f;
            k = scale;
        } else if (index == selectItemId + 1) {
            dy += (float) (gap / 2);
            k = 1.0f - scale;
        } else if (index == selectItemId + 2) {
            dy += (((float) gap) * (1.0f - scale)) / 2.0f;
        }
        b(index, k);
        return dy;
    }

    private void b(int index, float scale) {
        int selectColor = this.h;
        int normalColor = this.i;
        int selectalpha = Color.alpha(selectColor);
        int slecetred = Color.red(selectColor);
        int slecetgreen = Color.green(selectColor);
        int slecetblue = Color.blue(selectColor);
        int unselectalpha = Color.alpha(normalColor);
        int unslecetred = Color.red(normalColor);
        int unslecetgreen = Color.green(normalColor);
        int unslecetblue = Color.blue(normalColor);
        this.c.setColor(Color.argb(unselectalpha + ((int) (((float) (selectalpha - unselectalpha)) * scale)), unslecetred + ((int) (((float) (slecetred - unslecetred)) * scale)), unslecetgreen + ((int) (((float) (slecetgreen - unslecetgreen)) * scale)), unslecetblue + ((int) (((float) (slecetblue - unslecetblue)) * scale))));
        this.c.setTextSize(this.j + ((this.k - this.j) * scale));
        this.B = this.A + ((this.z - this.A) * scale);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || getViewAdapter() == null) {
            return true;
        }
        switch (event.getAction()) {
            case 1:
                if (!this.m) {
                    int distance = ((int) event.getY()) - (getHeight() / 2);
                    if (distance < 0) {
                        distance = (int) (((float) distance) + ((this.f / 2.0f) - ((float) getItemHeight())));
                    } else {
                        distance = (int) (((float) distance) - ((this.f / 2.0f) - ((float) getItemHeight())));
                    }
                    int items = distance / getItemHeight();
                    if (items != 0 && f(this.d + items)) {
                        c(this.d + items);
                        break;
                    }
                }
                break;
            case 2:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                }
                break;
        }
        return this.l.a(event);
    }

    private int getYScrollEnd() {
        if (this.a) {
            return Integer.MAX_VALUE;
        }
        return this.n + ((int) (((float) (getScrollEndItem() - getCurrentItem())) * this.g));
    }

    private int getYScrollStart() {
        if (this.a) {
            return -2147483647;
        }
        return this.n + ((int) (((float) (getScrollStartItem() - getCurrentItem())) * this.g));
    }

    private int getScrollEndItem() {
        int itemCount = this.s.a();
        if (this.a) {
            return 0;
        }
        int index;
        if (itemCount <= this.e) {
            index = itemCount - 1;
        } else {
            index = (itemCount - 1) - (this.e / 2);
        }
        return index;
    }

    private int getScrollStartItem() {
        int itemCount = this.s.a();
        if (this.a) {
            return 0;
        }
        int index;
        if (itemCount <= this.e) {
            index = 0;
        } else {
            index = this.e / 2;
        }
        return index;
    }

    private void e(int delta) {
        this.n += delta;
        int itemHeight = getItemHeight();
        int count = this.n / itemHeight;
        int pos = this.d - count;
        int itemCount = this.s.a();
        int fixPos = this.n % itemHeight;
        if (Math.abs(fixPos) <= itemHeight / 2) {
            fixPos = 0;
        }
        if (this.a && itemCount > 0) {
            if (fixPos > 0) {
                pos--;
                count++;
            } else if (fixPos < 0) {
                pos++;
                count--;
            }
            while (pos < 0) {
                pos += itemCount;
            }
            pos %= itemCount;
        } else if (pos < getScrollStartItem()) {
            count = this.d - getScrollStartItem();
            pos = getScrollStartItem();
            this.n = 0;
        } else if (pos > getScrollEndItem()) {
            count = this.d - getScrollEndItem();
            pos = getScrollEndItem();
            this.n = 0;
        } else if (pos > getScrollStartItem() && fixPos > 0) {
            pos--;
            count++;
            Log.i(b, "pos > 0 && fixPos > 0");
        } else if (pos < getScrollEndItem() && fixPos < 0) {
            pos++;
            count--;
        } else if (pos == getScrollEndItem()) {
            if (this.n < 0) {
                this.n = 0;
            }
        } else if (pos == getScrollStartItem() && this.n > 0) {
            this.n = 0;
        }
        int offset = this.n;
        if (pos != this.d) {
            setCurrentItem(pos, false);
        } else {
            invalidate();
        }
        this.n = offset - (count * itemHeight);
        if (this.n > getHeight() && getHeight() != 0) {
            this.n = (this.n % getHeight()) + getHeight();
        }
    }

    public void c(int itemsToScroll, int time) {
        this.l.a((getItemHeight() * itemsToScroll) + this.n, time);
    }

    private i getItemsRange() {
        if (getItemHeight() == 0) {
            return null;
        }
        int first = this.d;
        int count = 1;
        while ((count + 2) * getItemHeight() < getHeight()) {
            first--;
            count += 2;
        }
        if (this.n != 0) {
            if (this.n > 0) {
                first--;
            }
            int emptyItems = this.n / getItemHeight();
            first -= emptyItems;
            count = (int) (((double) (count + 1)) + Math.asin((double) emptyItems));
        }
        return this.q.a(first, count);
    }

    private boolean d() {
        this.q = getItemsRange();
        if (this.r <= this.q.a() || this.r > this.q.b()) {
            this.r = this.q.a();
        } else {
            for (int i = this.r - 1; i >= this.q.a(); i--) {
                this.r = i;
            }
        }
        return false;
    }

    private void e() {
        this.c.setTextSize(this.k);
        FontMetricsInt fontMetrics = this.c.getFontMetricsInt();
        this.z = (float) ((fontMetrics.bottom + fontMetrics.top) / 2);
        this.c.setTextSize(this.j);
        fontMetrics = this.c.getFontMetricsInt();
        this.A = (float) ((fontMetrics.bottom + fontMetrics.top) / 2);
    }

    private boolean f(int index) {
        return this.s != null && this.s.a() > 0 && (this.a || (index >= 0 && index < this.s.a()));
    }

    public void c() {
        if (this.l != null) {
            this.l.a();
        }
    }

    public void setTextColor(int selectedColor, int normalColor) {
        if (this.h != selectedColor || this.i != normalColor) {
            this.h = selectedColor;
            this.i = normalColor;
            invalidate();
        }
    }

    public void setTextPreference(float selectedSize, float normalSize, float selectHeight, float normalHeight) {
        if (this.f != selectHeight || this.g != normalHeight || this.k != selectedSize || this.j != normalSize) {
            this.f = selectHeight;
            this.g = normalHeight;
            this.k = selectedSize;
            this.j = normalSize;
            e();
            invalidate();
        }
    }

    public void setTextSize(float selectedSize, float normalSize) {
        setTextPreference(selectedSize, normalSize, this.f, this.g);
    }

    public void setItemHeight(float selectHeight, float normalHeight) {
        setTextPreference(this.k, this.j, selectHeight, normalHeight);
    }

    public void setSelectItemHeight(float selectHeight) {
        setTextPreference(this.k, this.j, selectHeight, this.g);
    }

    public void setNormalItemHeight(float normalHeight) {
        setTextPreference(this.k, this.j, this.f, normalHeight);
    }

    public void setIDataAdapter(b adapter) {
        this.t = adapter;
    }

    public b getIDataAdapter() {
        return this.t;
    }

    public int getItemsCount() {
        return this.s.a();
    }

    public void setTypeface(Typeface font) {
        this.c.setTypeface(font);
        e();
        invalidate();
    }

    public void setIsDrawFading(boolean isDrawFading) {
        this.y = isDrawFading;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ScrollTextView.class.getName());
    }
}
