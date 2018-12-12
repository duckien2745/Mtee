package kienpd.com.mtee.ui.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.RatingResponse;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class HeaderRatingTotalHolder extends CommentViewHolder {

    @BindView(R.id.image_user)
    ImageView mImageUser;

    @BindView(R.id.rating_bar)
    MaterialRatingBar mRatingBar;

    @BindView(R.id.progress_5_star)
    RoundCornerProgressBar mProcess5Star;

    @BindView(R.id.progress_4_star)
    RoundCornerProgressBar mProcess4Star;

    @BindView(R.id.progress_3_star)
    RoundCornerProgressBar mProcess3Star;

    @BindView(R.id.progress_2_star)
    RoundCornerProgressBar mProcess2Star;

    @BindView(R.id.progress_1_star)
    RoundCornerProgressBar mProcess1Star;

    @BindView(R.id.text_rate)
    TextView mTextRate;

    @BindView(R.id.text_total_rate)
    TextView mTextTotalRate;

    private Context mContext;

    public HeaderRatingTotalHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onBind(final RatingResponse totalRatting) {
        if (totalRatting != null) {
            mImageUser.setColorFilter(mContext.getResources().getColor(R.color.color_item_un_select));

            int totalRate = totalRatting.getRating1star() + totalRatting.getRating2star() + totalRatting.getRating3star() + totalRatting.getRating4star() + totalRatting.getRating5star();
            int totalStar = totalRatting.getRating1star() + totalRatting.getRating2star() * 2 + totalRatting.getRating3star() * 3 + totalRatting.getRating4star() * 4 + totalRatting.getRating5star() * 5;
            float avg = (float) totalStar / totalRate;
            //nearest one-half value
            float star = (Math.round(avg * 2) / 2.0f);

            //Star rating
            mTextRate.setText(String.valueOf(star));

            //Count rating
            mTextTotalRate.setText(totalRate + " đánh giá");

            //Rating statistics
            mProcess5Star.setMax(totalRate);
            mProcess5Star.setProgress(totalRatting.getRating5star());
            mProcess4Star.setMax(totalRate);
            mProcess4Star.setProgress(totalRatting.getRating4star());
            mProcess3Star.setMax(totalRate);
            mProcess3Star.setProgress(totalRatting.getRating3star());
            mProcess2Star.setMax(totalRate);
            mProcess2Star.setProgress(totalRatting.getRating2star());
            mProcess1Star.setMax(totalRate);
            mProcess1Star.setProgress(totalRatting.getRating1star());

            mRatingBar.setRating(star);

        }
    }

}