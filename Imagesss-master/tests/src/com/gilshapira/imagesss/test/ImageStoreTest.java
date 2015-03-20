package com.gilshapira.imagesss.test;

import junit.framework.TestCase;

import com.gilshapira.imagesss.ImageStore;
import com.gilshapira.imagesss.ImageStore.NetworkDelegate;
import com.gilshapira.imagesss.ImageStore.StorageDelegate;

public class ImageStoreTest extends TestCase {
    
    private static final String URL = "something";
    
    private ImageStore mStore;

    @Override
    protected void setUp() throws Exception {
        mStore = new ImageStore();
    }

    @Override
    protected void tearDown() throws Exception {
        mStore = null;
    }
    
    public void testEmpty() {
        byte[] data = mStore.getImage(URL);
        assertNull(data);
    }
    
    public void testLoadNoDelegates() {
        byte[] data = mStore.loadImageSync(URL);
        assertNull(data);
    }
    
    public void testLoadWithStorage() {
        mStore.setStorageDelegate(new MockDelegate());
        byte[] data = mStore.loadImageSync(URL);
        int size = data.length;
        assertEquals(size, 5);
    }
    
    public void testLoadWithNetwork() {
        mStore.setNetworkDelegate(new MockDelegate());
        byte[] data = mStore.loadImageSync(URL);
        int size = data.length;
        assertEquals(size, MockDelegate.MOCKDATA.length);
    }
    
    public void testLoadAndGet() {
        byte[] data = mStore.getImage(URL);
        assertNull(data);
        
        mStore.setStorageDelegate(new MockDelegate());
        data = mStore.loadImageSync(URL);
        int size = data.length;
        assertEquals(size, MockDelegate.MOCKDATA.length);
        
        data = mStore.getImage(URL);
        size = data.length;
        assertEquals(size, MockDelegate.MOCKDATA.length);
    }
    
    private static class MockDelegate implements StorageDelegate, NetworkDelegate {
        
        public static final byte[] MOCKDATA = { 12, 34, 56, 78, 90 };
        
        @Override
        public void put(String url, byte[] data) {
        }
        
        @Override
        public byte[] get(String url) {
            return MOCKDATA;
        }
        
        @Override
        public void clear() {
        }

        @Override
        public byte[] download(String url) {
            return MOCKDATA;
        }
        
    }

}
