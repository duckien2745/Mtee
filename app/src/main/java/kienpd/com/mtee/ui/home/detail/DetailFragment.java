package kienpd.com.mtee.ui.home.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.adapter.PriceAdapter;
import kienpd.com.mtee.ui.adapter.SliderDetailAdapter;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.view.ScrollViewExt;
import kienpd.com.mtee.utils.CommonUtils;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class DetailFragment extends BaseDialog implements DetailMvpView, ScrollViewExt.ScrollViewListener, PriceAdapter.PriceAdapterCallback {

    DetailMvpPresenter<DetailMvpView> mPresenter;

    @BindView(R.id.pager_detail)
    ViewPager mViewPagerDetail;

    @BindView(R.id.indicator)
    TabLayout mTabIndicator;

    @BindView(R.id.image_back)
    ImageView mImageBack;

    @BindView(R.id.text_title_center)
    TextView mTextTitleCenter;

    @BindView(R.id.text_title)
    TextView mTextTitle;

    @BindView(R.id.text_address)
    TextView mTextAddress;

    @BindView(R.id.layout_direct)
    LinearLayout mLayoutDirect;

    @BindView(R.id.recycler_price)
    RecyclerView mRecyclerPrice;

    @BindView(R.id.text_content_condition)
    TextView mTextCondition;

    @BindView(R.id.text_see_more)
    TextView mTextSeeMore;

    @BindView(R.id.text_rate)
    TextView mTextRate;

    @BindView(R.id.text_total_rate)
    TextView mTextTotalRate;

    @BindView(R.id.image_like)
    ImageView mImageLike;

    @BindView(R.id.image_share)
    ImageView mImageShare;

    @BindView(R.id.image_save)
    ImageView mImageSave;

    @BindView(R.id.image_user)
    ImageView mImageUser;

    @BindView(R.id.rating_bar)
    MaterialRatingBar mRatingBar;

    @BindView(R.id.progress_5_star)
    RoundCornerProgressBar mProcess5Star;

    @BindView(R.id.progress_4_star)
    RoundCornerProgressBar mProcess4Star;

    @BindView(R.id.progress_3_star)
    RoundCornerProgressBar mProcess3Star;

    @BindView(R.id.progress_2_star)
    RoundCornerProgressBar mProcess2Star;

    @BindView(R.id.progress_1_star)
    RoundCornerProgressBar mProcess1Star;

    @BindView(R.id.layout_bottom)
    LinearLayout mLayoutBottom;

    @BindView(R.id.scroll_view_detail)
    ScrollViewExt mScrollviewReaderContent;


    private ArrayList<String> mUrl;
    private Boolean mIsRunSlider;
    Boolean check = true;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new DetailPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {

        //Test Rate
        mProcess5Star.setMax(100);
        mProcess5Star.setProgress(35);
        mProcess4Star.setMax(100);
        mProcess4Star.setProgress(50);
        mProcess3Star.setMax(100);
        mProcess3Star.setProgress(75);
        mProcess2Star.setMax(100);
        mProcess2Star.setProgress(55);
        mProcess1Star.setMax(100);
        mProcess1Star.setProgress(15);
        mRatingBar.setRating(4.5f);

        //Set color bottom
        mImageLike.setColorFilter(getResources().getColor(R.color.color_item_select));
        mImageShare.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageSave.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageUser.setColorFilter(getResources().getColor(R.color.color_item_un_select));

        //Test Slide
        mUrl = new ArrayList<>();
        mUrl.add("192.168.16.103:8080/picture/bg_test.png");
        mUrl.add("192.168.16.103:8080/picture/diagram_mtee.png");
        mUrl.add("192.168.16.103:8080/picture/mtee_diagram.png");
        mUrl.add("192.168.16.103:8080/picture/bg_test.png");
        mUrl.add("192.168.16.103:8080/picture/diagram_mtee.png");
        mUrl.add("192.168.16.103:8080/picture/mtee_diagram.png");
        mUrl.add("192.168.16.103:8080/picture/bg_test.png");
        mUrl.add("192.168.16.103:8080/picture/diagram_mtee.png");
        mUrl.add("192.168.16.103:8080/picture/mtee_diagram.png");

        mViewPagerDetail.setAdapter(new SliderDetailAdapter(getBaseActivity(), mUrl));
        mTabIndicator.setupWithViewPager(mViewPagerDetail, true);

        mIsRunSlider = true;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 3000, 6000);

        //Test HTML
        String s = getResources().getString(R.string.text_deception);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextCondition.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextCondition.setText(Html.fromHtml(s));
        }

        mTextSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check) {
                    check = true;
                    ViewGroup.LayoutParams params = mTextCondition.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
                    mTextCondition.setLayoutParams(params);
                    mTextSeeMore.setText("Mở rộng");
                } else {
                    check = false;
                    ViewGroup.LayoutParams params = mTextCondition.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mTextCondition.setLayoutParams(params);
                    mTextSeeMore.setText("Thu nhỏ");
                }
            }
        });

        //Test price picture
        ArrayList<String> listPrice = new ArrayList<>();
        listPrice.add("1");
        listPrice.add("2");
        listPrice.add("3");
        PriceAdapter adapter = new PriceAdapter(getBaseActivity(), listPrice, this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerPrice.setLayoutManager(layoutManager);
        mRecyclerPrice.setAdapter(adapter);

        mScrollviewReaderContent.setScrollViewListener(this);
    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (Math.abs(scrollY - oldScrollY) > 4) {
            if (scrollY - oldScrollY < 0) {
                showBottomBarLayout();
            } else {
                hideBottomBarLayout();
            }
        }
    }

    @Override
    public void onClickPriceListener(int position) {

    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            if (mIsRunSlider && getBaseActivity() != null) {
                getBaseActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mViewPagerDetail.getCurrentItem() < mUrl.size() - 1) {
                            mViewPagerDetail.setCurrentItem(mViewPagerDetail.getCurrentItem() + 1);
                        } else {
                            mViewPagerDetail.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsRunSlider = false;
    }

    public void hideBottomBarLayout() {
        if (mLayoutBottom.getVisibility() == VISIBLE) {
            CommonUtils.SlideDownFromBottom(mLayoutBottom, getBaseActivity());
        }
    }

    public void showBottomBarLayout() {
        if (mLayoutBottom.getVisibility() == GONE) {
            CommonUtils.SlideUPfromBottom(mLayoutBottom, getBaseActivity());
        }
    }

}
