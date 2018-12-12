package kienpd.com.mtee.ui.home.detail.comment;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface CommentMvpPresenter<V extends CommentMvpView> extends MvpPresenter<V> {

    void loadComment(int voucherId,Boolean isClearData);
}

