package kienpd.com.mtee.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.follow.FollowFragment;
import kienpd.com.mtee.ui.home.HomeFragment;
import kienpd.com.mtee.ui.order.OrderFragment;
import kienpd.com.mtee.ui.user.UserFragment;

public class MainAdapter extends FragmentStatePagerAdapter {

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new OrderFragment();
                break;
            case 2:
                fragment = new FollowFragment();
                break;
            case 3:
                fragment = new UserFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
