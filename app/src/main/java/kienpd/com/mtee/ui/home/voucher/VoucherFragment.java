package kienpd.com.mtee.ui.home.voucher;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.WriterException;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.home.rules.RulesFragment;
import kienpd.com.mtee.utils.CommonUtils;

public class VoucherFragment extends BaseDialog implements VoucherMvpView, View.OnClickListener {

    VoucherMvpPresenter<VoucherMvpView> mPresenter;

    public static final String TAG = "VOUCHER_FRAGMENT";
    public static final String EXTRAS_DETAIL_ID = "extras_detail_id";

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

    private Integer mDetailId;

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
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new VoucherPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {

        mDetailId = getArguments().getInt(EXTRAS_DETAIL_ID);
        loadData(mDetailId, 1);

        int drawableResourceId = getBaseActivity().getResources().getIdentifier("bg_test", "drawable", getBaseActivity().getPackageName());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(40));
        Glide.with(getBaseActivity())
                .load(drawableResourceId)
                .thumbnail(1f)
                .apply(requestOptions)
                .into(mImageVoucher);


        //QR Code
        try {
            Bitmap bitmap = CommonUtils.TextToImageEncode(getBaseActivity(), "7337412341", 500);
            Glide.with(getBaseActivity())
                    .asBitmap()
                    .load(bitmap)
                    .thumbnail(1f)
                    .apply(requestOptions)
                    .into(mImageQRCode);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        //Onclick
        mImageBack.setOnClickListener(this);
        mTextShareCode.setOnClickListener(this);
        mTextViewDescription.setOnClickListener(this);
        mTextViewVoucher.setOnClickListener(this);
        mImageCopy.setOnClickListener(this);
        mTextActive.setOnClickListener(this);


    }

    @Override
    public void displayView(String title, String urlVoucher, int countLike, int code, String nameStore, String addressStore, String dateVoucher, String nameUser, String phoneUser, String emailUser) {
        mTextTitle.setText(title);

        //Image
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(40));
        Glide.with(getBaseActivity())
                .load(urlVoucher)
                .thumbnail(1f)
                .apply(requestOptions)
                .into(mImageVoucher);
        //todo count like

        //Code
        mTextCode.setText(String.valueOf(code));

        //QR Code
        try {
            Bitmap bitmap = CommonUtils.TextToImageEncode(getBaseActivity(), "7337412341", 500);
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
        mTextStore.setText(nameStore);
        mTextAddress.setText(addressStore);

        //Date
        mTextDate.setText(dateVoucher);

        //User
        mTextName.setText(nameUser);
        mTextPhone.setText(String.valueOf(phoneUser));
        mTextMail.setText(emailUser);
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
                //todo
                mPresenter.showDescriptionVoucher(1);
                break;
            case R.id.text_view_voucher:
                //todo
                mPresenter.showDetailVoucher(1);
                break;
            case R.id.image_copy:
                //todo
                mPresenter.copyCode(1);
                break;
            case R.id.text_active:
                //todo
                break;
            default:
                break;

        }
    }

    private void loadData(int detailId, Integer userId) {
        mPresenter.getInfoVoucherUser(detailId, userId);
    }
}
