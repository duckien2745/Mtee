package kienpd.com.mtee.ui.home.code;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.zxing.WriterException;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.model.Address;
import kienpd.com.mtee.data.model.Code;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.custom.ScrollViewExt;
import kienpd.com.mtee.ui.home.detail.VoucherFragment;
import kienpd.com.mtee.utils.CommonUtils;
import kienpd.com.mtee.utils.TextUtil;

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

    private Integer mDetailId;
    private Integer mUserId = 37281321;
    private String jsonUserCode;

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
            loadData(mDetailId, mUserId);
        } else if (jsonUserCode != null && !TextUtil.isEmpty(jsonUserCode)) {
            Gson gson = new Gson();
            UserCode userCode = gson.fromJson(jsonUserCode, UserCode.class);
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

    }

    @Override
    public void displayView(String title, String urlVoucher, int countLike, String code, String nameStore, String addressStore, String dateVoucher, String nameUser, String phoneUser, String emailUser, String description) {

        mTextTitle.setText(title);

        //Image
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(40));
        Glide.with(getBaseActivity())
                .load(API.HOST_DEV + urlVoucher)
                .thumbnail(1f)
                .apply(requestOptions)
                .into(mImageVoucher);
        //todo count like

        //Code
        mTextCode.setText(code);

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
        mTextDescription.setLineSpacing(16f, 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextDescription.setText(Html.fromHtml(description));
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
                break;
            case R.id.text_active:
                //todo
                break;
            case R.id.text_close:
                showDescription(false);
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

            //todo
            String dateVoucher = "17 tháng 12 2018";
            String phone = "0969.056.804";

            String nameUser = user.getName();
            String email = user.getEmail();

            String description = voucher.getDescription();

            displayView(title, pictureCover, countLike, sCode, nameStore, sAddress, dateVoucher, nameUser, phone, email, description);
        }
    }
}
