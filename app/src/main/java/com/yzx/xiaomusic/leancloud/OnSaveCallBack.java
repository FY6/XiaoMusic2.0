package com.yzx.xiaomusic.leancloud;

import com.avos.avoscloud.AVException;

/**
 * Created by yzx on 2018/8/13.
 * Description
 */
public interface OnSaveCallBack {

    void onSuccess();
    void onFail(AVException e);
}
