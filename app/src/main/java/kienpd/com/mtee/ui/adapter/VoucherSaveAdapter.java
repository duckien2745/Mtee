package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.model.Address;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.holder.HomeViewHolder;

public class VoucherSaveAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private ArrayList<Voucher> mList;
    private Context mContext;
    private VoucherSaveAdapterCallback mCallback;

    public VoucherSaveAdapter(Context context, ArrayList<Voucher> list, VoucherSaveAdapterCallback callback) {
        mContext = context;
        mList = list;
        mCallback = callback;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VoucherCollectionHolder(mContext, LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_save, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addItemCollection(List<Voucher> repoList, Boolean isClearData) {
        if (mList != null) {
            if (isClearData) {
                mList.clear();
            }
            mList.addAll(repoList);
            notifyDataSetChanged();
        }
    }

    class VoucherCollectionHolder extends HomeViewHolder {

        @BindView(R.id.image_store)
        ImageView mImageStore;

        @BindView(R.id.image_follow)
        ImageView mImageFollow;

        @BindView(R.id.text_store)
        TextView mTextStore;

        @BindView(R.id.text_address)
        TextView mTextAddress;

        @BindView(R.id.image_cover_voucher)
        ImageView mImageCoverVoucher;

        @BindView(R.id.text_title)
        TextView mTextTitle;

        @BindView(R.id.text_time)
        TextView mTextTime;

        @BindView(R.id.layout_voucher_save)
        LinearLayout mLayoutSave;


        private Context mContext;

        VoucherCollectionHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
        }

        @Override
        protected void clear() {
        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);
            mImageFollow.setColorFilter(mContext.getResources().getColor(R.color.color_item_un_select));
            Voucher voucher = mList.get(position);
            if (voucher != null) {
                Store store = voucher.getStore();
                if (store != null) {
                    mTextStore.setText(store.getName());
                    Address address = store.getAddress();
                    String sAddress = "";
                    if (address != null) {
                        sAddress = address.getNo() + "," + address.getStreet() + "," + address.getDistrict() + "," + address.getCity();
                    }
                    mTextAddress.setText(sAddress);
                }
                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

                Glide.with(mContext)
                        .load(API.HOST_DEV + "thumb/" + voucher.getCoverPicture())
                        .apply(requestOptions)
                        .into(mImageCoverVoucher);

                mTextTitle.setText(voucher.getTitle());
                mLayoutSave.setO
            }

        }
    }

    public interface VoucherSaveAdapterCallback {
        void onClickVoucherSaveListener(int id);

        void onClickVoucherFollowListener(int id);

    }

}
