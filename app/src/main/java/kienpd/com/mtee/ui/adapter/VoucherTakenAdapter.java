package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Address;
import kienpd.com.mtee.data.model.Code;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BaseViewHolder;
import kienpd.com.mtee.utils.TextUtil;
import kienpd.com.mtee.utils.TimeUtil;

public class VoucherTakenAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<UserCode> mList;
    private Context mContext;
    private VoucherTakenAdapterCallback mCallback;

    public VoucherTakenAdapter(Context context, ArrayList<UserCode> list, VoucherTakenAdapterCallback callback) {
        mContext = context;
        mList = list;
        mCallback = callback;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VoucherCollectionHolder(mContext, LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_taken, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addItem(List<UserCode> repoList, Boolean isClearData) {
        if (mList != null) {
            if (isClearData) {
                mList.clear();
            }
            mList.addAll(repoList);
            notifyDataSetChanged();
        }
    }

    class VoucherCollectionHolder extends BaseViewHolder {

        @BindView(R.id.image_logo)
        ImageView mImageLogo;

        @BindView(R.id.image_follow)
        ImageView mImageFollow;

        @BindView(R.id.text_store)
        TextView mTextStore;

        @BindView(R.id.text_address)
        TextView mTextAddress;

        @BindView(R.id.text_code)
        TextView mTextCode;

        @BindView(R.id.text_title)
        TextView mTextTitle;

        @BindView(R.id.text_time)
        TextView mTextTime;

        @BindView(R.id.image_clock)
        ImageView mImageClock;

        @BindView(R.id.image_verified)
        ImageView mImageVerified;

        @BindView(R.id.text_verified)
        TextView mTextVerified;

        @BindView(R.id.layout_taken_voucher)
        LinearLayout mLayoutTakenVoucher;

        @BindView(R.id.layout_store)
        RelativeLayout mLayoutStore;

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
            UserCode userCode = mList.get(position);
            if (userCode != null) {
                mImageFollow.setColorFilter(mContext.getResources().getColor(R.color.color_item_un_select));
                mImageClock.setColorFilter(mContext.getResources().getColor(R.color.color_item_un_select));
                mImageVerified.setColorFilter(mContext.getResources().getColor(R.color.color_item_un_select));

                //Store
                Code code = userCode.getCode();
                Voucher voucher = code.getVoucher();
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
                }
                mTextCode.setText("Mã : " + code.getCode());
                mTextTitle.setText(voucher.getTitle());
                if (code.getStatus() == 2) {
                    mImageVerified.setColorFilter(mContext.getResources().getColor(R.color.color_verified));
                    mTextVerified.setTextColor(mContext.getResources().getColor(R.color.color_verified));
                    mTextVerified.setText("Đã dùng ");
                } else {
                    mImageVerified.setColorFilter(mContext.getResources().getColor(R.color.color_item_un_select));
                    mTextVerified.setTextColor(mContext.getResources().getColor(R.color.color_item_un_select));
                    mTextVerified.setText("Chưa dùng ");
                }

                int drawableResourceId = mContext.getResources().getIdentifier("logo_royal_tea", "drawable", mContext.getPackageName());
                Glide.with(mContext)
                        .load(drawableResourceId)
                        .apply(RequestOptions.circleCropTransform())
                        .into(mImageLogo);

                mTextTime.setText("Áp dụng tới ngày: " + TimeUtil.getStringDateFromMiliseconds(voucher.getTimeEnd()));

                mLayoutTakenVoucher.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onClickVoucherTakenListener(mList.get(position));
                    }
                });
                mLayoutStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onClickStoreVoucher(mList.get(position).getCode().getVoucher().getStore().getId());
                    }
                });
            }

        }
    }

    public interface VoucherTakenAdapterCallback {

        void onClickVoucherTakenListener(UserCode userCode);

        void onClickVoucherFollow(int id);

        void onClickStoreVoucher(int id);

    }

}
