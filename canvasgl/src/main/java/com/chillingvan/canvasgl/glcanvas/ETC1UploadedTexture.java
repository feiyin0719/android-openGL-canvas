package com.chillingvan.canvasgl.glcanvas;

import android.opengl.ETC1Util;
import android.opengl.GLES20;

import javax.microedition.khronos.opengles.GL11;

public class ETC1UploadedTexture extends BasicTexture {
    private ETC1Util.ETC1Texture etc1Texture;

    public ETC1UploadedTexture(ETC1Util.ETC1Texture texture) {
        super();
        etc1Texture = texture;
        setSize(etc1Texture.getWidth(), etc1Texture.getHeight());
    }

    protected void uploadToCanvas(GLCanvas canvas) {
        if (!isLoaded()) {
            mId = canvas.getGLId().generateTexture();
            canvas.setTextureParameters(this);
            canvas.texETC1Texture(this, 0, //纹理层次
                    0,//纹理边框尺寸
                    GLES20.GL_RGB,//色彩通道格式
                    GLES20.GL_UNSIGNED_SHORT_5_6_5, //每像素数据数
                    etc1Texture);
            mState = STATE_LOADED;
            setAssociatedCanvas(canvas);
        }
    }

    @Override
    protected boolean onBind(GLCanvas canvas) {
        uploadToCanvas(canvas);
        return isLoaded();
    }

    @Override
    protected int getTarget() {
        return GL11.GL_TEXTURE_2D;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

}
