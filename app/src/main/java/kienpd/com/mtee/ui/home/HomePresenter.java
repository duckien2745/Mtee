package kienpd.com.mtee.ui.home;

import java.util.ArrayList;
import java.util.List;

import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BasePresenter;
import kienpd.com.mtee.utils.Const;

public class HomePresenter<V extends HomeMvpView> extends BasePresenter<V>
        implements HomeMvpPresenter<V> {

    @Override
    public void showDetailDialog() {
        getMvpView().showDetailDialog();
    }

    @Override
    public void loadHighLightData(int categoryId) {
        //Get data
        List<Voucher> voucherList = new ArrayList<>();

        //todo
        //check  categoryId = Const.Category.CATEGORY_ALL ?

        getMvpView().updateRepoHighLight(voucherList);
    }

    @Override
    public void loadCollectionData(int categoryId) {
        //Get data
        List<Collection> collectionList = new ArrayList<>();

        //todo
        //check  categoryId = Const.Category.CATEGORY_ALL ?

        getMvpView().updateRepoCollection(collectionList);
    }

    @Override
    public void loadNewestData(int categoryId) {
        //Get data
        ArrayList<Voucher> voucherList = new ArrayList<>();

        //todo
        //check  categoryId = Const.Category.CATEGORY_ALL ?

        getMvpView().updateRepoNewest(voucherList);
    }


}
