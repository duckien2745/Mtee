package kienpd.com.mtee.ui.home.voucher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.base.BaseDialog;

public class VoucherFragment extends BaseDialog implements VoucherMvpView {

    VoucherMvpPresenter<VoucherMvpView> mPresenter;

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

        Log.d("ybdedgeud","11");
        int drawableResourceId = getBaseActivity().getResources().getIdentifier("bg_test", "drawable", getBaseActivity().getPackageName());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));
        Glide.with(getBaseActivity())
                .load(drawableResourceId)
                .apply(requestOptions)
                .into(mImageVoucher);
    }
}
