package com.gilshapira.imagesss.test;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.test.AndroidTestCase;

import com.gilshapira.imagesss.DrawableConfig;
import com.gilshapira.imagesss.DrawableStore;
import com.gilshapira.imagesss.ImageDownloader;
import com.gilshapira.imagesss.ImageStore;

public class DrawableStoreTest extends AndroidTestCase {
    
    private static final String URL = TestConsts.IMAGE1_URL;
    private static final int WIDTH = TestConsts.IMAGE1_WIDTH;
    private static final int HEIGHT = TestConsts.IMAGE1_HEIGHT;
    
    private ImageStore mImageStore;
    private DrawableStore mDrawableStore;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mImageStore = new ImageStore();
        mImageStore.setNetworkDelegate(new ImageDownloader());
        mDrawableStore = new DrawableStore(getContext(), mImageStore);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mImageStore = null;
        mDrawableStore = null;
    }
    
    public void testContext() {
        Context context = getContext();
        assertNotNull(context);
    }
    
    public void testEmpty() {
        Drawable d = mDrawableStore.getDrawable(URL);
        assertNull(d);
    }
    
    public void testLoad() {
        Drawable d = mDrawableStore.loadDrawableSync(URL, DrawableConfig.DEFAULT_CONFIG);
        assertNotNull(d);
        
        int w = d.getIntrinsicWidth();
        assertEquals(WIDTH, w);
        
        int h = d.getIntrinsicHeight();
        assertEquals(HEIGHT, h);
    }
    
    public void testLoadResizeWidthLarge() {
        Drawable d = mDrawableStore.loadDrawableSync(URL, new DrawableConfig(WIDTH * 2, 0, false));
        assertNotNull(d);
        
        int w = d.getIntrinsicWidth();
        assertEquals(WIDTH * 2, w);
        
        int h = d.getIntrinsicHeight();
        assertEquals(HEIGHT * 2, h);
    }
    
    public void testLoadResizeHeightSmall() {
        Drawable d = mDrawableStore.loadDrawableSync(URL, new DrawableConfig(0, HEIGHT / 5, false));
        assertNotNull(d);
        
        int w = d.getIntrinsicWidth();
        assertEquals(WIDTH / 5, w);
        
        int h = d.getIntrinsicHeight();
        assertEquals(HEIGHT / 5, h);
    }
    
    public void testLoadResizeCrop() {
        Drawable d = mDrawableStore.loadDrawableSync(URL, new DrawableConfig(HEIGHT / 2, HEIGHT / 2, false));
        assertNotNull(d);
        
        int w = d.getIntrinsicWidth();
        assertEquals(HEIGHT / 2, w);
        
        int h = d.getIntrinsicHeight();
        assertEquals(HEIGHT / 2, h);
    }
    
}
