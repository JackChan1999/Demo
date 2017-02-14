package com.meizu.common.widget;

import android.content.Context;
import android.util.Log;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public class h {
    private int a;
    private final a b;
    private final a c;
    private Interpolator d;
    private final boolean e;

    static class a {
        private static float p = ((float) (Math.log(0.78d) / Math.log(0.9d)));
        private static final float[] q = new float[101];
        private static final float[] r = new float[101];
        private int A = 0;
        private int B = 50;
        private long C = 0;
        private boolean D = false;
        private int a;
        private int b;
        private int c;
        private int d;
        private float e;
        private float f;
        private long g;
        private int h;
        private int i;
        private int j;
        private boolean k = true;
        private int l;
        private float m = ViewConfiguration.getScrollFriction();
        private int n = 0;
        private float o;
        private float s = 0.0f;
        private float t = 0.0f;
        private float u = 0.0f;
        private int v = 0;
        private int w = 0;
        private int x = 0;
        private boolean y = false;
        private int z = 1;

        static {
            float x_min = 0.0f;
            float y_min = 0.0f;
            for (int i = 0; i < 100; i++) {
                float x;
                float coef;
                float y;
                float alpha = ((float) i) / 100.0f;
                float x_max = 1.0f;
                while (true) {
                    x = x_min + ((x_max - x_min) / 2.0f);
                    coef = (3.0f * x) * (1.0f - x);
                    float tx = ((((1.0f - x) * 0.175f) + (0.35000002f * x)) * coef) + ((x * x) * x);
                    if (((double) Math.abs(tx - alpha)) < 1.0E-5d) {
                        break;
                    } else if (tx > alpha) {
                        x_max = x;
                    } else {
                        x_min = x;
                    }
                }
                q[i] = ((((1.0f - x) * 0.5f) + x) * coef) + ((x * x) * x);
                float y_max = 1.0f;
                while (true) {
                    y = y_min + ((y_max - y_min) / 2.0f);
                    coef = (3.0f * y) * (1.0f - y);
                    float dy = ((((1.0f - y) * 0.5f) + y) * coef) + ((y * y) * y);
                    if (((double) Math.abs(dy - alpha)) < 1.0E-5d) {
                        break;
                    } else if (dy > alpha) {
                        y_max = y;
                    } else {
                        y_min = y;
                    }
                }
                r[i] = ((((1.0f - y) * 0.175f) + (0.35000002f * y)) * coef) + ((y * y) * y);
            }
            float[] fArr = q;
            r[100] = 1.0f;
            fArr[100] = 1.0f;
        }

        a(Context context) {
            this.o = (386.0878f * (context.getResources().getDisplayMetrics().density * 160.0f)) * 0.84f;
            this.z = 0;
            this.C = 0;
            this.y = false;
        }

        void a(float q) {
            this.b = this.a + Math.round(((float) (this.c - this.a)) * q);
        }

        private static float a(int velocity) {
            return velocity > 0 ? -2000.0f : 2000.0f;
        }

        private void c(int start, int oldFinal, int newFinal) {
            float x = Math.abs(((float) (newFinal - start)) / ((float) (oldFinal - start)));
            int index = (int) (100.0f * x);
            if (index < 100) {
                float x_inf = ((float) index) / 100.0f;
                float x_sup = ((float) (index + 1)) / 100.0f;
                float t_inf = r[index];
                this.h = (int) (((float) this.h) * (t_inf + (((x - x_inf) / (x_sup - x_inf)) * (r[index + 1] - t_inf))));
            }
        }

        void a(int start, int distance, int duration) {
            this.k = false;
            this.a = start;
            this.c = start + distance;
            this.g = AnimationUtils.currentAnimationTimeMillis();
            this.h = duration;
            this.f = 0.0f;
            this.d = 0;
        }

        void a() {
            this.b = this.c;
            this.k = true;
        }

        boolean b(int start, int min, int max) {
            this.k = true;
            this.c = start;
            this.a = start;
            this.d = 0;
            this.g = AnimationUtils.currentAnimationTimeMillis();
            this.h = 0;
            if (start < min) {
                d(start, min, 0);
            } else if (start > max) {
                d(start, max, 0);
            }
            if (this.k) {
                return false;
            }
            return true;
        }

        private void d(int start, int end, int velocity) {
            this.k = false;
            this.n = 1;
            this.a = start;
            this.c = end;
            int delta = start - end;
            this.f = a(delta);
            this.d = -delta;
            this.l = Math.abs(delta);
            if (this.D) {
                this.h = 618;
            } else {
                this.h = (int) (1000.0d * Math.sqrt((-2.0d * ((double) delta)) / ((double) this.f)));
            }
        }

        void a(int start, int velocity, int min, int max, int over) {
            this.l = over;
            this.k = false;
            this.d = velocity;
            this.e = (float) velocity;
            this.i = 0;
            this.h = 0;
            this.g = AnimationUtils.currentAnimationTimeMillis();
            this.a = start;
            this.b = start;
            if (start > max || start < min) {
                a(start, min, max, velocity);
                return;
            }
            this.n = 0;
            double totalDistance = 0.0d;
            if (velocity != 0) {
                int d = d(velocity);
                this.i = d;
                this.h = d;
                totalDistance = c(velocity);
            }
            if (this.D && this.z >= 4 && this.C > 40) {
                this.y = false;
            }
            this.z++;
            this.A = 0;
            if (this.y) {
                if (this.C < 20) {
                    this.B = 50;
                } else if (this.C < 40) {
                    this.B = 25;
                }
                int absVelocity = Math.abs(this.d);
                this.v = 0;
                this.s = 0.0f;
                this.u = 0.97f;
                int i = 0;
                this.t = (1.0f * ((float) this.d)) / ((float) this.B);
                while (((int) (((double) this.t) * Math.pow((double) this.u, (double) i))) != 0) {
                    i++;
                }
                this.v = i;
                totalDistance = (((double) this.t) * (1.0d - Math.pow((double) this.u, (double) i))) / ((double) (1.0f - this.u));
                if (absVelocity > 2000) {
                    this.h = 5000;
                } else if (absVelocity < 200) {
                    this.h = 0;
                } else {
                    this.h = 3000;
                }
                this.c = this.a + ((int) totalDistance);
                return;
            }
            this.j = (int) (((double) Math.signum((float) velocity)) * totalDistance);
            this.c = this.j + start;
            if (this.c < min) {
                c(this.a, this.c, min);
                this.c = min;
            }
            if (this.c > max) {
                c(this.a, this.c, max);
                this.c = max;
            }
        }

        private double b(int velocity) {
            return Math.log((double) ((0.35f * ((float) Math.abs(velocity))) / (this.m * this.o)));
        }

        private double c(int velocity) {
            return ((double) (this.m * this.o)) * Math.exp((((double) p) / (((double) p) - 1.0d)) * b(velocity));
        }

        private int d(int velocity) {
            return (int) (1000.0d * Math.exp(b(velocity) / (((double) p) - 1.0d)));
        }

        private void e(int start, int end, int velocity) {
            float totalDuration = (float) Math.sqrt((2.0d * ((double) (((((float) (velocity * velocity)) / 2.0f) / Math.abs(this.f)) + ((float) Math.abs(end - start))))) / ((double) Math.abs(this.f)));
            this.g -= (long) ((int) (1000.0f * (totalDuration - (((float) (-velocity)) / this.f))));
            this.a = end;
            this.d = (int) ((-this.f) * totalDuration);
        }

        private void f(int start, int end, int velocity) {
            int i;
            if (velocity == 0) {
                i = start - end;
            } else {
                i = velocity;
            }
            this.f = a(i);
            e(start, end, velocity);
            d();
        }

        private void a(int start, int min, int max, int velocity) {
            if (start <= min || start >= max) {
                int edge;
                boolean positive = start > max;
                if (positive) {
                    edge = max;
                } else {
                    edge = min;
                }
                int overDistance = start - edge;
                if (overDistance * velocity >= 0) {
                    f(start, edge, velocity);
                    return;
                } else if (c(velocity) > ((double) Math.abs(overDistance))) {
                    a(start, velocity, positive ? min : start, positive ? start : max, this.l);
                    return;
                } else {
                    d(start, edge, velocity);
                    return;
                }
            }
            Log.e("OverScroller", "startAfterEdge called from a valid position");
            this.k = true;
        }

        private void d() {
            float distance = ((float) (this.d * this.d)) / (Math.abs(this.f) * 2.0f);
            if (this.D) {
                this.u = 0.5f;
                this.s = 0.0f;
                this.k = false;
                this.h = Integer.MAX_VALUE;
                int i = 0;
                this.t = this.e / 150.0f;
                while (((int) (((double) this.t) * Math.pow((double) this.u, (double) i))) != 0) {
                    i++;
                }
                this.v = i;
                distance = (float) ((((double) this.t) * (1.0d - Math.pow((double) this.u, (double) i))) / ((double) (1.0f - this.u)));
                this.c = (int) (((float) this.a) + distance);
            } else {
                float sign = Math.signum((float) this.d);
                if (distance > ((float) this.l)) {
                    this.f = (((-sign) * ((float) this.d)) * ((float) this.d)) / (((float) this.l) * 2.0f);
                    distance = (float) this.l;
                }
                this.c = ((int) (this.d > 0 ? distance : -distance)) + this.a;
                this.h = -((int) ((1000.0f * ((float) this.d)) / this.f));
            }
            this.l = (int) distance;
            this.n = 2;
        }

        boolean b() {
            switch (this.n) {
                case 0:
                    if (this.h < this.i) {
                        this.a = this.c;
                        this.d = (int) this.e;
                        this.f = a(this.d);
                        this.g += (long) this.h;
                        d();
                        break;
                    }
                    return false;
                case 1:
                    return false;
                case 2:
                    if (this.D) {
                        this.g = AnimationUtils.currentAnimationTimeMillis();
                    } else {
                        this.g += (long) this.h;
                    }
                    d(this.c, this.a, 0);
                    break;
            }
            c();
            return true;
        }

        boolean c() {
            long currentTime = AnimationUtils.currentAnimationTimeMillis() - this.g;
            if (currentTime > ((long) this.h)) {
                if (this.D) {
                    if (this.y) {
                        if (this.v != 0) {
                            this.c = this.b;
                        }
                    } else if (this.h < this.i && this.c != this.b) {
                        this.b = this.c;
                        return true;
                    }
                    this.k = true;
                }
                return false;
            }
            double distance = 0.0d;
            float t;
            switch (this.n) {
                case 0:
                    if (!this.y) {
                        t = ((float) currentTime) / ((float) this.i);
                        int index = (int) (100.0f * t);
                        float distanceCoef = 1.0f;
                        float velocityCoef = 0.0f;
                        if (index < 100) {
                            float t_inf = ((float) index) / 100.0f;
                            float t_sup = ((float) (index + 1)) / 100.0f;
                            float d_inf = q[index];
                            velocityCoef = (q[index + 1] - d_inf) / (t_sup - t_inf);
                            distanceCoef = d_inf + ((t - t_inf) * velocityCoef);
                        }
                        distance = (double) (((float) this.j) * distanceCoef);
                        this.e = ((((float) this.j) * velocityCoef) / ((float) this.i)) * 1000.0f;
                        break;
                    }
                    this.A++;
                    if (this.D && this.A == 5) {
                        this.C = (this.C + (currentTime / ((long) this.A))) / 2;
                    }
                    this.e *= this.u;
                    distance = (double) (this.s + this.t);
                    this.t *= this.u;
                    this.s = (float) distance;
                    break;
                case 1:
                    t = ((float) currentTime) / ((float) this.h);
                    float t2 = t * t;
                    float sign = Math.signum((float) this.d);
                    if (!this.D) {
                        distance = (double) ((((float) this.l) * sign) * ((3.0f * t2) - ((2.0f * t) * t2)));
                        this.e = ((((float) this.l) * sign) * 6.0f) * ((-t) + t2);
                        break;
                    }
                    distance = (double) (((float) a(currentTime, this.a, this.l, (long) this.h)) * sign);
                    break;
                case 2:
                    if (!this.D) {
                        t = ((float) currentTime) / 1000.0f;
                        this.e = ((float) this.d) + (this.f * t);
                        distance = (double) ((((float) this.d) * t) + (((this.f * t) * t) / 2.0f));
                        break;
                    }
                    this.e *= this.u;
                    distance = (double) (this.s + this.t);
                    this.t *= this.u;
                    this.s = (float) distance;
                    break;
            }
            if (!this.D) {
                this.b = this.a + ((int) Math.round(distance));
            } else if (this.n != 0 || this.y) {
                this.b = this.a + ((int) distance);
                if (this.b == this.c) {
                    return false;
                }
            } else {
                this.b = this.a + ((int) Math.round(distance));
                return true;
            }
            return true;
        }

        private int a(long timePassed, int start, int distance, long duration) {
            return (int) Math.round(((double) distance) * (Math.pow((double) (((((float) timePassed) * 1.0f) / ((float) duration)) - 1.0f), 5.0d) + 1.0d));
        }

        public void a(boolean enable, boolean smoothFling) {
            this.D = enable;
            this.y = smoothFling;
        }
    }

    public h(Context context) {
        this(context, null);
    }

    public h(Context context, Interpolator interpolator) {
        this(context, interpolator, true);
    }

    public h(Context context, Interpolator interpolator, boolean flywheel) {
        this.d = interpolator;
        this.e = flywheel;
        this.b = new a(context);
        this.c = new a(context);
    }

    void a(Interpolator interpolator) {
        this.d = interpolator;
    }

    public final boolean a() {
        return this.b.k && this.c.k;
    }

    public final void a(boolean finished) {
        this.b.k = this.c.k = finished;
    }

    public final int b() {
        return this.b.b;
    }

    public boolean c() {
        if (a()) {
            return false;
        }
        switch (this.a) {
            case 0:
                long elapsedTime = AnimationUtils.currentAnimationTimeMillis() - this.b.g;
                int duration = this.b.h;
                if (elapsedTime >= ((long) duration)) {
                    d();
                    break;
                }
                float q = ((float) elapsedTime) / ((float) duration);
                if (this.d == null) {
                    q = l.a(q);
                } else {
                    q = this.d.getInterpolation(q);
                }
                this.b.a(q);
                this.c.a(q);
                break;
            case 1:
                if (!(this.b.k || this.b.c() || this.b.b())) {
                    this.b.a();
                }
                if (!(this.c.k || this.c.c() || this.c.b())) {
                    this.c.a();
                    break;
                }
        }
        return true;
    }

    public void a(int startX, int startY, int dx, int dy, int duration) {
        this.a = 0;
        this.b.a(startX, dx, duration);
        this.c.a(startY, dy, duration);
    }

    public boolean a(int startX, int startY, int minX, int maxX, int minY, int maxY) {
        this.a = 1;
        boolean spingbackX = this.b.b(startX, minX, maxX);
        boolean spingbackY = this.c.b(startY, minY, maxY);
        if (spingbackX || spingbackY) {
            return true;
        }
        return false;
    }

    public void a(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        a(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0);
    }

    public void a(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        if (this.e && !a()) {
            float oldVelocityX = this.b.e;
            float oldVelocityY = this.c.e;
            if (Math.signum((float) velocityX) == Math.signum(oldVelocityX) && Math.signum((float) velocityY) == Math.signum(oldVelocityY)) {
                velocityX = (int) (((float) velocityX) + oldVelocityX);
                velocityY = (int) (((float) velocityY) + oldVelocityY);
            }
        }
        this.a = 1;
        this.b.a(startX, velocityX, minX, maxX, overX);
        this.c.a(startY, velocityY, minY, maxY, overY);
    }

    public void d() {
        this.b.a();
        this.c.a();
    }

    public void a(boolean enable, boolean smoothFling) {
        this.b.a(enable, smoothFling);
        this.c.a(enable, smoothFling);
    }
}
