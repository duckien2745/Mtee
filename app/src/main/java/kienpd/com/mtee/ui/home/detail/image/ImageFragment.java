package kienpd.com.mtee.ui.home.detail.image;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.adapter.SliderDetailAdapter;
import kienpd.com.mtee.ui.base.BaseDialog;

public class ImageFragment extends BaseDialog implements ImageMvpView {

    private ImageMvpPresenter<ImageMvpView> mPresenter;
    public static final String TAG = "IMAGE_FRAGMENT";
    public static final String EXTRAS_IMAGE_LIST = "extras_image_list";
    public static final String EXTRAS_POSITION = "extras_position";

    @BindView(R.id.pager_image)
    ViewPager mViewPagerImage;

    @BindView(R.id.image_back)
    ImageView mImageBack;

    @BindView(R.id.text_pager)
    TextView mTextPager;

    private SliderDetailAdapter mSliderDetailAdapter;


    public static ImageFragment newInstance(String jsonImage, int pos) {
        ImageFragment f = new ImageFragment();

        Bundle args = new Bundle();
        args.putString(EXTRAS_IMAGE_LIST, jsonImage);
        args.putInt(EXTRAS_POSITION, pos);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new ImagePresenter<>();
        mPresenter.onAttach(this);

        return view;
    }


    @Override
    protected void setUp(View view) {
        String jsonImage = getArguments().getString(EXTRAS_IMAGE_LIST);
        final int pos = getArguments().getInt(EXTRAS_POSITION);

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        final List<String> mImageVouchers = gson.fromJson(jsonImage, listType);
        if (mImageVouchers != null && mImageVouchers.size() > 0) {
            final int size = mImageVouchers.size();
            mSliderDetailAdapter = new SliderDetailAdapter(getBaseActivity(), mImageVouchers);
            mViewPagerImage.setAdapter(mSliderDetailAdapter);
            mViewPagerImage.setCurrentItem(pos);

            mViewPagerImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mTextPager.setText((position + 1) + "/" + size);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            mTextPager.setText((pos + 1) + "/" + size);
        }

        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog(TAG);
            }
        });

    }


}
