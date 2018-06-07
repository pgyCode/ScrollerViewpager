package com.github.pgycode.scrollerviewpager;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ScrollerViewpager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = findViewById(R.id.viewpager);
        ArrayList<View> views = new ArrayList<>();
        views.add(LayoutInflater.from(this).inflate(R.layout.item, null,false));
        views.add(LayoutInflater.from(this).inflate(R.layout.item1, null,false));
        views.add(LayoutInflater.from(this).inflate(R.layout.item2, null,false));

        final Button btn1 = findViewById(R.id.button1);
        final Button btn2 = findViewById(R.id.button2);
        final Button btn3 = findViewById(R.id.button3);

        viewpager.init(views, 4, new OnViewpagerChangeListener() {
            @Override
            public void onChange(int currentPage) {
                switch (currentPage){
                    case 0:
                        btn1.setBackgroundColor(Color.YELLOW);
                        btn2.setBackgroundColor(Color.WHITE);
                        btn3.setBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        btn1.setBackgroundColor(Color.WHITE);
                        btn2.setBackgroundColor(Color.YELLOW);
                        btn3.setBackgroundColor(Color.WHITE);
                        break;
                    case 2:
                        btn1.setBackgroundColor(Color.WHITE);
                        btn2.setBackgroundColor(Color.WHITE);
                        btn3.setBackgroundColor(Color.YELLOW);
                        break;
                }
            }
        });
    }
}
