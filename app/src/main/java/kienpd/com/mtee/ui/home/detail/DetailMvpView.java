package kienpd.com.mtee.ui.home.detail;

import java.util.List;

import kienpd.com.mtee.data.model.RatingTotal;
import kienpd.com.mtee.ui.base.MvpView;

public interface DetailMvpView extends MvpView {

    void displayDetailView(String store, int countLike, List<String> urlImageVouchers, String title, String address, List<String> urlImagePrices, String description, float star, int countRating, RatingTotal ratingTotal);

    void displayMyRating(float star);

    void updateLike(Boolean isLike);

    void updateSave(Boolean isSave);

    void showTextDescription(Boolean isShow);
}
