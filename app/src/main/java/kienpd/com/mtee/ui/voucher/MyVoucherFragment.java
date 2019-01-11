package kienpd.com.mtee.ui.voucher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.db.StorageManager;
import kienpd.com.mtee.data.model.MessageEvent;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.adapter.MyVoucherAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.TextUtil;

public class MyVoucherFragment extends BaseFragment implements MyVoucherMvpView {

    private MyVoucherMvpPresenter<MyVoucherMvpView> mPresenter;

    @BindView(R.id.pager_my_voucher)
    ViewPager mViewPager;

    @BindView(R.id.tab_my_voucher)
    TabLayout mTabMyVoucher;

    @BindView(R.id.layout_my_voucher)
    LinearLayout mLayoutMyVoucher;

    @BindView(R.id.layout_sign_in_with_google)
    LinearLayout mLayoutSignWithGoogle;

    private static final int RC_SIGN_IN = 9003;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_voucher, container, false);

        mPresenter = new MyVoucherPresenter<>();
        mPresenter.onAttach(this);
        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }

    @Override
    protected void setUp(View view) {
        String jsonUser = StorageManager.getStringValue(getBaseActivity(), Const.User.KEY_SAVE_USER);
        if (jsonUser != null && !TextUtil.isEmpty(jsonUser)) {
            Gson gson = new Gson();
            User user = gson.fromJson(jsonUser, User.class);
            if (user != null) {
                mLayoutMyVoucher.setVisibility(View.VISIBLE);
                mLayoutSignWithGoogle.setVisibility(View.GONE);
                MyVoucherAdapter adapter = new MyVoucherAdapter(getBaseActivity(), getChildFragmentManager());
                mViewPager.setAdapter(adapter);
                mTabMyVoucher.setupWithViewPager(mViewPager);
            }
        } else {
            mLayoutMyVoucher.setVisibility(View.GONE);
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
            mLayoutMyVoucher.setVisibility(View.VISIBLE);
            mLayoutSignWithGoogle.setVisibility(View.GONE);
            MyVoucherAdapter adapter = new MyVoucherAdapter(getBaseActivity(), getChildFragmentManager());
            mViewPager.setAdapter(adapter);
            mTabMyVoucher.setupWithViewPager(mViewPager);
            EventBus.getDefault().post(new MessageEvent(1, "SignIn Success"));

        }


    }
}
