package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.modelTest;
import kienpd.com.mtee.ui.base.BaseViewHolder;

public class HighLightAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<modelTest> mList;
    private Context mContext;
    private HighLightAdapterCallback mCallback;

    public HighLightAdapter(Context context, ArrayList<modelTest> list, HighLightAdapterCallback callback) {
        mContext = context;
        mList = list;
        mCallback = callback;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderHolder holder = new OrderHolder(mContext,
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false));

        int pxPadding = mContext.getResources().getDimensionPixelSize(R.dimen.padding_item_home);
        int w = (Resources.getSystem().getDisplayMetrics().widthPixels - pxPadding * 4) / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.mLinearLayout.setLayoutParams(layoutParams);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return 18;
    }


    class OrderHolder extends BaseViewHolder {

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

            int drawableResourceId = mContext.getResources().getIdentifier("bg_test", "drawable", mContext.getPackageName());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
            Glide.with(mContext)
                    .load(drawableResourceId)
                    .apply(requestOptions)
                    .into(mImageProduct);

            mImageProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClickHighLightListener(position);
                }
            });
        }
    }

    public interface HighLightAdapterCallback {
        void onClickHighLightListener(int position);
    }
}
