package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Rating;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.base.BaseViewHolder;
import kienpd.com.mtee.utils.TextUtil;
import kienpd.com.mtee.utils.TimeUtil;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class EvaluationAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Rating> mList;
    private Context mContext;
    private EvaluationAdapterCallback mCallback;

    public EvaluationAdapter(Context context, List<Rating> list, EvaluationAdapterCallback callback) {
        mContext = context;
        mList = list;
        mCallback = callback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EvaluationHolder holder = new EvaluationHolder(mContext,
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation, parent, false));

        return holder;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {

        if (mList.size() > 3) {
            return 3;
        }
        return mList.size();
    }

    public void addItem(List<Rating> ratingList) {
        if (ratingList != null && ratingList.size() > 0) {
            mList.clear();
            mList.addAll(ratingList);
            notifyDataSetChanged();
        }
    }

    class EvaluationHolder extends BaseViewHolder {

        @BindView(R.id.text_name_user)
        TextView mTextNameUser;

        @BindView(R.id.text_time_post)
        TextView mTextTimePost;

        @BindView(R.id.text_comment)
        TextView mTextComment;

        @BindView(R.id.image_avatar)
        ImageView mImageAvatar;

        @BindView(R.id.rating_bar_user)
        MaterialRatingBar mRatingBarUser;

        @BindView(R.id.layout_user_ratting)
        RelativeLayout mLayoutUserRatting;

        private Context mContext;


        EvaluationHolder(Context context, View itemView) {
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

            Rating rating = mList.get(position);
            if (rating != null) {
                User user = rating.getUser();
                if (user != null) {
                    //Name
                    mTextNameUser.setText(user.getName());

                    //Avatar
                    Glide.with(mContext)
                            .load(user.getPicture())
                            .apply(RequestOptions.circleCropTransform())
                            .into(mImageAvatar);

                }
                mTextTimePost.setText(TextUtil.EMPTY_STRING + TimeUtil.getStringDateFromMiliseconds(rating.getTimePost()));
                mTextComment.setText(rating.getComment());

                if (rating.getStar() != 0) {
                    mRatingBarUser.setMax(5);
                    mRatingBarUser.setRating(rating.getStar());
                }
            }

            mLayoutUserRatting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClickEvaluationListener();
                }
            });

        }
    }

    public interface EvaluationAdapterCallback {
        void onClickEvaluationListener();
    }

}
