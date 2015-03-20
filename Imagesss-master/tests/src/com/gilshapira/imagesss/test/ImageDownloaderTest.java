package com.gilshapira.imagesss.test;

import com.gilshapira.imagesss.ImageDownloader;

import junit.framework.TestCase;

public class ImageDownloaderTest extends TestCase {
    
    public void testDownload() {
        ImageDownloader downloader = new ImageDownloader();
        byte[] file = downloader.download(TestConsts.IMAGE1_URL);
        assertNotNull(file);
        
        int size = file.length;
        assertEquals(size, TestConsts.IMAGE1_SIZE);
    }

}
