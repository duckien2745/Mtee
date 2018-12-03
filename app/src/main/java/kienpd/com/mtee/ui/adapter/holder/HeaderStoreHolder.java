package kienpd.com.mtee.ui.adapter.holder;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    private Context mContext;
    private int mCountFollow;

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
            //Store
            Glide.with(mContext)
                    .load(API.HOST_DEV + store.getPicture())
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
            mCountFollow = store.getCountFollow();
            mTextNumberFollow.setText(mCountFollow + "");

            //Icon follow
            mImageFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClickFollowStoreListener(store.getId());
                }
            });

        }
    }

    public void updateStatusFollow(Boolean isUserFollow) {
        if (isUserFollow) {
            mCountFollow = mCountFollow + 1;
            mImageFollow.setColorFilter(mContext.getResources().getColor(R.color.color_item_select));

        } else {
            mCountFollow = mCountFollow - 1;
            mImageFollow.setColorFilter(mContext.getResources().getColor(R.color.color_item_un_select));
        }
        mTextNumberFollow.setText(mCountFollow + "");
    }

    public void displayStatusFollow(Boolean isUserFollow) {
        Log.d("bedebde","aaaa"+isUserFollow);
        if (isUserFollow) {
            mImageFollow.setColorFilter(mContext.getResources().getColor(R.color.color_item_select));
        } else {
            mImageFollow.setColorFilter(mContext.getResources().getColor(R.color.color_item_un_select));
        }
    }

    public interface HeaderStoreHolderCallback {
        void onClickFollowStoreListener(Integer storeId);
    }
}