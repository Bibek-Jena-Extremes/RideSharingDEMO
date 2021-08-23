package com.eztruck.eztruckcustomer.ManagementUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;

import com.eztruck.eztruckcustomer.ConnectionUtil.ConnectionBuilder;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.DatabaseUtil.DatabaseHandler;
import com.eztruck.eztruckcustomer.DatabaseUtil.DatabaseObject;
import com.eztruck.eztruckcustomer.DatabaseUtil.QueryFactory;
import com.eztruck.eztruckcustomer.DatabaseUtil.QueryRunner;
import com.eztruck.eztruckcustomer.ObjectUtil.CursorObject;
import com.eztruck.eztruckcustomer.ObjectUtil.PrefObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;

import java.util.ArrayList;

public class Management {
    private String TAG = Management.class.getName();
    private Context context;
    private QueryRunner queryRunner;
    private QueryFactory queryFactory;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public Management(Context context) {

        Utility.Logger(TAG, "Setting : Working");

        this.context = context;
        queryFactory = new QueryFactory();
        queryRunner = new QueryRunner(context);
        sharedPreferences = context.getSharedPreferences(Utility.getStringFromRes(context, R.string.app_name), Context.MODE_PRIVATE);

    }


    /**
     * <p>It is used to send Request to Server</p>
     *
     * @param requestObject
     */
    public void sendRequestToServer(RequestObject requestObject) {

        String serverUrl = null;
        String requestType = Constant.REQUEST.POST.toString();

        Utility.Logger(TAG, "RequestObject : " + requestObject.toString());

        //region All functionality of giving Server Url
        if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.UI) {

            if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_RIDE_PAYMENT_TYPE
                    || requestObject.getConnection() == Constant.CONNECTION.REDEEM_COUPON
                    || requestObject.getConnection() == Constant.CONNECTION.PAYMENT_CARDS
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_CARD
                    || requestObject.getConnection() == Constant.CONNECTION.LOGIN
                    || requestObject.getConnection() == Constant.CONNECTION.SIGN_UP
                    || requestObject.getConnection() == Constant.CONNECTION.FORGOT
                    || requestObject.getConnection() == Constant.CONNECTION.UPDATE
                    || requestObject.getConnection() == Constant.CONNECTION.ESTIMATED_PICK_UP_TIME
                    || requestObject.getConnection() == Constant.CONNECTION.ESTIMATED_FARE_PRICE
                    || requestObject.getConnection() == Constant.CONNECTION.LIST_OF_CITY
                    || requestObject.getConnection() == Constant.CONNECTION.BOOK_RIDE
                    || requestObject.getConnection() == Constant.CONNECTION.ORDER_HISTORY
                    || requestObject.getConnection() == Constant.CONNECTION.CURRENT_RIDE
                    || requestObject.getConnection() == Constant.CONNECTION.PRIVACY_POLICY) {

                serverUrl = Constant.ServerInformation.REST_API_URL;


            }
            else if (requestObject.getConnection() == Constant.CONNECTION.NEARBY_LOCATIONS) {

                serverUrl = String.format(Constant.ServerInformation.NEAREST_POI_URL,
                        requestObject.getSourceCoordinates(), requestObject.getDestinationCoordinates());
                requestType = Constant.REQUEST.GET.toString();

            }
            else if (requestObject.getConnection() == Constant.CONNECTION.SEARCH_LOCATION) {

                serverUrl = String.format(Constant.ServerInformation.SEARCH_PLACES_URL, Utility.encodeUrl(requestObject.getSearchedLocation()),
                        requestObject.getSourceCoordinates(), requestObject.getDestinationCoordinates());
                requestType = Constant.REQUEST.GET.toString();


            }
            else if (requestObject.getConnection() == Constant.CONNECTION.AUTO_COMPLETE) {

                serverUrl = String.format(Constant.ServerInformation.AUTO_COMPLETE_URL, Utility.encodeUrl(requestObject.getCity()+" "+requestObject.getSearchedLocation()));
                requestType = Constant.REQUEST.GET.toString();


            }

        }
        else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.BACKGROUND) {

            if ( requestObject.getConnection() == Constant.CONNECTION.ADD_COMMENT
                    || requestObject.getConnection() == Constant.CONNECTION.DELETE_PAYMENT_CARD
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_RIDER_RATING
                    || requestObject.getConnection() == Constant.CONNECTION.CANCEL_ORDER
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_REPORT) {

                serverUrl = Constant.ServerInformation.REST_API_URL;

            }


        }


        //endregion

        //It initialize the Connection to Server

        new ConnectionBuilder.CreateConnection()
                .setRequestObject(requestObject
                        .setContext(context)
                        .setServerUrl(serverUrl)
                        .setRequestType(requestType))
                .buildConnection();


    }


    /**
     * <p>It is used to retrieve data from Database</p>
     *
     * @param databaseObject
     */
    public ArrayList<Object> getDataFromDatabase(DatabaseObject databaseObject) {
        ArrayList<Object> objectArrayList = new ArrayList<>();
        CursorObject cursorObject = null;
        int lastItemInsertedId;

        Utility.Logger(TAG, databaseObject.toString());

        //Get required Query for Specific Db Operation

        String formattedQuery = queryFactory.getRequiredFormattedQuery(databaseObject);
        Utility.Logger(TAG, "Setting : Working : Query = " + formattedQuery);

        //If Query Empty then return Empty Arraylist

        if (Utility.isEmptyString(formattedQuery))
            return objectArrayList;

        //Initialize Database Handler

        if (databaseObject.getTypeOperation() == Constant.TYPE.FAVOURITES
                || databaseObject.getTypeOperation() == Constant.TYPE.CART) {
            sqLiteOpenHelper = new DatabaseHandler(context);
        }

        //Perform Db Operation on formatted Sql Queries

        if (databaseObject.getDbOperation() == Constant.DB.RETRIEVE) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        } else if (databaseObject.getDbOperation() == Constant.DB.INSERT) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null) {
                    cursorObject.getDatabase().close();
                }

            }

        } else if (databaseObject.getDbOperation() == Constant.DB.DELETE) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null)
                    cursorObject.getDatabase().close();
            }

        } else if (databaseObject.getDbOperation() == Constant.DB.DELETE_SPECIFIC_TYPE) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null)
                    cursorObject.getDatabase().close();
            }

        } else if (databaseObject.getDbOperation() == Constant.DB.UPDATE) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null)
                    cursorObject.getDatabase().close();
            }

        } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_ID) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_TYPE) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        } else if (databaseObject.getDbOperation() == Constant.DB.INSERTION_ID) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        }

        return objectArrayList;
    }


    /**
     * <p>It is used to get Preference Data</p>
     *
     * @param prefObject
     * @return
     */
    public PrefObject getPreferences(PrefObject prefObject) {
        PrefObject pref = new PrefObject();

        if (prefObject.isRetrieveTags()) {
            pref.setTags(sharedPreferences.getString(Constant.SharedPref.PREF_TAGS, null));
        }

        if (prefObject.isRetrieveNext()) {
            pref.setNextPage(sharedPreferences.getString(Constant.SharedPref.NEXT_URL, null));
        }

        if (prefObject.isRetrievePosition()) {
            pref.setCurrentPosition(sharedPreferences.getInt(Constant.SharedPref.POSITION, -1));
        }

        if (prefObject.isRetrieveFirstLaunch()) {
            pref.setFirstLaunch(sharedPreferences.getBoolean(Constant.SharedPref.FIRST_LAUNCH, true));
        }

        if (prefObject.isRetrieveLogin()) {
            pref.setLogin(sharedPreferences.getBoolean(Constant.SharedPref.LOGIN, false));
        }

        if (prefObject.isRetrieveUserId()) {
            pref.setUserId(sharedPreferences.getString(Constant.SharedPref.USER_ID, "0"));
        }

        if (prefObject.isRetrieveUserRemember()) {
            pref.setUserRemember(sharedPreferences.getBoolean(Constant.SharedPref.USER_REMEMBER, false));
        }

        if (prefObject.isRetrieveNewsfeed()) {
            pref.setNewsfeedId(sharedPreferences.getString(Constant.SharedPref.NEWS_FEED, "0"));
        }

        if (prefObject.isRetrieveUserCredential()) {

            pref.setUserId(sharedPreferences.getString(Constant.SharedPref.USER_ID, "0"));
            pref.setArtistId(sharedPreferences.getString(Constant.SharedPref.ARTIST_ID, "0"));
            pref.setUserEmail(sharedPreferences.getString(Constant.SharedPref.USER_EMAIL, null));
            pref.setUserPassword(sharedPreferences.getString(Constant.SharedPref.USER_PASSWORD, null));
            pref.setFirstName(sharedPreferences.getString(Constant.SharedPref.USER_FIRST_NAME, null));
            pref.setLastName(sharedPreferences.getString(Constant.SharedPref.USER_LAST_NAME, null));
            pref.setUserPhone(sharedPreferences.getString(Constant.SharedPref.USER_PHONE, null));
            pref.setPictureUrl(sharedPreferences.getString(Constant.SharedPref.USER_PICTURE, null));
            pref.setLoginType(sharedPreferences.getString(Constant.SharedPref.LOGIN_TYPE, null));
            pref.setDescription(sharedPreferences.getString(Constant.SharedPref.BIO_TYPE, null));
            pref.setUid(sharedPreferences.getString(Constant.SharedPref.UID, null));


        }

        if (prefObject.isRetrieveNightMode()) {
            pref.setNightMode(sharedPreferences.getBoolean(Constant.SharedPref.NIGHT_MODE, false));
        }

        if (prefObject.isRetrievePush()) {
            pref.setPush(sharedPreferences.getBoolean(Constant.SharedPref.PUSH_NOTIFICATION, true));
        }

        if (prefObject.isRetrieveDownloadWifi()) {
            pref.setDownloadWifi(sharedPreferences.getBoolean(Constant.SharedPref.DOWNLOAD_WIFI, false));
        }

        if (prefObject.isRetrieveSaveWallpaper()) {
            pref.setDefaultWallpaper(sharedPreferences.getInt(Constant.SharedPref.DEFAULT_PICTURE, 0));
        }

        if (prefObject.isRetrieveParallaxMode()) {
            pref.setParallaxMode(sharedPreferences.getBoolean(Constant.SharedPref.PARALLAX_MODE, Constant.ParallaxConfig.DEFAULT_PARALLAX_MODE));
        }

        if (prefObject.isRetrieveRetrieveMotion()) {
            pref.setMotion(sharedPreferences.getInt(Constant.SharedPref.RANGE, Constant.ParallaxConfig.DEFAULT_RANGE));
        }

        if (prefObject.isRetrieveDelay()) {
            pref.setDelay(sharedPreferences.getInt(Constant.SharedPref.DENY, Constant.ParallaxConfig.DEFAULT_DELAY));
        }

        if (prefObject.isRetrieveScrollingMode()) {
            pref.setScrollingMode(sharedPreferences.getBoolean(Constant.SharedPref.SCROLL, Constant.ParallaxConfig.DEFAULT_SCROLL));
        }

        if (prefObject.isRetrievePowerSavingMode()) {
            pref.setPowerSavingMode(sharedPreferences.getBoolean(Constant.SharedPref.POWER_SAVER, Constant.ParallaxConfig.DEFAULT_POWER_SAVING));
        }

        if (prefObject.isRetrieveOrderId()) {
            pref.setOrderId(sharedPreferences.getString(Constant.SharedPref.ORDER_ID,"0"));
        }

        Utility.Logger(TAG, "getPreference = " + pref.toString());

        return pref;

    }


    /**
     * <p>It is used to save Preferences data</p>
     *
     * @param prefObject
     */
    public void savePreferences(PrefObject prefObject) {
        editor = sharedPreferences.edit();

        Utility.Logger(TAG, "Preference = " + prefObject.toString());

        if (prefObject.isSaveTags()) {
            editor.putString(Constant.SharedPref.PREF_TAGS, prefObject.getTags());
        } else if (prefObject.isSaveNext()) {
            editor.putString(Constant.SharedPref.NEXT_URL, prefObject.getNextPage());
        } else if (prefObject.isSavePosition()) {
            editor.putInt(Constant.SharedPref.POSITION, prefObject.getCurrentPosition());
        } else if (prefObject.isSaveFirstLaunch()) {
            editor.putBoolean(Constant.SharedPref.FIRST_LAUNCH, prefObject.isFirstLaunch());
        } else if (prefObject.isSaveLogin()) {
            editor.putBoolean(Constant.SharedPref.LOGIN, prefObject.isLogin());
        } else if (prefObject.isSaveUserId()) {
            editor.putString(Constant.SharedPref.USER_ID, prefObject.getUserId());
        } else if (prefObject.isSaveUserRemember()) {
            editor.putBoolean(Constant.SharedPref.USER_REMEMBER, prefObject.isUserRemember());
        } else if (prefObject.isSaveNewsfeed()) {
            editor.putString(Constant.SharedPref.NEWS_FEED, prefObject.getNewsfeedId());
        } else if (prefObject.isSaveUserCredential()) {

            if (!Utility.isEmptyString(prefObject.getUserId())) {
                editor.putString(Constant.SharedPref.USER_ID, prefObject.getUserId());
            }

            editor.putString(Constant.SharedPref.ARTIST_ID, prefObject.getArtistId());

            if (!Utility.isEmptyString(prefObject.getUserEmail())) {
                editor.putString(Constant.SharedPref.USER_EMAIL, prefObject.getUserEmail());
            }

            if (!Utility.isEmptyString(prefObject.getUserPassword())) {
                editor.putString(Constant.SharedPref.USER_PASSWORD, prefObject.getUserPassword());
            }

            if (!Utility.isEmptyString(prefObject.getFirstName())) {
                editor.putString(Constant.SharedPref.USER_FIRST_NAME, prefObject.getFirstName());
                editor.putString(Constant.SharedPref.USER_LAST_NAME, prefObject.getLastName());
            }

            if (!Utility.isEmptyString(prefObject.getUserPhone())) {
                editor.putString(Constant.SharedPref.USER_PHONE, prefObject.getUserPhone());
            }

            editor.putString(Constant.SharedPref.USER_PICTURE, prefObject.getPictureUrl());
            editor.putString(Constant.SharedPref.LOGIN_TYPE, prefObject.getLoginType());
            editor.putString(Constant.SharedPref.BIO_TYPE, prefObject.getDescription());
            editor.putString(Constant.SharedPref.UID, prefObject.getUid());

        } else if (prefObject.isSaveNightMode()) {
            editor.putBoolean(Constant.SharedPref.NIGHT_MODE, prefObject.isNightMode());
        } else if (prefObject.isSaveDownloadWifi()) {
            editor.putBoolean(Constant.SharedPref.DOWNLOAD_WIFI, prefObject.isDownloadWifi());
        } else if (prefObject.isSavePush()) {
            editor.putBoolean(Constant.SharedPref.PUSH_NOTIFICATION, prefObject.isPush());
        } else if (prefObject.isSaveWallpaper()) {

            editor.putInt(Constant.SharedPref.DEFAULT_PICTURE, prefObject.getDefaultWallpaper());

        } else if (prefObject.isSaveParallaxMode()) {

            editor.putBoolean(Constant.SharedPref.PARALLAX_MODE, prefObject.isParallaxMode());

        } else if (prefObject.isSaveMotion()) {

            editor.putInt(Constant.SharedPref.RANGE, prefObject.getMotion());

        } else if (prefObject.isSaveDelay()) {

            editor.putInt(Constant.SharedPref.DENY, prefObject.getDelay());

        } else if (prefObject.isSaveScrollingMode()) {

            editor.putBoolean(Constant.SharedPref.SCROLL, prefObject.isScrollingMode());

        } else if (prefObject.isSavePowerSavingMode()) {

            editor.putBoolean(Constant.SharedPref.POWER_SAVER, prefObject.isPowerSavingMode());

        } else if (prefObject.isSaveOrderId()) {

            editor.putString(Constant.SharedPref.ORDER_ID, prefObject.getOrderId());

        }

        editor.commit();

    }





}
