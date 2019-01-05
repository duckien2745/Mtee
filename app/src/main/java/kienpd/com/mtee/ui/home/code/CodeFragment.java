package kienpd.com.mtee.ui.home.code;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.zxing.WriterException;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.db.StorageManager;
import kienpd.com.mtee.data.model.Address;
import kienpd.com.mtee.data.model.Code;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.custom.ScrollViewExt;
import kienpd.com.mtee.ui.home.active.ActiveFragment;
import kienpd.com.mtee.ui.home.detail.VoucherFragment;
import kienpd.com.mtee.utils.CommonUtils;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.TextUtil;
import kienpd.com.mtee.utils.TimeUtil;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CodeFragment extends BaseDialog implements CodeMvpView, View.OnClickListener {

    CodeMvpPresenter<CodeMvpView> mPresenter;

    public static final String TAG = "VOUCHER_FRAGMENT";
    public static final String EXTRAS_DETAIL_ID = "extras_detail_id";
    public static final String EXTRAS_JSON_USER_CODE = "extras_json_user_code";

    @BindView(R.id.image_back)
    ImageView mImageBack;

    @BindView(R.id.text_title_center)
    TextView mTextTitleToolbar;

    @BindView(R.id.image_voucher)
    ImageView mImageVoucher;

    @BindView(R.id.text_title)
    TextView mTextTitle;

    @BindView(R.id.text_share_code)
    TextView mTextShareCode;

    @BindView(R.id.text_view_description)
    TextView mTextViewDescription;

    @BindView(R.id.text_view_voucher)
    TextView mTextViewVoucher;

    @BindView(R.id.image_qr_code)
    ImageView mImageQRCode;

    @BindView(R.id.text_code)
    TextView mTextCode;

    @BindView(R.id.image_copy)
    ImageView mImageCopy;

    @BindView(R.id.text_store)
    TextView mTextStore;

    @BindView(R.id.text_address)
    TextView mTextAddress;

    @BindView(R.id.text_date)
    TextView mTextDate;

    @BindView(R.id.text_name)
    TextView mTextName;

    @BindView(R.id.text_phone)
    TextView mTextPhone;

    @BindView(R.id.text_mail)
    TextView mTextMail;

    @BindView(R.id.text_active)
    TextView mTextActive;

    @BindView(R.id.view_drop_shadow)
    View mViewDropShadow;

    @BindView(R.id.text_description_voucher)
    TextView mTextDescription;

    @BindView(R.id.text_close)
    TextView mTextClose;

    @BindView(R.id.scroll_description)
    ScrollViewExt mScrollDescription;

    @BindView(R.id.layout_waiting)
    RelativeLayout mLayoutWaiting;

    @BindView(R.id.layout_employee_active)
    RelativeLayout mLayoutEmployeeActive;

    @BindView(R.id.text_cancel)
    TextView mTextCancel;

    @BindView(R.id.text_employee_active)
    TextView mTextEmployeeActive;

    @BindView(R.id.text_input_password)
    TextView mTextInputPassword;

    @BindView(R.id.layout_process_bar)
    RelativeLayout mLayoutProcessBar;

    @BindView(R.id.layout_count_like)
    LinearLayout mLayoutCountLike;

    @BindView(R.id.text_count_like)
    TextView mTextCountLike;

    private Integer mDetailId;
    private Integer mUserId = 0;
    private String jsonUserCode;
    private String mDescription;

    public static final int RESULT_CODE_FRAGMENT = 1;

    public static CodeFragment newInstance(int detailId, String jsonUserCode) {

        CodeFragment f = new CodeFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRAS_DETAIL_ID, detailId);
        args.putString(EXTRAS_JSON_USER_CODE, jsonUserCode);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new CodePresenter<>();
        mPresenter.onAttach(this);

        return view;

    }

    @Override
    protected void setUp(View view) {
        mDetailId = getArguments().getInt(EXTRAS_DETAIL_ID, -111);
        jsonUserCode = getArguments().getString(EXTRAS_JSON_USER_CODE, TextUtil.EMPTY_STRING);

        if (mDetailId != null && mDetailId != -111) {
            mLayoutWaiting.setVisibility(View.VISIBLE);
            String jsonUser = StorageManager.getStringValue(getBaseActivity(), Const.User.KEY_SAVE_USER);
            if (jsonUser != null && !TextUtil.isEmpty(jsonUser)) {
                Gson gson = new Gson();
                User user = gson.fromJson(jsonUser, User.class);
                if (user != null) {
                    mUserId = user.getId();
                    loadData(mDetailId, mUserId);
                }
            }
        } else if (jsonUserCode != null && !TextUtil.isEmpty(jsonUserCode)) {
            mLayoutProcessBar.setVisibility(View.VISIBLE);
            Gson gson = new Gson();
            UserCode userCode = gson.fromJson(jsonUserCode, UserCode.class);
            mDetailId = userCode.getCode().getVoucher().getId();
            getDataFromBundle(userCode);
        }

        //Onclick
        mImageBack.setOnClickListener(this);
        mTextShareCode.setOnClickListener(this);
        mTextViewDescription.setOnClickListener(this);
        mTextViewVoucher.setOnClickListener(this);
        mImageCopy.setOnClickListener(this);
        mTextActive.setOnClickListener(this);
        mTextClose.setOnClickListener(this);
        mTextCancel.setOnClickListener(this);
        mTextEmployeeActive.setOnClickListener(this);
        mTextInputPassword.setOnClickListener(this);

    }

    @Override
    public void displayView(String title, String urlVoucher, int countLike, String code, String nameStore, String addressStore, String dateVoucher, String nameUser, String phoneUser, String emailUser, String description, Long timeStart, Long timeEnd) {
        mLayoutWaiting.setVisibility(View.GONE);
        mTextTitle.setText(title);
        //Image
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(40));
        Glide.with(getBaseActivity())
                .load(API.HOST_DEV + urlVoucher)
                .thumbnail(1f)
                .apply(requestOptions)
                .into(mImageVoucher);

        //Count Like
        if (countLike > 0) {
            mTextCountLike.setText("" + countLike);
            mLayoutCountLike.setVisibility(VISIBLE);
        } else {
            mLayoutCountLike.setVisibility(GONE);
        }

        //Code
        mTextCode.setText(code);

        //Store
        mTextTitleToolbar.setText(nameStore);
        mTextStore.setText(nameStore);
        mTextAddress.setText(addressStore);

        //Date
        mTextDate.setText(dateVoucher);

        //User
        mTextName.setText(nameUser);
        mTextPhone.setText(String.valueOf(phoneUser));
        mTextMail.setText(emailUser);

        //Description
        String sTimeStart = TimeUtil.getStringDateFromMiliseconds(timeStart);
        String sTimeEnd = TimeUtil.getStringDateFromMiliseconds(timeEnd);

        mDescription = String.format(description, sTimeStart, sTimeEnd, sTimeEnd);

        mTextDescription.setLineSpacing(16f, 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextDescription.setText(Html.fromHtml(mDescription, Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextDescription.setText(Html.fromHtml(mDescription));
        }
        mLayoutProcessBar.setVisibility(View.GONE);
        //QR Code
        try {
            Bitmap bitmap = CommonUtils.TextToImageEncode(getBaseActivity(), code, 500);
            Glide.with(getBaseActivity())
                    .asBitmap()
                    .load(bitmap)
                    .thumbnail(1f)
                    .apply(requestOptions)
                    .into(mImageQRCode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                dismissDialog(TAG);
                break;
            case R.id.text_share_code:
                //todo
                mPresenter.shareVoucher(getBaseActivity(), "title", "content");
                break;
            case R.id.text_view_description:
                showDescription(true);
                break;
            case R.id.text_view_voucher:
                VoucherFragment fragment = VoucherFragment.newInstance(mDetailId);
                fragment.show(getFragmentManager(), VoucherFragment.TAG);
                break;
            case R.id.image_copy:
                mPresenter.copyCode(getBaseActivity(), mTextCode.getText().toString());
                getBaseActivity().showMessage("Đã copy mã " + mTextCode.getText().toString() + " thành công ");
                break;
            case R.id.text_active:
                mLayoutEmployeeActive.setVisibility(View.VISIBLE);
                mTextActive.setVisibility(View.GONE);
                break;
            case R.id.text_close:
                showDescription(false);
                break;
            case R.id.text_cancel:
                mLayoutEmployeeActive.setVisibility(View.GONE);
                mTextActive.setVisibility(View.VISIBLE);
                break;
            case R.id.text_employee_active:
                break;
            case R.id.text_input_password:
                ActiveFragment dialogFrag = ActiveFragment.newInstance(mTextCode.getText().toString());
                dialogFrag.setTargetFragment(this, RESULT_CODE_FRAGMENT);
                dialogFrag.show(getFragmentManager(), ActiveFragment.TAG);
                break;
            default:
                break;

        }
    }

    private void loadData(int detailId, Integer userId) {
        mPresenter.getInfoVoucherUser(detailId, userId);
    }

    private void showDescription(Boolean isShow) {
        if (isShow) {
            mScrollDescription.setVisibility(View.VISIBLE);
            mTextActive.setVisibility(View.GONE);
            mViewDropShadow.setVisibility(View.GONE);
        } else {
            mScrollDescription.setVisibility(View.GONE);
            mTextActive.setVisibility(View.VISIBLE);
            mViewDropShadow.setVisibility(View.VISIBLE);
        }
    }

    private void getDataFromBundle(UserCode userCode) {
        Code code = userCode.getCode();
        User user = userCode.getUser();

        if (user != null && code != null) {
            Voucher voucher = code.getVoucher();

            String title = voucher.getTitle();
            String pictureCover = voucher.getCoverPicture();
            int countLike = voucher.getLikeCount();

            Store store = voucher.getStore();
            String nameStore = store.getName();
            Address address = store.getAddress();
            String sAddress = "";
            if (address != null) {
                sAddress = address.getNo() + "," + address.getStreet() + "," + address.getDistrict() + "," + address.getCity();
            }
            String sCode = code.getCode();

            //Date
            String dateVoucher;
            Long timeGetCode = userCode.getTimeGetCode();
            Date dateGetCode = new Date(timeGetCode);
            Calendar c = Calendar.getInstance();
            c.setTime(dateGetCode);

            //Next 10 day
            c.add(Calendar.DATE, 10);
            dateGetCode = c.getTime();

            if (dateGetCode.before(new Date(voucher.getTimeEnd()))) {
                dateVoucher = "Áp dụng tới ngày: " + TimeUtil.formatDate(dateGetCode);
            } else {
                dateVoucher = "Áp dụng tới ngày: " + TimeUtil.getStringDateFromMiliseconds(voucher.getTimeEnd());
            }

            //Phone
            String phone = "0969.056.804";

            String nameUser = user.getName();
            String email = user.getEmail();

            String description = voucher.getDescription();
            Long timeStart = voucher.getTimeStart();
            Long timeEnd = voucher.getTimeEnd();

            displayView(title, pictureCover, countLike, sCode, nameStore, sAddress, dateVoucher, nameUser, phone, email, description, timeStart, timeEnd);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CODE_FRAGMENT:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Boolean isActive = data.getBooleanExtra(ActiveFragment.ACTIVE, false);
                    if (isActive) {
                        mLayoutEmployeeActive.setVisibility(View.GONE);
                        mTextActive.setText("Đã kích hoạt");
                        mTextActive.setClickable(false);
                        mTextActive.setVisibility(View.VISIBLE);
                        mTextActive.setBackground(ContextCompat.getDrawable(getBaseActivity(), R.drawable.bg_un_active));
                    }
                }
                break;
            default:
                break;
        }
    }
}
