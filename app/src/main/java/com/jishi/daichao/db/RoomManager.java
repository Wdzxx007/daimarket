package com.jishi.daichao.db;

import com.jishi.daichao.db.service.GarbageService;

/**
 * @author Noway
 * @date 2020/5/19
 * @desc
 */
public class RoomManager {

    public static String getDbCount() {
        return "1";
    }

    public static GarbageService getGarbageService() {
        return GarbageService.getInstance();
    }



    public static void deleteAll() {
        GarbageService.getInstance().deleteAll();

    }

}
