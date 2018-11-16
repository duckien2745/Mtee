package kienpd.com.mtee.ui.home.detail;

import kienpd.com.mtee.ui.base.BasePresenter;

public class DetailPresenter<V extends DetailMvpView> extends BasePresenter<V>
        implements DetailMvpPresenter<V> {

    private Boolean mIsShow = false;


    @Override
    public void loadDetailData(int detailId) {
        //todo
    }

    @Override
    public void likeDetail(int userId, int detailId) {
        //todo
    }

    @Override
    public void saveDetail(int userId, int detailId) {
        //todo
    }

    @Override
    public void rattingDetail(int userId, int detailId, int rating) {
        //todo
    }

    @Override
    public void shareDetail(String title, String content) {
        //todo
    }

    @Override
    public void direct(String location) {
        //todo
    }

    @Override
    public void getStatusLikeDetail(int userId, int detailId) {
        //todo
    }

    @Override
    public void getStatusDaveDetail(int userId, int detailId) {
        //todo
    }

    @Override
    public void showTextMore() {
        mIsShow = !mIsShow;
        getMvpView().showTextDescription(mIsShow);
    }
}
