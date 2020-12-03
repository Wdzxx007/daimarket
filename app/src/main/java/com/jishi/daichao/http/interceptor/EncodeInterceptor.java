package com.jishi.daichao.http.interceptor;
import com.jishi.daichao.app.App;
import com.jishi.daichao.utils.EncodeUtil;
import com.jishi.daichao.utils.ProConfigUtil;
import com.jishi.daichao.utils.SystemUtil;
import com.jishi.daichao.utils.TimeUtil;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 统一拦截器、参数加密
 * Created by laulee on 17/12/8.
 */

public class EncodeInterceptor implements Interceptor {

    private final static String POST = "POST";
    private final static String SOURCE = "source";
    private final static String VERSION = "version";
    private final static String PARAMS = "params";
    private final static String LANG = "lang";
    private final static String CLONE_ID = "clone_id";
    private final static String TIME = "time";
    private final static String SIGN = "sign";
    private final static String DEVICE_UDID = "device-udid";

    /**
     * requestBody拿到字符串内容
     *
     * @param request
     * @return
     */
    private static String bodyToString(final RequestBody request ) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer( );
            if( copy != null )
                copy.writeTo( buffer );
            else
                return "";
            return buffer.readUtf8( );
        } catch( final IOException e ) {
            return "did not work";
        }
    }

    @Override
    public Response intercept( Chain chain ) throws IOException {
        Request request = chain.request( );
        //post请求
        if( request.method( ).equals( POST ) ) {
            Request.Builder requestBuilder = request.newBuilder( );
            //form表单请求
            if( request.body( ) instanceof FormBody ) {
                FormBody oldFormBody = (FormBody) request.body( );
                String params = getEncodeParams( request, oldFormBody );
                //重新获取FormBody
                requestBuilder.post( getNewFormBuilder( params ) );
                request = requestBuilder.build( );
            } else if( request.body( ) instanceof MultipartBody ) {
                //MultipartBody 进行加密
                MultipartBody multipartBody = (MultipartBody) request.body( );
                requestBuilder.post( getNewMultipartBuidler( request, multipartBody ) );
                request = requestBuilder.build( );
            } else {
                //重新定义一个FormBody
                //拿到内层加密参数
                String params = getQueryParams( request.url( ) );
                String url = request.url( ).toString( );
                //重定向url路径
                requestBuilder.url( url.substring( 0, url.indexOf( "?" ) ) );
                requestBuilder.post( getNewFormBuilder( params ) );
                request = requestBuilder.build( );
            }
        }

        return chain.proceed( request );
    }

    /**
     * 重写MultipartBody参数
     *
     * @param request
     * @param body
     * @return
     */
    private MultipartBody getNewMultipartBuidler( Request request, MultipartBody body ) {
        //重新定义一个Builder
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder( );
        //取出传递的parts
        List<MultipartBody.Part> oldParts = body.parts( );
        JSONObject jsonObject = new JSONObject( );
        try {
            if( oldParts != null && oldParts.size( ) > 0 ) {
                for( MultipartBody.Part part : oldParts ) {
                    //如果不是字符串类型直接添加，如果是字符串类型加密添加
                    if( part.body( ).contentType( ).toString( )
                            .equals( "application/json; charset=UTF-8" ) || part.body( )
                            .contentType( ).toString( ).equals( "text/plain; charset=UTF-8" ) ) {
                        //拿到字符串参数
                        //获取字符串part的key
                        String name = partGetKey( part.headers( ) );
                        //获取字符串part的value
                        String value = bodyToString( part.body( ) );
                        jsonObject.put( name, value );
                    } else {
                        multipartBuilder.addPart( part );
                    }
                }
            }
            //判断是否有query参数
            HttpUrl httpUrl = request.url( );
            int querySize = httpUrl.querySize( );
            if( httpUrl.querySize( ) > 0 ) {
                for( int i = 0; i < querySize; i++ ) {
                    jsonObject.put( httpUrl.queryParameterName( i ),
                                    httpUrl.queryParameterValue( i ) );
                }
            }
        } catch( JSONException e ) {
            e.printStackTrace( );
        }
        String params = EncodeUtil.encode( jsonObject.toString( ) );
        String time = TimeUtil.getYMDTime( );
        String sign = getEncodeSign( time, params );
        multipartBuilder.addFormDataPart( SOURCE, ProConfigUtil.getConfigKey( "SOURCE" ) );
        multipartBuilder.addFormDataPart( VERSION, SystemUtil.getAPPVersion( App.getInstance( ) ) );
        multipartBuilder.addFormDataPart( LANG, ProConfigUtil.getConfigKey( "LANG" ) );
        multipartBuilder
                .addFormDataPart( DEVICE_UDID, SystemUtil.getDeviceOnlyId( App.getInstance( ) ) );
        multipartBuilder.addFormDataPart( CLONE_ID, "clone_id" );
        multipartBuilder.addFormDataPart( PARAMS, params );
        multipartBuilder.addFormDataPart( TIME, time );
        multipartBuilder.addFormDataPart( SIGN, sign );
        return multipartBuilder.build( );
    }

    /**
     * 获得part的key
     *
     * @param headers
     * @return
     */
    private String partGetKey(Headers headers ) {
        String key = headers.value( 0 );
        //查看源码得知 header name 进行拼接"form-data; name="得到
        key = key.substring( 17, key.length( ) - 1 );
        return key;
    }

    /**
     * 获取Form参数
     *
     * @param params
     * @return
     */
    private FormBody getNewFormBuilder( String params ) {
        FormBody.Builder newFormBuilder = new FormBody.Builder( );
        String time = TimeUtil.getYMDTime( );
        String sign = getEncodeSign( time, params );
        newFormBuilder.add( SOURCE, ProConfigUtil.getConfigKey( "SOURCE" ) );
        newFormBuilder.add( VERSION, SystemUtil.getAPPVersion( App.getInstance( ) ) );
        newFormBuilder.add( LANG, ProConfigUtil.getConfigKey( "LANG" ) );
        newFormBuilder.add( DEVICE_UDID, SystemUtil.getDeviceOnlyId( App.getInstance( ) ) );
        newFormBuilder.add( CLONE_ID, "clone_id" );
        newFormBuilder.add( PARAMS, params );
        newFormBuilder.add( TIME, time );
        newFormBuilder.add( SIGN, sign );
        return newFormBuilder.build( );
    }

    /**
     * 获取加密后的sign
     *
     * @param time
     * @param params
     * @return
     */
    private String getEncodeSign(String time, String params ) {
        StringBuffer sb = new StringBuffer( );
        sb.append( params );
        sb.append( time );

        StringBuffer encodeStr = new StringBuffer( );
        encodeStr.append( EncodeUtil.getMD5( sb.toString( ) ) );
        encodeStr.append( ProConfigUtil.getConfigKey( "APP_KEY" ) );
        return EncodeUtil.getMD5( encodeStr.toString( ) );
    }

    /**
     * 拿到form参数对params加密
     *
     * @param request
     * @param oldFormBody
     * @return
     */
    private String getEncodeParams(Request request, FormBody oldFormBody ) {
        JSONObject jsonObject = new JSONObject( );
        try {
            int paramSize = oldFormBody.size( );
            if( paramSize > 0 ) {
                for( int i = 0; i < paramSize; i++ ) {
                    jsonObject.put( oldFormBody.name( i ), oldFormBody.value( i ) );
                }
            }
            //判断是否有query参数
            HttpUrl httpUrl = request.url( );
            int querySize = httpUrl.querySize( );
            if( httpUrl.querySize( ) > 0 ) {
                for( int i = 0; i < querySize; i++ ) {
                    jsonObject.put( httpUrl.queryParameterName( i ),
                                    httpUrl.queryParameterValue( i ) );
                }
            }
        } catch( JSONException e ) {
            e.printStackTrace( );
        }
        return EncodeUtil.encode( jsonObject.toString( ) );
    }

    /**
     * 获取query参数并加密
     *
     * @param httpUrl
     * @return
     */
    private String getQueryParams(HttpUrl httpUrl ) {
        JSONObject jsonObject = new JSONObject( );
        try {
            for( int i = 0; i < httpUrl.querySize( ); i++ ) {
                jsonObject.put( httpUrl.queryParameterName( i ), httpUrl.queryParameterValue( i ) );
            }
        } catch( JSONException e ) {
            e.printStackTrace( );
        }
        return EncodeUtil.encode( jsonObject.toString( ) );
    }
}
