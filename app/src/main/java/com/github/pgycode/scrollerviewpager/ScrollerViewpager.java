package com.github.pgycode.scrollerviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Scroller;

import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScrollerViewpager extends ViewPager {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100002)
                setCurrentItem(getCurrentItem() + 1);
        }
    };

    //滚动的页面
    private List<View> views;

    //滚动的页面数量
    private int count;

    //自动滚动信号
    private final int SCROLL = 100002;

    //创建一个滚动线程
    private Thread thread;

    //间隔时间
    private int SPACE;

    //目前线程状态
    private boolean NORMAL = true;

    public ScrollerViewpager(@NonNull Context context) {
        super(context);
    }

    public ScrollerViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 功能：初始化
     * @param views 视图组
     * @param space 间隔时间
     * @param listener 监听事件
     */
    public void init(List<View> views, final int space, final OnViewpagerChangeListener listener){
        this.views = views;
        this.count = views.size();
        this.SPACE = space;

        setAdapter(new MyAdapter());
        setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % views.size());

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        NORMAL = true;
                        Thread.sleep(SPACE * 1000);
                        handler.sendEmptyMessage(SCROLL);
                    } catch (InterruptedException e) {
                        try {
                            NORMAL = false;
                            Thread.sleep(Integer.MAX_VALUE);
                        } catch (InterruptedException e1) { }
                    }
                }
            }
        });
        thread.start();

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                listener.onChange(position % count);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if ((!NORMAL && state != 1) || (NORMAL && state == 1)){
                    thread.interrupt();
                }
            }
        });
    }

    public class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int temp = position % count;
            View view = views.get(temp);
            if (view.getParent() == container) {
                container.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }
}
