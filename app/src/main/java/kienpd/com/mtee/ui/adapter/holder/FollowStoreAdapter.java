package kienpd.com.mtee.ui.adapter.holder;

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
import kienpd.com.mtee.data.model.Voucher;

public class FollowStoreAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private ArrayList<Store> mList;
    private Context mContext;
    private StoreFollowAdapterCallback mCallback;

    public FollowStoreAdapter(Context context, ArrayList<Store> list, StoreFollowAdapterCallback callback) {
        mContext = context;
        mList = list;
        mCallback = callback;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreFollowHolder(mContext, LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
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

    class StoreFollowHolder extends HomeViewHolder {

        @BindView(R.id.image_store)
        ImageView mImageStore;

        @BindView(R.id.image_follow)
        ImageView mImageFollow;

        @BindView(R.id.text_store)
        TextView mTextStore;

        @BindView(R.id.text_address)
        TextView mTextAddress;

        @BindView(R.id.layout_store)
        RelativeLayout mLayoutStore;

        private Context mContext;

        StoreFollowHolder(Context context, View itemView) {
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
            mImageFollow.setColorFilter(mContext.getResources().getColor(R.color.color_item_select));
            final Store store = mList.get(position);
            if (store != null) {
                mTextStore.setText(store.getName());
                Address address = store.getAddress();
                String sAddress = "";
                if (address != null) {
                    sAddress = address.getNo() + "," + address.getStreet() + "," + address.getDistrict() + "," + address.getCity();
                }
                mTextAddress.setText(sAddress);
            }

            int drawableResourceId = mContext.getResources().getIdentifier("logo_royal_tea", "drawable", mContext.getPackageName());
            Glide.with(mContext)
                    .load(drawableResourceId)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageStore);

            mLayoutStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClickStoreFollow(mList.get(position).getId());
                }
            });

            mImageFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClickButtonFollowStore(mList.get(position).getId());
                }
            });
        }

    }

    public interface StoreFollowAdapterCallback {
        void onClickStoreFollow(int id);

        void onClickButtonFollowStore(int storeId);
    }
}





