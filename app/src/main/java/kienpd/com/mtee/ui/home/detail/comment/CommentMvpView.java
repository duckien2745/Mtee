package kienpd.com.mtee.ui.home.detail.comment;

import java.util.List;

import kienpd.com.mtee.data.model.Rating;
import kienpd.com.mtee.ui.base.MvpView;

public interface CommentMvpView extends MvpView {

    void displayComment(List<Rating> ratingList,Boolean isClearData);
}
