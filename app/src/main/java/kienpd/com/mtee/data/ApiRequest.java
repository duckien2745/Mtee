package kienpd.com.mtee.data;


public class ApiRequest {

    public static class ApiRequestVoucher {

        public ApiRequestVoucher(int from, int size) {
            this.from = from;
            this.size = size;
        }

        public ApiRequestVoucher(int from, int size, int categoryId) {
            this.from = from;
            this.size = size;
            this.categoryId = categoryId;
        }

        private int from;
        private int size;
        private int categoryId;
    }

    public static class ApiRequestCollection {

        public ApiRequestCollection(int from, int size) {
            this.from = from;
            this.size = size;
        }

        public ApiRequestCollection(int from, int size, int categoryId) {
            this.from = from;
            this.size = size;
            this.categoryId = categoryId;
        }

        private int from;
        private int size;
        private int categoryId;
    }

    public static class ApiRequestVoucherById {

        int voucherId;

        public ApiRequestVoucherById(int id) {
            this.voucherId = id;
        }
    }

    public static class ApiRequestStatus {
        private Integer user_id;
        private Integer voucher_id;

        public ApiRequestStatus(Integer user_id, Integer voucher_id) {
            this.user_id = user_id;
            this.voucher_id = voucher_id;
        }

    }


    public static class ApiRequestUser {

        private Integer userId;

        public ApiRequestUser(Integer userId) {
            this.userId = userId;
        }

    }

    public static class ApiRequestRating {

        private int userId;
        private int voucherId;
        private int star;

        public ApiRequestRating(int userId, int voucherId, int star) {
            this.userId = userId;
            this.voucherId = voucherId;
            this.star = star;
        }

    }

    public static class ApiRequestTotalRatting {

        private Integer voucherId;

        public ApiRequestTotalRatting(Integer voucherId) {
            this.voucherId = voucherId;
        }
    }

    public static class ApiRequestGetCode {

        public int user_id;
        public int voucher_id;

        public ApiRequestGetCode(int user_id, int voucher_id) {
            this.user_id = user_id;
            this.voucher_id = voucher_id;
        }
    }

    public static class ApiRequestDetailCollection {

        private Integer categoryId;

        private Integer collectionId;


        public ApiRequestDetailCollection(Integer categoryId, Integer collectionId) {
            this.categoryId = categoryId;
            this.collectionId = collectionId;
        }
    }

    public static class ApiRequestVoucherTakenByUserId {

        private Integer userId;

        public ApiRequestVoucherTakenByUserId(Integer userId) {
            this.userId = userId;
        }
    }

    public static class ApiRequestVoucherSaveByUserId {

        Integer user_id;

        public ApiRequestVoucherSaveByUserId(Integer user_id) {
            this.user_id = user_id;
        }
    }

    public static class ApiRequestStoreFollow {

        Integer user_id;

        public ApiRequestStoreFollow(Integer user_id) {
            this.user_id = user_id;
        }
    }

    public static class ApiRequestStoreById {

        Integer storeId;

        public ApiRequestStoreById(Integer storeId) {
            this.storeId = storeId;
        }
    }

    public static class ApiRequestStatusUserFollow {

        int user_id;
        int store_id;

        public ApiRequestStatusUserFollow(Integer storeId, Integer userId) {
            this.user_id = userId;
            this.store_id = storeId;
        }
    }
}
