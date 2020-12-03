package com.jishi.daichao.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sean on 15/9/24.
 */
public class BitmapUtil {
    //private String saveFilePath = Environment.getExternalStorageDirectory( ) + "/DCIM/camera";
    public static String saveFilePath = Environment.getExternalStorageDirectory( ) +
                                        "/DCIM/MyMeyeralDesign";

    //获取文件保存路径
    public static File getSaveFile(String saveFilePath ) {
        File jpgFile = null;
        //判断SD卡是否存在
        if( Environment.getExternalStorageState( ).equals( Environment.MEDIA_MOUNTED ) ) {

            Date date = new Date( );
            SimpleDateFormat format = new SimpleDateFormat( "yyyyMMddHHmmss" ); // 格式化时间
            String filename = format.format( date ) + ".jpg";
            File fileFolder = new File( saveFilePath );
            if( !fileFolder.exists( ) ) {
                // 如果目录不存在，则创建一个名为"loan"的目录
                fileFolder.mkdir( );
            }
            jpgFile = new File( fileFolder, filename );

        }
        return jpgFile;
    }

    //将图片储存到SD卡中
    public String saveToSDCard(byte[] data ) {
        try {
            File jpgFile = getSaveFile( saveFilePath );
            FileOutputStream outputStream = new FileOutputStream( jpgFile ); // 文件输出流
            outputStream.write( data ); // 写入sd卡中
            outputStream.close( ); // 关闭输出流
            return jpgFile.getPath( );
        }
        catch( Exception ex ) {
            return null;
        }

    }

    public static String saveToSDCard(Bitmap bitmap ) {
        return saveToSDCard( bitmap, true );
    }

    /**
     * 保存图片
     *
     * @param bitmap
     * @return
     */
    public static String saveToSDCard(Bitmap bitmap, boolean isRecycle ) {
        try {
            File jpgFile = getSaveFile( saveFilePath );
            if( !jpgFile.exists( ) ) {
                jpgFile.createNewFile( );
            }
            FileOutputStream outputStream = new FileOutputStream( jpgFile );
            //将图片写入文件
            bitmap.compress( Bitmap.CompressFormat.PNG, 100, outputStream );
            outputStream.flush( );
            outputStream.close( );
            if( isRecycle )
                recycleBitmap( bitmap );

            return jpgFile.getPath( );
        }
        catch( Exception ex ) {
            return null;
        }
    }

    /**
     * 保存图片
     *
     * @param bitmap
     * @param degree
     * @return
     */
    public static String saveRotatingFile(Bitmap bitmap, int degree, String filePath ) {
        try {
            Bitmap saveBitmap = rotatingBitmap( degree, bitmap );
            File jpgFile = new File( filePath );
            if( jpgFile.exists( ) ) {
                FileOutputStream outputStream = new FileOutputStream( jpgFile );
                //将图片写入文件
                saveBitmap.compress( Bitmap.CompressFormat.PNG, 100, outputStream );
                outputStream.flush( );
                outputStream.close( );
            }
            recycleBitmap( bitmap );
            recycleBitmap( saveBitmap );

            return jpgFile.getPath( );
        }
        catch( Exception ex ) {
            return null;
        }
    }

    //保存旋转图片
    public String saveRotatingFile(byte[] data, int degree ) {
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray( data, 0, data.length );
            Bitmap saveBitmap = rotatingBitmap( degree, bitmap );

            File jpgFile = getSaveFile( saveFilePath );
            FileOutputStream outputStream = new FileOutputStream( jpgFile );
            //将图片写入文件
            saveBitmap.compress( Bitmap.CompressFormat.PNG, 100, outputStream );
            recycleBitmap( bitmap );
            recycleBitmap( saveBitmap );

            return jpgFile.getPath( );
        }
        catch( Exception ex ) {
            return null;
        }
    }

    //释放bitmap
    private static void recycleBitmap( Bitmap bitmap ) {
        if( bitmap != null && !bitmap.isRecycled( ) ) {
            bitmap.recycle( );
            bitmap = null;
        }

    }

    //获取系统图片
    public static String getSYSBitmap(Intent data, Context context ) {

        if( data != null ) {
            String path = data.getStringExtra( "filePath" );
            if( path != null && !TextUtils.isEmpty( path ) ) {
                return path;
            }
            path = "";
            Uri imageUri = data.getData( );
            if( imageUri != null ) {
                if( imageUri.getPath( ) != null ) {
                    String uriPath = imageUri.getPath( );
                    if( uriPath.endsWith( ".jpg" ) ) {
                        return uriPath;
                    }
                }
                String[] imageData = { MediaStore.Images.Media.DATA };
                Cursor cursor = context.getContentResolver( ).query( imageUri, imageData, null,
                        null, null );
                if( cursor == null ) {
                    return "";
                }
                int column_index = cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
                if( cursor.moveToFirst( ) ) {
                    path = cursor.getString( column_index );
                }
                try {
                    cursor.close( );
                }
                catch( Exception e ) {
                    // TODO: handle exception
                }
            }
            return path;
        }
        return "";
    }

    /**
     * 将图片压缩到原来的compressSize
     *
     * @return
     */
    public static File compressBitmap(File dst, int compressSize ) {
        if( null != dst && dst.exists( ) ) {
            BitmapFactory.Options opts;
            opts = new BitmapFactory.Options( );
            opts.inJustDecodeBounds = true;
            int width = opts.outWidth / compressSize;
            int height = opts.outHeight / compressSize;
            BitmapFactory.decodeFile( dst.getPath( ), opts );
            // 计算图片缩放比例
            final int minSideLength = Math.min( width, height );
            opts.inSampleSize = computeSampleSize( opts, minSideLength, width * height );
            opts.inJustDecodeBounds = false;
            opts.inInputShareable = true;
            opts.inPurgeable = true;
            try {
                Bitmap saveBitmap = BitmapFactory.decodeFile( dst.getPath( ), opts );
                File jpgFile = getSaveFile( saveFilePath );
                FileOutputStream outputStream = new FileOutputStream( jpgFile );
                //将图片写入文件
                saveBitmap.compress( Bitmap.CompressFormat.PNG, 100, outputStream );

                outputStream.flush( );
                outputStream.close( );

                recycleBitmap( saveBitmap );
                return jpgFile;
            }
            catch( Exception e ) {
                e.printStackTrace( );
            } finally {

            }
        }
        return null;
    }

    //获取图片文件并压缩到合适的尺寸
    public static Bitmap getBitmapFromFile(File dst, int width, int height ) {
        if( null != dst && dst.exists( ) ) {
            BitmapFactory.Options opts = null;
            if( width > 0 && height > 0 ) {
                opts = new BitmapFactory.Options( );
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile( dst.getPath( ), opts );
                // 计算图片缩放比例
                final int minSideLength = Math.min( width, height );
                opts.inSampleSize = computeSampleSize( opts, minSideLength, width * height );
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile( dst.getPath( ), opts );
            }
            catch( OutOfMemoryError e ) {
                e.printStackTrace( );
            }
        }
        return null;
    }

    private static int computeSampleSize(BitmapFactory.Options options, int minSideLength,
                                         int maxNumOfPixels ) {
        int initialSize = computeInitialSampleSize( options, minSideLength, maxNumOfPixels );

        int roundedSize;
        if( initialSize <= 8 ) {
            roundedSize = 1;
            while( roundedSize < initialSize ) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = ( initialSize + 7 ) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength,
                                                int maxNumOfPixels ) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = ( maxNumOfPixels == -1 ) ? 1 : (int) Math.ceil(
                Math.sqrt( w * h / maxNumOfPixels ) );
        int upperBound = ( minSideLength == -1 ) ? 128 : (int) Math.min(
                Math.floor( w / minSideLength ), Math.floor( h / minSideLength ) );

        if( upperBound < lowerBound ) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if( ( maxNumOfPixels == -1 ) && ( minSideLength == -1 ) ) {
            return 1;
        } else if( minSideLength == -1 ) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree( String path ) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface( path );
            int orientation = exifInterface.getAttributeInt( ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL );
            switch( orientation ) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }
        catch( IOException e ) {
            e.printStackTrace( );
        }
        return degree;
    }

    /*
    * 旋转图片
    * @param angle
    * @param bitmap
    * @return Bitmap
    */
    public static Bitmap rotatingBitmap(int angle, Bitmap bitmap ) {
        //旋转图片 动作
        Matrix matrix = new Matrix( );
        matrix.postRotate( angle );
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap( bitmap, 0, 0, bitmap.getWidth( ),
                bitmap.getHeight( ), matrix, true );
        return resizedBitmap;
    }

    /**
     * 根据尺寸压缩图片
     *
     * @param image
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public static Bitmap ratio(Bitmap image, float pixelW, float pixelH ) {
        //转化为二进制流
        ByteArrayOutputStream os = new ByteArrayOutputStream( );
        image.compress( Bitmap.CompressFormat.PNG, 100, os );

        BitmapFactory.Options newOpts = new BitmapFactory.Options( );
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray( os.toByteArray( ), 0, os.toByteArray( ).length, newOpts );

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int size = w > h ? h : w;
        float pixelSize = pixelW > pixelH ? pixelH : pixelW;

        int be;
        if( size > pixelSize ) {
            be = (int) ( newOpts.outWidth / pixelSize );
        } else {
            be = (int) ( newOpts.outHeight / size );
        }
        if( be <= 0 ) {
            be = 1;
        }
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPurgeable = true;
        newOpts.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray( os.toByteArray( ), 0, os.toByteArray( ).length,
                newOpts );
    }
}
