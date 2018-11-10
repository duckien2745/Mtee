package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.modelTest;
import kienpd.com.mtee.ui.adapter.holder.CategoryHolder;
import kienpd.com.mtee.ui.adapter.holder.CollectionHolder;
import kienpd.com.mtee.ui.adapter.holder.HighLightHolder;
import kienpd.com.mtee.ui.adapter.holder.NewestHolder;
import kienpd.com.mtee.ui.adapter.holder.OrderNewestHolder;
import kienpd.com.mtee.ui.base.BaseViewHolder;

public class HomeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int TYPE_CATEGORY = 0;
    private final int TYPE_HIGHLIGHT = 1;
    private final int TYPE_COLLECTION = 2;
    private final int TYPE_HEADER_NEWEST = 3;
    public static  final int TYPE_ITEM_NEWEST = 4;


    private Context mContext;

    public HomeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CATEGORY:
                return new CategoryHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false));
            case TYPE_HIGHLIGHT:
                return new HighLightHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_highlight, parent, false));
            case TYPE_COLLECTION:
                return new CollectionHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_collection, parent, false));
            case TYPE_HEADER_NEWEST:
                return new NewestHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_newest, parent, false));
            case TYPE_ITEM_NEWEST:
                return new OrderNewestHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newest, parent, false));
            default:
                return new OrderNewestHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newest, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HIGHLIGHT:
            case TYPE_COLLECTION:
                holder.onBind(new ArrayList<modelTest>());
                break;
            default:
                holder.onBind(position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_CATEGORY;
            case 1:
                return TYPE_HIGHLIGHT;
            case 2:
                return TYPE_COLLECTION;
            case 3:
                return TYPE_HEADER_NEWEST;
            default:
                return TYPE_ITEM_NEWEST;

        }
    }


    @Override
    public int getItemCount() {
        return 30;
    }
}
