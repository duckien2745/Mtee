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

        int id;

        public ApiRequestVoucherById(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class ApiRequestStatus{
        private Integer user_id;
        private Integer voucher_id;

        public ApiRequestStatus(Integer user_id, Integer voucher_id) {
            this.user_id = user_id;
            this.voucher_id = voucher_id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getVoucher_id() {
            return voucher_id;
        }

        public void setVoucher_id(Integer voucher_id) {
            this.voucher_id = voucher_id;
        }
    }
}
