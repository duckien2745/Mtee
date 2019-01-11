package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.voucher.save.VoucherSaveFragment;
import kienpd.com.mtee.ui.voucher.taken.VoucherTakenFragment;

public class MyVoucherAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public MyVoucherAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        Log.d("dnedede","kakaka");

        BaseFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new VoucherTakenFragment();
                break;
            case 1:
                fragment = new VoucherSaveFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position

        switch (position) {
            case 0:
                return mContext.getString(R.string.code_taken);
            case 1:
                return mContext.getString(R.string.code_save);
            default:
                return null;
        }
    }
}
