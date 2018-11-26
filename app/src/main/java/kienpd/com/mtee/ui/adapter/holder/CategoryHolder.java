package kienpd.com.mtee.ui.adapter.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    @BindView(R.id.layout_food)
    LinearLayout mLayoutFood;

    @BindView(R.id.layout_beauty)
    LinearLayout mLayoutBeauty;

    @BindView(R.id.layout_fashion)
    LinearLayout mLayoutFashion;

    @BindView(R.id.text_food)
    TextView mTextFood;

    @BindView(R.id.text_beauty)
    TextView mTextBeauty;

    @BindView(R.id.text_fashion)
    TextView mTextFashion;


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
                setColorLayout();
                mCallback.onClickCategoryHolderListener(mCategoryIdSelected);
                break;
            case R.id.image_beauty:
                mImageBeauty.startAnimation(anim);
                if (mCategoryIdSelected == Const.Category.CATEGORY_BEAUTY) {
                    mCategoryIdSelected = Const.Category.CATEGORY_ALL;
                } else {
                    mCategoryIdSelected = Const.Category.CATEGORY_BEAUTY;
                }
                setColorLayout();
                mCallback.onClickCategoryHolderListener(mCategoryIdSelected);
                break;
            case R.id.image_fashion:
                mImageFashion.startAnimation(anim);
                if (mCategoryIdSelected == Const.Category.CATEGORY_FASHION) {
                    mCategoryIdSelected = Const.Category.CATEGORY_ALL;
                } else {
                    mCategoryIdSelected = Const.Category.CATEGORY_FASHION;
                }
                setColorLayout();
                mCallback.onClickCategoryHolderListener(mCategoryIdSelected);
                break;
            default:
                break;
        }
    }

    public interface CategoryHolderCallback {
        void onClickCategoryHolderListener(int category);
    }

    private void setColorLayout() {
        mLayoutFood.setBackground(mCategoryIdSelected == Const.Category.CATEGORY_FOOD ? mContext.getResources().getDrawable(R.drawable.bg_category_red) : mContext.getResources().getDrawable(R.drawable.bg_category_gray));
        mTextFood.setTextColor(mCategoryIdSelected == Const.Category.CATEGORY_FOOD ? mContext.getResources().getColor(R.color.color_item_select) : mContext.getResources().getColor(R.color.color_gray));
        mLayoutBeauty.setBackground(mCategoryIdSelected == Const.Category.CATEGORY_BEAUTY ? mContext.getResources().getDrawable(R.drawable.bg_category_red) : mContext.getResources().getDrawable(R.drawable.bg_category_gray));
        mTextBeauty.setTextColor(mCategoryIdSelected == Const.Category.CATEGORY_BEAUTY ? mContext.getResources().getColor(R.color.color_item_select) : mContext.getResources().getColor(R.color.color_gray));
        mLayoutFashion.setBackground(mCategoryIdSelected == Const.Category.CATEGORY_FASHION ? mContext.getResources().getDrawable(R.drawable.bg_category_red) : mContext.getResources().getDrawable(R.drawable.bg_category_gray));
        mTextFashion.setTextColor(mCategoryIdSelected == Const.Category.CATEGORY_FASHION ? mContext.getResources().getColor(R.color.color_item_select) : mContext.getResources().getColor(R.color.color_gray));
    }
}
