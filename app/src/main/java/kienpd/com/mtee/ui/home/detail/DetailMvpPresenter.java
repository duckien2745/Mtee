package kienpd.com.mtee.ui.home.detail;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface DetailMvpPresenter<V extends DetailMvpView> extends MvpPresenter<V> {

    void loadDetailData(int detailId);

    void likeDetail(int userId, int detailId);

    void saveDetail(int userId, int detailId);

    void rattingDetail(int userId, int detailId, int rating);

    void shareDetail(String title, String content);

    void direct(String location);

    void getStatusLikeDetail(int userId, int detailId);

    void getStatusDaveDetail(int userId, int detailId);

    void showTextMore();
}
