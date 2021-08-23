package com.eztruck.eztruckcustomer.ConnectionUtil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import android.widget.Toast;


import com.eztruck.eztruckcustomer.BuildConfig;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.offline.ProgressiveDownloader;
import com.google.gson.Gson;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.FontUtil.Font;
import com.eztruck.eztruckcustomer.JsonUtil.AutoCompleteUtil.AutoCompleteJson;
import com.eztruck.eztruckcustomer.JsonUtil.CaptainListUtil.CaptainJson;
import com.eztruck.eztruckcustomer.JsonUtil.CardUtil.CardJson;
import com.eztruck.eztruckcustomer.JsonUtil.CouponUtil.CouponJson;
import com.eztruck.eztruckcustomer.JsonUtil.FareCalculationUtil.FareCalculationJson;
import com.eztruck.eztruckcustomer.JsonUtil.FindPlaceUtil.FindPlaceJson;
import com.eztruck.eztruckcustomer.JsonUtil.GeneralResponseUtil.GeneralResponse;
import com.eztruck.eztruckcustomer.JsonUtil.ListOfCountryUtil.ListOfCountryJson;
import com.eztruck.eztruckcustomer.JsonUtil.NearbyUtil.NearbyJson;
import com.eztruck.eztruckcustomer.JsonUtil.OrderUtil.OrderJson;
import com.eztruck.eztruckcustomer.JsonUtil.PrivacyPolicyUtil.PrivacyPolicyJson;
import com.eztruck.eztruckcustomer.JsonUtil.RideHistoryUtil.RideHistoryJson;
import com.eztruck.eztruckcustomer.JsonUtil.RideTypeList.RideTypeJson;
import com.eztruck.eztruckcustomer.JsonUtil.UserUtil.UserJson;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.GlobalDataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RequestObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.ServiceUtil.MyIntentService;
import com.eztruck.eztruckcustomer.ServiceUtil.OreoIntentService;
import com.eztruck.eztruckcustomer.Utility.Utility;

import java.io.File;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import needle.Needle;
import needle.UiRelatedProgressTask;


public class ConnectionBuilder {
    private String TAG = ConnectionBuilder.class.getName();
    ACProgressFlower acProgressFlower = null;
    ProgressDialog progressDialog;

    public ConnectionBuilder(final RequestObject requestObject) {

        if (!Utility.checkConnection(requestObject.getContext())) {

            if (requestObject.getInternetCallback() != null) {
                requestObject.getInternetCallback().onConnectivityFailed();
                return;
            }

            Utility.Toaster(requestObject.getContext(), Utility.getStringFromRes(requestObject.getContext(), R.string.internet_not_available), Toast.LENGTH_SHORT);
            return;
        }

        Utility.extraData(TAG, "Json = " + requestObject.toString());

        if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.UI) {

            if (requestObject.getConnection() == Constant.CONNECTION.PRIVACY_POLICY
                    || requestObject.getConnection() == Constant.CONNECTION.FORGOT
                    || requestObject.getConnection() == Constant.CONNECTION.UPDATE
                    || requestObject.getConnection() == Constant.CONNECTION.BOOK_RIDE
                    || requestObject.getConnection() == Constant.CONNECTION.CURRENT_RIDE) {


                if (Utility.isEmptyString(requestObject.getLoadingText()))
                    acProgressFlower = getACProgressFlower(requestObject.getContext(),
                            Utility.getStringFromRes(requestObject.getContext(), R.string.progress));
                else
                    acProgressFlower = getACProgressFlower(requestObject.getContext(),
                            requestObject.getLoadingText());

                if (acProgressFlower != null)
                    acProgressFlower.show();

            }

            Needle.onBackgroundThread().execute(new UiRelatedProgressTask<String, Integer>() {

                @Override
                protected void onProgressUpdate(Integer integer) {

                }

                @Override
                protected String doWork() {

                    /*if (requestObject.getConnection() == Constant.CONNECTION.NEARBY_LOCATIONS) {
                        return Constant.loadNearbyJsonFromAsset();
                    } else if (requestObject.getConnection() == Constant.CONNECTION.SEARCH_LOCATION) {
                        return Constant.loadSearchPlaceJsonFromAsset();
                    } else if (requestObject.getConnection() == Constant.CONNECTION.AUTO_COMPLETE) {
                        return Constant.loadAutoCompleteJsonFromAsset();
                    }
*/
                    if (Constant.REQUEST.valueOf(requestObject.getRequestType()) == Constant.REQUEST.GET) {
                        return Connection.makeRequest(requestObject.getServerUrl(), requestObject.getRequestType());
                    } else if (Constant.REQUEST.valueOf(requestObject.getRequestType()) == Constant.REQUEST.POST) {
                        return Connection.makeRequest(requestObject.getServerUrl(), requestObject.getJson(), requestObject.getRequestType());
                    } else
                        return Connection.makeRequest(requestObject.getServerUrl(), requestObject.getRequestType());

                }

                @Override
                protected void thenDoUiRelatedWork(String data) {

                    Utility.extraData(TAG, "Response = " + data);

                    if (Utility.isEmptyString(data)) {
                        return;
                    }

                    if (data.equals(Constant.ImportantMessages.CONNECTION_ERROR)) {
                        return;
                    }

                    Gson gson = new Gson();
                    Object object = null;
                    String responseCode = null;
                    String responseMessage = null;
                    DataObject dataObject = null;

                    if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_RIDE_PAYMENT_TYPE) {

                        object = gson.fromJson(data, RideTypeJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.ESTIMATED_PICK_UP_TIME) {

                        object = gson.fromJson(data, CaptainJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.ESTIMATED_FARE_PRICE) {

                        object = gson.fromJson(data, FareCalculationJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.REDEEM_COUPON) {

                        object = gson.fromJson(data, CouponJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.NEARBY_LOCATIONS) {

                        object = gson.fromJson(data, NearbyJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.SEARCH_LOCATION) {

                        object = gson.fromJson(data, FindPlaceJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.AUTO_COMPLETE) {

                        object = gson.fromJson(data, AutoCompleteJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.LIST_OF_CITY) {

                        object = gson.fromJson(data, ListOfCountryJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.PAYMENT_CARDS
                            || requestObject.getConnection() == Constant.CONNECTION.ADD_CARD) {

                        object = gson.fromJson(data, CardJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    }else if (requestObject.getConnection() == Constant.CONNECTION.BOOK_RIDE) {

                        object = gson.fromJson(data, OrderJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.SIGN_UP) {

                        object = gson.fromJson(data, UserJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.LOGIN) {

                        object = gson.fromJson(data, UserJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.ORDER_HISTORY) {

                        object = gson.fromJson(data, RideHistoryJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.CURRENT_RIDE) {

                        object = gson.fromJson(data, RideHistoryJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.FORGOT) {

                        object = gson.fromJson(data, UserJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.UPDATE) {

                        object = gson.fromJson(data, GeneralResponse.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    }else if (requestObject.getConnection() == Constant.CONNECTION.PRIVACY_POLICY) {

                        object = gson.fromJson(data, PrivacyPolicyJson.class);
                        dataObject = DataObject.getDataObject(requestObject, object);

                    }



                    if (Utility.isEmptyString(dataObject.getCode())) {
                        Utility.Logger(TAG, "No Response Code");
                        return;
                    }

                    responseCode = dataObject.getCode();
                    responseMessage = dataObject.getMessage();


                    if (acProgressFlower != null && acProgressFlower.isShowing())
                        acProgressFlower.dismiss();

                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (requestObject.getConnectionCallback() != null) {

                        if (responseCode.equals(Constant.ErrorCodes.success_code)) {
                            requestObject.getConnectionCallback().onSuccess(dataObject, requestObject);
                        } else if (responseCode.equals(Constant.ErrorCodes.error_code)) {
                                requestObject.getConnectionCallback().onError(responseMessage, requestObject);

                        } else {
                            requestObject.getConnectionCallback().onError(responseMessage, requestObject);
                        }

                    }

                }


            });

        }
        else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.BACKGROUND) {

            //region All background tasking functionalites

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                Intent intent = new Intent(requestObject.getContext(), OreoIntentService.class);

                if (requestObject.getConnection() == Constant.CONNECTION.ADD_COMMENT) {
                    GlobalDataObject.setRequestObject(requestObject);
                } else
                    intent.putExtra(Constant.IntentKey.REQUEST_OBJECT, requestObject);

                OreoIntentService.enqueueWork(requestObject.getContext(), intent);

            } else {


                Intent intent = new Intent(requestObject.getContext(), MyIntentService.class);

                if (requestObject.getConnection() == Constant.CONNECTION.ADD_COMMENT) {
                    GlobalDataObject.setRequestObject(requestObject);
                } else
                    intent.putExtra(Constant.IntentKey.REQUEST_OBJECT, requestObject);

                requestObject.getContext().startService(intent);

            }

            //endregion

        }
        else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.DOWNLOAD) {


            //region All Downloading functionalities

            final File folder = new File(requestObject.getContext().getFilesDir(), Utility.getStringFromRes(requestObject.getContext(), R.string.app_name));
            final ProgressDialog[] acProgressFlower = {null};

            if (!folder.exists())
                folder.mkdirs();

            int downloadId = 0;
            NotificationManager notificationManager = null;
            final String folderPath = folder.getAbsolutePath();

            String serverUrl = null;
            ACProgressFlower acDialog = null;


            serverUrl = requestObject.getServerUrl();
            //Utility.Logger(TAG, "Setting : Working : Folder Path  : " + folderPath + " : Url : " + requestObject.getServerUrl() + " File Name = " + requestObject.getTitle());

            notificationManager = createNotification(requestObject.getContext(), String.valueOf(downloadId), requestObject.getLoadingText());
            final NotificationManager finalNotificationManager = notificationManager;

            final String fileName = null/*requestObject.getPictureId().replaceAll("\\s", "_")*/;
            Utility.Logger(TAG, "FileName = " + fileName);
//            downloadId = Downloader.download(serverUrl, folder.getAbsolutePath(), fileName)
//                    .build()
//                    .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                        @Override
//                        public void onStartOrResume() {
//                            acProgressFlower[0] = getDownloadProgressDialog(requestObject.getContext(), Utility.getStringFromRes(requestObject.getContext(), R.string.downloading_tagline));
//
//                            if (acProgressFlower[0] != null)
//                                acProgressFlower[0].show();
//
//                        }
//                    })
//                    .setOnPauseListener(new OnPauseListener() {
//                        @Override
//                        public void onPause() {
//
//                        }
//                    })
//                    .setOnCancelListener(new OnCancelListener() {
//                        @Override
//                        public void onCancel() {
//
//                        }
//                    })
//                    .setOnProgressListener(new OnProgressListener() {
//                        @Override
//                        public void onProgress(Progress progress) {
//                            int pro = Integer.parseInt(Long.toString((progress.currentBytes * 100) / progress.totalBytes));
//
//                        }
//                    })
//                    .start(new OnDownloadListener() {
//                        @Override
//                        public void onDownloadComplete() {
//
//                            if (finalNotificationManager != null) {
//
//
//                                String link = folderPath + "/" + fileName;
//                                Uri uri, coverUri = null;
//
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//                                    if (requestObject.isShare())
//                                        uri = FileProvider.getUriForFile(requestObject.getContext(), BuildConfig.APPLICATION_ID + ".provider", new File(link));
//                                    else
//                                        uri = Uri.fromFile(new File(link));
//
//                                } else
//                                    uri = Uri.fromFile(new File(link));
//
//                                coverUri = uri;
//                                Management management = new Management(requestObject.getContext());
//
//                                if (!requestObject.isRead()) {
//
//                                    //For read onyl purpose
//                                }
//
//                                if (acProgressFlower[0].isShowing())
//                                    acProgressFlower[0].dismiss();
//
//                                if (requestObject.getConnectionCallback() != null) {
//
//                                    //For successfully perform task
//
//                                }
//
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onError(Error error) {
//
//                            Utility.Logger(TAG, "Error : Connection = "
//                                    + error.isConnectionError() + " : Server Error = " + error.isServerError());
//
//                            if (requestObject.getConnectionCallback() != null) {
//                                requestObject.getConnectionCallback().onError(Utility.getStringFromRes(requestObject.getContext(), R.string.download_error)
//                                        , requestObject);
//                            }
//
//                        }
//                    });

            //endregion


        }


    }


    public static class CreateConnection {
        private RequestObject requestObject;

        public CreateConnection setRequestObject(RequestObject requestObject) {
            this.requestObject = requestObject;
            return this;
        }

        public ConnectionBuilder buildConnection() {
            return new ConnectionBuilder(requestObject);
        }

    }

    /**
     * <p>It is used to Create Notification
     * with Look Up action button</p>
     *
     * @param
     * @param aMessage
     * @param context
     */
    public NotificationManager createNotification(Context context, String id, String aMessage) {
        int NOTIFY_ID = Integer.parseInt(id); // ID of notification
        //String id = context.getString(R.string.default_notification_channel_id); // default_channel_id
        String title = context.getString(R.string.app_name); // Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        Utility.Logger(TAG, "Working");

        NotificationManager notifManager = null;

        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);

            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }

            builder = new NotificationCompat.Builder(context, id);
            builder.setContentTitle(context.getString(R.string.app_name) + " " + context.getString(R.string.downloading))                            // required
                    .setSmallIcon(R.drawable.app_icon)   // required
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(aMessage))
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        } else {

            builder = new NotificationCompat.Builder(context, id);
            builder.setContentTitle(context.getString(R.string.app_name) + " " + context.getString(R.string.downloading))                            // required
                    .setSmallIcon(R.drawable.app_icon)   // required
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(aMessage))
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);

        }

        //Notification notification = builder.build();
        //notifManager.notify(NOTIFY_ID, notification);

        return notifManager;
    }

    /**
     * <p>It is used to retrieve Progress Dialog object</p>
     *
     * @param context
     * @param progress
     * @return
     */
    private ACProgressFlower getACProgressFlower(Context context, String progress) {

        ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(progress)
                .textTypeface(Font.ubuntu_medium_font(context))
                .fadeColor(Color.DKGRAY).build();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;


    }


    /**
     * <p>It is used to show Downloading progress dialog</p>
     *
     * @param context
     * @return
     */
    private ProgressDialog getDownloadProgressDialog(Context context, String loadingText) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        // Set your progress dialog Title
        mProgressDialog.setTitle(loadingText);
        // Set your progress dialog Message
        mProgressDialog.setMessage(loadingText);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //mProgressDialog.setMax(100);
        return mProgressDialog;
    }


}
