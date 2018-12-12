package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import kienpd.com.mtee.data.model.Rating;
import kienpd.com.mtee.data.model.RatingResponse;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.holder.CommentViewHolder;
import kienpd.com.mtee.ui.adapter.holder.FollowViewHolder;
import kienpd.com.mtee.ui.adapter.holder.HeaderRatingTotalHolder;
import kienpd.com.mtee.ui.adapter.holder.HeaderStoreHolder;
import kienpd.com.mtee.ui.adapter.holder.LoadingHolder;
import kienpd.com.mtee.ui.base.BaseViewHolder;
import kienpd.com.mtee.utils.NetworkUtils;
import kienpd.com.mtee.utils.TextUtil;
import kienpd.com.mtee.utils.TimeUtil;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> implements LoadingHolder.LoadingListener {

    private final int TYPE_HEADER_RATING = 0;
    public static final int TYPE_ITEM_RATING = 1;
    private final int TYPE_ITEM_LOAD_MORE = 2;

    private Context mContext;
    private RatingAdapterCallBack mCallBack;
    private List<Rating> mListRating;
    private RatingResponse mRatingResponse;

    public CommentAdapter(Context context, List<Rating> listRating, RatingResponse ratingResponse, RatingAdapterCallBack callBack) {
        mContext = context;
        mListRating = listRating;
        mRatingResponse = ratingResponse;
        mCallBack = callBack;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER_RATING:
                return new HeaderRatingTotalHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_total_rating, parent, false), mContext);

            case TYPE_ITEM_RATING:
                return new EvaluationHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation, parent, false));
            default:
                return null;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HEADER_RATING:
                if (mRatingResponse != null) {
                    holder.onBind(mRatingResponse);
                }
                break;
            case TYPE_ITEM_RATING:
                holder.onBind(position);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return TYPE_HEADER_RATING;
            default:
                return TYPE_ITEM_RATING;
        }
    }

    @Override
    public int getItemCount() {
        return mListRating.size() + 1;
    }

    @Override
    public void onLoadMoreClick(LoadingHolder loadingViewHolder) {
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            loadingViewHolder.setLoading(false);
        } else {
            loadingViewHolder.setLoading(true);
            mCallBack.onClickLoadMore();
        }
    }


    public interface RatingAdapterCallBack {

        void onClickLoadMore();
    }


    public void addItemRating(List<Rating> repoList, Boolean isClearData) {
        if (mListRating != null) {
            if (isClearData) {
                mListRating.clear();
            }
            mListRating.addAll(repoList);
            notifyDataSetChanged();
        }
    }

    public void addItemRatingResponse(RatingResponse ratingResponse) {
        if (ratingResponse != null) {
            mRatingResponse = ratingResponse;
            notifyDataSetChanged();
        }
    }


    class EvaluationHolder extends CommentViewHolder {

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
            Rating rating = mListRating.get(position - 1);
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
        }
    }

}
