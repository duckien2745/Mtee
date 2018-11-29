package kienpd.com.mtee.ui.adapter.holder;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.model.Address;
import kienpd.com.mtee.data.model.Store;

public class HeaderStoreHolder extends FollowViewHolder {

    private HeaderStoreHolderCallback mCallback;

    @BindView(R.id.image_logo)
    ImageView mImageLogo;

    @BindView(R.id.image_store)
    ImageView mImageStore;

    @BindView(R.id.image_follow)
    ImageView mImageFollow;

    @BindView(R.id.text_store)
    TextView mTextStore;

    @BindView(R.id.text_address)
    TextView mTextAddress;

    @BindView(R.id.text_number_follow)
    TextView mTextNumberFollow;

    private Boolean mIsUserFollow;
    private Context mContext;

    public HeaderStoreHolder(Context context, View itemView, HeaderStoreHolderCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mCallback = callback;
        mContext = context;
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onBind(final Store store) {
        if (store != null) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

            //Store
            Glide.with(mContext)
                    .load(API.HOST_DEV + store.getPicture())
                    .apply(requestOptions)
                    .into(mImageStore);
            //Logo
            int drawableResourceId = mContext.getResources().getIdentifier("logo_royal_tea", "drawable", mContext.getPackageName());
            Glide.with(mContext)
                    .load(drawableResourceId)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageLogo);

            //Name Store
            mTextStore.setText(store.getName());

            //Address
            Address address = store.getAddress();
            String sAddress = "";
            if (address != null) {
                sAddress = address.getNo() + "," + address.getStreet() + "," + address.getDistrict() + "," + address.getCity();
            }
            mTextAddress.setText(sAddress);

            //Number follow
            mTextNumberFollow.setText(store.getCountFollow() + "");

            //Icon follow
            if (store.getmIsUserFollow() != null) {
                mIsUserFollow = store.getmIsUserFollow();
                if (mIsUserFollow) {
                    mImageFollow.setColorFilter(mContext.getResources().getColor(R.color.color_item_select));
                } else {
                    mImageFollow.setColorFilter(mContext.getResources().getColor(R.color.color_item_un_select));
                }

                mImageFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onClickFollowStoreListener(store.getId());
                    }
                });
            }
        }
    }


    public interface HeaderStoreHolderCallback {
        void onClickFollowStoreListener(Integer storeId);
    }


}