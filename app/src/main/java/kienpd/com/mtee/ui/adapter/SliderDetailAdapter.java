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

    public SliderDetailAdapter(Context context, ArrayList<String> urlImages) {
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
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider_detail, null);

        int drawableResourceId = mContext.getResources().getIdentifier("bg_test", "drawable", mContext.getPackageName());
        ImageView imageVoucher = view.findViewById(R.id.image_voucher);
        Glide.with(mContext)
                .load(drawableResourceId)
                .into(imageVoucher);

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
}