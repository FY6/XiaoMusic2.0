package com.yzx.xiaomusic.ui.album;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.yzx.commonlibrary.base.adapter.CommonBaseAdapter;
import com.yzx.commonlibrary.utils.DensityUtils;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.base.BaseMvpFragment;
import com.yzx.xiaomusic.model.entity.album.AlbumDetail;
import com.yzx.xiaomusic.model.entity.common.MusicInfo;
import com.yzx.xiaomusic.model.entity.common.SingerInfo;
import com.yzx.xiaomusic.model.entity.eventbus.MessageEvent;
import com.yzx.xiaomusic.service.ServiceManager;
import com.yzx.xiaomusic.ui.adapter.MusicAdapter;
import com.yzx.xiaomusic.ui.common.CoverInfoFragment;
import com.yzx.xiaomusic.ui.singer.SingerDetailsFragment;
import com.yzx.xiaomusic.utils.EventBusUtils;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.utils.MusicDataUtils;
import com.yzx.xiaomusic.utils.TimeUtils;
import com.yzx.xiaomusic.widget.CircleProgress;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

import static com.yzx.xiaomusic.Constant.KEY_COVER;
import static com.yzx.xiaomusic.Constant.KEY_DES;
import static com.yzx.xiaomusic.Constant.KEY_ID;
import static com.yzx.xiaomusic.Constant.KEY_NAME;
import static com.yzx.xiaomusic.Constant.KEY_TITLE;
import static com.yzx.xiaomusic.model.entity.eventbus.MessageEvent.TYPE_MUSIC_CHANGED;
import static com.yzx.xiaomusic.model.entity.eventbus.MessageEvent.TYPE_MUSIC_PAUSE;
import static com.yzx.xiaomusic.model.entity.eventbus.MessageEvent.TYPE_MUSIC_PLAYING;
import static com.yzx.xiaomusic.model.entity.eventbus.MessageEvent.TYPE_MUSIC_UPDATE_PROGRESS;
import static com.yzx.xiaomusic.ui.singer.SingerDetailsFragment.KEY_ID_SINGER;

/**
 * @author yzx
 * @date 2018/6/12
 * Description 专辑详情
 */
public class AlbumDetailFragment extends BaseMvpFragment<AlbumDetailPresenter> implements CommonBaseAdapter.OnItemClickListener {
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_little_bg)
    ImageView ivLittleBg;
    @BindView(R.id.iv_info)
    ImageView ivInfo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_creator_name)
    TextView tvCreatorName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.rl_user_info)
    LinearLayout rlUserInfo;
    @BindView(R.id.tv_evaluation_num)
    TextView tvEvaluationNum;
    @BindView(R.id.tv_share_num)
    TextView tvShareNum;
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(R.id.tv_multi_selection)
    TextView tvMultiSelection;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb)
    Toolbar tb;
    @BindView(R.id.tbBg)
    ImageView tbBg;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static final String KEY_INFO_SONG_SHEET = "infoSOngSheet";
    @BindView(R.id.tv_play_all)
    TextView tvPlayAll;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.iv_music_cover)
    ImageView ivMusicCover;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.tv_music_singer)
    TextView tvMusicSinger;
    @BindView(R.id.iv_play_pause)
    CircleProgress ivPlayPause;
    @BindView(R.id.iv_song_sheet)
    ImageView ivSongSheet;
    @BindView(R.id.layout_bottom_music_controller)
    LinearLayout layoutBottomMusicController;
    private MusicAdapter adapter;
    private Bundle arguments;

    private String name;
    private String cover;
    private String id;
    private MusicInfo musicInfo;
    private AlbumDetail.AlbumBeanX album;

    @Override
    protected int initContentViewId() {
        return R.layout.fragment_detail_album;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        //重新注册到View里
        loadService = LoadSir
                .getDefault()
                .register(recyclerView, (Callback.OnReloadListener) v -> mPresenter.getAlbumDetail(id));
        return view;
    }

    @Override
    protected AlbumDetailPresenter getPresenter() {
        return new AlbumDetailPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        arguments = getArguments();
        name = arguments.getString(KEY_NAME);
        cover = arguments.getString(KEY_COVER);
        id = arguments.getString(KEY_ID);

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView(LayoutInflater inflater, Bundle savedInstanceState) {

        initToolBar(tb);
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float totalScrollRange = appBarLayout.getTotalScrollRange();
            llHead.setAlpha(1f + ((float) verticalOffset) / totalScrollRange);
            tbBg.setAlpha(-((float) verticalOffset) / (totalScrollRange - DensityUtils.dip2px(40)));
        });

        //初始化上个页面传来的信息
        tvTitle.setText(R.string.album);
        tvName.setText(name);
//        tvSubTitle.setText(recommend);
//        tvSubTitle.setVisibility(TextUtils.isEmpty(recommend) ? View.GONE : View.VISIBLE);
        GlideUtils.loadBlurImg(getContext(), cover, ivBg);
        GlideUtils.loadBlurImg(getContext(), cover, tbBg);
        GlideUtils.loadImg(getContext(), cover, ivLittleBg);

        adapter = new MusicAdapter(getFragmentManager(), this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        if (args != null) {
            name = args.getString(KEY_NAME);
            cover = args.getString(KEY_COVER);
            id = arguments.getString(KEY_ID);
            mPresenter.getAlbumDetail(id);
            //初始化上个页面传来的信息
            tvTitle.setText(name);
            tvName.setText(name);
            GlideUtils.loadBlurImg(getContext(), cover, ivBg);
            GlideUtils.loadBlurImg(getContext(), cover, tbBg);
            GlideUtils.loadImg(getContext(), cover, ivLittleBg);
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        mPresenter.getAlbumDetail(id);
    }

    @SuppressLint("CheckResult")
    public void setData(AlbumDetail albumDetail) {

        album = albumDetail.getAlbum();

        tvCreatorName.setText(String.format("歌手：%s", album.getArtist().getName()));
        tvDate.setText(String.format("发行时间：%s", TimeUtils.getFormatData(album.getPublishTime(), "yyyy.M.d")));
        AlbumDetail.AlbumBeanX.InfoBean info = album.getInfo();
        tvEvaluationNum.setText(String.valueOf(info.getCommentCount()));
        tvShareNum.setText(String.valueOf(info.getShareCount()));
        Observable
                .fromIterable(album.getSongs())
                .map(songsBean -> {
                    MusicInfo musicInfo = new MusicInfo();
                    musicInfo.setMusicId(String.valueOf(songsBean.getId()));
                    musicInfo.setMusicName(songsBean.getName());
                    musicInfo.setMvId(String.valueOf(songsBean.getMvid()));
                    musicInfo.setDuration(songsBean.getDuration());
                    if (this.album != null) {
                        musicInfo.setAlbumId(String.valueOf(this.album.getId()));
                        musicInfo.setAlbumName(this.album.getName());
                        musicInfo.setAlbumCoverPath(this.album.getPicUrl());
                    }

                    List<AlbumDetail.AlbumBeanX.SongsBean.ArtistsBeanX> artists = songsBean.getArtists();
                    ArrayList<SingerInfo> singerInfos = new ArrayList<>();
                    for (int i = 0; i < artists.size(); i++) {
                        SingerInfo singerInfo = new SingerInfo();
                        AlbumDetail.AlbumBeanX.SongsBean.ArtistsBeanX artistsBeanX = artists.get(i);
                        singerInfo.setSingerId(String.valueOf(artistsBeanX.getId()));
                        singerInfo.setSingerName(artistsBeanX.getName());
                        singerInfos.add(singerInfo);
                    }
                    musicInfo.setSingerInfos(singerInfos);
                    return musicInfo;
                })
                .toList()
                .subscribe(musicInfos -> adapter.setData(musicInfos));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                showToast(R.string.share);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.rl_user_info, R.id.tv_evaluation_num, R.id.tv_share_num, R.id.tv_download, R.id.tv_multi_selection, R.id.tv_play_all, R.id.iv_little_bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_user_info:
                //TODO
                if (album != null) {
                    arguments.clear();
                    arguments.putString(KEY_ID_SINGER, String.valueOf(album.getArtist().getId()));
                    easyStart(new SingerDetailsFragment(), arguments);
                }
                break;
            case R.id.tv_evaluation_num:
                break;
            case R.id.tv_share_num:
                break;
            case R.id.tv_download:
                break;
            case R.id.tv_multi_selection:
                break;
            case R.id.tv_play_all:
                break;
            case R.id.iv_little_bg:
                arguments.clear();
                //TODO
                if (album == null) {
                    showToast(R.string.wait);
                } else {
                    arguments.putString(KEY_COVER, album.getPicUrl());
                    arguments.putString(KEY_TITLE, album.getName());
                    arguments.putString(KEY_DES, album.getDescription());
                    easyStart(new CoverInfoFragment(), arguments);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        playMusicWithStartFragment(this, adapter.datas, position);
    }

    @Override
    public void onResume() {
        super.onResume();
        initBottomMusicController(layoutBottomMusicController);
        musicInfo = service.getMusicInfo();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusUtils.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getType()) {
            case TYPE_MUSIC_CHANGED:
                service = ServiceManager.getInstance().getService();
                musicInfo = service.getMusicInfo();
                tvMusicName.setText(musicInfo.getMusicName());
                tvMusicSinger.setText(MusicDataUtils.getSingers(musicInfo));
                if (!musicInfo.isLocal()) {
                    GlideUtils.loadImg(getContext(), musicInfo.getAlbumCoverPath(), ivMusicCover);
                }
                break;
            case TYPE_MUSIC_PLAYING:
                ivPlayPause.setState(CircleProgress.STATE_PLAY);
                break;
            case TYPE_MUSIC_PAUSE:
                ivPlayPause.setState(CircleProgress.STATE_PAUSE);
                break;
            case TYPE_MUSIC_UPDATE_PROGRESS:
                Integer content = (Integer) event.getContent();
                ivPlayPause.setMax((int) musicInfo.getDuration());
                ivPlayPause.setProgress(content);
                break;
        }
    }
}
