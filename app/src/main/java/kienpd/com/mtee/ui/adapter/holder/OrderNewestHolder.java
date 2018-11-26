package kienpd.com.mtee.ui.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;

public class OrderNewestHolder extends HomeViewHolder {

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

    public OrderNewestHolder(View itemView) {
        super(itemView);
    }
//    private OrderNewestHolderCallback mCallback;


//    public OrderNewestHolder(Context context, View itemView, OrderNewestHolderCallback callback) {
//        super(itemView);
//        ButterKnife.bind(this, itemView);
//        mContext = context;
//        mCallback = callback;
//
//    }

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
        mLayoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mCallback.onClickOrderNewestHolderListener(position);
            }
        });
    }

//    public interface OrderNewestHolderCallback {
//        void onClickOrderNewestHolderListener(int position);
//    }
}
