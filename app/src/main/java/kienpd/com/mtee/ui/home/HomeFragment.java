package kienpd.com.mtee.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.adapter.HomeAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;

public class HomeFragment extends BaseFragment implements HomeMvpView, HomeAdapter.HomeAdapterCallBack {

    HomeMvpPresenter<HomeMvpView> mPresenter;

    @BindView(R.id.recycler_home)
    RecyclerView mRecyclerViewHome;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new HomePresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        GridLayoutManager manager = new GridLayoutManager(mActivity, 2);
        final HomeAdapter adapter = new HomeAdapter(mActivity, this);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == HomeAdapter.TYPE_ITEM_NEWEST ? 1 : 2;
            }
        });

        mRecyclerViewHome.setLayoutManager(manager);
        mRecyclerViewHome.setAdapter(adapter);

    }

    @Override
    public void onClickListener(int type, int position) {
        switch (type) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }

    }

    @Override
    public void onClickCategoryListener(int category) {

    }
}
