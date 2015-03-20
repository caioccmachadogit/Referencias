package com.owleyes.moustache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class Viewer extends Activity implements OnCheckedChangeListener {

    private static final String SEE_WARNING = "warning";

    private static final String PREF_KEY_DELETE = "_to_delete";

    private static final String CHECKED = "ignored_warning";

    public static final int SCALE = 0;

    public static final int ROTATE = 1;

    public static final int MINUS = 2;

    public static final int PLUS = 3;

    /** The list of drawable resource ids. */
    private static final int[] imageList = { R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine,
            R.drawable.ten, R.drawable.eleven };

    /** The Linear Layout that contains all other elements. */
    private LinearLayout root_layout;

    /** ViewGroup to which we add moustaches. */
    private LinearLayout mMoustacheGroup;

    /** The picture being viewed. */
    private ImageView mCurrentPicture;

    /** The Horizontal Scrollbar we use to display the images we can add. */
    private FrameLayout mHorizontalScroll;

    /** The RelativeLayout for the Scrollbar. */
    private CustomRelativeLayout mRelative;

    /** The remove button. */
    private ImageView mRemove;

    /** The save button. */
    private Button mSave;

    /** Shared Preferences for this app. */
    private SharedPreferences mPreferences;

    /** The mode. Either Scaling or Rotating. */
    private ToggleButton mMode;

    private static final int MODE_ID = 10;

    /** The minus button or counterclockwise rotate. */
    private Button mMinus;

    private static final int MINUS_ID = 15;

    /** The plus button or clockwise rotate. */
    private Button mPlus;

    private static final int PLUS_ID = 20;

    /** The state of the current Viewer -- either rotate or scale. */
    private int mState;

    /** Handles holding down either the plus or minus buttons. */
    private Handler mHandler;

    /** The Thread to update the image in. */
    private UpdateImage _updateImageTask;

    /** The orientation of the image we are viewing. */
    private int mOrientation;

    /** Changes from rotate to scale mode. */
    private MenuItem switchButton;

    private AdView mAdView;

    private RelativeLayout mControls;

    private static final int REMOVE_ID = 30;

    private static final int SAVE_ID = 25;

    private static final int SCROLL_VIEW_SIZE = 105;

    /**
     * Depending on the orientation, this is the amount of screen space taken up
     * by the options -- the ScrollView and buttons.
     */
    private static final int OPTIONS_SIZE = 170;

    private static final String MY_IDENTIFIER = "Made with Moustache Madness!";
    private static final String MY_AD_ID = "a14f4c7839ee239";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void basicInit(int orientation) {
        root_layout = (LinearLayout) findViewById(R.id.root);
        root_layout.setBackgroundColor(15921906);

        mRelative = new CustomRelativeLayout(this);
        mRelative.setBackgroundColor(Color.rgb(242, 242, 242));

        LayoutParams fill = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        LayoutParams wrap = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mRelative.setLayoutParams(fill);
        root_layout.addView(mRelative);

        mAdView = new AdView(this, AdSize.BANNER, MY_AD_ID);

        AdRequest request = new AdRequest();
        request.addTestDevice("45A2D3C16C738A08425D22872911765E");
        mAdView.setLayoutParams(wrap);

        mAdView.setMinimumHeight(AdSize.BANNER.getHeight());
        mAdView.setMinimumWidth(AdSize.BANNER.getWidth());
        mAdView.setId(150);
        mRelative.addView(mAdView);
        mAdView.loadAd(request);
        mCurrentPicture = new ImageView(this);

        mCurrentPicture.setScaleType(ScaleType.CENTER_CROP);
        LayoutParams picParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        picParams.addRule(RelativeLayout.BELOW, 150);
        Display d = this.getWindowManager().getDefaultDisplay();
        int widthy = d.getWidth();
        int heighty = d.getHeight();
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            picParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mCurrentPicture.setMaxHeight(heighty - AdSize.BANNER.getHeight() - OPTIONS_SIZE);
            mCurrentPicture.setMaxWidth(widthy);
        } else {
            mCurrentPicture.setMaxHeight(heighty - AdSize.BANNER.getHeight());
            mCurrentPicture.setMaxWidth(widthy - OPTIONS_SIZE);
        }

        mCurrentPicture.setLayoutParams(picParams);

        mControls = new RelativeLayout(this);

        LayoutParams controlParams = null;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("Max height: ", (OPTIONS_SIZE - SCROLL_VIEW_SIZE) + "");
            controlParams = new LayoutParams(LayoutParams.FILL_PARENT, OPTIONS_SIZE - SCROLL_VIEW_SIZE);
            controlParams.bottomMargin = 10;
            controlParams.addRule(RelativeLayout.ABOVE, 1);
        } else {
            controlParams = new LayoutParams(OPTIONS_SIZE - SCROLL_VIEW_SIZE, LayoutParams.FILL_PARENT);
            controlParams.rightMargin = 10;
            // mControls.setOrientation(LinearLayout.VERTICAL);
            controlParams.addRule(RelativeLayout.LEFT_OF, 1);
        }
        // mControls.setPadding(0, 0, 0, -10)
        mControls.setMinimumHeight(55);
        mControls.setLayoutParams(controlParams);
        mRelative.addView(mControls);
        mRelative.addView(mCurrentPicture);
        mRelative.setEditable(mCurrentPicture);
    }

    /**
     * Populates the LienarLayout VG with the image resources from IMAGELIST.
     */
    private void addDraggableImages() {
        int counter = 0;
        mMoustacheGroup.setId(200);
        for (int i : imageList) {
            CustomImageView temp = new CustomImageView(this);
            temp.setImageResource(i);
            temp.setId(counter);
            counter++;
            mMoustacheGroup.addView(temp, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
    }

    /**
     * Adds the image specified by the Uri IMAGEURI to the current view.
     * 
     */
    private void addImage(Uri imageURI) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA, MediaStore.Images.Media.DESCRIPTION };
        Cursor cursor = getContentResolver().query(imageURI, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        int descInd = cursor.getColumnIndex(filePathColumn[1]);
        if (cursor.getString(descInd) != null && cursor.getString(descInd).equals(MY_IDENTIFIER)) {
            try {
                Bitmap b = android.provider.MediaStore.Images.Media.getBitmap(getContentResolver(), imageURI);
                if (b != null) {
                    mCurrentPicture.setImageBitmap(b);
                    mCurrentPicture.invalidate();
                    return;
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        mCurrentPicture.setImageBitmap(this.setUpPicture(filePath));

        mCurrentPicture.invalidate();
    }

    /**
     * Called by the constructor to set up the views. Because we need to keep
     * track of a lot of the View elements, its easier for us to inflate each
     * View in the Class rather than in the XML. This is where that happens.
     */
    private void init(LayoutParams removeLP, android.widget.LinearLayout.LayoutParams minusLP, android.widget.LinearLayout.LayoutParams modeLP, android.widget.LinearLayout.LayoutParams plusLP,
            LayoutParams saveLP, boolean portrait) {

        mRemove = new ImageView(this);
        mRemove.setLayoutParams(removeLP);
        mRemove.setImageResource(R.drawable.trash1);
        mRemove.setId(REMOVE_ID);
        mRelative.setTrashIcon(mRemove);
        mRemove.setMaxHeight(Viewer.OPTIONS_SIZE - Viewer.SCROLL_VIEW_SIZE);
        if (portrait) {
            mRemove.setPadding(0, 0, (int) getResources().getDimension(R.dimen.five), 0);
        } else {
            mRemove.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.five));
        }
        mRemove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelative.removeView(mRelative.getSelectedImage());
            }
        });

        mMinus = createButton(R.drawable.minus, minusLP, MINUS_ID);
        mMinus.setText("");
        mMinus.setMaxHeight(Viewer.OPTIONS_SIZE - Viewer.SCROLL_VIEW_SIZE);
        mMinus.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                _updateImageTask.setState(mState, Viewer.MINUS);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // if (mState == Viewer.SCALE) {
                        // mMinus.setBackgroundResource(R.drawable.minus2);
                        // } else {
                        // mMinus.setBackgroundResource(R.drawable.ctr_clk2);
                        // }
                        currentTime = System.currentTimeMillis();
                        mHandler.removeCallbacks(_updateImageTask);
                        mHandler.postDelayed(_updateImageTask, 30);
                        return true;
                    case MotionEvent.ACTION_OUTSIDE:
                        // if (mState == Viewer.SCALE) {
                        // mMinus.setBackgroundResource(R.drawable.minus_pressed);
                        // } else {
                        // mMinus.setBackgroundResource(R.drawable.ctr_clk_pressed);
                        // }
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                    case MotionEvent.ACTION_UP:
                        // if (mState == Viewer.SCALE) {
                        // mMinus.setBackgroundResource(R.drawable.minus_pressed);
                        // } else {
                        // mMinus.setBackgroundResource(R.drawable.ctr_clk_pressed);
                        // }
                        if (System.currentTimeMillis() - currentTime < 200) {
                            mRelative.handleEvent(mState, -3);
                        }
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                }

                return true;
            }

            private long currentTime;
        });

        mPlus = createButton(R.drawable.plus, plusLP, PLUS_ID);
        mPlus.setText("");
        mPlus.setMaxHeight(Viewer.OPTIONS_SIZE - Viewer.SCROLL_VIEW_SIZE);
        mPlus.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                _updateImageTask.setState(mState, Viewer.PLUS);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // if (mState == Viewer.SCALE) {
                        // mPlus.setBackgroundResource(R.drawable.plus2);
                        // } else {
                        // mPlus.setBackgroundResource(R.drawable.clk2);
                        // }
                        currentTime = System.currentTimeMillis();
                        mHandler.removeCallbacks(_updateImageTask);
                        mHandler.postDelayed(_updateImageTask, 30);
                        return true;
                    case MotionEvent.ACTION_OUTSIDE:
                        // if (mState == Viewer.SCALE) {
                        // mPlus.setBackgroundResource(R.drawable.plus_pressed);
                        // } else {
                        // mPlus.setBackgroundResource(R.drawable.clk_pressed);
                        // }
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                    case MotionEvent.ACTION_UP:
                        // if (mState == Viewer.SCALE) {
                        // mPlus.setBackgroundResource(R.drawable.plus_pressed);
                        // } else {
                        // mPlus.setBackgroundResource(R.drawable.clk_pressed);
                        // }
                        if (System.currentTimeMillis() - currentTime < 200) {
                            mRelative.handleEvent(mState, 3);
                        }
                        mHandler.removeCallbacks(_updateImageTask);
                        return true;
                }
                return true;
            }

            private long currentTime;
        });
        mMode = new ToggleButton(this);
        if (portrait) {
            mMode.setPadding(0, 0, 0, 0);
        }
        mMode.setMaxHeight(Viewer.OPTIONS_SIZE - Viewer.SCROLL_VIEW_SIZE);
        mMode.setLayoutParams(modeLP);
        mMode.setBackgroundResource(R.drawable.rotate_switch);
        mMode.setTextOff("");
        mMode.setTextOn("");
        mMode.setChecked(true);
        mMode.setId(MODE_ID);
        mMode.setOnCheckedChangeListener(this);

        mState = SCALE;

        mSave = new Button(this);
        mSave.setBackgroundResource(R.drawable.save);
        mSave.setLayoutParams(saveLP);
        mSave.setId(SAVE_ID);
        mSave.setMaxHeight(Viewer.OPTIONS_SIZE - Viewer.SCROLL_VIEW_SIZE);
        mSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomImageView view = (CustomImageView) mRelative.getSelectedImage();
                mRelative.removeAllSelected();
                mRelative.setDrawingCacheEnabled(true);
                Bitmap b = mRelative.getDrawingCache();
                final Bitmap cropped = Bitmap.createBitmap(b, mCurrentPicture.getLeft(), mCurrentPicture.getTop(), mCurrentPicture.getWidth(), mCurrentPicture.getHeight());
                mRelative.setDragging(view);

                String warning = mPreferences.getString(SEE_WARNING, "not_set");
                if (!warning.equals(CHECKED)) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Viewer.this);
                    LayoutInflater inflater = Viewer.this.getLayoutInflater();
                    View dialog = inflater.inflate(R.layout.save_location_warning, null);
                    alert.setView(dialog);
                    final CheckBox cb = (CheckBox) dialog.findViewById(R.id.ignore_warning);

                    alert.setNeutralButton("Okay", new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (cb.isChecked()) {
                                Editor edit = Viewer.this.mPreferences.edit();
                                edit.putString(SEE_WARNING, CHECKED);
                                edit.commit();
                            }
                            if (saveInternal(cropped) != null) {
                                Toast.makeText(Viewer.this, "Saved Successfully", 1000).show();
                            } else {
                                Toast.makeText(Viewer.this, "Save Failed", 1000).show();
                            }
                        }
                    });
                    alert.create().show();
                } else {
                    if (saveInternal(cropped) != null) {
                        Toast.makeText(Viewer.this, "Saved Successfully", 1000).show();
                    } else {
                        Toast.makeText(Viewer.this, "Save Failed", 1000).show();
                    }
                }
                mCurrentPicture.setDrawingCacheEnabled(false);

            }
        });

        mMoustacheGroup = new LinearLayout(this);

        if (portrait) {
            mHorizontalScroll = new CustomHorizontalScrollView(this);
        } else {
            mHorizontalScroll = new CustomScrollView(this);
        }
        mHorizontalScroll.setId(1);
        LayoutParams lp = null;
        if (portrait) {
            lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else {
            lp = new LayoutParams(100, LayoutParams.FILL_PARENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        // mHorizontalScroll.setBackgroundColor(Color.rgb(242, 242, 242));
        mHorizontalScroll.setLayoutParams(lp);
        mRelative.addView(mHorizontalScroll);

        mMoustacheGroup.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        if (!portrait) {
            mMoustacheGroup.setOrientation(LinearLayout.VERTICAL);
        }
        mHorizontalScroll.addView(mMoustacheGroup);
    }

    private void initPortrait() {
        Resources res = getResources();
        LayoutParams removeLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        removeLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        removeLP.leftMargin = (int) res.getDimension(R.dimen.three);

        LinearLayout.LayoutParams minusLP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        minusLP.rightMargin = (int) res.getDimension(R.dimen.five);

        LinearLayout.LayoutParams plusLP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        plusLP.leftMargin = (int) res.getDimension(R.dimen.five);

        LinearLayout.LayoutParams modeLP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        LayoutParams saveLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        saveLP.rightMargin = (int) res.getDimension(R.dimen.three);
        saveLP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        LinearLayout layout = new LinearLayout(this);
        LayoutParams layoutLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutLP.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layout.setLayoutParams(layoutLP);

        init(removeLP, minusLP, modeLP, plusLP, saveLP, true);

        layout.addView(mMinus);
        layout.addView(mMode);
        layout.addView(mPlus);
        mControls.addView(mRemove);
        mControls.addView(layout);

        mControls.addView(mSave);
    }

    private void initLandscape() {
        Resources res = getResources();
        LayoutParams removeLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // removeLP.rightMargin = (int) res.getDimension(R.dimen.two);
        removeLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        LinearLayout.LayoutParams minusLP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        minusLP.topMargin = (int) res.getDimensionPixelOffset(R.dimen.five);

        LinearLayout.LayoutParams plusLP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        plusLP.bottomMargin = (int) res.getDimensionPixelOffset(R.dimen.five);

        LinearLayout.LayoutParams modeLP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        LayoutParams saveLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        saveLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        LinearLayout layout = new LinearLayout(this);
        LayoutParams layoutLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutLP.addRule(RelativeLayout.CENTER_VERTICAL);
        layout.setLayoutParams(layoutLP);
        layout.setOrientation(LinearLayout.VERTICAL);

        init(removeLP, minusLP, modeLP, plusLP, saveLP, false);

        layout.addView(mPlus);
        layout.addView(mMode);
        layout.addView(mMinus);

        mControls.addView(mRemove);
        mControls.addView(layout);
        mControls.addView(mSave);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentPicture = null;
        mMoustacheGroup = null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHandler = new Handler();

        mPreferences = this.getSharedPreferences(Main.PREFS_FILE, 0);
        setContentView(R.layout.nothing);

        // Inflate all the views.
        int orientation = getResources().getConfiguration().orientation;
        basicInit(orientation);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            initPortrait();

        } else {
            initLandscape();
        }

        Intent intent = getIntent();
        Uri imageURI = null;
        Log.e("URI:", intent.getData() + "");
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_SEND)) {
            Bundle extras = intent.getExtras();
            if (extras.containsKey(Intent.EXTRA_STREAM)) {
                imageURI = (Uri) extras.getParcelable(Intent.EXTRA_STREAM);
            }
        } else if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_VIEW)) {
            imageURI = intent.getData();
        } else {
            imageURI = (Uri) intent.getParcelableExtra("image");
        }

        addImage(imageURI);

        addDraggableImages();

        _updateImageTask = new UpdateImage(mRelative, mHandler);

        /*
         * Set<String> strings = new HashSet<String>(); strings =
         * mPreferences.getStringSet(PREF_KEY_DELETE, strings); if
         * (!strings.isEmpty()) { Iterator<String> itr = strings.iterator();
         * while (itr.hasNext()) { new File(itr.next()).delete(); } }
         */
    }

    /**
     * This function takes a filepath as a parameter and returns the
     * corresponding image, correctly scaled and rotated (if necessary)
     * 
     * @param filePath
     *            The filepath of the image
     * @return the scaled and rotated bitmap
     */
    private Bitmap setUpPicture(String filePath) {

        int w = 512;
        int h = 384; // size that does not lead to OutOfMemoryException on Nexus
        // One
        Bitmap b = BitmapFactory.decodeFile(filePath);

        // Hack to determine whether the image is rotated
        boolean rotated = b.getWidth() > b.getHeight();

        Bitmap resultBmp = null;

        // If not rotated, just scale it
        int degree;
        if ((mOrientation = degreeRotated(filePath)) == 0) {
            resultBmp = Bitmap.createScaledBitmap(b, w, h, true);
            b.recycle();
            b = null;
            // If rotated, scale it by switching width and height and then
            // rotated it
        } else {
            Bitmap scaledBmp = Bitmap.createScaledBitmap(b, w, h, true);
            b.recycle();
            b = null;
            Matrix mat = new Matrix();
            mat.postRotate(mOrientation);
            resultBmp = Bitmap.createBitmap(scaledBmp, 0, 0, w, h, mat, true);

            // Release image resources
            scaledBmp.recycle();
            scaledBmp = null;
        }
        return resultBmp;
    }

    /**
     * RETURNS the number of degrees the image specified by FILEPATH is rotate
     * (i.e. what degree rotation the phone was at while taking the picture.
     * 
     */
    private int degreeRotated(String filePath) {
        try {
            JpegImageMetadata meta = ((JpegImageMetadata) Sanselan.getMetadata(new File(filePath)));
            TiffImageMetadata data = null;
            if (meta != null) {
                data = meta.getExif();
            }
            int orientation = 0;
            if (data != null) {
                orientation = data.findField(ExifTagConstants.EXIF_TAG_ORIENTATION).getIntValue();
            } else {
                String[] projection = { Images.ImageColumns.ORIENTATION };
                Cursor c = getContentResolver().query(Uri.fromFile(new File(filePath)), projection, null, null, null);

                if (c != null && c.moveToFirst()) {
                    orientation = c.getInt(0);
                }
            }
            switch (orientation) {
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;

            }
            /*
             * } catch (JpegProcessingException e1) { e1.printStackTrace(); }
             * catch (MetadataException e) { e.printStackTrace(); }
             */} catch (ImageReadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            /* Scale mode */
            mMinus.setBackgroundResource(R.drawable.minus);
            mPlus.setBackgroundResource(R.drawable.plus);
            mMode.setBackgroundResource(R.drawable.rotate_switch);
            mState = SCALE;
        } else {
            /* Rotate mode */
            mMinus.setBackgroundResource(R.drawable.ctr_clk);
            mPlus.setBackgroundResource(R.drawable.clk);
            mMode.setBackgroundResource(R.drawable.scale_switch);
            mState = ROTATE;
        }

    }

    private Button createButton(int resource, android.widget.LinearLayout.LayoutParams minusLP, int id) {
        Button button = new Button(this);
        button.setBackgroundResource(resource);
        button.setLayoutParams(minusLP);
        button.setId(id);
        return button;
    }

    private String saveInternal(Bitmap image) {
        // String save = null; // =
        Log.e("HEIGHT OF SCROLLVIEW", this.mHorizontalScroll.getHeight() + "");
        Uri uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(Viewer.this.getContentResolver(), image, null, null));
        String save = this.getRealPathFromURI(uri);

        Log.v("Moustache_Madness", "Save path: " + save);
        Toast.makeText(this, save, 2000);

        ContentValues cv = new ContentValues();

        cv.put(android.provider.MediaStore.Images.Media.ORIENTATION, mOrientation);
        cv.put(android.provider.MediaStore.Images.Media.DESCRIPTION, Viewer.MY_IDENTIFIER);
        int numRows = this.getContentResolver().update(uri, cv, null, null);

        Log.v("Moustache Madness", numRows + " rows were updated");
        if (save == null) {
            Log.e("Moustache Madness", "Save failed");
            Toast.makeText(this, "Save failed", 2000);
            return null;
        }
        MediaScannerConnection msc = null;
        MyScannerClient client = new MyScannerClient(save);
        msc = new MediaScannerConnection(Viewer.this, client);
        client.setScanner(msc);

        msc.connect();
        // }
        return save;
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        switchButton = (MenuItem) menu.findItem(R.id.switch_mode);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                CustomImageView view = (CustomImageView) mRelative.getSelectedImage();
                mRelative.removeAllSelected();
                mRelative.setDrawingCacheEnabled(true);
                Bitmap b = mRelative.getDrawingCache();
                final Bitmap cropped = Bitmap.createBitmap(b, mCurrentPicture.getLeft(), mCurrentPicture.getTop(), mCurrentPicture.getWidth(), mCurrentPicture.getHeight());
                mRelative.setDragging(view);

                String path = saveInternal(cropped);
                if (path == null) {
                    // error
                    return true;
                }
                sharingIntent.setType("image/png");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                /*
                 * Editor editor = mPreferences.edit(); Set<String> strings =
                 * new HashSet<String>(); strings =
                 * mPreferences.getStringSet(PREF_KEY_DELETE, strings);
                 * strings.add(path); editor.putStringSet(PREF_KEY_DELETE,
                 * strings);
                 */

                break;
            case R.id.switch_mode:
                if (mState == ROTATE) {
                    /* Scale mode */
                    mMinus.setBackgroundResource(R.drawable.minus);
                    mPlus.setBackgroundResource(R.drawable.plus);
                    mMode.setBackgroundResource(R.drawable.rotate_switch);
                    mState = SCALE;
                } else {
                    /* Rotate mode */
                    mMinus.setBackgroundResource(R.drawable.ctr_clk);
                    mPlus.setBackgroundResource(R.drawable.clk);
                    mMode.setBackgroundResource(R.drawable.scale_switch);
                    mState = ROTATE;
                }
        }
        return true;
    }

    private static class MyScannerClient implements MediaScannerConnectionClient {
        private MediaScannerConnection _msc;
        private String _file;

        public MyScannerClient(String file) {
            _file = file;
        }

        public void setScanner(MediaScannerConnection msc) {
            _msc = msc;
        }

        @Override
        public void onMediaScannerConnected() {
            _msc.scanFile(_file, "*/*");
        }

        @Override
        public void onScanCompleted(String path, Uri uri) {

        }

    }
}
