#### 1.时刻通讯app预留有供第三方app调用的页面，参照Android的Intent.ACTION_SEND
####。直接调用方式如下：

```
private void sendShareMessage() {
        String packageName = "com.shiketongxun.rongxin.lite";//时刻通讯包名     
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);//主要用于判断用户手机是否安装了时刻通讯app
        if (intent != null) {
            Intent share = new Intent();
            share.setClassName(packageName, "com.yuntongxun.rongxin.lite.ui.PreStartActivity");
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.setAction(Intent.ACTION_SEND);
            share.setType("text/plain"); //分享的是文本类型，h5地址可以作为文本类型发送
            share.putExtra(Intent.EXTRA_TEXT, "https://blog.csdn.net/oudetu/article/details/78443826");//分享出去的内容
            startActivity(share);
            getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(getApplicationContext(), "你还没安装该应用，请先安装", Toast.LENGTH_LONG).show();
        }
}
```

#### 2.文件类型说明

```
博文地址：https://blog.csdn.net/oudetu/article/details/78443826
text/plain（纯文本）
text/html（HTML文档）
application/xhtml+xml（XHTML文档）
image/gif（GIF图像）
image/jpeg（JPEG图像）【PHP中为：image/pjpeg】
image/png（PNG图像）【PHP中为：image/x-png】
video/mpeg（MPEG动画）
application/octet-stream（任意的二进制数据）
application/pdf（PDF文档）
application/msword（Microsoft Word文件）
message/rfc822（RFC 822形式）
multipart/alternative（HTML邮件的HTML形式和纯文本形式，相同内容使用不同形式表示）
application/x-www-form-urlencoded（使用HTTP的POST方法提交的表单）
multipart/form-data（同上，但主要用于表单提交时伴随文件上传的场合）
```