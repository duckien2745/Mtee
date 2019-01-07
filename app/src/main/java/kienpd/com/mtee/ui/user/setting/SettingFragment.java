package kienpd.com.mtee.ui.user.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.db.StorageManager;
import kienpd.com.mtee.data.model.Category;
import kienpd.com.mtee.ui.adapter.CategorySettingAdapter;
import kienpd.com.mtee.ui.adapter.CollectionAdapter;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.TextUtil;

public class SettingFragment extends BaseDialog implements SettingMvpView, CategorySettingAdapter.CategoryAdapterCallback, View.OnClickListener {

    SettingMvpPresenter<SettingMvpView> mPresenter;

    public static final String TAG = "SETTING_FRAGMENT";

    @BindView(R.id.recycler_category)
    RecyclerView mRecyclerCategory;

    @BindView(R.id.image_back)
    ImageView mImageBack;

    @BindView(R.id.text_right)
    TextView mTextRight;

    @BindView(R.id.text_title_center)
    TextView mTextCenter;

    private List<Category> mListCategory;
    private Gson mGson;

    public static SettingFragment newInstance() {

        SettingFragment f = new SettingFragment();
        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_category, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new SettingPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        mListCategory = new ArrayList<>();
        mGson = new Gson();

        mTextCenter.setText("Chuyên mục");
        mTextRight.setText("Lưu");
        mTextRight.setVisibility(View.VISIBLE);

        String json = StorageManager.getStringValue(getBaseActivity(), Const.UserCategory.key_category);
        if (json != null && !TextUtil.isEmpty(json)) {
            mListCategory = mGson.fromJson(json, new TypeToken<List<Category>>() {
            }.getType());

            if (mListCategory != null && mListCategory.size() > 0) {

                CategorySettingAdapter adapter = new CategorySettingAdapter(getBaseActivity(), mListCategory, this);
                GridLayoutManager layoutManager = new GridLayoutManager(getBaseActivity(), 1, LinearLayoutManager.VERTICAL, false);
                mRecyclerCategory.setLayoutManager(layoutManager);
                mRecyclerCategory.setAdapter(adapter);

            }
        }

        mImageBack.setOnClickListener(this);
        mTextRight.setOnClickListener(this);
    }


    @Override
    public void onClickCategorySettingListener(int position, Boolean isShow) {
        if (mListCategory != null && mListCategory.size() > 0) {
            if (mListCategory.get(position) != null) {
                mListCategory.get(position).setShow(isShow);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                dismissDialog(TAG);
                break;
            case R.id.text_right:
                if (mListCategory != null && mListCategory.size() > 0) {
                    String json = mGson.toJson(mListCategory);
                    StorageManager.setStringValue(getBaseActivity(), Const.UserCategory.key_category, json);
                }
                dismissDialog(TAG);
                break;
            default:
                break;
        }
    }
}
