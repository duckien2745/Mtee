package kienpd.com.mtee.ui.main;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.adapter.MainAdapter;
import kienpd.com.mtee.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainMvpView {

    @BindView(R.id.image_search)
    ImageView mImageSearch;

    @BindView(R.id.pager_main)
    ViewPager mViewPager;

    @BindView(R.id.tab_bottom)
    TabLayout mTabBottom;

    private MainMvpPresenter<MainMvpView> mPresenter;

    private TypedArray mIconArray;
    private String[] mLabelArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUnBinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {
        mIconArray = getResources().obtainTypedArray(R.array.iconArray);
        mLabelArray = getResources().getStringArray(R.array.labelArray);

        mPresenter = new MainPresenter<>();

        mPresenter.onAttach(this);
        mPresenter.onFacebookLoginClick();

        mImageSearch.setColorFilter(Color.parseColor("#ff5f6368"));

        setUpViewPager();
        setupTabIcons();
    }


    @Override
    public void openMainActivity() {

    }

    private void setUpViewPager() {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mTabBottom.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout layout = (LinearLayout) tab.getCustomView();
                if (layout != null) {
                    ImageView imageIcon = layout.findViewById(R.id.image_icon_pager);
                    TextView textTitle = layout.findViewById(R.id.text_title_pager);

                    imageIcon.setColorFilter(getResources().getColor(R.color.color_item_select));
                    textTitle.setTextColor(getResources().getColor(R.color.color_item_select));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout layout = (LinearLayout) tab.getCustomView();
                if (layout != null) {
                    ImageView imageIcon = layout.findViewById(R.id.image_icon_pager);
                    TextView textTitle = layout.findViewById(R.id.text_title_pager);

                    imageIcon.setColorFilter(getResources().getColor(R.color.color_item_un_select));
                    textTitle.setTextColor(getResources().getColor(R.color.color_item_un_select));
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mTabBottom.setupWithViewPager(mViewPager);

    }

    private void setupTabIcons() {
        for (int i = 0; i < mTabBottom.getTabCount(); i++) {
            LinearLayout tab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_tab, null);

            ImageView imageIcon = tab.findViewById(R.id.image_icon_pager);
            TextView textTitle = tab.findViewById(R.id.text_title_pager);

            imageIcon.setImageResource(mIconArray.getResourceId(i, -1));
            textTitle.setText(mLabelArray[i]);

            if (i == 0) {
                imageIcon.setColorFilter(getResources().getColor(R.color.color_item_select));
                textTitle.setTextColor(getResources().getColor(R.color.color_item_select));
            } else {
                imageIcon.setColorFilter(getResources().getColor(R.color.color_item_un_select));
                textTitle.setTextColor(getResources().getColor(R.color.color_item_un_select));
            }

            mTabBottom.getTabAt(i).setCustomView(tab);
        }
    }

}
