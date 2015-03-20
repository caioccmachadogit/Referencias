package com.gilshapira.imagesss;

import static android.graphics.Bitmap.Config.ARGB_8888;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Some random {@code Drawable} manipulation routines.
 *
 * @author Gil Shapira
 */
class DrawableUtils {
    
    public static final String LOGTAG = "DrawableUtils";
    
    /**
     * Creates a {@code Drawable} object from the image data.
     * @param context the {@code Context} object to use for determining pixel densities.
     * @param data the image data.
     * @param config the configuration of the {@code Drawable}.
     * @return the {@code Drawable} object.
     */
    public static Drawable createDrawable(Context context, byte[] data, DrawableConfig config) {
        Bitmap bitmap;
        int targetWidth = config.getWidth();
        int targetHeight = config.getHeight();
        
        // we can return a FastBitmapDrawable will be drawn as is and
        // won't need to support scaling
        boolean useFastBitmapDrawable = true;
        
        if ((targetWidth == 0) && (targetHeight == 0)) {
            // simply return the original bitmap
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            
            // since the drawable size wasn't specified we don't use a 
            // FastBitmapDrawable so as to support scaling 
            useFastBitmapDrawable = false;
        } else {
            // a specific drawable size was requested, lets check what
            // the size of the original bitmap is first, without decoding it yet
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, options);

            // in this case only one of the dimensions might have been left unspecified,
            // in which case we set according to the scale of the other one
            if (targetWidth == 0) {
                float ratio = targetHeight / (float) options.outHeight;
                targetWidth = (int) Math.ceil(options.outWidth * ratio);
            } else if (targetHeight == 0) {
                float ratio = targetWidth / (float) options.outWidth;
                targetHeight = (int) Math.ceil(options.outHeight * ratio);
            }
            
            // lets see if we should do some downscaling during the decoding
            float scale;
            if (options.outWidth < options.outHeight) {
                scale = targetWidth / (float) options.outWidth;
            } else {
                scale = targetHeight / (float) options.outHeight;
            }

            // if the downscaling exceeds a factor of 2 we can ask the decoder to
            // downsample the bitmap during the decoding.
            if (scale <= 0.5) {
                // if scale is 0.5 factor will be 2, if scale is 0.25 factor will be 4, etc
                // we're limiting this to powers of 2, so e.g., if scale is 0.33 factor will be 2
                int exp = (int) Math.floor(Math.log(scale) / Math.log(0.5));
                int factor = 1 << exp;
                options.inSampleSize = factor;
            }
            
            options.inJustDecodeBounds = false;
            Bitmap source = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            
            // extract a thumbnail from the source bitmap, recycling the source
            // bitmap if it isn't returned as is
            bitmap = extractThumbnail(source, targetWidth, targetHeight, true);
        }
        
        // apply the rounding effect if needed
        if (config.isRounded()) {
            Bitmap source = bitmap;
            bitmap = getRoundedCornerBitmap(source);
            source.recycle();
        }
        
        Drawable drawable;
        if (useFastBitmapDrawable) {
            drawable = new FastBitmapDrawable(bitmap);
        } else {
            //bitmap.setDensity(DisplayMetrics.DENSITY_LOW);
            drawable = new BitmapDrawable(context.getResources(), bitmap);
        }
        
        return drawable;
    }

    /**
     * Applies rounded corners to the specified {@code Bitmap}.
     * @param bitmap the source {@code Bitmap}, can and should be {@code recycle()}ed if not needed after this.
     * @return the output {@code Bitmap}.
     * @source http://stackoverflow.com/questions/2459916/how-to-make-an-imageview-to-have-rounded-corners
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        
        Bitmap output = Bitmap.createBitmap(width, height, ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = Math.max(width, height) / 10.0f;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        
        return output;
    }

    /**
     * Creates a centered bitmap of the desired size.
     * @param source original bitmap source.
     * @param width targeted width.
     * @param height targeted height.
     * @param recycle whether to recycle the source bitmap if it's not returned as is.
     * @return the output bitmap.
     * @source ThumbnailUtils.java in Android Open Source Project
     */
    public static Bitmap extractThumbnail(Bitmap source, int width, int height, boolean recycle) {
        if (source == null) {
            return null;
        }
        if ((source.getWidth() == 0) || (source.getHeight() == 0)) {
            return source;
        }

        float scale;
        if (source.getWidth() < source.getHeight()) {
            scale = width / (float) source.getWidth();
        } else {
            scale = height / (float) source.getHeight();
        }
        
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap thumbnail = transform(matrix, source, width, height, recycle);
        return thumbnail;
    }

    /**
     * Transform source Bitmap to targeted width and height.
     * @source ThumbnailUtils.java in Android Open Source Project
     */
    private static Bitmap transform(Matrix scaler, Bitmap source, int targetWidth, int targetHeight, boolean recycle) {
        float bitmapWidthF = source.getWidth();
        float bitmapHeightF = source.getHeight();

        float bitmapAspect = bitmapWidthF / bitmapHeightF;
        float viewAspect = (float) targetWidth / targetHeight;

        if (bitmapAspect > viewAspect) {
            float scale = targetHeight / bitmapHeightF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        } else {
            float scale = targetWidth / bitmapWidthF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        }

        Bitmap b1;
        if (scaler != null) {
            // this is used for minithumb and crop, so we want to filter here.
            b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), scaler, true);
        } else {
            b1 = source;
        }

        if (recycle && b1 != source) {
            source.recycle();
        }

        int dx1 = Math.max(0, b1.getWidth() - targetWidth);
        int dy1 = Math.max(0, b1.getHeight() - targetHeight);

        Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth, targetHeight);

        if (b2 != b1) {
            if (recycle || b1 != source) {
                b1.recycle();
            }
        }

        return b2;
    }
    
}
