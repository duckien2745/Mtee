package kienpd.com.mtee.ui.home.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.adapter.SliderDetailAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;

public class DetailFragment extends BaseFragment implements DetailMvpView {

    DetailMvpPresenter<DetailMvpView> mPresenter;

    @BindView(R.id.pager_detail)
    ViewPager mViewPagerDetail;
    @BindView(R.id.indicator)
    TabLayout mTabIndicator;

    private ArrayList<String> mUrl;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new DetailPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        mUrl = new ArrayList<>();
        mUrl.add("192.168.16.103:8080/picture/bg_test.png");
        mUrl.add("192.168.16.103:8080/picture/diagram_mtee.png");
        mUrl.add("192.168.16.103:8080/picture/mtee_diagram.png");
        mUrl.add("192.168.16.103:8080/picture/bg_test.png");
        mUrl.add("192.168.16.103:8080/picture/diagram_mtee.png");
        mUrl.add("192.168.16.103:8080/picture/mtee_diagram.png");
        mUrl.add("192.168.16.103:8080/picture/bg_test.png");
        mUrl.add("192.168.16.103:8080/picture/diagram_mtee.png");
        mUrl.add("192.168.16.103:8080/picture/mtee_diagram.png");


        mViewPagerDetail.setAdapter(new SliderDetailAdapter(mActivity, mUrl));
        mTabIndicator.setupWithViewPager(mViewPagerDetail, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 3000, 6000);
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mViewPagerDetail.getCurrentItem() < mUrl.size() - 1) {
                        mViewPagerDetail.setCurrentItem(mViewPagerDetail.getCurrentItem() + 1);
                    } else {
                        mViewPagerDetail.setCurrentItem(0);
                    }
                }
            });
        }
    }

}
