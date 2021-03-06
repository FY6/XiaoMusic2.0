package com.yzx.commonlibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * 单位转换
 * Created by yzx on 2017/7/1.
 */

public class DensityUtils {


        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2px(float dpValue) {
//        dpValue*Resources.getSystem().getDisplayMetrics().density;
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(float pxValue) {

        return pxValue / Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * sp转px
     *
     * @param spValue
     * @return
     */
    public static float spToPx(float spValue) {
//        spValue*Resources.getSystem().getDisplayMetrics().scaledDensity;
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * px转sp
     *
     * @param pxValue
     * @return
     */
    public static float pxToSp(float pxValue) {
        return pxValue / Resources.getSystem().getDisplayMetrics().scaledDensity;
    }
}
