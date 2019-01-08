package kienpd.com.mtee.utils;

public class Const {

    public static class Category {
        public static final Integer CATEGORY_ALL = 0;
        public static final Integer CATEGORY_FOOD = 57281321;
        public static final Integer CATEGORY_BEAUTY = 57281323;
        public static final Integer CATEGORY_FASHION = 57281322;
    }

    public static class Type {
        public static final Integer TYPE_CATEGORY = 0;
        public static final Integer TYPE_HIGHLIGHT = 1;
        public static final Integer TYPE_COLLECTION = 2;
        public static final Integer TYPE_NEWEST = 3;
    }

    public static class User {
        public static final String KEY_SAVE_USER = "keysave_user";
    }

    public static class Action {
        public static final int ACTION_FOLLOW_STORE = 0;
        public static final int ACTION_LIKE = 1;
        public static final int ACTION_SAVE = 2;
        public static final int ACTION_GET_CODE = 3;
        public static final int ACTION_SUBMIT = 4;
        public static final int ACTION_EDIT_RATTING = 5;
    }

    public static class GoogleMap {
        public static String URL_REQUEST_GOOGLE_MAP = "https://www.google.com/maps/search/?api=1&query=";
        public static String PACKAGE_NAME_GOOGLE_MAP = "com.google.android.apps.maps";
        public static String CLASS_NAME_GOOGLE_MAP = "com.google.android.maps.MapsActivity";

    }

    public static class UserCategory {
        public static String key_category = "key_category";
    }

}
