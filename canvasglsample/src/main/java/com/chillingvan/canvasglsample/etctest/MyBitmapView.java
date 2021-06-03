package com.chillingvan.canvasglsample.etctest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glview.texture.GLContinuousTextureView;

import java.io.IOException;
import java.io.InputStream;

public class MyBitmapView extends GLContinuousTextureView {
    public Bitmap bitmap;

    public MyBitmapView(Context context) {
        super(context);
    }

    public MyBitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onGLDraw(ICanvasGL canvas) {

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, new Rect(0, 0, 3506, 2238), new Rect(0, 0, getWidth(), getHeight()));
        }

    }

    public void loadBitmap() {
        if (bitmap == null) {
            InputStream inputStream = null;
            try {
                inputStream = getContext().getAssets().open("test.jpg");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bitmap = BitmapFactory.decodeStream(inputStream,null,options);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
