package com.jishi.daichao.utils;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sean on 15/10/18.
 */
public class TimeUtil {
    //24*60*60*1000 一天
    private static final long DAY_TIME = 24 * 60 * 60 * 1000;
    //60*60*1000 一小时
    private static final long HOUR_TIME = 60 * 60 * 1000;
    //60*1000
    private static final long MINUTE_TIME = 60 * 1000;
    //
    private static final long SECOND_TIME = 1 * 1000;
    //
    private static final String TIME_FORMAT_ERROR = "时间格式错误";


    public static String getYMDTime() {
        SimpleDateFormat dataFormat = new SimpleDateFormat( "yyyyMMddHHmmss" );
        String nowTime = dataFormat.format( new Date( ) );
        return nowTime;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat dataFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String nowTime = dataFormat.format( new Date( ) );
        return nowTime;
    }

    public static String getYMD(String time ) {
        if( TextUtils.isEmpty( time ) ) {
            return "";
        }
        time = time.replace( " ", "/" );
        String[] appTime = time.split( "/" );
        if( appTime.length > 0 ) {
            return appTime[0];
        }
        return "";
    }

    public static String getHMS(String time ) {
        if( TextUtils.isEmpty( time ) ) {
            return "";
        }
        time = time.replace( " ", "/" );
        String[] appTime = time.split( "/" );
        if( appTime.length > 0 ) {
            return appTime[1];
        }
        return "";
    }

    /**
     * 获取剩余时间
     *
     * @param limitTime
     * @return
     */
    public static String getSurplusTime(String limitTime ) {
        try {
            StringBuffer sb = new StringBuffer( );

            SimpleDateFormat limitFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            long time = limitFormat.parse( limitTime ).getTime( );
            SimpleDateFormat nowFormat = new SimpleDateFormat( "yyyyMMddHHmmss" );
            long nowTime = nowFormat.parse( getYMDTime( ) ).getTime( );

            long surplusTime = time - nowTime;
            if( surplusTime < 0 ) {
                return "";
            }

            int day = (int) ( surplusTime / ( DAY_TIME ) );
            int hour = (int) ( surplusTime % ( DAY_TIME ) / ( HOUR_TIME ) );
            if( day > 0 && hour < 1 ) {
                sb.append( day );
                sb.append( "天前" );
                return sb.toString( );
            }
            if( day > 0 && hour > 0 ) {
                day += 1;
                sb.append( day );
                sb.append( "天" );
                return sb.toString( );
            }
            if( day < 1 && hour > 0 ) {
                sb.append( hour );
                sb.append( "小时" );
                return sb.toString( );
            }
        }
        catch( ParseException e ) {
            e.printStackTrace( );
            return "";
        }
        return "";
    }

    /**
     * 比较当前时间
     *
     * @param formatTime
     */
    public static int compareToNow( String formatTime ) {
        SimpleDateFormat dataFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String nowTime = dataFormat.format( new Date( ) );

        int result = nowTime.compareTo( formatTime );
        return result;
    }

    /**
     * 判断时间时候是否在工作时间内
     *
     * @return
     */
    public static boolean getIsLimit() {
        int hours = Calendar.getInstance( ).get( Calendar.HOUR_OF_DAY );
        int minutes = Calendar.getInstance( ).get( Calendar.MINUTE );
        return !( hours < 9 || hours > 21 || ( hours == 9 && minutes >= 30 ) );
    }

    /**
     * 获取APP时间
     *
     * @param createTime 创建时间
     * @return
     */
    public static String getAPPTime(String createTime ) {
        try {
            StringBuffer sb = new StringBuffer( );

            SimpleDateFormat createFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            long time = createFormat.parse( createTime ).getTime( );
            SimpleDateFormat nowFormat = new SimpleDateFormat( "yyyyMMddHHmmss" );
            long nowTime = nowFormat.parse( getYMDTime( ) ).getTime( );

            long surplusTime = nowTime - time;
            int day = (int) ( surplusTime / DAY_TIME );
            int hour = (int) ( surplusTime % DAY_TIME / ( HOUR_TIME ) );
            int min = (int) ( surplusTime % DAY_TIME % HOUR_TIME / MINUTE_TIME );
            if( day > 0 && hour < 1 ) {
                sb.append( day );
                sb.append( "天前" );
                return sb.toString( );
            }
            if( day > 0 && hour > 0 ) {
                day += 1;
                sb.append( day );
                sb.append( "天前" );
                return sb.toString( );
            }
            if( day < 1 && hour > 0 ) {
                sb.append( hour );
                sb.append( "小时前" );
                return sb.toString( );
            }
            if( day < 1 && hour < 1 && min > 0 ) {
                sb.append( min );
                sb.append( "分钟前" );
                return sb.toString( );
            }
            if( day < 1 && hour < 1 && min < 1 ) {
                sb.append( "刚刚" );
                return sb.toString( );
            }
        }
        catch( ParseException e ) {
            e.printStackTrace( );
            return "";
        }
        return "";
    }


    public static void countDownTime(String beginTime, long postTime, final TextView lblCountDown,
                                     final ICountDownFinish countDownFinish ) {
        try {
            SimpleDateFormat beginFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            long time = beginFormat.parse( beginTime ).getTime( );
            SimpleDateFormat nowFormat = new SimpleDateFormat( "yyyyMMddHHmmss" );
            long nowTime = nowFormat.parse( getYMDTime( ) ).getTime( );

            long surplusTime = time - nowTime;
            if( surplusTime < 0 ) {
                return;
            }
            CountDownTimer timer = new CountDownTimer( surplusTime, postTime ) {
                @Override
                public void onTick( long millisUntilFinished ) {
                    int day = (int) ( millisUntilFinished / DAY_TIME );
                    int hour = (int) ( millisUntilFinished % DAY_TIME / ( HOUR_TIME ) );
                    int min = (int) ( millisUntilFinished % DAY_TIME % HOUR_TIME / MINUTE_TIME );
                    int second = (int) ( millisUntilFinished % DAY_TIME % HOUR_TIME % MINUTE_TIME /
                                         SECOND_TIME );
                    if( day > 0 ) {
                        lblCountDown.setText(
                                "距开抢购还剩" + day + "天" + hour + "小时" + min + "分" + second + "秒" );
                    } else if( hour > 0 ) {
                        lblCountDown.setText( "距开抢购还剩" + hour + "小时" + min + "分" + second + "秒" );
                    } else if( min > 0 ) {
                        lblCountDown.setText( "距开抢购还剩" + min + "分" + second + "秒" );
                    } else if( second > 0 ) {
                        lblCountDown.setText( "距开抢购还剩" + second + "秒" );
                    } else {
                        lblCountDown.setText( "马上开抢" );
                    }
                }

                @Override
                public void onFinish() {
                    countDownFinish.onFinish( );
                    cancel( );
                }
            };
            timer.start( );
        }
        catch( ParseException e ) {
            e.printStackTrace( );
        }


    }

    public interface ICountDownFinish {
        void onFinish();
    }

}

