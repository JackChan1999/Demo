package com.meizu.cloud.base.b;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.o;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.cloud.app.widget.FlowLayout;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import java.util.ArrayList;
import java.util.List;

public abstract class i<T> extends f<T> {
    private boolean a;
    protected Context b;
    protected int c = 0;
    protected a d = null;
    protected FrameLayout e;
    protected LinearLayout f;
    protected int g = 1;
    public int h;
    private List<b> i;

    public abstract class a<D> {
        private int a;
        protected List<D> b;
        protected List<a> c;
        protected ViewGroup d;
        final /* synthetic */ i e;

        public class a {
            Fragment a;
            String b;
            String c;
            boolean d = false;
            final /* synthetic */ a e;

            public a(a aVar, Fragment fragment, String name, String tag) {
                this.e = aVar;
                this.a = fragment;
                this.b = name;
                this.c = tag;
            }
        }

        public abstract a a(int i);

        public a(i iVar) {
            this.e = iVar;
        }

        public void a(List<D> dataList) {
            this.b = dataList;
            c();
        }

        public D b(int position) {
            if (this.b == null || position < 0 || position >= this.b.size()) {
                return null;
            }
            return this.b.get(position);
        }

        public List<a> a() {
            return this.c;
        }

        public int b() {
            return this.b == null ? 0 : this.b.size();
        }

        private void c() {
            if (b() > 0) {
                this.c = new ArrayList();
                for (int i = 0; i < b(); i++) {
                    this.c.add(a(i));
                }
                b(this.c);
            }
        }

        private void b(List<a> fragmentList) {
            if (fragmentList != null && fragmentList.size() > 0) {
                int color;
                if (this.d != null && this.d.getChildCount() > 0) {
                    this.d.removeAllViews();
                }
                if (this.e.g == 1) {
                    LinearLayout linearLayout = new LinearLayout(this.e.getActivity());
                    linearLayout.setClickable(true);
                    linearLayout.setClipChildren(false);
                    linearLayout.setClipToPadding(false);
                    linearLayout.setOrientation(0);
                    this.d = linearLayout;
                    color = this.e.getResources().getColor(c.theme_color);
                } else if (this.e.g == 2) {
                    this.d = new FlowLayout(this.e.getActivity());
                    color = this.e.getResources().getColor(c.desc_color);
                } else {
                    return;
                }
                int unSelectedColor = color;
                float density = this.e.getResources().getDisplayMetrics().density;
                for (int i = 0; i < b(); i++) {
                    String name = ((a) fragmentList.get(i)).b;
                    String tag = ((a) fragmentList.get(i)).c;
                    if (!(name == null || tag == null)) {
                        TextView btn = new TextView(this.e.b);
                        btn.setText(name);
                        btn.setGravity(17);
                        btn.setTextSize(((float) this.e.getResources().getDimensionPixelSize(d.multi_tab_text_size)) / density);
                        btn.setTextColor(this.e.getResources().getColor(c.title_color));
                        btn.setSingleLine();
                        btn.setTag(tag);
                        final List<a> list = fragmentList;
                        final int i2 = unSelectedColor;
                        btn.setOnClickListener(new OnClickListener(this) {
                            final /* synthetic */ a c;

                            public void onClick(View v) {
                                TextView btn = (TextView) v;
                                for (int i = 0; i < this.c.b(); i++) {
                                    if (btn.getTag().equals(((a) list.get(i)).c)) {
                                        this.c.a(list, i, i2);
                                        return;
                                    }
                                }
                            }
                        });
                        if (this.e.g == 1) {
                            a(i, btn);
                        } else if (this.e.g == 2) {
                            a(btn);
                        }
                    }
                }
                int topBarsHeight = com.meizu.cloud.app.utils.d.f(this.e.b);
                int extraPadding = this.e.getArguments().getInt("extra_padding_top", 0);
                int margin = this.e.getResources().getDimensionPixelSize(d.multi_tab_margin);
                this.e.h = topBarsHeight + extraPadding;
                int btnHeight = this.e.getResources().getDimensionPixelSize(d.multi_tab_button_height);
                LayoutParams layoutParams = new LayoutParams(-1, -2);
                if (this.b.size() > 1) {
                    if (this.e.g == 1) {
                        layoutParams.setMargins(margin, this.e.h + margin, margin, margin);
                    } else if (this.d instanceof FlowLayout) {
                        layoutParams.setMargins(margin, this.e.h, margin, this.e.getResources().getDimensionPixelSize(d.multi_tab_flow_line_margin));
                    }
                    this.d.setMinimumHeight(btnHeight);
                    this.d.setVisibility(0);
                    this.a = (margin * 2) + btnHeight;
                    if (this.e.f.getChildCount() > 1) {
                        this.e.f.removeViewAt(0);
                    }
                    this.e.f.addView(this.d, 0, layoutParams);
                } else {
                    this.d.setVisibility(8);
                    this.a = extraPadding;
                }
                a(fragmentList, 0, unSelectedColor);
            }
        }

        private void a(TextView btn) {
            int innerSpace = this.e.getResources().getDimensionPixelSize(d.multi_tab_button_inner_margin);
            btn.setPadding(innerSpace, 0, innerSpace, 0);
            btn.setBackgroundResource(e.multi_btn_flow_selector);
            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(-2, this.e.getResources().getDimensionPixelSize(d.multi_tab_button_height));
            layoutParams.setMargins(this.e.getResources().getDimensionPixelSize(d.multi_tab_flow_item_margin), this.e.getResources().getDimensionPixelSize(d.multi_tab_flow_line_margin), 0, 0);
            this.d.addView(btn, layoutParams);
        }

        private void a(int index, TextView btn) {
            this.d.setBackgroundResource(e.multi_tab_bg_shape);
            if (index == 0) {
                btn.setBackgroundResource(e.multi_btn_left_selector);
            } else if (index == b() - 1) {
                btn.setBackgroundResource(e.multi_btn_right_selector);
            } else {
                btn.setBackgroundResource(e.multi_btn_middle_selector);
            }
            this.d.addView(btn, new LayoutParams(1, this.e.getResources().getDimensionPixelSize(d.multi_tab_button_height), 1.0f));
            if (index != b() - 1) {
                ImageView dividerView = new ImageView(this.e.b);
                dividerView.setBackgroundColor(this.e.getResources().getColor(c.theme_color));
                this.d.addView(dividerView, new LayoutParams(this.e.getResources().getDimensionPixelSize(d.multi_tab_stroke), this.e.getResources().getDimensionPixelSize(d.multi_tab_button_height)));
            }
        }

        private void a(List<a> fragmentList, int position, int unSelectedColor) {
            if (fragmentList != null && position >= 0 && position < fragmentList.size()) {
                int i;
                for (i = 0; i < this.d.getChildCount(); i++) {
                    if (this.d.getChildAt(i) instanceof TextView) {
                        TextView btn = (TextView) this.d.getChildAt(i);
                        if (btn.getTag().equals(((a) fragmentList.get(position)).c)) {
                            btn.setTextColor(this.e.getResources().getColor(c.white));
                            btn.setSelected(true);
                        } else {
                            btn.setTextColor(unSelectedColor);
                            btn.setSelected(false);
                        }
                    }
                }
                o transaction = this.e.getChildFragmentManager().a();
                Object fragment = ((a) fragmentList.get(position)).a;
                if (fragment != null) {
                    if (!((a) fragmentList.get(position)).d) {
                        fragment.getArguments().putInt("extra_padding_top", this.a);
                        Fragment oldFragment = this.e.getChildFragmentManager().a(((a) fragmentList.get(position)).c);
                        if (oldFragment != null) {
                            transaction.a(oldFragment);
                        }
                        transaction.a(f.fragment_container, fragment, ((a) fragmentList.get(position)).c);
                        ((a) fragmentList.get(position)).d = true;
                        if (fragment instanceof b) {
                            this.e.a((b) fragment);
                        }
                    } else if (fragment.isDetached()) {
                        transaction.e(fragment);
                    }
                }
                for (i = 0; i < b(); i++) {
                    Fragment item = ((a) fragmentList.get(i)).a;
                    if (item != null && item.isAdded()) {
                        if (i == position) {
                            transaction.c(item);
                        } else {
                            transaction.b(item);
                        }
                    }
                }
                transaction.a(null);
                transaction.c();
                if (this.e.i != null && this.e.a) {
                    for (b listener : this.e.i) {
                        if (listener == ((a) fragmentList.get(this.e.c)).a) {
                            listener.a(((a) fragmentList.get(this.e.c)).b, false);
                        }
                        if (listener == fragment) {
                            listener.a(((a) fragmentList.get(position)).b, true);
                        }
                    }
                }
                this.e.c = position;
            }
        }
    }

    public interface b {
        void a(String str, boolean z);
    }

    protected abstract a b();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.b = getRootFragment().getActivity().getApplicationContext();
    }

    public void onDestroy() {
        super.onDestroy();
        e();
    }

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(g.base_multi_fragment, container, false);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        this.e = (FrameLayout) rootView.findViewById(f.fragment_container);
        this.f = (LinearLayout) rootView.findViewById(f.tab_layout_container);
        this.f.setBackgroundColor(getResources().getColor(c.activity_background_color));
        this.d = b();
    }

    public void a(b listener) {
        if (this.i == null) {
            this.i = new ArrayList();
        }
        this.i.add(listener);
    }

    public void e() {
        if (this.i != null && this.i.size() > 0) {
            this.i.clear();
        }
    }

    protected void onRealPageStart() {
        this.a = true;
        if (this.i != null && this.d != null && this.d.a() != null) {
            a holder = (a) this.d.a().get(this.c);
            if (holder != null) {
                for (b listener : this.i) {
                    if (listener == holder.a) {
                        listener.a(holder.b, true);
                        return;
                    }
                }
            }
        }
    }

    protected void onRealPageStop() {
        this.a = false;
        if (this.i != null && this.d != null && this.d.a() != null) {
            a holder = (a) this.d.a().get(this.c);
            if (holder != null) {
                for (b listener : this.i) {
                    if (listener == holder.a) {
                        listener.a(holder.b, false);
                        return;
                    }
                }
            }
        }
    }
}
