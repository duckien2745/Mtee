package kienpd.com.mtee.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.home.HomeMvpPresenter;
import kienpd.com.mtee.ui.home.HomeMvpView;
import kienpd.com.mtee.ui.home.HomePresenter;
import kienpd.com.mtee.utils.TimeUtil;

public class UserFragment extends BaseFragment implements UserMvpView, View.OnClickListener {

    UserMvpPresenter<UserMvpView> mPresenter;

    @BindView(R.id.layout_sign_in_with_google)
    LinearLayout mLayoutSignInGoogle;

    @BindView(R.id.text_name)
    TextView mTextName;

    @BindView(R.id.text_type_account)
    TextView mTextTypeAccount;

    @BindView(R.id.text_join_time)
    TextView mTextJoinTime;

    @BindView(R.id.image_arrow_right)
    ImageView mImageArrowRight;

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.layout_logout)
    LinearLayout mLayoutLogout;

    @BindView(R.id.layout_user)
    RelativeLayout mLayoutUser;

    private static final String TAG = "UserFragment";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new UserPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        mImageArrowRight.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getBaseActivity(), gso);

        mLayoutSignInGoogle.setOnClickListener(this);
        mLayoutLogout.setOnClickListener(this);
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
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_sign_in_with_google:
                signIn();
                break;
            case R.id.layout_logout:
                signOut();
                break;
            default:
                break;
        }
    }


    @Override
    public void updateUI(User user) {
        if (user != null) {
            mLayoutUser.setVisibility(View.VISIBLE);
            mLayoutSignInGoogle.setVisibility(View.GONE);
            mLayoutLogout.setVisibility(View.VISIBLE);
            mTextName.setText(user.getName());
            mTextTypeAccount.setText("Tài khoản " + user.getType());

            String dateCreate = TimeUtil.getStringDateFromMiliseconds(user.getDateCreated());
            mTextJoinTime.setText("Ngày tham gia " + dateCreate);

            Glide.with(getBaseActivity())
                    .load(user.getPicture())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageAvatar);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getBaseActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
}
