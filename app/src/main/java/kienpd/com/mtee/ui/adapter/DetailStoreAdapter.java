package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.holder.FollowStoreAdapter;
import kienpd.com.mtee.ui.adapter.holder.FollowViewHolder;
import kienpd.com.mtee.ui.adapter.holder.HeaderStoreHolder;
import kienpd.com.mtee.ui.adapter.holder.LoadingHolder;
import kienpd.com.mtee.ui.base.BaseViewHolder;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.NetworkUtils;
import kienpd.com.mtee.utils.TextUtil;

public class DetailStoreAdapter extends RecyclerView.Adapter<FollowViewHolder> implements LoadingHolder.LoadingListener, HeaderStoreHolder.HeaderStoreHolderCallback {

    private final int TYPE_HEADER_STORE = 0;
    private final int TYPE_ITEM_VOUCHER_STORE = 1;
    private final int TYPE_ITEM_LOAD_MORE = 2;

    private Context mContext;
    private DetailStoreAdapterCallBack mCallBack;
    private List<Voucher> mListVoucher;
    private Store mStore;

    public DetailStoreAdapter(Context context, List<Voucher> listVoucher, Store store, DetailStoreAdapterCallBack callBack) {
        mContext = context;
        mCallBack = callBack;
        mListVoucher = listVoucher;
        mStore = store;
    }


    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER_STORE:
                return new HeaderStoreHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_store, parent, false), this);
            case TYPE_ITEM_VOUCHER_STORE:
                return new VoucherInStoreHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newest, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HEADER_STORE:
                if (mStore != null) {
                    holder.onBind(mStore);
                }
                break;
            case TYPE_ITEM_VOUCHER_STORE:
                holder.onBind(position);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return TYPE_HEADER_STORE;
            default:
                return TYPE_ITEM_VOUCHER_STORE;

        }
    }


    @Override
    public int getItemCount() {
        return mListVoucher.size() + 1;
    }


    @Override
    public void onLoadMoreClick(LoadingHolder loadingViewHolder) {
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            loadingViewHolder.setLoading(false);
        } else {
            loadingViewHolder.setLoading(true);
            mCallBack.onClickLoadMore();
        }
    }

    @Override
    public void onClickFollowStoreListener(Integer storeId) {
        mCallBack.onClickFollow(storeId);
    }


    public interface DetailStoreAdapterCallBack {

        void onClickListener(int type, int id);

        void onClickLoadMore();

        void onClickFollow(int storeId);
    }


    public void addItemVoucherInStore(List<Voucher> repoList, Boolean isClearData) {
        if (mListVoucher != null) {
            if (isClearData) {
                mListVoucher.clear();
            }
            mListVoucher.addAll(repoList);
            notifyDataSetChanged();
        }
    }

    public void addItemStore(Store store) {
        if (store != null) {
            mStore = store;
            notifyDataSetChanged();
        }
    }

    public void updateStatusFollow(Boolean isUerFollow) {
        //todo
    }

    class VoucherInStoreHolder extends FollowViewHolder {

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

        private Context mContext;

        VoucherInStoreHolder(Context context, View itemView) {
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
            if (position > 0) {
                Voucher voucher = mListVoucher.get(position - 1);
                if (voucher != null) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

                    Glide.with(mContext)
                            .load(API.HOST_DEV + voucher.getCoverPicture())
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
                        if (position > 0) {
                            mCallBack.onClickListener(Const.Type.TYPE_NEWEST, mListVoucher.get(position - 1).getId());
                        }
                    }
                });
            }
        }
    }

}
