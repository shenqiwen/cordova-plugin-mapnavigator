package cordova.plugin.MapNavigator;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import android.text.TextUtils;
import android.content.Intent;
import android.widget.Toast;

/**
 * This class echoes a string called from JavaScript.
 */
public class MapNavigator extends CordovaPlugin {
    private static final String GAODE_PACKAGE_NAME = "com.autonavi.minimap";  
    private static final String BAIDU_PACKAGE_NAME = "com.baidu.BaiduMap";
    public static final String ACTION_NAME = "baiMapNavigatorMethod";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (action.equals(ACTION_NAME)) {
                String destination = args.getString(0);
                this.coolMethod(destination, callbackContext);
                return true;
            }else{
                callbackContext.error("Invalid Action" + action);
                return false;
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }


    /**
     * 启动App进行导航
     * 默认起点为  当前位置
     * @param destination 目的地
     */
    private void coolMethod(String destination, CallbackContext callbackContext) {
        if(isInstallByRead(BAIDU_PACKAGE_NAME)){  // 百度地图
            Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("baidumap://map/direction?origin=我的位置&destination="+destination+""));
            intent.setPackage("com.baidu.BaiduMap");
            cordova.getActivity().startActivity(intent); //启动调用
            return;
        }else if(isInstallByRead(GAODE_PACKAGE_NAME)){  // 高德地图
            Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("androidamap://route/plan/?&dname="+destination+"&dev=0&t=0"));
            intent.setPackage("com.autonavi.minimap");
            cordova.getActivity().startActivity(intent);
            return;
        }else{
            callbackContext.success("false");
            // Toast.makeText(cordova.getActivity(), "导航失败！请安装百度或高德地图", Toast.LENGTH_SHORT)
            //             .show();
        }

    }

    /**
     * 根据包名检测某个APP是否安装
     * @param packageName 包名
     * @return true 安装 false 没有安装
     */
    public boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
}
