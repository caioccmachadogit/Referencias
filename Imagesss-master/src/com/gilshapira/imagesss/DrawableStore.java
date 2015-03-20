package com.gilshapira.imagesss;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;


/**
 * The {@code DrawableStore} allows UI components to request {@code Drawable} objects
 * for remote images by URL. Specific configurations such as width, height and whether
 * to round the image are supported.
 *
 * @author Gil Shapira
 */
public class DrawableStore {
    
    public static final String LOGTAG = "DrawableStore";
    
    /** Maximum number of drawable references to store in memory. */
    private static final int MAX_NUM_DRAWABLES = 100;
    
    /** The internal container for drawables */
    private final Map<String, Map<DrawableConfig, Drawable>> mDrawables;
    
    /** The collection of pending drawable requests */
    private final Map<String, Set<DrawableConfig>> mPending;
    
    /** The set of listeners to be notified of drawable loads */
    private final Set<DrawableListener> mListeners;
    
    /** A reference to the image store */
    private final ImageStore mImageStore;
    
    /** A Context object to use for determining pixel densities */
    private Context mContext;
    
    /** The Handler on which listeners will be notified */
    private Handler mHandler;
    
    /** Whether we've added a listener to the ImageStore */
    private boolean mListening;
    
    /**
     * Creates a new {@code DrawableStore} object.
     * @param context the {@code Context} object used for determining pixel densities.
     * @param imageStore the {@code ImageStore} to use for image retrieval.
     */
    public DrawableStore(Context context, ImageStore imageStore) {
        mContext = context;
        mImageStore = imageStore;
        mDrawables = new LimitedStorageHashMap<String, Map<DrawableConfig, Drawable>>(MAX_NUM_DRAWABLES);
        mPending = new HashMap<String, Set<DrawableConfig>>();
        mListeners = new HashSet<DrawableListener>();
        mListening = false;
        mHandler = new Handler();
    }
    
    /**
     * Retrieves a {@code Drawable} object for the image at the specified URL,
     * with the default configuration.
     * @param url the image URL.
     * @return the {@code Drawable}, or null if the image hasn't been loaded.
     */
    public Drawable getDrawable(String url) {
        return getDrawable(url, DrawableConfig.DEFAULT_CONFIG);
    }
    
    /**
     * Retrieves a {@code Drawable} object for the image at the specified URL and with
     * the specified configuration.
     * @param url the image URL.
     * @param config the drawable configuration.
     * @return the {@code Drawable}, or null if the image hasn't been loaded.
     */
    public Drawable getDrawable(String url, DrawableConfig config) {
        Drawable d = null;
        synchronized(mDrawables) {
            Map<DrawableConfig, Drawable> drawables = mDrawables.get(url);
            if (drawables != null) {
                d = drawables.get(config);
            }
        }
        if (d == null) {
            byte[] data = mImageStore.getImage(url);
            if (data != null) {
                d = addImage(url, config, data);
            }
        }
        return d;
    }
    
    /**
     * Loads a {@code Drawable} for the image at the specified URL, with the default configuration.
     * @param url the image URL.
     */
    public void loadDrawable(String url) {
        loadDrawable(url, DrawableConfig.DEFAULT_CONFIG);
    }
    
    /**
     * Loads a {@code Drawable} for the image at the specified URL and configuration.
     * @param url the image URL.
     * @param config the drawable configuration.
     */
    public void loadDrawable(String url, DrawableConfig config) {
        if (url == null) {
            throw new IllegalArgumentException("Attempt to load null image");
        }

        // stop now if we're already loading this drawable 
        if (addPendingLoad(url, config) == false) {
            return;
        }
        
        mImageStore.loadImage(url);
    }
    
    /**
     * Loads a {@code Drawable} for the image at the specific URL and configuration.
     * Performs the load synchronously.
     * @param url the image URL.
     * @param config the drawable configuration.
     * @return the {@code Drawable} object.
     */
    public Drawable loadDrawableSync(String url, DrawableConfig config) {
        if (url == null) {
            throw new IllegalArgumentException("Attempt to load null image");
        }

        byte[] data = mImageStore.loadImageSync(url);
        if (data != null) {
            return addImage(url, config, data);
        } else {
            return null;
        }
    }
    
    /**
     * Adds a {@code Drawable} to the store.
     * @param url the drawable url.
     * @param config the drawable configuration.
     * @param data the image data.
     * @return the {@code Drawable} for the image that was added, or null if something went horrible wrong.
     */
    private Drawable addImage(String url, DrawableConfig config, byte[] data) {
        try {
            Drawable drawable = DrawableUtils.createDrawable(mContext, data, config);
            addDrawable(drawable, url, config);
            return drawable;
        }
        catch (Throwable tr) {
            Log.w(LOGTAG, "Couldn't create a Drawable for image with url: "+url, tr);
            if (tr instanceof OutOfMemoryError) {
                synchronized(mDrawables) {
                    mDrawables.clear();
                }
            }
            return null;
        }
    }
    
    /**
     * Adds a drawable to the store.
     * @param drawable the drawable to add.
     * @param url the drawable url.
     * @param config the drawable configuration.
     */
    private void addDrawable(Drawable drawable, String url, DrawableConfig config) {
        synchronized(mDrawables) {
            Map<DrawableConfig, Drawable> drawables = mDrawables.get(url);
            if (drawables == null) {
                drawables = new HashMap<DrawableConfig, Drawable>();
                mDrawables.put(url, drawables);
            }
            drawables.put(config, drawable);
        }
    }
    
    /**
     * Adds a pending load entry.
     * @param url the URL of the drawable.
     * @param config the configuration of the drawable.
     * @return true if an entry was added, false if there's already a pending load.
     */
    private boolean addPendingLoad(String url, DrawableConfig config) {
        synchronized(mPending) {
            if (!mListening) {
                mListening = true;
                mImageStore.addListener(mImageListener);
            }
            
            Set<DrawableConfig> loads = mPending.get(url);
            if (loads == null) {
                loads = new HashSet<DrawableConfig>();
                mPending.put(url, loads);
            }
            
            // is there already a pending load?
            if (loads.contains(config)) {
                return false;
            }
            
            // add an entry for this load
            loads.add(config);
            return true;
        }
    }
    
    /**
     * Removes a pending load entry.
     * @param url the URL of the drawable.
     * @param config the configuration of the drawable.
     * @return true if an entry was removed.
     */
    private Set<DrawableConfig> removePendingLoad(String url) {
        synchronized(mPending) {
            Set<DrawableConfig> loads = mPending.remove(url);
            if (mPending.isEmpty() && mListening) {
                mListening = false;
                mImageStore.removeListener(mImageListener);
            }
            return loads;
        }
    }
    
    /**
     * The event that's invoked when the {@code ImageStore} finishes loading an image.
     */
    private ImageStore.ImageListener mImageListener = new ImageStore.ImageListener() {
        @Override
        public void onImageLoad(String url, byte[] data) {
            Set<DrawableConfig> loads = removePendingLoad(url);
            if (loads != null) {
                for (DrawableConfig config : loads) {
                    if (data != null) {
                        Drawable drawable = addImage(url, config, data);
                        notifyListeners(url, config, drawable);
                    } else {
                        notifyListeners(url, config, null);
                    }
                }
            }
        }
    };
    
    /**
     * Adds an {@code DrawableListener} to be notified when drawable loads are done.
     * @param listener the {@code DrawableListener} to add.
     */
    public void addListener(DrawableListener listener) {
        synchronized(mListeners) {
            mListeners.add(listener);
        }
    }
    
    /**
     * Removes an {@code DrawableListener} from the set of listeners.
     * @param listener the {@code DrawableListener} to remove.
     */
    public void removeListener(DrawableListener listener) {
        synchronized(mListeners) {
            mListeners.remove(listener);
        }
    }
    
    /**
     * Notified listeners that the drawable load succeeded or failed.
     * @param url the requested URL.
     * @param config the requested {@code Drawable} configuration.
     * @param data the {@code Drawable} object, or null if the load failed.
     */
    private void notifyListeners(final String url, final DrawableConfig config, final Drawable drawable) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Set<DrawableListener> listeners;
                synchronized(mListeners) {
                    listeners = new HashSet<DrawableListener>(mListeners);
                }
                
                for (DrawableListener listener : listeners) {
                    listener.onDrawableLoad(url, config, drawable);
                }
            }
        });
    }
    
    /**
     * Clears all {@code Drawable} callbacks. This needs be called in the onDestroy method
     * of every activity that uses the {@code DrawableStore} to create {@code Drawable}s.
     */
    public void clearCallbacks() {
        synchronized(mDrawables) {
            for (Map<DrawableConfig, Drawable> drawables : mDrawables.values()) {
                for (Drawable d : drawables.values()) {
                    d.setCallback(null);
                }
            }
        }
    }
    
    /**
     * Releases in-memory caches. This should be called in low-memory situations.
     */
    public void releaseMemory() {
        synchronized(mDrawables) {
            mDrawables.clear();
        }
    }
    
    /**
     * A listener that will be notified when {@code Drawable} loads succeed or fail.
     */
    public interface DrawableListener {
        
        /**
         * A method that's invoked when a {@code Drawable} load completes.
         * @param url the requested URL.
         * @param config the requested {@code Drawable} configuration.
         * @param data the {@code Drawable} object or {@code null} if the load failed.
         */
        void onDrawableLoad(String url, DrawableConfig config, Drawable drawable);
        
    }
    
}
