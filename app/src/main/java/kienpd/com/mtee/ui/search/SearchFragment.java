package kienpd.com.mtee.ui.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.ui.adapter.SearchAdapter;
import kienpd.com.mtee.ui.adapter.VoucherTakenAdapter;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.custom.GridDividerItemDecoration;
import kienpd.com.mtee.ui.follow.store.StoreFragment;
import kienpd.com.mtee.utils.CommonUtils;
import kienpd.com.mtee.utils.TextUtil;

public class SearchFragment extends BaseDialog implements SearchMvpView, SearchAdapter.SearchAdapterCallback, View.OnClickListener {

    SearchMvpPresenter<SearchMvpView> mPresenter;
    public static final String TAG = "SEARCH_FRAGMENT";

    @BindView(R.id.image_back)
    ImageView mImageBack;

    @BindView(R.id.image_delete)
    ImageView mImageDelete;

    @BindView(R.id.edit_search)
    EditText mEditSearch;

    @BindView(R.id.recycler_search)
    RecyclerView mRecyclerSearch;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwipeToRefresh;

    @BindView(R.id.layout_refresh)
    LinearLayout mLayoutRefresh;

    @BindView(R.id.text_tab_to_refresh)
    TextView mTextRefresh;

    private SearchAdapter mAdapter;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new SearchPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        int px = CommonUtils.dpToPx(10);
        GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(px, 1);

        mAdapter = new SearchAdapter(getBaseActivity(), new ArrayList<Store>(), this);
        mRecyclerSearch.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerSearch.addItemDecoration(itemDecoration);
        mRecyclerSearch.setAdapter(mAdapter);

        mEditSearch.addTextChangedListener(searchTextWatcher);
        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String mTextSearch = mEditSearch.getText().toString();
                    if (!mTextSearch.isEmpty()) {
                        mEditSearch.clearFocus();
                        hideKeyboardFrom(getBaseActivity(), mEditSearch);
                        mPresenter.loadData(mTextSearch, true);
                    }
                    return true;
                }
                return false;
            }
        });
        mSwipeToRefresh.setColorSchemeResources(R.color.color_item_select);
        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String mTextSearch = mEditSearch.getText().toString();
                if (!mTextSearch.isEmpty()) {
                    mEditSearch.clearFocus();
                    hideKeyboardFrom(getBaseActivity(), mEditSearch);
                    mPresenter.loadData(mTextSearch, true);
                }
            }
        });

        mTextRefresh.setOnClickListener(this);

        mImageBack.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageDelete.setColorFilter(getResources().getColor(R.color.color_item_un_select));

        mImageBack.setOnClickListener(this);
        mImageDelete.setOnClickListener(this);
    }

    @Override
    public void onClickSearchListener(int id) {
        StoreFragment fragment = StoreFragment.newInstance(id);
        fragment.show(getFragmentManager(), StoreFragment.TAG);
    }

    @Override
    public void updateRepoSearchView(List<Store> repoList, Boolean isClearData) {
        mSwipeToRefresh.setRefreshing(false);

        if (repoList.size() > 0) {
            mRecyclerSearch.setVisibility(View.VISIBLE);
            mLayoutRefresh.setVisibility(View.GONE);
            mAdapter.addItem(repoList, isClearData);
        } else {
            mRecyclerSearch.setVisibility(View.GONE);
            mLayoutRefresh.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                dismissDialog(TAG);
                break;
            case R.id.image_delete:
                mEditSearch.setText(TextUtil.EMPTY_STRING);
                break;
            case R.id.text_tab_to_refresh:
                mSwipeToRefresh.setRefreshing(true);
                String mTextSearch = mEditSearch.getText().toString();
                if (!mTextSearch.isEmpty()) {
                    mEditSearch.clearFocus();
                    hideKeyboardFrom(getBaseActivity(), mEditSearch);
                    mPresenter.loadData(mTextSearch, true);
                }
                break;
            default:
                break;
        }
    }

    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onEditTextChanged();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEditSearch.requestFocus();
                showKeyboard(getContext());
            }
        }, 500);

    }

    private void onEditTextChanged() {
        CharSequence text = mEditSearch.getText();
        boolean hasText = !TextUtils.isEmpty(text);
        if (hasText) {
            mImageDelete.setVisibility(View.VISIBLE);
        } else {
            mImageDelete.setVisibility(View.GONE);
        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context) {
        try {
            ((InputMethodManager) (context).getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
