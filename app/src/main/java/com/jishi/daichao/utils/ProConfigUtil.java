package com.jishi.daichao.utils;

import com.jishi.daichao.app.App;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties配置文件
 * Created by laulee on 17/9/13.
 */

public class ProConfigUtil {

    /**
     * 获得配置文件数据
     *
     * @param key
     * @return
     */
    public static String getConfigKey(String key ) {
        Properties properties = new Properties( );
        try {
            InputStream inputStream = App.getInstance( ).getAssets( )
                    .open( "config.properties" );
            properties.load( inputStream );
        } catch( FileNotFoundException e ) {
            e.printStackTrace( );
            return "";
        } catch( IOException e ) {
            e.printStackTrace( );
            return "";
        }
        return properties.getProperty( key );
    }
}
