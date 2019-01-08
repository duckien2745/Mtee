package kienpd.com.mtee.ui.user;

import android.content.Intent;
import android.net.Uri;
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
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.db.StorageManager;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.home.HomeMvpPresenter;
import kienpd.com.mtee.ui.home.HomeMvpView;
import kienpd.com.mtee.ui.home.HomePresenter;
import kienpd.com.mtee.ui.home.rules.RulesFragment;
import kienpd.com.mtee.ui.user.info.InfoFragment;
import kienpd.com.mtee.ui.user.setting.SettingFragment;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.TextUtil;
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

    @BindView(R.id.layout_more)
    LinearLayout mLayoutMore;

    @BindView(R.id.text_version)
    TextView mTextVersion;

    @BindView(R.id.layout_idea)
    RelativeLayout mLayoutIdea;

    @BindView(R.id.image_idea)
    ImageView mImageIdea;

    @BindView(R.id.image_arrow_idea)
    ImageView mImageArrowIdea;

    @BindView(R.id.layout_setting_category)
    RelativeLayout mLayoutSettingCategory;

    @BindView(R.id.image_setting)
    ImageView mImageSetting;

    @BindView(R.id.image_arrow_setting)
    ImageView mImageArrowSetting;

    @BindView(R.id.layout_cooperation)
    RelativeLayout mLayoutCooperation;

    @BindView(R.id.image_cooperation)
    ImageView mImageCooperation;

    @BindView(R.id.image_arrow_cooperation)
    ImageView mImageArrowCooperation;

    @BindView(R.id.layout_rating)
    RelativeLayout mLayoutRating;

    @BindView(R.id.image_rating)
    ImageView mImageRating;

    @BindView(R.id.image_arrow_rating)
    ImageView mImageArrowRating;

    @BindView(R.id.layout_share)
    RelativeLayout mLayoutShare;

    @BindView(R.id.image_share)
    ImageView mImageShare;

    @BindView(R.id.image_arrow_share)
    ImageView mImageArrowShare;

    private static final String TAG = "UserFragment";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private String jsonUser;

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

        mImageIdea.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageArrowIdea.setColorFilter(getResources().getColor(R.color.color_item_gray));

        mImageSetting.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageArrowSetting.setColorFilter(getResources().getColor(R.color.color_item_gray));

        mImageRating.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageArrowRating.setColorFilter(getResources().getColor(R.color.color_item_gray));

        mImageCooperation.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageArrowCooperation.setColorFilter(getResources().getColor(R.color.color_item_gray));

        mImageShare.setColorFilter(getResources().getColor(R.color.color_item_un_select));
        mImageArrowShare.setColorFilter(getResources().getColor(R.color.color_item_gray));


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getBaseActivity(), gso);

        jsonUser = StorageManager.getStringValue(getBaseActivity(), Const.User.KEY_SAVE_USER);
        if (jsonUser != null && !TextUtil.isEmpty(jsonUser)) {
            Gson gson = new Gson();
            User user = gson.fromJson(jsonUser, User.class);
            if (user != null) {
                updateUI(user);
            }
        }

        mLayoutSignInGoogle.setOnClickListener(this);
        mLayoutLogout.setOnClickListener(this);
        mImageArrowRight.setOnClickListener(this);
        mLayoutSettingCategory.setOnClickListener(this);
        mLayoutIdea.setOnClickListener(this);
        mLayoutCooperation.setOnClickListener(this);
        mLayoutRating.setOnClickListener(this);
        mLayoutShare.setOnClickListener(this);
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
            case R.id.image_arrow_right:
                InfoFragment fragment = InfoFragment.newInstance(jsonUser);
                fragment.show(getFragmentManager(), InfoFragment.TAG);
                break;
            case R.id.layout_setting_category:
                SettingFragment settingFragment = SettingFragment.newInstance();
                settingFragment.show(getFragmentManager(), SettingFragment.TAG);
                break;
            case R.id.layout_idea:
                directMail(new String[]{"duckien2745@gmail.com"}, "Góp ý sản phẩm", "Góp ý", "Send mail...");
                break;
            case R.id.layout_cooperation:
                directMail(new String[]{"duckien2745@gmail.com"}, "Hợp tác kinh doanh", "Hợp tác", "Send mail...");
                break;
            case R.id.layout_rating:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.jamjavn.jamja")));
                break;
            case R.id.layout_share:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.jamjavn.jamja")));
                break;
            default:
                break;
        }
    }


    @Override
    public void updateUI(User user) {
        if (user != null) {
            mLayoutMore.setVisibility(View.VISIBLE);
            mTextVersion.setVisibility(View.VISIBLE);
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

            StorageManager.saveUserDetails(getBaseActivity(), user);
            jsonUser = StorageManager.getStringValue(getBaseActivity(), Const.User.KEY_SAVE_USER);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getBaseActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        StorageManager.saveUserDetails(getBaseActivity(), null);

                        mLayoutSignInGoogle.setVisibility(View.VISIBLE);
                        mLayoutUser.setVisibility(View.GONE);
                        mLayoutLogout.setVisibility(View.GONE);
                        mLayoutMore.setVisibility(View.GONE);
                        mTextVersion.setVisibility(View.GONE);
                        mLayoutUser.setVisibility(View.GONE);
                    }
                });
    }

    private void directMail(String[] mail, String subject, String content, String chooser) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, mail);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(emailIntent, chooser));
    }
}
