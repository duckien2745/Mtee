package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.model.Address;
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.holder.HomeViewHolder;
import kienpd.com.mtee.ui.base.BaseViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Store> mList;
    private Context mContext;
    private SearchAdapterCallback mCallback;

    public SearchAdapter(Context context, List<Store> list, SearchAdapterCallback callback) {
        mContext = context;
        mList = list;
        mCallback = callback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchHolder holder = new SearchHolder(mContext,
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false));

//        int pxPadding = mContext.getResources().getDimensionPixelSize(R.dimen.padding_item_home);
//        int w = (Resources.getSystem().getDisplayMetrics().widthPixels - pxPadding * 4) / 3;
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
//        holder.mLinearLayout.setLayoutParams(layoutParams);

        return holder;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class SearchHolder extends BaseViewHolder {

        @BindView(R.id.text_store)
        TextView mTextStore;

        @BindView(R.id.text_address)
        TextView mTextAddress;

        @BindView(R.id.image_store)
        ImageView mImageStore;

        @BindView(R.id.layout_item_search)
        LinearLayout mLayoutSearch;

        private Context mContext;

        SearchHolder(Context context, View itemView) {
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
            final Store store = mList.get(position);
            if (store != null) {
                Glide.with(mContext)
                        .load(API.HOST_DEV + store.getPicture())
                        .into(mImageStore);
                mTextStore.setText(store.getName());
                Address address = store.getAddressSearch();
                if (address != null) {
                    String sAddress = address.getNo() + "," + address.getStreet() + "," + address.getDistrict() + "," + address.getCity();
                    mTextAddress.setText(sAddress);
                }
                mLayoutSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onClickSearchListener(store.getId());
                    }
                });
            }
        }
    }

    public interface SearchAdapterCallback {
        void onClickSearchListener(int id);
    }

    public void addItem(List<Store> repoList, Boolean isClearData) {
        if (mList != null) {
            if (isClearData) {
                mList.clear();
            }
            mList.addAll(repoList);
            notifyDataSetChanged();
        }
    }
}
