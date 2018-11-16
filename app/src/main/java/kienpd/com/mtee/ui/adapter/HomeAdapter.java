package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.holder.CategoryHolder;
import kienpd.com.mtee.ui.adapter.holder.CollectionHolder;
import kienpd.com.mtee.ui.adapter.holder.HighLightHolder;
import kienpd.com.mtee.ui.adapter.holder.HomeViewHolder;
import kienpd.com.mtee.ui.adapter.holder.LoadingHolder;
import kienpd.com.mtee.ui.adapter.holder.NewestHolder;
import kienpd.com.mtee.ui.adapter.holder.OrderNewestHolder;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.NetworkUtils;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> implements CategoryHolder.CategoryHolderCallback, CollectionHolder.CollectionHolderCallback, HighLightHolder.HighLightHolderCallback
        , OrderNewestHolder.OrderNewestHolderCallback, LoadingHolder.LoadingListener {

    private final int TYPE_CATEGORY = 0;
    private final int TYPE_HIGHLIGHT = 1;
    private final int TYPE_COLLECTION = 2;
    private final int TYPE_HEADER_NEWEST = 3;
    public static final int TYPE_ITEM_NEWEST = 4;
    private final int TYPE_ITEM_LOAD_MORE = 5;

    private Context mContext;
    private HomeAdapterCallBack mCallBack;
    private LoadingHolder mLoadingHolder;

    private ArrayList<Voucher> mListHighLight;
    private List<Collection> mListCollection;
    private List<Voucher> mListNewest;

    public HomeAdapter(Context context, ArrayList<Voucher> listHighLight, List<Voucher> listNewest, List<Collection> listCollection, HomeAdapterCallBack callBack) {
        mContext = context;
        mCallBack = callBack;

        mListHighLight = listHighLight;
        mListCollection = listCollection;
        mListNewest = listNewest;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CATEGORY:
                return new CategoryHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false), this);
            case TYPE_HIGHLIGHT:
                return new HighLightHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_highlight, parent, false), this);
            case TYPE_COLLECTION:
                return new CollectionHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_collection, parent, false), this);
            case TYPE_HEADER_NEWEST:
                return new NewestHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_newest, parent, false));
            case TYPE_ITEM_NEWEST:
                return new OrderNewestHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newest, parent, false), this);
            case TYPE_ITEM_LOAD_MORE:
                mLoadingHolder = new LoadingHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false), this);
                return mLoadingHolder;
            default:
                return new OrderNewestHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newest, parent, false), this);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HIGHLIGHT:
                holder.onBind(mListHighLight);
                break;
            case TYPE_COLLECTION:
                holder.onBind(mListCollection);
                break;
            case TYPE_ITEM_LOAD_MORE:
                if (!NetworkUtils.isNetworkConnected(mContext)) {
                    mLoadingHolder.setLoading(false);
                } else {
                    mLoadingHolder.setLoading(true);
                    mCallBack.onClickLoadMore();
                }
            default:
                holder.onBind(position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_ITEM_LOAD_MORE;
        }

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


    @Override
    public void onClickCategoryHolderListener(int category) {
        mCallBack.onClickCategoryListener(category);
    }

    @Override
    public void onClickOrderNewestHolderListener(int position) {
        if (position > 3) {
            mCallBack.onClickListener(Const.Type.TYPE_NEWEST, position - 4);
        }
    }

    @Override
    public void onClickCollectionHolderListener(int position) {
        mCallBack.onClickListener(Const.Type.TYPE_COLLECTION, position);
    }

    @Override
    public void onClickHighLightHolderListener(int position) {
        mCallBack.onClickListener(Const.Type.TYPE_HIGHLIGHT, position);
    }

    @Override
    public void onLoadMoreClick(LoadingHolder loadingViewHolder) {
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            loadingViewHolder.setLoading(false);
        } else {
            loadingViewHolder.setLoading(true);
            mCallBack.onClickLoadMore();
        }
    }

    public interface HomeAdapterCallBack {
        void onClickListener(int type, int position);

        void onClickCategoryListener(int category);

        void onClickLoadMore();
    }

    public void addItemHighLight(List<Voucher> repoList) {
        mListHighLight.addAll(repoList);
//        notifyDataSetChanged();
    }

    public void addItemCollection(List<Collection> repoList) {
        mListCollection.addAll(repoList);
//        notifyDataSetChanged();
    }

    public void addItemNewest(List<Voucher> repoList) {
        mListNewest.addAll(repoList);
//        notifyDataSetChanged();
    }
}
