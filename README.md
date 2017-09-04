# 原项目地址: https://github.com/1ziton/cordova-plugin-mapnavigator 修改版!

# 支持的平台
  Android 和 IOS
  
# 主要功能
  开启第三方地图应用如 百度 高德 进入导航界面 默认起点为 当前位置 终点为为传递的参数;

# 使用方法
```

 cordova.plugins.MapNavigator.baiMapNavigatorMethod('北京市亦庄经济技术开发区鹿鸣苑', result => {
                        if (result === 'false') {
                            //提示请安装百度或者高德app;
                            alert("未检测到导航软件，请安装百度地图或高度地图App");
                        }
                    }, error => {
                        console.log(error);
                    })
                          
```
# 相对于原项目修改的地方有(主要是android代码)
 1. plugin.xml 文件里 android平台下24行应改为: target-dir="src/cordova/plugin/MapNavigator"

 2. MapNavigator.java 里的if判断 应为:
 ```
  if (action.equals(ACTION_NAME)) {
                String destination = args.getString(0);
                this.coolMethod(destination, callbackContext);
                return true;
            }else{
                callbackContext.error("Invalid Action" + action);
                return false;
            }
 
 ```
 coolMethod 方法 :
 ```
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
 
 ```
