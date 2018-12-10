package kienpd.com.mtee.ui.home.detail;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface VoucherMvpPresenter<V extends VoucherMvpView> extends MvpPresenter<V> {

    void loadInfoUser(int userId);

    void loadDetailData(int voucherId);

    void likeDetail(int userId, int detailId);

    void saveDetail(int userId, int detailId);

    void rattingDetail(int userId, int detailId, float rating);

    void shareDetail(String title, String content);

    void getStatusLikeSaveRateDetail(int userId, int detailId);

    void showTextMore();

    void checkGetCodeInDay(int userId,int voucherId);

    void updateStatusUserFollow(int storeId, int userId);

    void getStatusUserFollow(int storeId, int userId);

    void signInWithGoogle(String type,String idToken,int action);

}
