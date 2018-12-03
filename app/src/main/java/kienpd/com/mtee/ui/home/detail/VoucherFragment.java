package kienpd.com.mtee.ui.home.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Messager;
import kienpd.com.mtee.data.model.RatingResponse;
import kienpd.com.mtee.ui.adapter.PriceAdapter;
import kienpd.com.mtee.ui.adapter.SliderDetailAdapter;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.home.rules.RulesFragment;
import kienpd.com.mtee.ui.custom.ScrollViewExt;
import kienpd.com.mtee.utils.CommonUtils;
import kienpd.com.mtee.utils.TextUtil;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class VoucherFragment extends BaseDialog implements VoucherMvpView, ScrollViewExt.ScrollViewListener, PriceAdapter.PriceAdapterCallback, SliderDetailAdapter.SliderDetailAdapterCallback, View.OnClickListener {

    public static final String TAG = "DETAIL_FRAGMENT";
    public static final String EXTRAS_DETAIL_ID = "extras_detail_id";
    private VoucherMvpPresenter<VoucherMvpView> mPresenter;

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

    @BindView(R.id.text_rate_voucher)
    TextView mTextRateVoucher;

    @BindView(R.id.text_name_user)
    TextView mTextNameUser;

    @BindView(R.id.text_submit)
    TextView mTextSubmit;

    @BindView(R.id.my_rating_bar)
    MaterialRatingBar mMyRatingBar;

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.image_edit)
    ImageView mImageEdit;

    @BindView(R.id.layout_my_rate)
    LinearLayout mLayoutMyRate;

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

    @BindView(R.id.layout_like)
    RelativeLayout mLayoutLike;

    @BindView(R.id.layout_share)
    RelativeLayout mLayoutShare;

    @BindView(R.id.layout_save)
    RelativeLayout mLayoutSave;

    @BindView(R.id.layout_bottom)
    LinearLayout mLayoutBottom;

    @BindView(R.id.layout_get_code)
    RelativeLayout mLayoutGetCode;

    @BindView(R.id.scroll_view_detail)
    ScrollViewExt mScrollviewReaderContent;

    private SliderDetailAdapter mSliderDetailAdapter;
    private PriceAdapter mPriceAdapter;
    private ArrayList<String> mImageVouchers;
    private ArrayList<String> mImagePrices;
    private Boolean mIsRunSlider;
    private String mDescription;
    private Integer mDetailId;
    private Integer userId = 37281321;

    public static VoucherFragment newInstance(int detailId) {
        VoucherFragment f = new VoucherFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRAS_DETAIL_ID, detailId);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new VoucherPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {

        //Load Data
        mDetailId = getArguments().getInt(EXTRAS_DETAIL_ID);
        loadData(mDetailId);
        loadInfoUser();

        //Set color bottom
        mImageLike.setColorFilter(getResources().getColor(R.color.color_item_select));
        mImageShare.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageSave.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageUser.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageEdit.setColorFilter(getResources().getColor(R.color.color_item_un_select));

        mImageVouchers = new ArrayList<>();
        mSliderDetailAdapter = new SliderDetailAdapter(getBaseActivity(), mImageVouchers, this);
        mViewPagerDetail.setAdapter(mSliderDetailAdapter);
        mTabIndicator.setupWithViewPager(mViewPagerDetail, true);

        mIsRunSlider = true;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 3000, 6000);

        mImagePrices = new ArrayList<>();
        mPriceAdapter = new PriceAdapter(getBaseActivity(), mImagePrices, this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerPrice.setLayoutManager(layoutManager);
        mRecyclerPrice.setAdapter(mPriceAdapter);

        //todo
        mLayoutMyRate.setVisibility(GONE);

        //NnClick
        mScrollviewReaderContent.setScrollViewListener(this);
        mImageBack.setOnClickListener(this);
        mLayoutDirect.setOnClickListener(this);
        mTextSeeMore.setOnClickListener(this);
        mLayoutLike.setOnClickListener(this);
        mLayoutShare.setOnClickListener(this);
        mLayoutSave.setOnClickListener(this);
        mLayoutGetCode.setOnClickListener(this);
        mLayoutGetCode.setOnClickListener(this);
        mImageEdit.setOnClickListener(this);
        mTextSubmit.setOnClickListener(this);
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
        //todo
    }

    @Override
    public void displayDetailView(String store, int countLike, List<String> urlImageVouchers, String title, String address, List<String> urlImagePrices, String description) {
        //Store
        mTextTitleCenter.setText(store);

        // todo count like

        //Image voucher
        mImageVouchers.clear();
        mImageVouchers.addAll(urlImageVouchers);
        mSliderDetailAdapter.notifyDataSetChanged();

        //Title
        mTextTitle.setText(title);

        //Address
        mTextAddress.setText(address);

        //Image price
        mImagePrices.clear();
        mImagePrices.addAll(urlImagePrices);
        mPriceAdapter.notifyDataSetChanged();

        //Description
        mDescription = description;
        mTextCondition.setLineSpacing(16f, 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextCondition.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextCondition.setText(Html.fromHtml(description));
        }

    }

    @Override
    public void displayMyRating(int star) {
        if (star != 0) {
            editRating(false);
        } else {
            editRating(true);
        }

        mMyRatingBar.setMax(5);
        mMyRatingBar.setRating(star);

    }

    @Override
    public void changeMyRatting(boolean isChange) {
        if (isChange) {
            editRating(false);
        } else {
            editRating(true);
        }
    }

    @Override
    public void displayInfoUser(String name, String avatarUrl) {
        if (!TextUtil.isEmpty(name) && !TextUtil.isEmpty(avatarUrl)) {
            mPresenter.getStatusLikeSaveRateDetail(userId, mDetailId);

            mLayoutMyRate.setVisibility(VISIBLE);
            mTextNameUser.setVisibility(VISIBLE);

            mTextSubmit.setVisibility(GONE);
            mTextRateVoucher.setVisibility(GONE);
            mMyRatingBar.setVisibility(GONE);
            mTextNameUser.setText(name);

            Glide.with(getBaseActivity())
                    .load(avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageAvatar);
        } else {
            mLayoutMyRate.setVisibility(GONE);
        }
    }

    @Override
    public void updateLike(Boolean isLike) {
        if (isLike) {
            mImageLike.setColorFilter(getResources().getColor(R.color.color_item_select));
        } else {
            mImageLike.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        }
    }

    @Override
    public void updateSave(Boolean isSave) {
        if (isSave) {
            mImageSave.setColorFilter(getResources().getColor(R.color.color_item_select));
        } else {
            mImageSave.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        }
    }

    @Override
    public void showTextDescription(Boolean isShow) {
        if (!isShow) {
            ViewGroup.LayoutParams params = mTextCondition.getLayoutParams();
            params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            mTextCondition.setLayoutParams(params);
            mTextSeeMore.setText("Mở rộng");
        } else {
            ViewGroup.LayoutParams params = mTextCondition.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mTextCondition.setLayoutParams(params);
            mTextSeeMore.setText("Thu nhỏ");
        }
    }

    @Override
    public void displayTotalRatting(RatingResponse totalRatting) {

        int totalRate = totalRatting.getRating1star() + totalRatting.getRating2star() + totalRatting.getRating3star() + totalRatting.getRating4star() + totalRatting.getRating5star();
        int totalStar = totalRatting.getRating1star() + totalRatting.getRating2star() * 2 + totalRatting.getRating3star() * 3 + totalRatting.getRating4star() * 4 + totalRatting.getRating5star() * 5;
        float avg = (float) totalStar / totalRate;
        //nearest one-half value
        float star = (Math.round(avg * 2) / 2.0f);

        //Star rating
        mTextRate.setText(String.valueOf(star));

        //Count rating
        mTextTotalRate.setText(totalRate + " đánh giá");

        //Rating statistics
        mProcess5Star.setMax(totalRate);
        mProcess5Star.setProgress(totalRatting.getRating5star());
        mProcess4Star.setMax(totalRate);
        mProcess4Star.setProgress(totalRatting.getRating4star());
        mProcess3Star.setMax(totalRate);
        mProcess3Star.setProgress(totalRatting.getRating3star());
        mProcess2Star.setMax(totalRate);
        mProcess2Star.setProgress(totalRatting.getRating2star());
        mProcess1Star.setMax(totalRate);
        mProcess1Star.setProgress(totalRatting.getRating1star());

        //todo
        mRatingBar.setRating(star);
    }

    @Override
    public void statusGetCodeInDay(Messager messager) {
        if (messager != null) {
            if (messager.getStatus() == 1) {
                RulesFragment fragment = RulesFragment.newInstance(mDescription, mDetailId);
                fragment.show(getFragmentManager(), RulesFragment.TAG);
            } else {
                getBaseActivity().showMessage(messager.getMessage());
            }
        }
    }

    @Override
    public void onClickSliderImageListener(int position) {
        //todo
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                dismissDialog(TAG);
                break;
            case R.id.layout_direct:
                //todo
                mPresenter.direct("Lê Thanh Nghị");
                break;
            case R.id.text_see_more:
                mPresenter.showTextMore();
                break;
            case R.id.layout_like:
                //todo
                mPresenter.likeDetail(userId, mDetailId);
                break;
            case R.id.layout_share:
                //todo
                mPresenter.shareDetail(mTextTitle.toString(), "content");
                break;
            case R.id.layout_save:
                //todo
                mPresenter.saveDetail(userId, mDetailId);
                break;
            case R.id.image_edit:
                editRating(true);
                break;
            case R.id.text_submit:
                mPresenter.rattingDetail(userId, mDetailId, mMyRatingBar.getRating());
                break;
            case R.id.layout_get_code:
                mPresenter.checkGetCodeInDay(userId, mDetailId);
                break;
            default:
                break;
        }
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            if (mIsRunSlider && getBaseActivity() != null) {
                getBaseActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mViewPagerDetail.getCurrentItem() < mImageVouchers.size() - 1) {
                            mViewPagerDetail.setCurrentItem(mViewPagerDetail.getCurrentItem() + 1);
                        } else {
                            mViewPagerDetail.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }

    private void loadData(int voucherId) {
        mPresenter.loadDetailData(voucherId);
    }

    private void loadInfoUser() {
        mPresenter.loadInfoUser(userId);
    }

    private void editRating(boolean isEdit) {
        mMyRatingBar.setVisibility(VISIBLE);
        if (isEdit) {
            mMyRatingBar.setIsIndicator(false);
            mTextSubmit.setVisibility(VISIBLE);
            mTextRateVoucher.setVisibility(VISIBLE);
            mTextNameUser.setVisibility(GONE);
            mImageEdit.setVisibility(GONE);
        } else {
            mMyRatingBar.setIsIndicator(true);
            mTextSubmit.setVisibility(GONE);
            mTextRateVoucher.setVisibility(GONE);
            mTextNameUser.setVisibility(VISIBLE);
            mImageEdit.setVisibility(VISIBLE);
        }
    }

}
