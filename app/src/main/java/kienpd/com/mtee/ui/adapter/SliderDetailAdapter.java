package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kienpd.com.mtee.R;

public class SliderDetailAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<String> mUrlImages;
    private SliderDetailAdapterCallback mSliderDetailAdapterCallback;

    public SliderDetailAdapter(Context context, ArrayList<String> urlImages, SliderDetailAdapterCallback sliderDetailAdapterCallback) {
        this.mContext = context;
        this.mUrlImages = urlImages;
        this.mSliderDetailAdapterCallback = sliderDetailAdapterCallback;
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

        int drawableResourceId = mContext.getResources().getIdentifier("bg_test", "drawable", mContext.getPackageName());
        ImageView imageVoucher = view.findViewById(R.id.image_voucher);
        Glide.with(mContext)
                .load(drawableResourceId)
                .into(imageVoucher);

        imageVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSliderDetailAdapterCallback.onClickSliderImageListener(position);
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