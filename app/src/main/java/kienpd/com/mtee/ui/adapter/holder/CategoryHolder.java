package kienpd.com.mtee.ui.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.base.BaseViewHolder;
import kienpd.com.mtee.utils.Const;

public class CategoryHolder extends BaseViewHolder implements View.OnClickListener {

    //commit
    private CategoryHolderCallback mCallback;

    @BindView(R.id.image_food)
    ImageView mImageFood;

    @BindView(R.id.image_beauty)
    ImageView mImageBeauty;

    @BindView(R.id.image_fashion)
    ImageView mImageFashion;

    public CategoryHolder(View itemView, CategoryHolderCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mCallback = callback;
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onBind(int position) {
        super.onBind(position);
        mImageFood.setOnClickListener(this);
        mImageBeauty.setOnClickListener(this);
        mImageFashion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_food:
                mCallback.onClickCategoryHolderListener(Const.Category.CATEGORY_FOOD);
                break;
            case R.id.image_beauty:
                mCallback.onClickCategoryHolderListener(Const.Category.CATEGORY_BEAUTY);
                break;
            case R.id.image_fashion:
                mCallback.onClickCategoryHolderListener(Const.Category.CATEGORY_FASHION);
                break;
            default:
                break;
        }
    }

    public interface CategoryHolderCallback {
        void onClickCategoryHolderListener(int category);
    }
}
