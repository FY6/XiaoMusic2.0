package com.yzx.xiaomusic.ui.songsheet.detail;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.yzx.commonlibrary.base.adapter.CommonBaseAdapter;
import com.yzx.commonlibrary.utils.DensityUtils;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.base.BaseMvpFragment;
import com.yzx.xiaomusic.model.entity.common.MusicInfo;
import com.yzx.xiaomusic.model.entity.common.SingerInfo;
import com.yzx.xiaomusic.model.entity.songsheet.SongSheetDetail;
import com.yzx.xiaomusic.ui.adapter.MusicAdapter;
import com.yzx.xiaomusic.ui.common.CoverInfoFragment;
import com.yzx.xiaomusic.ui.usercenter.UserCenterFragment;
import com.yzx.xiaomusic.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

import static com.yzx.xiaomusic.Constant.KEY_COVER;
import static com.yzx.xiaomusic.Constant.KEY_DES;
import static com.yzx.xiaomusic.Constant.KEY_ID;
import static com.yzx.xiaomusic.Constant.KEY_NAME;
import static com.yzx.xiaomusic.Constant.KEY_TAG;
import static com.yzx.xiaomusic.Constant.KEY_TITLE;
import static com.yzx.xiaomusic.ui.usercenter.UserCenterFragment.KEY_USER_ID;

/**
 * @author yzx
 * @date 2018/6/12
 * Description 歌单详情
 */
public class SongSheetDetailFragment extends BaseMvpFragment<SongSheetDetailPresenter> implements CommonBaseAdapter.OnItemClickListener {
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_play_count)
    TextView tvPlayCount;
    @BindView(R.id.iv_little_bg)
    ImageView ivLittleBg;
    @BindView(R.id.iv_info)
    ImageView ivInfo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.iv_user_type)
    CircleImageView ivUserType;
    @BindView(R.id.tv_creator_name)
    TextView tvCreatorName;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
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
    //    @BindView(R.id.tv_subTitle)
//    TextView tvSubTitle;
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
    private MusicAdapter adapter;
    private Bundle arguments;
    private SongSheetDetail.ResultBean result;

    private String name;
    private String cover;
    private String songSheetId;

    @Override
    protected int initContentViewId() {
        return R.layout.fragment_detail_song_sheet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        //重新注册到View里
        loadService = LoadSir
                .getDefault()
                .register(recyclerView, (Callback.OnReloadListener) v -> mPresenter.getSongSheetDetail(songSheetId));
        return view;
    }

    @Override
    protected SongSheetDetailPresenter getPresenter() {
        return new SongSheetDetailPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        arguments = getArguments();

        name = arguments.getString(KEY_NAME);
        cover = arguments.getString(KEY_COVER);
        songSheetId = arguments.getString(KEY_ID);

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
        tvTitle.setText(name);
        tvName.setText(name);
//        tvSubTitle.setText(recommend);
//        tvSubTitle.setVisibility(TextUtils.isEmpty(recommend) ? View.GONE : View.VISIBLE);
        GlideUtils.loadImg(getContext(), cover, GlideUtils.TYPE_TRANSFORM_BLUR, ivBg, true);
        GlideUtils.loadImg(getContext(), cover, GlideUtils.TYPE_TRANSFORM_BLUR, tbBg);
        GlideUtils.loadImg(getContext(), cover, ivLittleBg);

        adapter = new MusicAdapter(getFragmentManager());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        args.getSerializable(KEY_INFO_SONG_SHEET);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        mPresenter.getSongSheetDetail(songSheetId);
    }

    @SuppressLint("CheckResult")
    public void setData(SongSheetDetail songSheetDetail) {
        result = songSheetDetail.getResult();
        SongSheetDetail.ResultBean.CreatorBean creator = result.getCreator();
        GlideUtils.loadImg(getContext(), creator.getAvatarUrl(), ivHead);
        tvCreatorName.setText(creator.getNickname());
        tvEvaluationNum.setText(String.valueOf(result.getCommentCount()));
        tvShareNum.setText(String.valueOf(result.getShareCount()));
        tvPlayCount.setText(String.valueOf(result.getPlayCount()));

        Observable
                .fromIterable(songSheetDetail.getResult().getTracks())
                .map(tracksBean -> {
                    MusicInfo musicInfo = new MusicInfo();
                    musicInfo.setMusicId(String.valueOf(tracksBean.getId()));
                    musicInfo.setMusicName(tracksBean.getName());
                    musicInfo.setDuration(tracksBean.getDuration());
                    musicInfo.setMvId(String.valueOf(tracksBean.getMvid()));
                    SongSheetDetail.ResultBean.TracksBean.AlbumBean album = tracksBean.getAlbum();
                    if (album != null) {
                        musicInfo.setAlbumId(String.valueOf(album.getId()));
                        musicInfo.setAlbumName(album.getName());
                        musicInfo.setAlbumCoverPath(album.getBlurPicUrl());
                    }

                    List<SongSheetDetail.ResultBean.TracksBean.ArtistsBeanX> artists = tracksBean.getArtists();
                    ArrayList<SingerInfo> singerInfos = new ArrayList<>();
                    for (int i = 0; i < artists.size(); i++) {
                        SingerInfo singerInfo = new SingerInfo();
                        SongSheetDetail.ResultBean.TracksBean.ArtistsBeanX artistsBeanX = artists.get(i);
                        singerInfo.setSingerId(String.valueOf(artistsBeanX.getId()));
                        singerInfo.setSingerName(artistsBeanX.getName());
                        singerInfo.setSingerCoverPath(artistsBeanX.getPicUrl());
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
                arguments.clear();
                arguments.putString(KEY_USER_ID, String.valueOf(result.getCreator().getUserId()));
                easyStart(new UserCenterFragment(), arguments);
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
                if (result == null) {
                    showToast(R.string.wait);
                } else {
                    arguments.putString(KEY_COVER, result.getCoverImgUrl());
                    arguments.putString(KEY_TITLE, result.getName());
                    arguments.putStringArrayList(KEY_TAG, (ArrayList<String>) result.getTags());
                    arguments.putString(KEY_DES, result.getDescription());
                    easyStart(new CoverInfoFragment(), arguments);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        playMusicWithStartFragment(adapter.datas, position);
    }
}