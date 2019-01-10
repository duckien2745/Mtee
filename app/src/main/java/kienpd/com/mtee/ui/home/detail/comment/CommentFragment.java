package kienpd.com.mtee.ui.home.detail.comment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Rating;
import kienpd.com.mtee.data.model.RatingResponse;
import kienpd.com.mtee.ui.adapter.CommentAdapter;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.utils.TextUtil;

public class CommentFragment extends BaseDialog implements CommentMvpView, CommentAdapter.RatingAdapterCallBack {

    CommentMvpPresenter<CommentMvpView> mPresenter;
    public static final String TAG = "COMMENT_FRAGMENT";
    public static final String EXTRAS_DETAIL_ID = "extras_detail_id";
    public static final String EXTRAS_JSON_RATING = "extras_json_rating";

    @BindView(R.id.recycler_rating)
    RecyclerView mRecyclerRating;

    @BindView(R.id.text_title_center)
    TextView mTextCenter;

    @BindView(R.id.image_back)
    ImageView mImageBack;


    private CommentAdapter mAdapter;
    private Integer mDetailId;
    private String mJson;

    public static CommentFragment newInstance(String jsonTotalRating, int detailId) {

        CommentFragment f = new CommentFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRAS_DETAIL_ID, detailId);
        args.putSerializable(EXTRAS_JSON_RATING, jsonTotalRating);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new CommentPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        mTextCenter.setText("Đánh giá");
        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog(TAG);
            }
        });

        mDetailId = getArguments().getInt(EXTRAS_DETAIL_ID);
        mJson = getArguments().getString(EXTRAS_JSON_RATING);

        if (mDetailId != 0 && !TextUtil.isEmpty(mJson)) {
            Gson gson = new Gson();
            RatingResponse ratingResponse = gson.fromJson(mJson, RatingResponse.class);

            mAdapter = new CommentAdapter(getBaseActivity(), new ArrayList<Rating>(), ratingResponse, this);
            mRecyclerRating.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
            mRecyclerRating.setAdapter(mAdapter);

            mPresenter.loadComment(mDetailId, true);
        }

    }

    @Override
    public void onBackButtonPressed() {
        dismissDialog(TAG);
    }

    @Override
    public void onClickLoadMore() {

    }

    @Override
    public void displayComment(List<Rating> ratingList, Boolean isClearData) {
        if (ratingList != null && ratingList.size() > 0) {
            mAdapter.addItemRating(ratingList, isClearData);
        }
    }
}
