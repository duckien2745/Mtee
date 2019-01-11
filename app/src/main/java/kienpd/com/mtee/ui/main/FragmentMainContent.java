package kienpd.com.mtee.ui.main;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.MessageEvent;
import kienpd.com.mtee.ui.adapter.MainAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.search.SearchFragment;
import kienpd.com.mtee.ui.user.UserFragment;

public class FragmentMainContent extends BaseFragment {

    @BindView(R.id.image_search)
    ImageView mImageSearch;

    @BindView(R.id.pager_main)
    ViewPager mViewPager;

    @BindView(R.id.edit_search_main)
    EditText mEditSearchMain;

    @BindView(R.id.tab_bottom)
    TabLayout mTabBottom;

    private TypedArray mIconArray;
    private String[] mLabelArray;

    private MainAdapter mAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_content, container, false);

        setUnBinder(ButterKnife.bind(this, view));


        return view;
    }

    @Override
    protected void setUp(View view) {
        mIconArray = getResources().obtainTypedArray(R.array.iconArray);
        mLabelArray = getResources().getStringArray(R.array.labelArray);
        mImageSearch.setColorFilter(Color.parseColor("#ff5f6368"));

        setUpViewPager();
        setupTabIcons();
    }

    private void setUpViewPager() {
        mAdapter = new MainAdapter(getBaseActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

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

        mEditSearchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment fragment = SearchFragment.newInstance();
                fragment.show(getFragmentManager(), SearchFragment.TAG);
            }
        });

    }

    private void setupTabIcons() {
        for (int i = 0; i < mTabBottom.getTabCount(); i++) {
            LinearLayout tab = (LinearLayout) LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_tab, null);

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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getStatus() == 1) {
            if (mAdapter != null) {
//                mAdapter.notifyDataSetChanged();
//                mViewPager.getIte
                if (mAdapter.getItem(3) instanceof UserFragment) {
                    Log.d("byddye", "aaaaa");
                    ((UserFragment) mAdapter.getItem(3)).updateView(getBaseActivity());
                }
            }
        }
    }

}
