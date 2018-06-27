package com.yzx.xiaomusic.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.yzx.commonlibrary.utils.DensityUtils;
import com.yzx.xiaomusic.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by yzx on 2018/5/31.
 * Description
 */
public class GlideUtils {

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_HEAD = 1;
    public static final int TYPE_SONG_SHEET = 2;
    public static final int TYPE_BG_SINGER =3;
    public static final int TYPE_BG_USER =4;

    public static final int TYPE_TRANSFORM_DEFAULT = 0;
    public static final int TYPE_TRANSFORM_BLUR = 1;


    @SuppressLint("CheckResult")
    public static void loadImg(Context context, Object resource, ImageView imageView) {
        loadImg(context, resource, TYPE_DEFAULT, TYPE_TRANSFORM_DEFAULT, imageView, false);
    }

    @SuppressLint("CheckResult")
    public static void loadImg(Context context, Object resource, int transform, ImageView imageView) {
        loadImg(context, resource, TYPE_DEFAULT, transform, imageView, false);
    }

    @SuppressLint("CheckResult")
    public static void loadImg(Context context, Object resource, int transform, ImageView imageView, boolean anim) {
        loadImg(context, resource, TYPE_DEFAULT, transform, imageView, anim);
    }

    @SuppressLint("CheckResult")
    public static void loadImg(Context context, Object resource, int type, int transform, ImageView imageView, boolean anim) {

        //占位图
        int placeholder;
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        switch (type) {
            case TYPE_HEAD:
                placeholder = R.drawable.ic_default_head;
                break;
            case TYPE_SONG_SHEET:
                placeholder = R.drawable.ic_default_song_sheet;
                break;
            case TYPE_BG_SINGER:
                placeholder = R.drawable.ud;
                break;
            case TYPE_BG_USER:
                placeholder = R.drawable.pz;
                break;
            default:
                placeholder = R.drawable.zhanweitu;
                break;
        }
        options.placeholder(placeholder).error(placeholder);

        //图形转换
        Transformation<Bitmap> transformation = null;
        switch (transform) {
            case TYPE_TRANSFORM_DEFAULT:
                transformation = new RoundedCornersTransformation(DensityUtils.dip2px(context, 3), 0);
                break;
            case TYPE_TRANSFORM_BLUR:
                transformation = new BlurTransformation(10, 40);
                break;
        }
        if (transformation != null) {
            options.transform(transformation);
        }

        //加载动画(会造成transform效果失效，但是刷新后就好了)
        DrawableTransitionOptions transitionOptions = DrawableTransitionOptions.withCrossFade(300);

        RequestBuilder<Drawable> apply = Glide.with(context)
                .load(resource)
                .apply(options);

        if (anim) {
            apply.transition(transitionOptions);
        }

        apply.into(imageView);
    }

    public static void loadBlurImg(Context context, Object resource, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(context).load(resource)
                .apply(requestOptions.transform(new BlurTransformation(10, 40)))
                .transition(DrawableTransitionOptions.withCrossFade(300)).into(imageView);
    }
}
