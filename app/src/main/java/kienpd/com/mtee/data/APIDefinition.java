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
        public static final String TAG = "Update Save";
        public static final String PATH = "storage_voucher/set_storage_voucher";
    }

    public static class GetInfoUser {
        public static final String TAG = "Get Information";
        public static final String PATH = "users/get_info_by_id";
    }

    public static class RateVoucher {
        public static final String TAG = "Rate Voucher";
        public static final String PATH = "rating/set_rating";
    }

    public static class GetTotalRatting {
        public static final String TAG = "Total Ratting";
        public static final String PATH = "rating/get_ratting_by_voucher_id";
    }

    public static class GetCode {
        public static final String TAG = "Get Code";
        public static final String PATH = "code/get_code";
    }

    public static class GetDetailCollection {
        public static final String TAG = "Get Voucher in Collection";
        public static final String PATH = "collections/get_voucher_by_collection_id";
    }

    public static class GetVoucherTakenByUserId {
        public static final String TAG = "Get Voucher Taken By User ID";
        public static final String PATH = "code/get_code_by_userId";
    }

    public static class GetVoucherSaveByUserId {
        public static final String TAG = "Get Voucher Save By User ID";
        public static final String PATH = "storage_voucher/get_list_voucher_follow";
    }

    public static class GetStoreFollow {
        public static final String TAG = "Get Store Follow";
        public static final String PATH = "track_store/get_store_follow_by_user_id";
    }

    public static class GetStoreById {
        public static final String TAG = "Get Store By Id";
        public static final String PATH = "store/get_store_by_id";
    }

    public static class GetVoucherByStoreId {
        public static final String TAG = "Get Voucher By Store Id";
        public static final String PATH = "voucher/get_list_voucher_by_store_id";
    }

    public static class StatusUserFollow {
        public static final String TAG = "Status User Follow";
        public static final String PATH = "track_store/set_track_store";
    }

    public static class GetStatusUserFollow {
        public static final String TAG = "Get Status User Follow";
        public static final String PATH = "track_store/get_status_follow_store";
    }
}
