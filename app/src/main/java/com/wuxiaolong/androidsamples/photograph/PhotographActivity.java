package com.wuxiaolong.androidsamples.photograph;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wuxiaolong.androidsamples.BaseActivity;
import com.wuxiaolong.androidsamples.R;
import com.wuxiaolong.androidsamples.utils.AppConfig;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PhotographActivity extends BaseActivity {
    @InjectView(R.id.imageViewShow)
    ImageView imageViewShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photograph);
        ButterKnife.inject(this);
    }

    /**
     * 相册取照片
     */
    @OnClick(R.id.btn_user_album)
    void albumOnClick() {
        try {
            // 选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，
            // 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,
                    AppConfig.REQUEST_CODE_USER_ALBUM);
        } catch (ActivityNotFoundException e) {

        }
    }

    /**
     * 拍照
     */
    private String capturePath = null;
    @OnClick(R.id.btn_photograph)
    void photographOnClick() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent(
                    "android.media.action.IMAGE_CAPTURE");
            String out_file_path = AppConfig.SAVED_IMAGE_DIR_PATH;
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            capturePath = AppConfig.SAVED_IMAGE_DIR_PATH
                    + System.currentTimeMillis() + ".jpg";
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(capturePath)));
            getImageByCamera
                    .putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(getImageByCamera,
                    AppConfig.BTN_PHOTOGRAPH);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡",
                    Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.photograph_cutting)
    void picPopupItemsOnClick() {
        // 实例化SelectPicPopupWindow
        picPopupWindow = new com.wuxiaolong.androidsamples.photograph.PicPopupWindow(PhotographActivity.this,
                picPopupItemsOnClick);
        // 显示窗口
        picPopupWindow.showAtLocation(
                PhotographActivity.this.findViewById(R.id.photograph_layout),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

    }
// 自定义的弹出框类

    private PicPopupWindow picPopupWindow;
    /**
     * 为弹出窗口实现监听类
     */
    private View.OnClickListener picPopupItemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent;
            picPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_user_album:
                    try {
                        // 选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，
                        // 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                        intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, AppConfig.PHOTOGRAPH);
                    } catch (ActivityNotFoundException e) {

                    }
                    break;
                case R.id.btn_photograph:

                    try {
                        // 拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE，
                        // 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        startActivityForResult(intent, AppConfig.PHOTOGRAPH);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

        }

    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /**
             * 用户相册
             */
            if (requestCode == AppConfig.REQUEST_CODE_USER_ALBUM) {
                if (data != null) {

                    // 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                    Uri mImageCaptureUri = data.getData();
                    // 返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
                    if (mImageCaptureUri != null) {

                        // try {
                        // // 这个方法是根据Uri获取Bitmap图片的静态方法,大图会内存溢出
                        // bitmap = MediaStore.Images.Media.getBitmap(
                        // this.getContentResolver(), mImageCaptureUri);
                        //
                        // if (bitmap != null) {
                        // add_camera.setImageBitmap(bitmap);
                        // }
                        // // add_camera.setImageURI(mImageCaptureUri);
                        //
                        // } catch (Exception e) {
                        // e.printStackTrace();
                        // }
                        getPath(mImageCaptureUri);
                    } else {
                        // android拍照获得图片URI为空的处理方法http://www.xuebuyuan.com/1929552.html
                        // 这样做取得是缩略图,以下链接是取得原始图片
                        // http://blog.csdn.net/beyond0525/article/details/8940840
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            // 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                            // Bitmap imageBitmap =
                            // extras.getParcelable("data");
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            mImageCaptureUri = Uri
                                    .parse(MediaStore.Images.Media.insertImage(
                                            getContentResolver(), imageBitmap,
                                            null, null));

                            // if (imageBitmap != null) {
                            // add_camera.setImageBitmap(imageBitmap);
                            // }

                            getPath(mImageCaptureUri);
                        }
                    }

                }

            }
        }
        /**
         * 拍照
         */
        if (requestCode == AppConfig.BTN_PHOTOGRAPH) {
            imagePath = capturePath;
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            // BitmapFactory.decodeFile(path, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
            opts.inJustDecodeBounds = false;
            try {
                Bitmap bmp = BitmapFactory.decodeFile(capturePath, opts);
                imageViewShow.setImageBitmap(bmp);
            } catch (OutOfMemoryError err) {
            }
        }
        if (requestCode == AppConfig.PHOTOGRAPH) {
            Uri mImageCaptureUri;
            if (data != null) {
                // 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                mImageCaptureUri = data.getData();
                // 返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
                if (mImageCaptureUri != null) {
                    startPhotoZoom(mImageCaptureUri);

                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        // 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片

                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        mImageCaptureUri = Uri
                                .parse(MediaStore.Images.Media.insertImage(
                                        getContentResolver(), imageBitmap,
                                        null, null));
                        startPhotoZoom(mImageCaptureUri);

                    }
                }

            }
        }
        if (requestCode == AppConfig.PHOTO_CUTTING) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                // 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                File baseFile = FileHelper
                        .getBaseFile(FileHelper.PATH_PHOTOGRAPH);
                if (baseFile == null) {
                    // showLongMessage("SD卡不可用,请检查SD卡情况");
                    Toast.makeText(PhotographActivity.this, "SD卡不存在，请插入SD卡",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String fileName = FileHelper.getFileName(); // 图片名称
                FileHelper.saveBitmap(photo, fileName, baseFile);

                String imagePath = Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + FileHelper.PATH_PHOTOGRAPH
                        + File.separator
                        + fileName;

                imageViewShow.setImageBitmap(photo);
            }

        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, AppConfig.PHOTO_CUTTING);
    }

    private String imagePath = "";

    public void getPath(Uri mImageCaptureUri) {
        Cursor cursor = getContentResolver().query(mImageCaptureUri, null,
                null, null, null);
        cursor.moveToFirst();
        String path = cursor.getString(1); // 获取的是图片的绝对路径
        imagePath = path;
        cursor.close();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        // BitmapFactory.decodeFile(path, opts);

        opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
        opts.inJustDecodeBounds = false;
        try {
            Bitmap bmp = BitmapFactory.decodeFile(path, opts);
            imageViewShow.setImageBitmap(bmp);
        } catch (OutOfMemoryError err) {
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photograph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
