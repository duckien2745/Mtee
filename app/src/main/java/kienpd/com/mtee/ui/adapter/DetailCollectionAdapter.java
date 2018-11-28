package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.holder.HomeViewHolder;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.TextUtil;

public class DetailCollectionAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private ArrayList<Voucher> mList;
    private Context mContext;
    private DetailCollectionAdapterCallback mCallback;

    public DetailCollectionAdapter(Context context, ArrayList<Voucher> list, DetailCollectionAdapterCallback callback) {
        mContext = context;
        mList = list;
        mCallback = callback;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VoucherCollectionHolder holder = new VoucherCollectionHolder(mContext,
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_collection, parent, false));

        int pxPadding = mContext.getResources().getDimensionPixelSize(R.dimen.divider_size);
        int w = (Resources.getSystem().getDisplayMetrics().widthPixels - pxPadding * 3) / 2;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.mLayoutVoucher.setLayoutParams(layoutParams);

        return holder;

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

        @BindView(R.id.text_store)
        TextView mTextStore;

        @BindView(R.id.text_title)
        TextView mTextTitle;

        @BindView(R.id.text_discount)
        TextView mTextDiscount;

        @BindView(R.id.text_count_like)
        TextView mTextCountLike;

        @BindView(R.id.image_newest_product)
        ImageView mImageProduct;

        @BindView(R.id.layout_order)
        RelativeLayout mLayoutOrder;

        @BindView(R.id.layout_voucher_collection)
        LinearLayout mLayoutVoucher;

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
            Voucher voucher = mList.get(position);
            if (voucher != null) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

                Glide.with(mContext)
                        .load(API.HOST_DEV + "thumb/" + voucher.getCoverPicture())
                        .apply(requestOptions)
                        .into(mImageProduct);

                mTextStore.setText(voucher.getStore().getName());
                mTextTitle.setText(voucher.getTitle());
                if (voucher.getPercentDiscount() != 0) {
                    mTextDiscount.setVisibility(View.VISIBLE);
                    mTextDiscount.setText(String.valueOf(voucher.getPercentDiscount()) + TextUtil.CHARACTER_PERCENT);
                } else {
                    mTextDiscount.setVisibility(View.GONE);
                }
                mTextCountLike.setText(String.valueOf(voucher.getLikeCount()));
            }

            mLayoutOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClickDetailCollectionListener(mList.get(position).getId());

                }
            });

        }
    }

    public interface DetailCollectionAdapterCallback {
        void onClickDetailCollectionListener(int id);
    }

}
