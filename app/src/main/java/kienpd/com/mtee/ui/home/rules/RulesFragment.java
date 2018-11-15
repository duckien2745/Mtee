package kienpd.com.mtee.ui.home.rules;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.home.voucher.VoucherFragment;

public class RulesFragment extends BaseDialog implements RulesMvpView {

    RulesMvpPresenter<RulesMvpView> mPresenter;

    @BindView(R.id.text_deception)
    TextView mTextDeception;

    @BindView(R.id.layout_accept)
    LinearLayout mLayoutAccept;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rules, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new RulesPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {

        String s = getResources().getString(R.string.text_deception);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextDeception.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextDeception.setText(Html.fromHtml(s));
        }
        mTextDeception.setLineSpacing(20f, 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        }

        mLayoutAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoucherFragment fragment = new VoucherFragment();
                fragment.show(getFragmentManager(), "VOUCHER_FRAGMENT");
            }
        });
    }
}
