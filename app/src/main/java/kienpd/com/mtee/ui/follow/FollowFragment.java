package kienpd.com.mtee.ui.follow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.db.StorageManager;
import kienpd.com.mtee.data.model.MessageEvent;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.adapter.holder.FollowStoreAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.custom.GridDividerItemDecoration;
import kienpd.com.mtee.ui.follow.store.StoreFragment;
import kienpd.com.mtee.utils.CommonUtils;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.TextUtil;

public class FollowFragment extends BaseFragment implements FollowMvpView, FollowStoreAdapter.StoreFollowAdapterCallback, StoreFragment.StoreListener {

    FollowMvpPresenter<FollowMvpView> mPresenter;

    @BindView(R.id.recycler_store)
    RecyclerView mRecyclerStore;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwipeToRefresh;

    @BindView(R.id.layout_refresh)
    LinearLayout mLayoutRefresh;

    @BindView(R.id.text_tab_to_refresh)
    TextView mTextRefresh;

    @BindView(R.id.layout_follow)
    LinearLayout mLayoutFollow;

    @BindView(R.id.layout_sign_in_with_google)
    LinearLayout mLayoutSignWithGoogle;

    private FollowStoreAdapter mAdapter;
    private List<Store> mList;

    private int userId;

    private static final int RC_SIGN_IN = 9002;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_store, container, false);
        setUnBinder(ButterKnife.bind(this, view));

        mPresenter = new FollowPresenter<>();
        mPresenter.onAttach(this);
        return view;
    }

    @Override
    protected void setUp(View view) {
        String jsonUser = StorageManager.getStringValue(getBaseActivity(), Const.User.KEY_SAVE_USER);
        if (jsonUser != null && !TextUtil.isEmpty(jsonUser)) {
            Gson gson = new Gson();
            User user = gson.fromJson(jsonUser, User.class);
            if (user != null) {
                mLayoutFollow.setVisibility(View.VISIBLE);
                mLayoutSignWithGoogle.setVisibility(View.GONE);

                int px = CommonUtils.dpToPx(10);
                GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(px, 1);

                userId = user.getId();
                mSwipeToRefresh.setColorSchemeResources(R.color.color_item_select);

                mList = new ArrayList<>();
                mAdapter = new FollowStoreAdapter(getBaseActivity(), mList, this);
                mRecyclerStore.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
                mRecyclerStore.addItemDecoration(itemDecoration);
                mRecyclerStore.setAdapter(mAdapter);

                mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mPresenter.loadData(userId, true);
                    }
                });

                mTextRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSwipeToRefresh.setRefreshing(true);
                        mPresenter.loadData(userId, true);
                    }
                });

                mSwipeToRefresh.setRefreshing(true);
                mPresenter.loadData(userId, true);
            }
        } else {
            mLayoutFollow.setVisibility(View.GONE);
            mLayoutSignWithGoogle.setVisibility(View.VISIBLE);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestId()
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(getBaseActivity(), gso);

        }

        mLayoutSignWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    public void onClickStoreFollow(int id) {
        StoreFragment fragment = StoreFragment.newInstance(id);
        fragment.setStoreListener(this);
        fragment.show(getFragmentManager(), StoreFragment.TAG);
    }

    @Override
    public void onClickButtonFollowStore(int storeId) {
        mPresenter.updateStatusUserFollow(storeId, userId);
    }

    @Override
    public void displayData(List<Store> storeList, Boolean isClearData) {
        mSwipeToRefresh.setRefreshing(false);

        if (storeList.size() > 0) {
            mRecyclerStore.setVisibility(View.VISIBLE);
            mLayoutRefresh.setVisibility(View.GONE);
            mList = storeList;
            mAdapter.addItem(mList, isClearData);
        } else {
            mRecyclerStore.setVisibility(View.GONE);
            mLayoutRefresh.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updateStatusFollow(Boolean isUserFollow, int storeId) {
        if (!isUserFollow) {
            if (mList != null && mList.size() > 0) {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getId() == storeId) {
                        mList.remove(mList.get(i));
                        mAdapter.removeAt(i);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account.getIdToken() != null) {
                mPresenter.signInWithGoogle("Google", account.getIdToken());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateUI(User user) {
        if (user != null) {
            StorageManager.saveUserDetails(getBaseActivity(), user);
            mLayoutFollow.setVisibility(View.VISIBLE);
            mLayoutSignWithGoogle.setVisibility(View.GONE);

            int px = CommonUtils.dpToPx(10);
            GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(px, 1);

            mAdapter = new FollowStoreAdapter(getBaseActivity(), new ArrayList<Store>(), this);
            mRecyclerStore.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
            mRecyclerStore.addItemDecoration(itemDecoration);
            mRecyclerStore.setAdapter(mAdapter);
            mPresenter.loadData(user.getId(), true);

            String jsonUser = StorageManager.getStringValue(getBaseActivity(), Const.User.KEY_SAVE_USER);
            Log.d("byddye", "a1a1" + jsonUser);
            EventBus.getDefault().post(new MessageEvent(1, "SignIn Success"));
        }
    }

    @Override
    public void onUpdateFollow(int id, Boolean isFollow) {
        updateStatusFollow(isFollow, id);
    }

}
