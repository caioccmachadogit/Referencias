package com.gilshapira.imagesss;

/**
 * An object that encapsulates the requested configuration for a Drawable. 
 * 
 * @author Gil Shapira
 */
public class DrawableConfig {

    /** The same dimension as the source or one that scales to maintain aspect. */
    public static final int MATCH_SOURCE = 0;
    
    public static final DrawableConfig DEFAULT_CONFIG = new DrawableConfig(MATCH_SOURCE, MATCH_SOURCE, false);
    public static final DrawableConfig ROUNDED_CONFIG = new DrawableConfig(MATCH_SOURCE, MATCH_SOURCE, true);
    
    /** The desired width, or MATCH_SOURCE if unspecified */
    private final int mWidth;
    
    /** The desired height, or MATCH_SOURCE if unspecified */
    private final int mHeight;
    
    /** Whether the drawable should have rounded corners */
    private final boolean mRounded;
    
    /** The cached hashcode */
    int mHash;
    
    /**
     * Creates a new {@code DrawableConfig} object.
     * @param width the desired width, or MATCH_SOURCE to keep as default.
     * @param height the desired height, or MATCH_SOURCE to keep as default.
     * @param rounded whether the drawable should have rounded corners.
     */
    public DrawableConfig(int width, int height, boolean rounded) {
        mWidth = width;
        mHeight = height;
        mRounded = rounded;
    }
    
    /**
     * @return the desired width, or MATCH_SOURCE to keep as default.
     */
    public int getWidth() {
        return mWidth;
    }
    
    /**
     * @return the desired height, or MATCH_SOURCE to keep as default.
     */
    public int getHeight() {
        return mHeight;
    }
    
    /**
     * @return whether the drawable should have rounded corners
     */
    public boolean isRounded() {
        return mRounded;
    }
    
    @Override
    public int hashCode() {
        if (mHash == 0) {
            final int prime = 31;
            int result = 1;
            result = prime * result + mWidth;
            result = prime * result + mHeight;
            mHash = prime * result + (mRounded ? 1231 : 1237);
        }
        return mHash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DrawableConfig)) return false;
        DrawableConfig other = (DrawableConfig) obj;
        if (mWidth != other.mWidth) return false;
        if (mHeight != other.mHeight) return false;
        if (mRounded != other.mRounded) return false;
        return true;
    }

    @Override
    public String toString() {
        return "DrawableConfig {"+mWidth+"x"+mHeight+(mRounded ? ", rounded" : "")+"}";
    }
    
}
