package kienpd.com.mtee.ui.adapter.holder;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.utils.Const;

public class CategoryHolder extends HomeViewHolder implements View.OnClickListener {

    private CategoryHolderCallback mCallback;

    @BindView(R.id.image_food)
    ImageView mImageFood;

    @BindView(R.id.image_beauty)
    ImageView mImageBeauty;

    @BindView(R.id.image_fashion)
    ImageView mImageFashion;

    private int mCategoryIdSelected = Const.Category.CATEGORY_ALL;
    private Animation anim;
    private Context mContext;


    public CategoryHolder(Context context, View itemView, CategoryHolderCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mCallback = callback;
        mContext = context;
        anim = AnimationUtils.loadAnimation(mContext, R.anim.buttom_check);
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
                mImageFood.startAnimation(anim);
                if (mCategoryIdSelected == Const.Category.CATEGORY_FOOD) {
                    mCategoryIdSelected = Const.Category.CATEGORY_ALL;
                } else {
                    mCategoryIdSelected = Const.Category.CATEGORY_FOOD;
                }
                mCallback.onClickCategoryHolderListener(mCategoryIdSelected);
                break;
            case R.id.image_beauty:
                mImageBeauty.startAnimation(anim);
                if (mCategoryIdSelected == Const.Category.CATEGORY_BEAUTY) {
                    mCategoryIdSelected = Const.Category.CATEGORY_ALL;
                } else {
                    mCategoryIdSelected = Const.Category.CATEGORY_BEAUTY;
                }
                mCallback.onClickCategoryHolderListener(mCategoryIdSelected);
                break;
            case R.id.image_fashion:
                mImageFashion.startAnimation(anim);
                if (mCategoryIdSelected == Const.Category.CATEGORY_FASHION) {
                    mCategoryIdSelected = Const.Category.CATEGORY_ALL;
                } else {
                    mCategoryIdSelected = Const.Category.CATEGORY_FASHION;
                }
                mCallback.onClickCategoryHolderListener(mCategoryIdSelected);
                break;
            default:
                break;
        }
    }

    public interface CategoryHolderCallback {
        void onClickCategoryHolderListener(int category);
    }
}
