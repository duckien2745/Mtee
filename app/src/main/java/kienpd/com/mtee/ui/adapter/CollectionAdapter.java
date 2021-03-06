package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.holder.HomeViewHolder;
import kienpd.com.mtee.utils.TextUtil;

public class CollectionAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private List<Collection> mList;
    private Context mContext;
    private CollectionAdapterCallback mCallback;

    public CollectionAdapter(Context context, List<Collection> list, CollectionAdapterCallback callback) {
        mContext = context;
        mList = list;
        mCallback = callback;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderHolder holder = new OrderHolder(mContext,
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false));

        int pxPadding = mContext.getResources().getDimensionPixelSize(R.dimen.padding_item_home);
        int w = (Resources.getSystem().getDisplayMetrics().widthPixels - pxPadding * 4) / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.mLinearLayout.setLayoutParams(layoutParams);

        return holder;

    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class OrderHolder extends HomeViewHolder {

        @BindView(R.id.text_store)
        TextView mTextStore;

        @BindView(R.id.text_title)
        TextView mTextTitle;

        @BindView(R.id.text_discount)
        TextView mTextDiscount;

        @BindView(R.id.text_count_like)
        TextView mTextCountLike;

        @BindView(R.id.image_product)
        ImageView mImageProduct;

        @BindView(R.id.layout_item_home)
        LinearLayout mLinearLayout;

        @BindView(R.id.layout_count_like)
        LinearLayout mLayoutCountLike;

        @BindView(R.id.layout_content_item)
        RelativeLayout mLayoutContent;

        private Context mContext;


        OrderHolder(Context context, View itemView) {
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

            Collection collection = mList.get(position);
            if (collection != null) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
                requestOptions = requestOptions.override(100,100);

                Glide.with(mContext)
                        .load(API.HOST_DEV + "thumb/" + collection.getPicture())
                        .apply(requestOptions)
                        .into(mImageProduct);

                mTextStore.setVisibility(View.GONE);
                mTextDiscount.setVisibility(View.GONE);
                mTextCountLike.setVisibility(View.GONE);
                mLayoutCountLike.setVisibility(View.GONE);
                mTextTitle.setText(collection.getName());
            }


            mLayoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClickCollectionListener(mList.get(position).getId());
                }
            });

        }
    }

    public interface CollectionAdapterCallback {
        void onClickCollectionListener(int position);
    }

}
