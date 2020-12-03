package com.jishi.daichao.base.params;

import com.jishi.daichao.app.App;
import com.jishi.daichao.utils.SystemUtil;

/**
 * Created by zhuxingxing on 18/1/30.
 */

public class AuthCodeParams extends BaseParams {
    public String mobile;
    public String softType;
    public String version;
    public String apiVersion;
    public AuthCodeParams(){
        softType = "ymqb_and";
        version = SystemUtil.getAPPVersion(App.getContext());
        apiVersion = "v1";
    }

}
