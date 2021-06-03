package com.chillingvan.canvasglsample.etctest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.chillingvan.canvasgl.glcanvas.BasicTexture;
import com.chillingvan.canvasglsample.R;

public class ETC1Activity extends AppCompatActivity {
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_t_c1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameLayout frameLayout = findViewById(R.id.content);

                if (i == 0) {
//                    MyBitmapView myBitmapView = new MyBitmapView(frameLayout.getContext());
//                    frameLayout.addView(myBitmapView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//                    myBitmapView.loadBitmap();
                    ETC1Textureview etc1Textureview = new ETC1Textureview(frameLayout.getContext());
                    frameLayout.addView(etc1Textureview, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                    etc1Textureview.loadETCTexture();
                }else{
                    frameLayout.removeAllViews();
                    BasicTexture.yieldAllTextures();
                }
                ++i;

            }
        });
    }
}