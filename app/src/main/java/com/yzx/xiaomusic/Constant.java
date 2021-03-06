package com.yzx.xiaomusic;

import android.os.Environment;

/**
 * Created by yzx on 2018/5/14.
 * Description
 */
public class Constant {
    /**
     * 友盟的Key
     */
    public static final String KEY_UMENG = "5af951658f4a9d5a42000040";
    public static final String KEY_BUGLY = "48270824a9";

    /**
     * baseUrl
     */
//    public static final String BASE_URL = "http://music.163.com/api/";
    public static final String BASE_URL = "https://api.imjad.cn/";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_COVER = "cover";

    public static final String KEY_TITLE = "title";
    public static final String KEY_DES = "des";
    public static final String KEY_TAG = "tag";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_TYPE = "type";

    /**
     * App缓存路径
     */
    public static final String PATH_APP = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XiaoMusic";

    /**
     * 缓存
     */
    public static final String CACHE = "/cache";
    /**
     * 歌词目录
     */
    public static final String LYRIC = "/lyric";
    /**
     * 歌曲缓存路径
     */
    public static final String MUSIC = "/music";
    /**
     * 图片缓存路径
     */
    public static final String IMG = "/img";
    /**
     * 歌曲缓存路径
     */
    public static final String DOWNLOAD = "/download";
    /**
     * 缓存绝对路径
     */
    public static final String PATH_ABSOLUTE_CACHE = PATH_APP + CACHE;

    /**
     * 歌词绝缓存对路径
     */
    public static final String PATH_ABSOLUTE_CACHE_LYRIC = PATH_APP + CACHE + LYRIC;

    /**
     * 歌曲缓存绝对路径
     */
    public static final String PATH_ABSOLUTE_CACHE_IMG = PATH_APP + CACHE + IMG;

    /**
     * 歌曲缓存绝对路径
     */
    public static final String PATH_ABSOLUTE_CACHE_MUSIC = PATH_APP + CACHE + MUSIC;

    /**
     * 下载绝对路径绝对路径
     */
    public static final String PATH_ABSOLUTE_DOWNLOAD = PATH_APP + DOWNLOAD;
    /**
     * 歌曲下载绝对路径绝对路径
     */
    public static final String PATH_ABSOLUTE_DOWNLOAD_MUISC = PATH_APP + DOWNLOAD + "/Music";

    /**
     * 封面下载绝对路径绝对路径
     */
    public static final String PATH_ABSOLUTE_DOWNLOAD_COVER = PATH_APP + DOWNLOAD + "/晓音乐相册";
    /**
     * 图片
     */
    public static final String PIC = "http://p2.music.126.net/o_iih2VXlBwk8Pb3I2J-5w==/109951163172034776.jpg?param=180y180";

    /**
     * MD5加密key
     */
    public static final String MD5_KEY = "y183399";
}
