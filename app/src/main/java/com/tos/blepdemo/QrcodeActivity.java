package com.tos.blepdemo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.tos.util.MacUtil;
import com.tos.util.RGBLuminanceSource;

import java.util.Hashtable;

public class QrcodeActivity extends FragmentActivity implements OnClickListener {

    private String mac;

    //二维码
    private static final int CHOOSE_PIC = 0;
    private static final int PHOTO_PIC = 1;

    private ImageView qrcodeImageView = null;
    private String imgPath = null;
    private TextView macS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        //二维码
        setupViews();
        getQrcode();
    }

    private void getQrcode() {
//        String mac = "94:a1:a2:f4:13:45";
        if (null == mac || "".equals(mac)) {
            Toast.makeText(QrcodeActivity.this, "无法获取Mac地址,请连接网络 ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            //生成二维码图片，第一个参数是二维码的内容，第二个参数是正方形图片的边长，单位是像素
            Bitmap qrcodeBitmap = EncodingHandler.createQRCode(mac, 400);
            qrcodeImageView.setImageBitmap(qrcodeBitmap);
            macS.setText(mac);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static final String CODE = "utf-8";

    public static Bitmap getBarcode(String str, Integer width,
                                    Integer height) {

        if (width == null || width < 200) {
            width = 200;
        }

        if (height == null || height < 50) {
            height = 50;
        }

        try {
            // 文字编码
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, CODE);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(str,
                    BarcodeFormat.CODE_128, width, height, hints);

            return BitMatrixToBitmap(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * BitMatrix转换成Bitmap
     *
     * @param matrix
     * @return
     */
    private static Bitmap BitMatrixToBitmap(BitMatrix matrix) {
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
            }
        }
        return createBitmap(width, height, pixels);
    }

    /**
     * 生成Bitmap
     *
     * @param width
     * @param height
     * @param pixels
     * @return
     */
    private static Bitmap createBitmap(int width, int height, int[] pixels) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private void setupViews() {
        qrcodeImageView = (ImageView) findViewById(R.id.img1);
        qrcodeImageView.setOnClickListener(this);
    }

    //解析二维码图片,返回结果封装在Result对象中
    private com.google.zxing.Result parseQRcodeBitmap(String bitmapPath) {
        //解析转换类型UTF-8
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        //获取到待解析的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)
        //并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你
        options.inJustDecodeBounds = true;
        //此时的bitmap是null，这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //我们现在想取出来的图片的边长（二维码图片是正方形的）设置为400像素
        /**
         options.outHeight = 400;
         options.outWidth = 400;
         options.inJustDecodeBounds = false;
         bitmap = BitmapFactory.decodeFile(bitmapPath, options);
         */
        //以上这种做法，虽然把bitmap限定到了我们要的大小，但是并没有节约内存，如果要节约内存，我们还需要使用inSimpleSize这个属性
        options.inSampleSize = options.outHeight / 400;
        if (options.inSampleSize <= 0) {
            options.inSampleSize = 1; //防止其值小于或等于0
        }
        /**
         * 辅助节约内存设置
         *
         * options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
         * options.inPurgeable = true;
         * options.inInputShareable = true;
         */
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        //开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgPath = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PIC:
                    String[] proj = new String[]{MediaStore.Images.Media.DATA};
                    Cursor cursor = QrcodeActivity.this.getContentResolver().query(data.getData(), proj, null, null, null);

                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        System.out.println(columnIndex);
                        //获取到用户选择的二维码图片的绝对路径
                        imgPath = cursor.getString(columnIndex);
                    }
                    cursor.close();

                    //获取解析结果
                    Result ret = parseQRcodeBitmap(imgPath);
                    Toast.makeText(QrcodeActivity.this, "解析结果：" + ret.toString(), Toast.LENGTH_LONG).show();
                    break;
                case PHOTO_PIC:
                    String result = data.getExtras().getString("result");
                    Toast.makeText(QrcodeActivity.this, "解析结果：" + result, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img1: {
                //判断内容是否为空
//                String mac = "94:a1:a2:f4:13:45";
                if (null == mac || "".equals(mac)) {
                    Toast.makeText(QrcodeActivity.this, "无法获取Mac地址,请连接网络 ",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    //生成二维码图片，第一个参数是二维码的内容，第二个参数是正方形图片的边长，单位是像素
                    Bitmap qrcodeBitmap = EncodingHandler.createQRCode(mac, 400);
                    qrcodeImageView.setImageBitmap(qrcodeBitmap);
                    macS.setText(mac);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void init() {
        //获取mac地址
        mac = MacUtil.getMacAddress();
        macS = (TextView) findViewById(R.id.macS);
    }


}
