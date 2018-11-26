package kienpd.com.mtee.data;

public class APIDefinition {

    public static class GetListVoucherNewest {
        public static final String TAG = "Get List Newest Voucher";
        public static final String PATH = "voucher/get_list_by_time";
    }

    public static class GetListVoucherHighLight {
        public static final String TAG = "Get List HighLight Voucher";
        public static final String PATH = "voucher/get_list_high_lights";
    }

    public static class GetListCollection {
        public static final String TAG = "Get List Collection";
        public static final String PATH = "collections/get_collection";
    }


    public static class GetVoucherByCollectionId {
        public static final String TAG = "Get Voucher By Collection Id";
        public static final String PATH = "collections/get_voucher_by_collection_id";
    }

    public static class GetVoucherById {
        public static final String TAG = "Get Voucher By Id";
        public static final String PATH = "/voucher/id";
    }

    public static class GetStatusLikeSaveRating {
        public static final String TAG = "Get Status";
        public static final String PATH = "like_rating_status/get_status_by_id";
    }

    public static class UpdateLike {
        public static final String TAG = "Update Like";
        public static final String PATH = "like/update_like";
    }

    public static class UpdateSave {
        public static final String TAG = "Update Like";
        public static final String PATH = "storage_voucher/set_storage_voucher";
    }
}
