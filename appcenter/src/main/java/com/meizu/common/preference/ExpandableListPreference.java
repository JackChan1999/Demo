package com.meizu.common.preference;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Handler;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.meizu.common.a.d;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import com.meizu.common.a.j;
import java.lang.reflect.Field;

@SuppressLint({"NewApi"})
public class ExpandableListPreference extends Preference {
    public boolean a;
    private ListView b;
    private LinearLayout c;
    private a d;
    private int e;
    private CharSequence[] f;
    private CharSequence[] g;
    private String h;
    private b i;
    private boolean j;
    private TextView k;
    private ImageView l;
    private Handler m;
    private boolean n;
    private Runnable o;

    @SuppressLint({"NewApi"})
    public class a {
        final /* synthetic */ ExpandableListPreference a;
        private View b;
        private View c;
        private View d;
        private int e;
        private long f;
        private LayoutParams g;
        private int h;
        private int i;
        private int j;
        private float k;
        private float l;
        private int m = 0;
        private boolean n = false;

        private class a implements Interpolator {
            final /* synthetic */ a a;

            private a(a aVar) {
                this.a = aVar;
            }

            public float getInterpolation(float input) {
                return (float) (1.0d - Math.pow((double) (1.0f - input), 3.0d));
            }
        }

        public a(ExpandableListPreference expandableListPreference) {
            this.a = expandableListPreference;
        }

        public void a(View expandItemView, View ronateView, View summaryView, int type, long millisTime) {
            float f;
            float f2 = 1.0f;
            this.b = expandItemView;
            this.c = summaryView;
            this.d = ronateView;
            this.e = type;
            this.f = millisTime;
            this.g = (LayoutParams) this.b.getLayoutParams();
            this.h = this.g.height;
            if (this.e == 0) {
                this.g.bottomMargin = -this.h;
            } else {
                this.g.bottomMargin = 0;
            }
            this.b.setVisibility(0);
            View view = this.b;
            if (this.e == 0) {
                f = 0.0f;
            } else {
                f = 1.0f;
            }
            view.setAlpha(f);
            this.c.setVisibility(0);
            View view2 = this.c;
            if (this.e != 0) {
                f2 = 0.0f;
            }
            view2.setAlpha(f2);
        }

        public void a() {
            if (this.e == 0) {
                this.i = (-this.h) + this.m;
                this.j = 0;
                this.k = 0.0f;
                this.l = 1.0f;
            } else if (this.e == 1) {
                this.i = 0;
                this.j = (-this.h) + this.m;
                this.k = 1.0f;
                this.l = 0.0f;
            }
            AnimatorSet aniSet = new AnimatorSet();
            ValueAnimator alphaAnimator = ValueAnimator.ofFloat(new float[]{this.l, this.k});
            alphaAnimator.setDuration((long) ((int) (((double) this.f) * 0.4d)));
            if (this.e == 1) {
                alphaAnimator.setStartDelay((long) ((int) (((double) this.f) * 0.6d)));
            }
            alphaAnimator.setInterpolator(c());
            alphaAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onAnimationUpdate(ValueAnimator animator) {
                    this.a.c.setAlpha(((Float) animator.getAnimatedValue()).floatValue());
                }
            });
            alphaAnimator.addListener(new AnimatorListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onAnimationStart(Animator arg0) {
                }

                public void onAnimationRepeat(Animator arg0) {
                }

                public void onAnimationEnd(Animator arg0) {
                    if (this.a.e == 1) {
                        this.a.c.setVisibility(0);
                    } else {
                        this.a.c.setVisibility(4);
                    }
                }

                public void onAnimationCancel(Animator arg0) {
                }
            });
            ValueAnimator listAlphaAnimator = ValueAnimator.ofFloat(new float[]{this.k, this.l});
            listAlphaAnimator.setDuration((long) ((int) (((double) this.f) * 0.5d)));
            if (this.e == 0) {
                listAlphaAnimator.setStartDelay((long) ((int) (((double) this.f) * 0.4d)));
            }
            listAlphaAnimator.setInterpolator(c());
            listAlphaAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onAnimationUpdate(ValueAnimator animator) {
                    this.a.b.setAlpha(((Float) animator.getAnimatedValue()).floatValue());
                }
            });
            final ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{this.i, this.j});
            valueAnimator.setInterpolator(c());
            valueAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onAnimationUpdate(ValueAnimator animator) {
                    float scale = ((float) Math.abs(((Integer) animator.getAnimatedValue()).intValue() - this.a.i)) / ((float) Math.abs(this.a.i - this.a.j));
                    if (this.a.e == 0) {
                        this.a.d.setRotation(180.0f * scale);
                    } else {
                        this.a.d.setRotation((1.0f - scale) * 180.0f);
                    }
                    this.a.g.bottomMargin = ((Integer) animator.getAnimatedValue()).intValue();
                    if (!this.a.b.isInLayout()) {
                        this.a.b.requestLayout();
                    }
                }
            });
            valueAnimator.addListener(new AnimatorListener(this) {
                final /* synthetic */ a b;

                public void onAnimationStart(Animator animation) {
                    this.b.b.setVisibility(0);
                    this.b.n = true;
                }

                public void onAnimationRepeat(Animator animation) {
                }

                public void onAnimationEnd(Animator animation) {
                    animation.removeAllListeners();
                    valueAnimator.removeAllUpdateListeners();
                    valueAnimator.removeAllListeners();
                    if (this.b.e == 1) {
                        this.b.b.setVisibility(4);
                    }
                    this.b.n = false;
                }

                public void onAnimationCancel(Animator animation) {
                }
            });
            valueAnimator.setDuration(this.f);
            aniSet.playTogether(new Animator[]{valueAnimator, listAlphaAnimator, alphaAnimator});
            aniSet.start();
        }

        public void a(int marginTop) {
            this.m = marginTop;
        }

        private Interpolator c() {
            if (VERSION.SDK_INT >= 21) {
                return new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
            }
            return new a();
        }

        public boolean b() {
            return this.n;
        }
    }

    private class b extends BaseAdapter {
        final /* synthetic */ ExpandableListPreference a;
        private CharSequence[] b;
        private Context c;
        private int d = -1;
        private ListView e;

        private class a {
            public CheckedTextView a;
            final /* synthetic */ b b;

            private a(b bVar) {
                this.b = bVar;
            }
        }

        public b(ExpandableListPreference expandableListPreference, Context context, CharSequence[] data) {
            this.a = expandableListPreference;
            this.c = context;
            this.b = data;
        }

        public int getCount() {
            return this.b.length;
        }

        public Object getItem(int position) {
            return this.b[position];
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            a holder;
            if (convertView == null) {
                holder = new a();
                convertView = ((LayoutInflater) this.c.getSystemService("layout_inflater")).inflate(g.mc_expandable_preference_list_item, null);
                holder.a = (CheckedTextView) convertView;
                convertView.setLayoutParams(new AbsListView.LayoutParams(-1, this.c.getResources().getDimensionPixelOffset(d.mc_expandable_preference_list_item_height)));
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            holder.a.setText(this.b[position]);
            if (position == this.d) {
                this.e.setItemChecked(position, true);
            }
            return convertView;
        }

        public void a(int position) {
            this.d = position;
        }

        public void a(ListView listView) {
            this.e = listView;
        }
    }

    public ExpandableListPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableListPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = false;
        this.e = 400;
        this.m = new Handler();
        this.n = false;
        this.o = new Runnable(this) {
            final /* synthetic */ ExpandableListPreference a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.a();
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, j.ExpandableListPreference, 0, 0);
        this.f = a.getTextArray(j.ExpandableListPreference_mcEntries);
        this.g = a.getTextArray(j.ExpandableListPreference_mcEntryValues);
        a.recycle();
        setLayoutResource(g.mc_expandable_preference_layout);
        this.d = new a(this);
        this.d.a(-context.getResources().getDimensionPixelSize(d.mc_expandable_preference_inner_list_margin));
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        this.b = (ListView) view.findViewById(f.expand_listview);
        this.i = new b(this, getContext(), this.f);
        this.k = (TextView) view.findViewById(16908304);
        this.l = (ImageView) view.findViewById(f.pull_icon);
        int selectedPosition = 0;
        if (this.f != null && this.f.length > 0) {
            if (c() != -1) {
                selectedPosition = c();
            }
            setSummary(this.f[selectedPosition]);
            this.k.setText(this.f[selectedPosition]);
            this.i.a(selectedPosition);
            this.i.notifyDataSetChanged();
            if (this.a) {
                this.k.setVisibility(4);
            } else {
                this.k.setVisibility(0);
            }
            this.b.setAdapter(this.i);
            this.i.a(this.b);
            this.b.setChoiceMode(1);
            this.b.setOnItemClickListener(new OnItemClickListener(this) {
                final /* synthetic */ ExpandableListPreference a;

                {
                    this.a = r1;
                }

                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                    if (!this.a.b() && !this.a.n) {
                        this.a.i.a(position);
                        this.a.i.notifyDataSetChanged();
                        if (this.a.g != null) {
                            String value = this.a.g[position].toString();
                            this.a.k.setText(this.a.f[position]);
                            this.a.a(this.a.f[position]);
                            if (this.a.callChangeListener(value)) {
                                this.a.a(value);
                            }
                        }
                        this.a.m.postDelayed(this.a.o, 200);
                    }
                }
            });
        }
        this.c = (LinearLayout) view.findViewById(f.container);
        this.c.measure(0, 0);
        LayoutParams params = (LayoutParams) this.c.getLayoutParams();
        if (this.f != null && this.f.length > 0) {
            params.height = this.c.getMeasuredHeight() * this.f.length;
        }
        if (this.a) {
            this.c.setVisibility(0);
            this.c.setFocusable(false);
            return;
        }
        this.c.setVisibility(8);
    }

    protected void onClick() {
        if (!b() && !this.n) {
            if (this.a) {
                this.d.a(this.c, this.l, this.k, 1, (long) this.e);
                this.d.a();
                this.a = false;
                return;
            }
            this.d.a(this.c, this.l, this.k, 0, (long) this.e);
            this.d.a();
            this.a = true;
        }
    }

    public void a() {
        if (this.a) {
            this.d.a(this.c, this.l, this.k, 1, (long) this.e);
            this.d.a();
            this.a = false;
        }
    }

    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        a(restorePersistedValue ? getPersistedString(this.h) : (String) defaultValue);
    }

    public void a(String value) {
        boolean changed = !TextUtils.equals(this.h, value);
        if (changed || !this.j) {
            this.h = value;
            this.j = true;
            persistString(value);
            if (changed) {
                notifyChanged();
            }
        }
    }

    public int b(String value) {
        if (!(value == null || this.g == null)) {
            for (int i = this.g.length - 1; i >= 0; i--) {
                if (this.g[i].equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    private int c() {
        return b(this.h);
    }

    public boolean b() {
        if (this.d != null) {
            return this.d.b();
        }
        return false;
    }

    private void a(CharSequence summary) {
        try {
            Field field = Preference.class.getDeclaredField("mSummary");
            field.setAccessible(true);
            field.set(this, summary);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        }
    }
}
