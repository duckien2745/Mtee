package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.adapter.holder.HomeViewHolder;

public class PriceAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private ArrayList<String> mListUrl;
    private PriceAdapterCallback mCallback;
    private Context mContext;

    public PriceAdapter(Context context, ArrayList<String> listUrl, PriceAdapterCallback callback) {
        mContext = context;
        mCallback = callback;
        mListUrl = listUrl;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHolder(mContext,
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return mListUrl.size();
    }


    class OrderHolder extends HomeViewHolder {

        @BindView(R.id.image_price)
        ImageView mImagePrice;
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
                    .into(mImagePrice);

            mImagePrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClickPriceListener(position);
                }
            });
        }
    }

    public interface PriceAdapterCallback {
        void onClickPriceListener(int position);
    }
}
