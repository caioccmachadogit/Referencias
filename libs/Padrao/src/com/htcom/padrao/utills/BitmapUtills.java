package com.htcom.padrao.utills;

import java.io.File;
import java.io.FileOutputStream;
import java.text.Normalizer;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapUtills {

	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) 
	{ // BEST QUALITY MATCH
		final BitmapFactory.Options options = new BitmapFactory.Options(); 
		try {
	    	//First decode with inJustDecodeBounds=true to check dimensions
	 	    options.inJustDecodeBounds = true;
	 	    options.inSampleSize = 8;
	 	    BitmapFactory.decodeFile(path, options);
	 	 
	 	    // Calculate inSampleSize, Raw height and width of image
	 	    final int height = options.outHeight;
	 	    final int width = options.outWidth;
	 	    options.inPreferredConfig = Bitmap.Config.ALPHA_8;//RGB_565;
	 	    int inSampleSize = 1;
	 	 
	 	    if (height > reqHeight) 
	 	    {
	 	        inSampleSize = Math.round((float)height / (float)reqHeight);
	 	    }
	 	    int expectedWidth = width / inSampleSize;
	 	 
	 	    if (expectedWidth > reqWidth) 
	 	    {
	 	        //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
	 	        inSampleSize = Math.round((float)width / (float)reqWidth);
	 	    }
	 	 
	 	    options.inSampleSize = inSampleSize;
	 	 
	 	    // Decode bitmap with inSampleSize set
	 	    options.inJustDecodeBounds = false;
		}
	     catch (Exception e) {
	    	 Log.e("ERROR DECODE BITMAP", e.getMessage());
		}
	    return BitmapFactory.decodeFile(path, options);
	}

	public static void compactarBitmap(Bitmap bitmap, File dest) {
		try {
			FileOutputStream out = new FileOutputStream(dest.getAbsolutePath());
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			Log.e("ERROR COMPACTAR BITMAP", e.getMessage());
			e.printStackTrace();
		}

	}
	
	@SuppressLint("NewApi") public static String SemAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
