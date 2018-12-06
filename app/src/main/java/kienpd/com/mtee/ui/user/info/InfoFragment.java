package kienpd.com.mtee.ui.user.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.base.BaseDialog;

public class InfoFragment extends BaseDialog implements InfoMvpView {

    InfoMvpPresenter<InfoMvpView> mPresenter;

    public static final String TAG = "INFO_FRAGMENT";
    public static final String EXTRAS_USER = "extras_detail";

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.edit_name)
    EditText mEditName;

    @BindView(R.id.edit_mail)
    EditText mEditMail;

    @BindView(R.id.edit_phone)
    EditText mEditPhone;

    @BindView(R.id.text_birthday)
    TextView mTextBirthday;

    @BindView(R.id.text_gender)
    TextView mTextGender;

    @BindView(R.id.layout_save)
    LinearLayout mLayoutSave;

    public static InfoFragment newInstance(String jsonUser) {

        InfoFragment f = new InfoFragment();
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
        mPresenter = new InfoPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {

    }
}
