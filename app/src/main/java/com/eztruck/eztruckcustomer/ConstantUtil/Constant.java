package com.eztruck.eztruckcustomer.ConstantUtil;


import com.eztruck.eztruckcustomer.BuildConfig;
import com.google.android.gms.maps.model.LatLng;
import com.eztruck.eztruckcustomer.ObjectUtil.GlobalDataObject;

public class Constant {

    public static GlobalDataObject globalDataObject;
    public static LatLng baseLatLng;

    public enum DB {RETRIEVE, INSERT, DELETE, UPDATE, SPECIFIC_ID, SPECIFIC_TYPE, DELETE_SPECIFIC_TYPE, INSERTION_ID}

    public enum CONNECTION {
        PAYMENT_CARDS, ADD_COMMENT,PRIVACY_POLICY,ORDER_HISTORY, DELETE_PAYMENT_CARD,ADD_REPORT,
        RETRIEVE_RIDE_PAYMENT_TYPE, ESTIMATED_PICK_UP_TIME, ESTIMATED_FARE_PRICE, NEARBY_LOCATIONS, SEARCH_LOCATION,
        AUTO_COMPLETE, LIST_OF_CITY , BOOK_RIDE , CANCEL_ORDER , CURRENT_RIDE , UPDATE ,LOGIN, SIGN_UP, FORGOT ,
        ADD_RIDER_RATING,REDEEM_COUPON , ADD_CARD
    }

    public enum CONNECTION_TYPE {BACKGROUND, UI, DOWNLOAD, STATUS}

    public enum DATA_TYPE {
        ARTIST, COMMON, POPULAR, PRODUCT, CATEGORIES, FEED, FEATURED, TOP_BRANDS, NEAREST, HEADING_BAR, REVIEW, HISTORY, CATEGORY_BY_COLOR, CATEGORY_BY_NAME, ARTIST_HEADER, COURSE_SELECTOR, DURATION, SERVING,
        FROM_CHAT, TO_CHAT,

        LABEL_VIEW, LOCATION_SELECTOR_VIEW, RIDE_TYPE, CARD_TYPE, CARD_DETAIL, ADD_CARD, CITY_VIEW, AUTO_COMPLETE_VIEW
    }

    public enum TYPE {FAVOURITES, CART, INSERTION_ID}

    public enum DATETIME {DATE, TIME, HOUR24, HOUR12, BOTH12, BOTH24, DATE_DD_MM_YYYY_hh_mm_ss, LONG}


    public enum REQUEST {
        GET, POST
    }


    /**
     * <p>It contain all Server Url</p>
     */
    public static class ServerInformation {
        public static String GOOGLE_DRIVE_LINK = "https://docs.google.com/uc?id=";
        public static String PRIVACY_URL = "https://docs.google.com/document/d/";

        static String BASE_URL = "http://app8.eztruck.co";
        static String FOLDER = "/kareem_taxi_app/";

        public static String REST_API_URL = BASE_URL + FOLDER + "kareem_taxi_home.php";
        public static String PICTURE_URL = BASE_URL + FOLDER + "admin/uploads/image/";
        public static String FACEBOOK_HIGH_PIXEL_URL = "?type=large&redirect=true&width=300&height=300";
        public static String NEAREST_POI_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&radius=500&key=" + Credentials.NEARBY_API_KEY;
        public static String SEARCH_PLACES_URL = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=%s&inputtype=textquery&fields=formatted_address,name,geometry&locationbias=point:%s,%s&key=" + Credentials.NEARBY_API_KEY;
        public static String AUTO_COMPLETE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=%s&key=" + Credentials.NEARBY_API_KEY;


    }


    /**
     * <p>It contain all of the Credentials </p>
     */
    public static class Credentials {

        public static String GOOGLE_API_KEY = BuildConfig.GoogleSecAPIKEY;
        public static String NEARBY_API_KEY = BuildConfig.Google_Places_key;
        public static String MAPBOX_API_KEY = BuildConfig.Mapbox_Access_key;
        public static boolean isFacebookHashKeyRequired = true;
        public static String DEVICE_TOKEN;
    }


    /**
     * <p>It contain all of the Important Messages</p>
     */
    public static class ImportantMessages {
        public static final String CONNECTION_ERROR = "Connection Error";

    }


    /**
     * <p>It contain all of the App Configuration</p>
     */
    public static class AppConfiguration {
        public static String DEFAULT_RADIUS = "25";
    }


    /**
     * <p>It contain all of the Key of Share Preferences</p>
     */
    public static class SharedPref {
        public static String PREF_TAGS = "TAGS";
        public static String NEXT_URL = "NEXT_URL";
        public static String POSITION = "POSITION";
        public static String FIRST_LAUNCH = "FIRST_LAUNCH";
        public static String LOGIN = "LOGIN";
        public static String USER_ID = "USER_ID";
        public static String USER_REMEMBER = "USER_REMEMBER";
        public static String USER_EMAIL = "USER_EMAIL";
        public static String USER_PASSWORD = "USER_PASSWORD";
        public static String USER_FIRST_NAME = "USER_FIRST_NAME";
        public static String USER_LAST_NAME = "USER_LAST_NAME";
        public static String USER_PICTURE = "USER_PICTURE";
        public static String NEWS_FEED = "NEWS_FEED";
        public static String NIGHT_MODE = "NIGHT_MODE";
        public static String DOWNLOAD_WIFI = "DOWNLOAD_WIFI";
        public static String PUSH_NOTIFICATION = "PUSH_NOTIFICATION";
        public static String LOGIN_TYPE = "LOGIN_TYPE";
        public static String BIO_TYPE = "BIO_TYPE";
        public static String UID = "UID";
        public static String ARTIST_ID = "ARTIST_ID";

        public final static String RANGE = "range";
        public final static String DENY = "deny";
        public final static String SCROLL = "scroll";
        public final static String DEFAULT_PICTURE = "default_picture";
        public final static String POWER_SAVER = "power_saver";
        public final static String PARALLAX_MODE = "PARALLAX_MODE";
        public final static String SERVICE_WORKING = "service_working";
        public static String USER_PHONE = "USER_PHONE";
        public static String ORDER_ID = "ORDER_ID";

    }


    /**
     * <p>It contain all of the Request Code</p>
     */
    public static class RequestCode {
        public static int PERMISSION_REQUEST_CODE = 1;
        public static int REQUEST_CODE_GALLERY = 2;
        public static int REQUEST_CODE_CAMERA = 3;
        public static int GOOGLE_SIGN_IN_CODE = 4;
        public static int REQ_CODE_SPEECH_INPUT = 5;
        public static int PRODUCT_DETAIL_CODE = 6;
        public static int COUPON_CODE = 7;
        public static int REQUEST_CODE_PICTURE = 8;
        public static int REQUEST_LOCATION = 9;
        public static int SEARCH_SPECIFIC_PLACE = 10;
        public static int REQUEST_CODE_FROM = 11;
        public static int REQUEST_CODE_TO = 12;
        public static int CITY_SELECTOR_REQUEST_CODE = 13;
        public static int REQUEST_CODE_ADD_CARD = 14;
    }


    /**
     * <p>It contain all of the Key of Intent Sharing</p>
     */
    public static class IntentKey {
        public static String REQUEST_OBJECT = "REQUEST_OBJECT";
        public static String CATEGORY = "CATEGORY";
        public static String CATEGORY_ID = "CATEGORY_ID";
        public static String FUNCTIONALITY = "FUNCTIONALITY";
        public static String POST_TYPE = "POST_TYPE";
        public static String POST_ID = "POST_ID";
        public static String FILE_PATH = "FILE_PATH";
        public static String ARTIST_ID = "ARTIST_ID";
        public static String ARTIST_NAME = "ARTIST_NAME";
        public static String PLAYLIST = "PLAYLIST";
        public static String PLAYLIST_ID = "PLAYLIST_ID";
        public static String PLAYLIST_NAME = "PLAYLIST_NAME";
        public static String CONNECTION = "CONNECTION";
        public static String ARTIST_WORK = "ARTIST_WORK";
        public static String ARTIST_DETAIL = "ARTIST_DETAIL";
        public static String POST_DETAIL = "POST_DETAIL";
        public static String RESTAURANT_DETAIL = "RESTAURANT_DETAIL";
        public static String VIDEO_URL = "VIDEO_URL";
        public static String BOOK_TYPE = "BOOK_TYPE";
        public static String ON_BOARD = "ON_BOARD";
        public static String LOGIN_REQUIRED = "LOGIN_REQUIRED";
        public static String CONTINUE_REQUIRED = "CONTINUE_REQUIRED";
        public static String BACK_ACTION = "BACK_ACTION";
        public static String RECIPE_FRAGMENT_DETAIL = "RECIPE_FRAGMENT_DETAIL";
        public static String RECIPE_DETAIL = "RECIPE_DETAIL";
        public static String SEARCH_KEYWORD = "SEARCH_KEYWORD";
        public static String FEATURED_ID = "FEATURED_ID";
        public static String FEATURED_PLACE = "FEATURED_PLACE";
        public static String FILTER = "FILTER";
        public static String COUPON_DETAIL = "COUPON_DETAIL";
        public static String ORDER_DETAIL = "ORDER_DETAIL";
        public static String CHATTING = "CHATTING";
        public static String USER = "USER";
        public static String RIDER = "RIDER";
        public static String LOCATION_SELECTOR = "LOCATION_SELECTOR";
        public static String SOURCE_LATITUDE = "SOURCE_LATITUDE";
        public static String SOURCE_LONGITUDE = "SOURCE_LONGITUDE";
        public static String SOURCE_CITY = "SOURCE_CITY";
        public static String CARD_INFORMATION = "CARD_INFORMATION";
        public static String RIDE_HISTORY = "RIDE_HISTORY";
    }


    /**
     * <p>It contain al lof the database columns</p>
     */
    public static class DatabaseColumn {
        public static final String TAG = "Database";

        public static String FAVOURITES_TABLE_NAME = "Favourites";
        public static String FAVOURITES_COLUMN_ID = "id";
        public static String FAVOURITES_COLUMN_USER_ID = "user_id";
        public static String FAVOURITES_COLUMN_RESTAURANT_ID = "restaurant_id";

        public static String CART_TABLE_NAME = "Cart";
        public static String CART_COLUMN_ID = "id";
        public static String CART_COLUMN_USER_ID = "user_id";
        public static String CART_COLUMN_RESTAURANT_ID = "restaurant_id";
        public static String CART_COLUMN_RESTAURANT_LATITUDE = "restaurant_latitude";
        public static String CART_COLUMN_RESTAURANT_LONGITUDE = "restaurant_longitude";
        public static String CART_COLUMN_DELIVERY_CHARGES = "delivery_charges";
        public static String CART_COLUMN_MIN_ORDER_PRICE = "min_order_price";
        public static String CART_COLUMN_DELIVERY_TIME = "delivery_time";
        public static String CART_COLUMN_PAYMENT_TYPE = "payment_type";
        public static String CART_COLUMN_PRODUCT_ID = "product_id";
        public static String CART_COLUMN_PRODUCT_NAME = "product_name";
        public static String CART_COLUMN_PRODUCT_QUANTITY = "product_quantity";
        public static String CART_COLUMN_PRODUCT_BASE_PRICE = "product_base_price";
        public static String CART_COLUMN_PRODUCT_PRICE = "product_price";
        public static String CART_COLUMN_CURRENCY_SYMBOL = "currency_symbol";
        public static String CART_COLUMN_PRODUCT_ATTRIBUTE = "product_attribute";
        public static String CART_COLUMN_SPECIAL_INSTRUCTIONS = "product_special_instructions";

    }


    /**
     * <p>It contain list of Time & Date Formats</p>
     */
    public static class TimeDateFormat {
        public static String timeDateFormat24 = "dd/MM/yyyy hh:mm";
        public static String dateFormat24 = "dd MMM yyyy";
        public static String timeFormat24 = "hh:mm";

        public static String timeDateFormat12 = "yyyy-MM-dd hh:mm:ss";
        public static String dateFormat12 = "dd MMM yyyy";
        public static String timeFormat12 = "hh:mm a";

        public static String hourFormat = "hh a";
        public static String dayFormat = "E , MMMM dd";
        public static String internationalTimeFormat = "hh:mm";
    }


    /**
     * <p>It contain all of error code which may come from Server Request</p>
     */
    public static class ErrorCodes {
        public static String success_code = "202";
        public static String error_code = "206";
    }


    public static class PostType {
        public static String AUTHOR_TYPE = "author";
        public static String CATEGORY_TYPE = "category";
        public static String POST_TYPE = "restaurant";
        public static String REFER_USER = "refer";
        public static String STANDARD_POST = "standard";
        public static String SERVER_VIDEO = "file";
        public static String URL_VIDEO = "youtube";
    }


    /**
     * <p>It consist list of default configuration
     * of parallax wallpaper</p>
     */
    public static class ParallaxConfig {

        public static int DEFAULT_RANGE = 15;
        public static int DEFAULT_DELAY = 10;
        public static boolean DEFAULT_SCROLL = false;
        public static boolean DEFAULT_POWER_SAVING = true;
        public static boolean DEFAULT_PARALLAX_MODE = true;

    }

    /**
     * <p>It consist list of difficulty level</p>
     */
    public static class LoginType {

        public static String NATIVE_LOGIN = "native";
        public static String FACEBOOK_LOGIN = "facebook";
        public static String GOOGLE_LOGIN = "google";

    }

    public static LatLng getBaseLatLng() {
        return baseLatLng;
    }

    public static void setBaseLatLng(LatLng baseLatLng) {
        Constant.baseLatLng = baseLatLng;
    }

/*
    public static String loadNearbyJsonFromAsset() {
        String json = null;
        try {
            InputStream is = MyApplication.getInstance().getAssets().open("nearby.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String loadSearchPlaceJsonFromAsset() {
        String json = null;
        try {
            InputStream is = MyApplication.getInstance().getAssets().open("search_place.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String loadAutoCompleteJsonFromAsset() {
        String json = null;
        try {
            InputStream is = MyApplication.getInstance().getAssets().open("auto_complete_places.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }*/


}
