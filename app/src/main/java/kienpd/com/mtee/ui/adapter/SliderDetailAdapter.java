package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kienpd.com.mtee.R;
import kienpd.com.mtee.data.API;

public class SliderDetailAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mUrlImages;
    private SliderDetailAdapterCallback mSliderDetailAdapterCallback;

    public SliderDetailAdapter(Context context, List<String> urlImages, SliderDetailAdapterCallback sliderDetailAdapterCallback) {
        this.mContext = context;
        this.mUrlImages = urlImages;
        this.mSliderDetailAdapterCallback = sliderDetailAdapterCallback;
    }

    public SliderDetailAdapter(Context context, List<String> urlImages) {
        this.mContext = context;
        this.mUrlImages = urlImages;
    }


    @Override
    public int getCount() {
        return mUrlImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider_detail, null);

        ImageView imageVoucher = view.findViewById(R.id.image_voucher);
        Glide.with(mContext)
                .load(API.HOST_DEV + mUrlImages.get(position))
                .into(imageVoucher);

        imageVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSliderDetailAdapterCallback != null) {
                    mSliderDetailAdapterCallback.onClickSliderImageListener(position);
                }
            }
        });

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }

    public interface SliderDetailAdapterCallback {
        void onClickSliderImageListener(int position);
    }
}