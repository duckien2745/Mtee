package kienpd.com.mtee.ui.home.detail;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.db.StorageManager;
import kienpd.com.mtee.data.model.Messager;
import kienpd.com.mtee.data.model.RatingResponse;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.adapter.PriceAdapter;
import kienpd.com.mtee.ui.adapter.SliderDetailAdapter;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.custom.ScrollViewExt;
import kienpd.com.mtee.ui.home.image.ImageFragment;
import kienpd.com.mtee.ui.home.rules.RulesFragment;
import kienpd.com.mtee.utils.CommonUtils;
import kienpd.com.mtee.utils.Const;
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

    @BindView(R.id.image_right)
    ImageView mImageRight;

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

    @BindView(R.id.layout_login)
    RelativeLayout mLayoutLogin;

    @BindView(R.id.layout_sign_in_with_google)
    LinearLayout mLayoutSignWithGoogle;

    @BindView(R.id.scroll_view_detail)
    ScrollViewExt mScrollviewReaderContent;

    @BindView(R.id.layout_count_like)
    LinearLayout mLayoutCountLike;

    @BindView(R.id.text_count_like)
    TextView mTextCountLike;

    private SliderDetailAdapter mSliderDetailAdapter;
    private PriceAdapter mPriceAdapter;
    private ArrayList<String> mImageVouchers;
    private ArrayList<String> mImagePrices;
    private Boolean mIsRunSlider;
    private String mDescription;
    private Integer mDetailId;
    private Integer mUserId = 0;
    private Integer mStoreId = 0;
    private Integer mCountLike = 0;
    private Integer mAction = -1111;


    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

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
        String jsonUser = StorageManager.getStringValue(getBaseActivity(), Const.User.KEY_SAVE_USER);
        if (jsonUser != null && !TextUtil.isEmpty(jsonUser)) {
            Gson gson = new Gson();
            User user = gson.fromJson(jsonUser, User.class);
            if (user != null) {
                mUserId = user.getId();
                loadInfoUser();
            }
        } else {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestId()
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(getBaseActivity(), gso);
        }

        //Set color bottom
        mImageLike.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageShare.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageSave.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageUser.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageEdit.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageRight.setColorFilter(getResources().getColor(R.color.color_item_un_select));

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
        mImageRight.setOnClickListener(this);
        mLayoutLogin.setOnClickListener(this);
        mLayoutSignWithGoogle.setOnClickListener(this);
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
        Gson gson = new Gson();
        String json = gson.toJson(mImagePrices);
        ImageFragment fragment = ImageFragment.newInstance(json, position);
        fragment.show(getFragmentManager(), ImageFragment.TAG);
    }

    @Override
    public void displayDetailView(Store store, int countLike, List<String> urlImageVouchers, String title, String address, List<String> urlImagePrices, String description) {
        //Store
        if (store != null) {
            mStoreId = store.getId();
            mTextTitleCenter.setText(store.getName());
            if (mUserId != 0 && mStoreId != 0)
                mPresenter.getStatusUserFollow(mStoreId, mUserId);
        }

        //Count Like
        if (countLike > 0) {
            mCountLike = countLike;
            mTextCountLike.setText(countLike+"");
            mLayoutCountLike.setVisibility(VISIBLE);
        } else {
            mLayoutCountLike.setVisibility(GONE);
        }

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
            mPresenter.getStatusLikeSaveRateDetail(mUserId, mDetailId);

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
    public void getStatusLike(Boolean isLike) {
        if (isLike) {
            mImageLike.setColorFilter(getResources().getColor(R.color.color_item_select));
        } else {
            mImageLike.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        }
    }

    @Override
    public void updateLike(Boolean isLike) {

        if (isLike) {
            mCountLike = mCountLike + 1;
            mImageLike.setColorFilter(getResources().getColor(R.color.color_item_select));
        } else {
            mImageLike.setColorFilter(getResources().getColor(R.color.color_item_un_select));
            if (mCountLike > 0) {
                mCountLike = mCountLike - 1;
            }
        }

        if (mCountLike > 0) {
            mLayoutCountLike.setVisibility(VISIBLE);
            mTextCountLike.setText(mCountLike+"");
        } else {
            mLayoutCountLike.setVisibility(GONE);
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
    public void updateStatusFollow(Boolean isUserFollow) {
        if (isUserFollow) {
            mImageRight.setColorFilter(getBaseActivity().getResources().getColor(R.color.color_item_select));
        } else {
            mImageRight.setColorFilter(getBaseActivity().getResources().getColor(R.color.color_item_un_select));
        }
    }

    @Override
    public void onClickSliderImageListener(int position) {
        Gson gson = new Gson();
        String json = gson.toJson(mImageVouchers);
        ImageFragment fragment = ImageFragment.newInstance(json, position);
        fragment.show(getFragmentManager(), ImageFragment.TAG);
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
                String strUri = Const.GoogleMap.URL_REQUEST_GOOGLE_MAP + mTextAddress.getText().toString().replace(' ', '+');
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName(Const.GoogleMap.PACKAGE_NAME_GOOGLE_MAP, Const.GoogleMap.CLASS_NAME_GOOGLE_MAP);
                startActivity(intent);
                break;
            case R.id.text_see_more:
                mPresenter.showTextMore();
                break;
            case R.id.layout_like:
                if (mUserId == 0) {
                    mAction = Const.Action.ACTION_LIKE;
                    mLayoutLogin.setVisibility(VISIBLE);
                } else {
                    mPresenter.likeDetail(mUserId, mDetailId);
                }
                break;
            case R.id.layout_share:
                //todo
                mPresenter.shareDetail(mTextTitle.toString(), "content");
                break;
            case R.id.layout_save:
                if (mUserId == 0) {
                    mAction = Const.Action.ACTION_SAVE;
                    mLayoutLogin.setVisibility(VISIBLE);
                } else {
                    mPresenter.saveDetail(mUserId, mDetailId);
                }
                break;
            case R.id.image_edit:
                if (mUserId == 0) {
                    mAction = Const.Action.ACTION_EDIT_RATTING;
                    mLayoutLogin.setVisibility(VISIBLE);
                } else {
                    editRating(true);
                }
                break;
            case R.id.text_submit:
                if (mUserId == 0) {
                    mAction = Const.Action.ACTION_SUBMIT;
                    mLayoutLogin.setVisibility(VISIBLE);
                } else {
                    mPresenter.rattingDetail(mUserId, mDetailId, mMyRatingBar.getRating());
                }
                break;
            case R.id.layout_get_code:
                if (mUserId == 0) {
                    mAction = Const.Action.ACTION_GET_CODE;
                    mLayoutLogin.setVisibility(VISIBLE);
                } else {
                    mPresenter.checkGetCodeInDay(mUserId, mDetailId);
                }
                break;
            case R.id.image_right:
                if (mUserId == 0) {
                    mAction = Const.Action.ACTION_FOLLOW_STORE;
                    mLayoutLogin.setVisibility(VISIBLE);
                } else {
                    mPresenter.updateStatusUserFollow(mStoreId, mUserId);
                }
                break;
            case R.id.layout_sign_in_with_google:
                signIn();
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
        mPresenter.loadInfoUser(mUserId);
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

    @Override
    public void displayStatusFollow(Boolean isUserFollow) {
        if (isUserFollow) {
            mImageRight.setColorFilter(getBaseActivity().getResources().getColor(R.color.color_item_select));
        } else {
            mImageRight.setColorFilter(getBaseActivity().getResources().getColor(R.color.color_item_un_select));
        }
    }

    @Override
    public void updateUI(User user, int action) {
        if (user != null) {
            mUserId = user.getId();
            mLayoutLogin.setVisibility(View.GONE);
            StorageManager.saveUserDetails(getBaseActivity(), user);
            switch (mAction) {
                case Const.Action.ACTION_FOLLOW_STORE:
                    mPresenter.updateStatusUserFollow(mStoreId, mUserId);
                    break;
                case Const.Action.ACTION_LIKE:
                    mPresenter.likeDetail(mUserId, mDetailId);
                    break;
                case Const.Action.ACTION_SAVE:
                    mPresenter.saveDetail(mUserId, mDetailId);
                    break;
                case Const.Action.ACTION_SUBMIT:
                    mPresenter.rattingDetail(mUserId, mDetailId, mMyRatingBar.getRating());
                    break;
                case Const.Action.ACTION_GET_CODE:
                    mPresenter.checkGetCodeInDay(mUserId, mDetailId);
                    break;
                case Const.Action.ACTION_EDIT_RATTING:
                    editRating(true);
                    break;

            }
            loadInfoUser();
            if (mUserId != 0 && mStoreId != 0) {
                mPresenter.getStatusUserFollow(mStoreId, mUserId);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account.getIdToken() != null) {
                mPresenter.signInWithGoogle("Google", account.getIdToken(), mAction);
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

}
