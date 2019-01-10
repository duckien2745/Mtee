package kienpd.com.mtee.ui.home.active;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Message;
import kienpd.com.mtee.data.model.Messager;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.utils.TextUtil;

public class ActiveFragment extends BaseDialog implements ActiveMvpView {

    ActiveMvpPresenter<ActiveMvpView> mPresenter;
    public static final String TAG = "ACTIVE_FRAGMENT";
    public static final String EXTRAS_CODE = "extras_code";
    public static String ACTIVE = "active";

    @BindView(R.id.text_title_center)
    TextView mTextTitle;

    @BindView(R.id.edit_password)
    EditText mEditPassword;

    @BindView(R.id.layout_accept)
    LinearLayout mLayoutAccept;

    private String mCode;

    public static ActiveFragment newInstance(String code) {

        ActiveFragment dialogFragment = new ActiveFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_CODE, code);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new ActivePresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        mCode = getArguments().getString(EXTRAS_CODE, TextUtil.EMPTY_STRING);

        mLayoutAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtil.isEmpty(mEditPassword.getText().toString()) && !TextUtil.isEmpty(mCode))
                    mPresenter.activeCode(Integer.valueOf(mEditPassword.getText().toString()), mCode);
            }
        });
    }

    @Override
    public void onBackButtonPressed() {
        dismissDialog(TAG);
    }


    @Override
    public void statusActiveVoucher(Messager message) {
        if (message != null) {
            if (message.getStatus() == 2) {
                dismissDialog(TAG);
                Intent intent = new Intent();
                intent.putExtra(ACTIVE, true);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            } else {
                getBaseActivity().showMessage(message.getMessage());
            }
        }
    }
}
