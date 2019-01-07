package kienpd.com.mtee.ui.user.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.base.BaseDialog;

public class SettingFragment extends BaseDialog implements SettingMvpView {

    SettingMvpPresenter<SettingMvpView> mPresenter;

    public static final String TAG = "INFO_FRAGMENT";
    public static final String EXTRAS_USER = "extras_detail";

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    public static SettingFragment newInstance(String jsonUser) {

        SettingFragment f = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(EXTRAS_USER, jsonUser);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new SettingPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {

    }


}
