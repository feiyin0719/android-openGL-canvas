package com.chillingvan.canvasglsample.etctest;

import android.opengl.ETC1Util;

import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.CloseableImage;

public class ETCTextureImage extends CloseableImage {
    private volatile ETC1Util.ETC1Texture etc1Texture;
    private CloseableReference<ETC1Util.ETC1Texture> etc1TextureCloseableReference;
    @Override
    public int getSizeInBytes() {
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
