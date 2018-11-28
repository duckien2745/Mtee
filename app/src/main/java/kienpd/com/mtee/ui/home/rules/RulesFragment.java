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
import kienpd.com.mtee.ui.home.code.CodeFragment;

public class RulesFragment extends BaseDialog implements RulesMvpView {

    RulesMvpPresenter<RulesMvpView> mPresenter;
    public static final String TAG = "RULES_FRAGMENT";
    public static final String EXTRAS_DETAIL_ID = "extras_detail_id";
    public static final String EXTRAS_DETAIL_DESCRIPTION = "extras_detail_description";

    @BindView(R.id.text_deception)
    TextView mTextDeception;

    @BindView(R.id.layout_accept)
    LinearLayout mLayoutAccept;

    private Integer mDetailId;
    private String mDescription;

    public static RulesFragment newInstance(String description, int detailId) {

        RulesFragment f = new RulesFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRAS_DETAIL_ID, detailId);
        args.putString(EXTRAS_DETAIL_DESCRIPTION, description);
        f.setArguments(args);

        return f;
    }

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

        mDetailId = getArguments().getInt(EXTRAS_DETAIL_ID);
        mDescription = getArguments().getString(EXTRAS_DETAIL_DESCRIPTION);

        //String s = mDescription;


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
                CodeFragment fragment = CodeFragment.newInstance(mDetailId);
                fragment.show(getFragmentManager(), CodeFragment.TAG);
            }
        });
    }
}
