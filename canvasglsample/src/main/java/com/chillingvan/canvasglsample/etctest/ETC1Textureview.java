package com.chillingvan.canvasglsample.etctest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.opengl.ETC1Util;
import android.util.AttributeSet;
import android.util.Log;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glview.texture.GLContinuousTextureView;
import com.chillingvan.canvasgl.textureFilter.DirectionalSobelEdgeDetectionFilter;
import com.chillingvan.canvasgl.textureFilter.GammaFilter;
import com.chillingvan.canvasgl.textureFilter.GaussianBlurFilter;
import com.chillingvan.canvasgl.textureFilter.HueFilter;
import com.chillingvan.canvasgl.textureFilter.SaturationFilter;
import com.chillingvan.canvasgl.textureFilter.TextureFilter;

import java.io.IOException;
import java.io.InputStream;

public class ETC1Textureview extends GLContinuousTextureView {
    ETC1Util.ETC1Texture etc1Texture;
    TextureFilter textureFilter;
    Bitmap bitmap;

    public ETC1Textureview(Context context) {
        super(context);

    }

    public ETC1Textureview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ETC1Textureview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onGLDraw(ICanvasGL canvas) {


        if (etc1Texture != null) {

            canvas.drawETC1Texture(etc1Texture, new Rect(0, 0, etc1Texture.getWidth(), etc1Texture.getHeight()), new Rect(0, 0, getWidth(), getHeight()), textureFilter);
        }
//        if(bitmap!=null)
//            canvas.drawBitmap(bitmap,new RectF(0,0,bitmap.getWidth(),bitmap.getHeight()),new RectF(0,0,getWidth(),getHeight()),textureFilter);
    }


    public void loadETCTexture() {
        if (etc1Texture == null) {

            InputStream inputStream = null;
            try {
//                bitmap = BitmapFactory.decodeStream(getContext().getAssets().open("test.jpg"));
                inputStream = getContext().getAssets().open("test.pkm");
                etc1Texture = ETC1Util.createTexture(inputStream);
                textureFilter = new GaussianBlurFilter(6f);
                Log.i("myyf", "width:" + etc1Texture.getWidth() + "&height:" + etc1Texture.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
//            InputStream inputStream = null;
//            try {
//                inputStream = getContext().getAssets().open("test.jpg");
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    options.outConfig = Bitmap.Config.RGB_565;
//                }
//                options.inPreferredConfig = Bitmap.Config.RGB_565;
//                Bitmap m_TexBitmap = BitmapFactory.decodeStream(inputStream,null,options);
//                Log.i("myyf","alpha"+m_TexBitmap.getConfig());
//                int size = m_TexBitmap.getByteCount();
//                ByteBuffer bb = ByteBuffer.allocateDirect(size); // size is good
//                bb.order(ByteOrder.nativeOrder());
//                m_TexBitmap.copyPixelsToBuffer(bb);
//                bb.position(0);
//                int m_TexWidth = m_TexBitmap.getWidth(), m_TexHeight = m_TexBitmap.getHeight();
////                final int encodedImageSize = ETC1.getEncodedDataSize(m_TexWidth, m_TexHeight);
////                ByteBuffer compressedImage = ByteBuffer.allocateDirect(encodedImageSize).order(ByteOrder.nativeOrder());
////// RGB_565 is 2 bytes per pixel
////                ETC1.encodeImage(bb, m_TexWidth, m_TexHeight, 2, 2 * m_TexWidth, compressedImage);
//                etc1Texture = ETC1Util.compressTexture(bb,m_TexWidth,m_TexHeight,2,2*m_TexWidth);
//
////ETC1Util.loadTexture(GL10.GL_TEXTURE_2D, 0, 0, GL10.GL_RGB, GL10.GL_UNSIGNED_SHORT_5_6_5, etc1tex);
//
//                m_TexBitmap.recycle();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


        }

    }
}
