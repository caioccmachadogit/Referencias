package com.gilshapira.imagesss.views;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gilshapira.imagesss.DrawableConfig;
import com.gilshapira.imagesss.DrawableStore;
import com.gilshapira.imagesss.R;

/**
 * <p>An {@code ImageView} that supports setting remote images.</p>
 * 
 * <p>Supported XML attributes:
 * <ul>
 * <li>width (Dimension)
 * <li>height (Dimension)
 * <li>round (Boolean)
 * <li>default_src (Drawable)
 * <li>error_src (Drawable)
 * </ul></p>
 *
 * @author Gil Shapira
 */
public class RemoteImageView extends ImageView {

    public static final String LOGTAG = "RemoteImageView";
    
    /** A default {@code DrawableStore} to be used when creating new {@code RemoteImageView}s */
    private static DrawableStore sDefaultDrawableStore;
    
    /** The URL of the current remote image we're loading */
    private String mUrl;
    
    /** The Drawable width */
    private int mWidth;
    
    /** The Drawable height */
    private int mHeight;
    
    /** Whether to use rounded corners */
    private boolean mRounded;
    
    /** Default resource image that will be displayed while remote image is loading */
    private final Drawable mDefaultDrawable;
    
    /** Error resource image that will be displayed if remote load fails */
    private final Drawable mErrorDrawable;
    
    /** The DrawableStore used to load the image */
    private DrawableStore mDrawableStore;
    
    /** The drawable configuration used by this view */
    private DrawableConfig mConfig;
    
    /** The listener that's invoked when a drawable is loaded */
    private DrawableListener mListener = new DrawableListener(this);
    
    /** Whether we're listening for drawable loaded events */
    private boolean mListening = false;
    
    /**
     * Creates a new {@code RemoteImageView} object.
     */
    public RemoteImageView(Context context) {
        super(context);
        mUrl = null;
        mWidth = 0;
        mHeight = 0;
        mRounded = false;
        mDefaultDrawable = null;
        mErrorDrawable = null;
        mConfig = DrawableConfig.DEFAULT_CONFIG;
        mDrawableStore = sDefaultDrawableStore;
    }

    /**
     * Creates a new {@code RemoteImageView} object.
     */
    public RemoteImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Creates a new {@code RemoteImageView} object.
     */
    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RemoteImageView);
        
        mUrl = null;
        mWidth = (int) a.getDimension(R.styleable.RemoteImageView_width, 0);
        mHeight = (int) a.getDimension(R.styleable.RemoteImageView_height, 0);
        mRounded = a.getBoolean(R.styleable.RemoteImageView_rounded, false);
        mDefaultDrawable = a.getDrawable(R.styleable.RemoteImageView_default_src);
        mErrorDrawable = a.getDrawable(R.styleable.RemoteImageView_error_src);
        mDrawableStore = sDefaultDrawableStore;

        if (mDefaultDrawable != null) {
            setImageDrawable(mDefaultDrawable);
        }
        
        if ((mWidth != 0) || (mHeight != 0)) {
            mConfig = new DrawableConfig(mWidth, mHeight, mRounded);
        } else if (mRounded) {
            mConfig = DrawableConfig.ROUNDED_CONFIG;
        } else {
            mConfig = DrawableConfig.DEFAULT_CONFIG;
        }
        
        a.recycle();
    }
    
    /**
     * Sets a {@code DrawableStore} object to use for image loading.
     * @param drawableStore the {@code DrawableStore} object to use.
     */
    public void setDrawableStore(DrawableStore drawableStore) {
        mDrawableStore = drawableStore;
    }
    
    /**
     * Sets a {@code DrawableConfig} object to use for image loading. Note that
     * this clears the {@code RemoteImageView} and any pending loads.
     * @param drawableConfig the {@code DrawableConfig} object to use.
     */
    public void setDrawableConfig(DrawableConfig drawableConfig) {
        clearImage();
        mWidth = drawableConfig.getWidth();
        mHeight = drawableConfig.getHeight();
        mRounded = drawableConfig.isRounded();
        mConfig = drawableConfig;
    }

    /**
     * Loads a remote or resource image.
     * @param url the URL of the image to load.
     */
    public void loadImage(String url) {
        // save the store and image URL for later access
        mUrl = url;
        
        if (mDrawableStore == null) {
            throw new IllegalStateException("A DrawableStore must be set before loading images");
        }
        
        // just to be on the safe side, do nothing for null URLs instead of crashing
        if (url == null) {
            Log.w(LOGTAG, "Attempt to load a null image");
            setImageDrawable(null);
            setVisibility(View.INVISIBLE);
            removeListener();
            return;
        }
        
        // actual remote url
        Drawable d = mDrawableStore.getDrawable(url, mConfig);
        if (d != null) {
            // drawable is already available
            setImageDrawable(d);
            setVisibility(View.VISIBLE);
            removeListener();
        } else {
            // need to load the image remotely
            addListener();
            
            mDrawableStore.loadDrawable(url, mConfig);

            // apply the default drawable while loading
            if (mDefaultDrawable != null) {
                setImageDrawable(mDefaultDrawable);
                setVisibility(View.VISIBLE);
            } else {
                setVisibility(View.INVISIBLE);
            }
        }
    }
    
    /**
     * Adds the listener to the {@code DrawableStore}.
     */
    private void addListener() {
        if (!mListening) {
            mListening = true;
            mDrawableStore.addListener(mListener);
        }
    }
    
    /**
     * Removes the listener from the {@code DrawableStore}.
     */
    private void removeListener() {
        if (mListening) {
            mDrawableStore.removeListener(mListener);
            mListening = false;
        }
    }
 
    /**
     * Disables any ongoing image loading and hides the view.
     */
    public void clearImage() {
        clearImage(View.INVISIBLE);
    }

    /**
     * Disables any ongoing image loading and hides the view.
     * @param visibility either View.GONE or View.Invisible.
     */
    public void clearImage(int visibility) {
        mUrl = null;
        setImageDrawable(null);
        setVisibility(visibility);
        removeListener();
    }

    /**
     * Called when the {@code Drawable} is ready for the remote image.
     * @param drawable
     */
    private void onDrawableLoadSuccess(Drawable drawable) {
        setImageDrawable(drawable);
        setVisibility(View.VISIBLE);
        removeListener();
    }
    
    /**
     * Called when a {@code Drawable} couldn't be loaded for the remote image.
     */
    private void onDrawableLoadFailure() {
        // we encountered an error
        if (mErrorDrawable != null) {
            setImageDrawable(mErrorDrawable);
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.INVISIBLE);
        }
        removeListener();
    }
    
    /**
     * Sets a default {@code DrawableStore} to be used when creating new {@code RemoteImageView}s.
     * @param drawableStore the {@code DrawableStore}.
     */
    public static void setDefaultDrawableStore(DrawableStore drawableStore) {
        sDefaultDrawableStore = drawableStore;
    }
    
    //
    // DrawableListener class
    //
    
    /**
     * A listener that's created for each instance of {@code RemoteImageView} 
     * but only keeps a WeakReference to it. This is prevents listeners from 
     * keeping references to discarded views and leaking the {@code Context}.
     */
    private static class DrawableListener implements DrawableStore.DrawableListener {
        
        /** The {@code RemoteImageView} in which to set the image.*/
        private WeakReference<RemoteImageView> mViewRef;
        
        /**
         * Creates a new {@code DrawableListener} object.
         * @param view the {@code RemoteImageView} in which to set the image.
         */
        public DrawableListener(RemoteImageView view) {
            mViewRef = new WeakReference<RemoteImageView>(view);
        }

        @Override
        public void onDrawableLoad(String url, DrawableConfig config, Drawable drawable) {
            RemoteImageView view = mViewRef.get();
            if (view != null) {
                // the view still exists, check if we got the event for
                // the appropriate image and configuration
                if (url.equals(view.mUrl) && (config.equals(view.mConfig))) {
                    if (drawable != null) {
                        view.onDrawableLoadSuccess(drawable);
                    } else {
                        view.onDrawableLoadFailure();
                    }
                }
            }
        }

    }
}
