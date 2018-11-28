package kienpd.com.mtee.ui.home.detail;

import java.util.List;

import kienpd.com.mtee.data.model.RatingResponse;
import kienpd.com.mtee.data.model.RatingTotal;
import kienpd.com.mtee.ui.base.MvpView;

public interface VoucherMvpView extends MvpView {

    void displayDetailView(String store, int countLike, List<String> urlImageVouchers, String title, String address, List<String> urlImagePrices, String description);

    void displayMyRating(int star);

    void changeMyRatting(boolean isChange);

    void displayInfoUser(String name, String avatarUrl);

    void updateLike(Boolean isLike);

    void updateSave(Boolean isSave);

    void showTextDescription(Boolean isShow);

    void displayTotalRatting(RatingResponse totalRatting);
}
