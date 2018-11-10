package kienpd.com.mtee.ui.adapter.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.base.BaseViewHolder;

public class OrderNewestHolder extends BaseViewHolder {

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

    private Context mContext;


    public OrderNewestHolder(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onBind(int position) {
        super.onBind(position);
        int drawableResourceId = mContext.getResources().getIdentifier("bg_test", "drawable", mContext.getPackageName());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(mContext)
                .load(drawableResourceId)
                .apply(requestOptions)
                .into(mImageProduct);


    }
}
