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
import kienpd.com.mtee.data.model.Category;
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.ui.adapter.holder.HomeViewHolder;
import kienpd.com.mtee.utils.TextUtil;

public class CategorySettingAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private List<Category> mList;
    private Context mContext;
    private CategoryAdapterCallback mCallback;

    public CategorySettingAdapter(Context context, List<Category> list, CategoryAdapterCallback callback) {
        mContext = context;
        mList = list;
        mCallback = callback;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CategoryHolder holder = new CategoryHolder(mContext,
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));

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

    class CategoryHolder extends HomeViewHolder {

        @BindView(R.id.layout_item_category)
        RelativeLayout mLayoutItemCategory;

        @BindView(R.id.text_number)
        TextView mTextNumber;

        @BindView(R.id.text_category)
        TextView mTextCategory;

        @BindView(R.id.image_enable)
        ImageView mImageEnable;

        private Context mContext;

        CategoryHolder(Context context, View itemView) {
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

            final Category category = mList.get(position);
            if (category != null) {
                mTextNumber.setText(TextUtil.EMPTY_STRING + position);
                mTextCategory.setText(category.getCategoryName());

                if (category.getShow()) {
                    mImageEnable.setColorFilter(mContext.getResources().getColor(R.color.color_verified));
                } else {
                    mImageEnable.setColorFilter(mContext.getResources().getColor(R.color.color_white));
                }

                mLayoutItemCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (category.getShow()) {
                            mImageEnable.setColorFilter(mContext.getResources().getColor(R.color.color_white));
                            mCallback.onClickCategorySettingListener(position, false);
                        } else {
                            mImageEnable.setColorFilter(mContext.getResources().getColor(R.color.color_verified));
                            mCallback.onClickCategorySettingListener(position, true);
                        }
                    }
                });
            }

        }
    }

    public interface CategoryAdapterCallback {
        void onClickCategorySettingListener(int position, Boolean isShow);
    }

}
