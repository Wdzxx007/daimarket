package com.jishi.daichao.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import com.jishi.daichao.bean.CallRecordsBean;
import com.jishi.daichao.bean.ContactBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class ContactsUtils {
    /**
     * 获取通话记录
     * 所需权限:android.permission.READ_CALL_LOG,会弹出授权框
     * @param context
     * @return
     */
    public static List<CallRecordsBean> getCallRecords(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context.checkPermission(Manifest.permission.READ_CALL_LOG, android.os.Process.myPid(), android.os.Process.myUid())) {
            Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " desc");
            if (cursor == null) {
                return null;
            }

            List<CallRecordsBean> callRecordList = new ArrayList<>();
            while (cursor.moveToNext()) {
                try {
                    CallRecordsBean bean = new CallRecordsBean();
                    //号码
                    String mobile = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));

                    bean.setMobile(mobile);
                    //呼叫类型
                    String type;
                    switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)))) {
                        case CallLog.Calls.INCOMING_TYPE:
                            type = "呼入";
                            break;
                        case CallLog.Calls.OUTGOING_TYPE:
                            type = "呼出";
                            break;
                        case CallLog.Calls.MISSED_TYPE:
                            type = "未接";
                            break;
                        default:
                            type = "挂断";//应该是挂断.根据我手机类型判断出的
                            break;
                    }
                    bean.setType(type);

                    //开始通话时间
                    String callTime = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE));
                    bean.setCallTime(callTime);
                    //联系人
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
                    bean.setName(name);

                    //通话时间,单位:s
                    String duration = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
                    bean.setDuration(duration);

                    callRecordList.add(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            cursor.close();

            return callRecordList;
        }

        return null;
    }

    /**
     * 获取通讯录中所有联系人的简单信息
     *
     * @throws Throwable
     */
    public static List<ContactBean> getAllContact(Context context) throws Throwable {

        List<ContactBean> list = new ArrayList<>();
        Map<String, String> contactIdMap = new HashMap<String, String>();
        //获取联系人信息的Uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //获取ContentResolver
        ContentResolver contentResolver = context.getContentResolver();
        //查询数据，返回Cursor
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            return list;
        }
        while (cursor.moveToNext()) {
            StringBuilder sb = new StringBuilder();
            //获取联系人的ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            if (contactIdMap.containsKey(contactId)) {
                continue;
            }
            contactIdMap.put(contactId, contactId);
            ContactBean contactBean = new ContactBean();
            //获取联系人的姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contactBean.setName(name);
            //构造联系人信息
            sb.append("contactId=").append(contactId).append(",Name=").append(name);
            //查询电话类型的数据操作
            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            contactBean.setPhoneNumber(null);
            while (phones.moveToNext()) {
                String phoneNumber = phones.getString(phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                //添加Phone的信息
                contactBean.setPhoneNumber(phoneNumber);
            }
            phones.close();

            //查询Email类型的数据操作
            Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                    null, null);
            contactBean.setEmail(null);
            while (emails.moveToNext()) {
                String emailAddress = emails.getString(emails.getColumnIndex(
                        ContactsContract.CommonDataKinds.Email.DATA));
                //添加Email的信息
                contactBean.setEmail(emailAddress);
            }
            emails.close();
            list.add(contactBean);
        }
        cursor.close();
        return list;
    }

}
