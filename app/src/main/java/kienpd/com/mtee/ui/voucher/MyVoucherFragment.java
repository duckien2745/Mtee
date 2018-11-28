package kienpd.com.mtee.ui.voucher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.adapter.MyVoucherAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;

public class MyVoucherFragment extends BaseFragment {

    @BindView(R.id.pager_my_voucher)
    ViewPager mViewPager;

    @BindView(R.id.tab_my_voucher)
    TabLayout mTabMyVoucher;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_voucher, container, false);
        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }

    @Override
    protected void setUp(View view) {
        MyVoucherAdapter adapter = new MyVoucherAdapter(getBaseActivity(), getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabMyVoucher.setupWithViewPager(mViewPager);
    }


}
