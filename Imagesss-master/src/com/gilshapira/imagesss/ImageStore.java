package com.gilshapira.imagesss;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * The {@code ImageStore} facilitates loading of images from the network, caching them in
 * memory and storing them for later access. The store limits the amount of memory used and
 * will discard least recently accessed images when this limit is reached.
 *
 * @author Gil Shapira
 */
public class ImageStore {
    
    public static final String LOGTAG = "ImageStore";
    
    private final static int CONCURRENT_LOADS = 7;
    private final static long DEFAULT_IMAGE_CACHE_SIZE = 768*1024;
    
    /** The container for stored images */
    private final LimitedStorageHashMap<String, byte[]> mImages;
    
    /** The set of URLs of pending downloads */
    private final Set<String> mPending;
    
    /** The set of listeners to be notified of image loads */
    private final Set<ImageListener> mListeners;
    
    /** The thread pool that runs background loads */
    private final Executor mThreadPool;
    
    /** The object used for storage functionality */
    private StorageDelegate mStorageDelegate;
    
    /** The object used for network functionality */
    private NetworkDelegate mNetworkDelegate;
    
    /**
     * Creates a new {@code ImageStore} object.
     */
    public ImageStore() {
        mImages = new LimitedStorageHashMap<String, byte[]>(DEFAULT_IMAGE_CACHE_SIZE);
        mPending = new HashSet<String>();
        mListeners = new HashSet<ImageListener>();
        mThreadPool = new ThreadPoolExecutor(CONCURRENT_LOADS, CONCURRENT_LOADS, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        setStorageDelegate(null);
        setNetworkDelegate(null);
    }
    
    /**
     * Retrieves an image that has already been loaded.
     * @param url the image URL.
     * @return the image data.
     */
    public byte[] getImage(String url) {
        synchronized(mImages) {
            return mImages.get(url);
        }
    }
    
    /**
     * Loads the image with the specified URL.
     * @param url the image URL.
     */
    public void loadImage(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Attempt to load image with null URL");
        }
        
        synchronized(mPending) {
            if (!mPending.contains(url)) {
                mPending.add(url);
                mThreadPool.execute(new LoadTask(url));
            }
        }
    }
    
    /**
     * Loads the image with the specified URL synchronously.
     * @param url the image URL.
     */
    public byte[] loadImageSync(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Attempt to load null image");
        }
        
        synchronized(mPending) {
            if (!mPending.contains(url)) {
                mPending.add(url);
            }
        }
        
        return load(url);
    }
    
    /**
     * Sets the maximum size of the image memory cache.
     * @param size the new maximum size in bytes.
     */
    public void setImageCacheSize(long size) {
        mImages.setMaxSize(size);
    }
    
    /**
     * Loads image data from the storage or network.
     * @param url the image URL.
     */
    private byte[] load(String url) {
        boolean fromNetwork = false;
        
        // we try loading from the storage first. if the image isn't found
        // we load it from the network and save it later on.
        byte[] data = mStorageDelegate.get(url);
        if (data == null) {
            data = mNetworkDelegate.download(url);
            fromNetwork = true;
        }
        
        // store the image data in the memory cache
        synchronized(mImages) {
            if (data != null) {
                mImages.put(url, data, data.length);
            }
        }

        // notify any listeners and remove the pending entry
        synchronized(mPending) {
            for (ImageListener listener : mListeners) {
                listener.onImageLoad(url, data);
            }
            mPending.remove(url);
        }
        
        // save the downloaded image to the storage.
        if (fromNetwork && (data != null)) {
            mStorageDelegate.put(url, data);
        }
        
        return data;
    }
    
    /**
     * Adds an {@code ImageListener} to be notified when images loads are done.
     * @param listener the {@code ImageListener} to add.
     */
    public void addListener(ImageListener listener) {
        synchronized(mPending) {
            mListeners.add(listener);
        }
    }
    
    /**
     * Removes an {@code ImageListener} from the set of listeners.
     * @param listener the {@code ImageListener} to remove.
     */
    public void removeListener(ImageListener listener) {
        synchronized(mPending) {
            mListeners.remove(listener);
        }
    }
    
    /**
     * Clears the {@code ImagesStore}.
     */
    public void clear() {
        synchronized(mImages) {
            mImages.clear();
            mStorageDelegate.clear();
        }
    }
    
    /**
     * Releases in-memory caches. This should be called in low-memory situations.
     */
    public void releaseMemory() {
        synchronized(mImages) {
            mImages.clear();
        }
    }
    
    /**
     * Sets an object to be used for storage functionality.
     * @param storageDelegate the object.
     */
    public void setStorageDelegate(StorageDelegate storageDelegate) {
        if (storageDelegate != null) {
            mStorageDelegate = storageDelegate;
        } else {
            // we use a dummy implementation when no delegate is available
            mStorageDelegate = new StorageDelegate() {
                public void put(String url, byte[] data) {}
                public byte[] get(String url) { return null; }
                public void clear() {}
            };
        }
    }
    
    /**
     * Sets an object to be used for network functionality.
     * @param networkDelegate the object.
     */
    public void setNetworkDelegate(NetworkDelegate networkDelegate) {
        if (networkDelegate != null) {
            mNetworkDelegate = networkDelegate;
        } else {
            // we use a dummy implementation when no delegate is available
            mNetworkDelegate = new NetworkDelegate() {
                public byte[] download(String url) { return null; }
            };
        }
    }
    
    /**
     * A runnable used to load images in the background via the thread pool. 
     */
    private class LoadTask implements Runnable {
        
        /** The image URL */
        private String mUrl;
        
        /**
         * Creates a new {@code LoadTask} object.
         * @param url the image URL.
         */
        public LoadTask(String url) {
            mUrl = url;
        }

        @Override
        public void run() {
            load(mUrl);
        }
        
    }
    
    /**
     * A listener that will be notified when image loads succeed or fail.
     */
    public interface ImageListener {
        
        /**
         * A method that's invoked when image loads complete.
         * @param url the url of the image.
         * @param data the data of the image or {@code null} if the load failed.
         */
        void onImageLoad(String url, byte[] data);
        
    }
    
    /**
     * Classes the implement the {@code NetworkDelegate} interface are
     * used to download the images. 
     */
    public interface NetworkDelegate {
        
        /**
         * Retrieves an image.
         * @param url the image url.
         * @return the image data.
         */
        byte[] download(String url);
        
    }
    
    /**
     * Classes that implement the {@code StorageDelegate} interface can
     * be used to retrieve and store images.
     */
    public interface StorageDelegate {
        
        /**
         * Retrieves an image.
         * @param url the image url.
         * @return the image data.
         */
        byte[] get(String url);
        
        /**
         * Stores an image.
         * @param url the image url.
         * @param data the image data.
         */
        void put(String url, byte[] data);
        
        /**
         * Clears all images.
         */
        void clear();
        
    }

}
