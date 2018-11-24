package kienpd.com.mtee.data;


public class ApiRequest {

    public static class ApiRequestVoucher {

        public ApiRequestVoucher(int from, int size) {
            this.from = from;
            this.size = size;
        }

        private int from;
        private int size;
    }
}
