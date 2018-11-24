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
        public static final String PATH = "collections/get_list_all";
    }

    public static class GetListCollectionById {
        public static final String TAG = "Get List Collection By Id";
        public static final String PATH = "collections/get_collection_by_category_id";
    }

    public static class GetVoucherByCollectionId {
        public static final String TAG = "Get Voucher By Collection Id";
        public static final String PATH = "collections/get_voucher_by_collection_id";
    }

    public static class GetVoucherById {
        public static final String TAG = "Get Voucher By Id";
        public static final String PATH = "/voucher/id";
    }
}
