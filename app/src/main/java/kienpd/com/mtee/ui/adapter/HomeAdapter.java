package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.model.Category;
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.holder.CategoryHolder;
import kienpd.com.mtee.ui.adapter.holder.CollectionHolder;
import kienpd.com.mtee.ui.adapter.holder.HighLightHolder;
import kienpd.com.mtee.ui.adapter.holder.HomeViewHolder;
import kienpd.com.mtee.ui.adapter.holder.LoadingHolder;
import kienpd.com.mtee.ui.adapter.holder.NewestHolder;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.NetworkUtils;
import kienpd.com.mtee.utils.TextUtil;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> implements CategoryHolder.CategoryHolderCallback, CollectionHolder.CollectionHolderCallback, HighLightHolder.HighLightHolderCallback, LoadingHolder.LoadingListener {

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
    private List<Category> mListCategory;

    public HomeAdapter(Context context, ArrayList<Voucher> listHighLight, List<Voucher> listNewest, List<Collection> listCollection, List<Category> listCategory, HomeAdapterCallBack callBack) {
        mContext = context;
        mCallBack = callBack;

        mListHighLight = listHighLight;
        mListCollection = listCollection;
        mListNewest = listNewest;
        mListCategory = listCategory;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CATEGORY:
                return new CategoryHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false), this,mListCategory);
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
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newest, parent, false));
            case TYPE_ITEM_LOAD_MORE:
                mLoadingHolder = new LoadingHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false), this);
                return mLoadingHolder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_CATEGORY:
                holder.onBind(position);
                break;
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
            case TYPE_ITEM_NEWEST:
                holder.onBind(position);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == getItemCount() - 1) {
//            return TYPE_ITEM_LOAD_MORE;
//        }

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
        return mListNewest.size() + 4;
    }


    @Override
    public void onClickCategoryHolderListener(int category) {
        mCallBack.onClickCategoryListener(category);
    }

    @Override
    public void onClickCollectionHolderListener(int id) {
        mCallBack.onClickListener(Const.Type.TYPE_COLLECTION, id);
    }

    @Override
    public void onClickHighLightHolderListener(int id) {
        mCallBack.onClickListener(Const.Type.TYPE_HIGHLIGHT, id);
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
        void onClickListener(int type, int id);

        void onClickCategoryListener(int category);

        void onClickLoadMore();
    }

    public void addItemHighLight(List<Voucher> repoList, Boolean isClearData) {
        if (mListHighLight != null) {
            if (isClearData) {
                mListHighLight.clear();
            }
            mListHighLight.addAll(repoList);
            notifyDataSetChanged();
        }
    }

    public void addItemCollection(List<Collection> repoList, Boolean isClearData) {
        if (mListCollection != null) {
            if (isClearData) {
                mListCollection.clear();
            }
            mListCollection.addAll(repoList);
            notifyDataSetChanged();
        }
    }

    public void addItemNewest(List<Voucher> repoList, Boolean isClearData) {
        if (mListNewest != null) {
            if (isClearData) {
                mListNewest.clear();
            }
            mListNewest.addAll(repoList);
        }
    }

    public void addItemCategory(List<Category> repoList, Boolean isClearData) {
        if (mListCategory != null) {
            if (isClearData) {
                mListCategory.clear();
            }
            mListCategory.addAll(repoList);
        }
    }

    class OrderNewestHolder extends HomeViewHolder {

        @BindView(R.id.text_store)
        TextView mTextStore;

        @BindView(R.id.text_title)
        TextView mTextTitle;

        @BindView(R.id.text_discount)
        TextView mTextDiscount;

        @BindView(R.id.text_count_like)
        TextView mTextCountLike;

        @BindView(R.id.image_newest_product)
        ImageView mImageProduct;

        @BindView(R.id.layout_order)
        RelativeLayout mLayoutOrder;

        private Context mContext;

        OrderNewestHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);
            if (position > 3) {
                Voucher voucher = mListNewest.get(position - 4);
                if (voucher != null) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

                    Glide.with(mContext)
                            .load(API.HOST_DEV + "thumb/" + voucher.getCoverPicture())
                            .apply(requestOptions)
                            .into(mImageProduct);

                    mTextStore.setText(voucher.getStore().getName());
                    mTextTitle.setText(voucher.getTitle());
                    if (voucher.getPercentDiscount() != 0) {
                        mTextDiscount.setVisibility(View.VISIBLE);
                        mTextDiscount.setText(String.valueOf(voucher.getPercentDiscount()) + TextUtil.CHARACTER_PERCENT);
                    } else {
                        mTextDiscount.setVisibility(View.GONE);
                    }
                    mTextCountLike.setText(String.valueOf(voucher.getLikeCount()));
                }

                mLayoutOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position > 3) {
                            mCallBack.onClickListener(Const.Type.TYPE_NEWEST, mListNewest.get(position - 4).getId());
                        }
                    }
                });
            }
        }
    }

}
