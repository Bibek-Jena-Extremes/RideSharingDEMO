package com.eztruck.eztruckcustomer.ActivityUtil;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.eztruck.eztruckcustomer.AdapterUtil.ChattingAdapter;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.ManagementUtil.Management;
import com.eztruck.eztruckcustomer.ObjectUtil.ChattingObject;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.DateTimeObject;
import com.eztruck.eztruckcustomer.ObjectUtil.PrefObject;
import com.eztruck.eztruckcustomer.ObjectUtil.RiderObject;
import com.eztruck.eztruckcustomer.ObjectUtil.TypingObject;
import com.eztruck.eztruckcustomer.ObjectUtil.UserObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class Chatting extends AppCompatActivity implements View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {
    private String TAG = Chatting.class.getName();
    private TextView txtMenu;
    private TextView txtTyping;
    private ImageView imageBack;
    private Management management;
    private PrefObject prefObject;
    public DataObject chattingDetail;
    private UserObject userObject;
    private RiderObject riderObject;
    private RecyclerView recyclerViewChatting;
    private LinearLayoutManager linearLayoutManager;
    private ChattingAdapter chattingAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private HashMap<String, Object> dataHashMap = new HashMap<>();
    private LinearLayout layoutSend;
    private EditText editChat;
    private DatabaseReference chatRef;
    private DatabaseReference statusRef;
    private ValueEventListener chatEventListener;
    private ChildEventListener chatChildEventListener;
    private ValueEventListener statusEventListener;
    private DatabaseReference rootReference;
    private String orderId;
    private boolean isStartTyping = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI


    }

    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {
        orderId = getIntent().getStringExtra(Constant.IntentKey.RESTAURANT_DETAIL);
        userObject = getIntent().getParcelableExtra(Constant.IntentKey.USER);
        riderObject = getIntent().getParcelableExtra(Constant.IntentKey.RIDER);
    }


    /**
     * <p>It is used to initialize UI</p>
     */
    private void initUI() {

        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true)
                .setRetrieveLogin(true));


        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.chatting));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        layoutSend = findViewById(R.id.layout_send);
        editChat = findViewById(R.id.edit_chat);
        txtTyping = findViewById(R.id.txt_typing);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewChatting = findViewById(R.id.recycler_view_chatting);
        recyclerViewChatting.setLayoutManager(linearLayoutManager);

        chattingAdapter = new ChattingAdapter(this, objectArrayList);
        recyclerViewChatting.setAdapter(chattingAdapter);

        rootReference = FirebaseDatabase.getInstance().getReference();

        chatRef = rootReference.limitToFirst(100).getRef().child("order").child(orderId).child("userChattingObject");
        chatEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "ChatEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                if (dataSnapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    for (String key : dataMap.keySet()) {

                        Utility.Logger(TAG, "Key = " + key);
                        Object data = dataMap.get(key);
                        HashMap<String, Object> userData = (HashMap<String, Object>) data;

                        DataObject chattingObject = new DataObject()
                                .setChatting((String) userData.get("message"))
                                .setDate((String) userData.get("date"))
                                .setTime((String) userData.get("time"))
                                .setFrom((Boolean) userData.get("from"))
                                .setPicture((Boolean) userData.get("from") ? userObject.getUser_picture() : riderObject.getUser_picture())
                                .setDataType((Boolean) userData.get("from") ? Constant.DATA_TYPE.FROM_CHAT : Constant.DATA_TYPE.TO_CHAT);

                        if (!dataHashMap.containsKey(key)) {
                            objectArrayList.add(chattingObject);
                            dataHashMap.put(key, chattingObject);
                        }


                    }
                    chattingAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in LocationEvent = " + databaseError.toException());
            }
        };
        chatChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChattingObject chattingObject = dataSnapshot.getValue(ChattingObject.class);
                Utility.Logger(TAG, "Chatting Data = " + chattingObject.toString());


                objectArrayList.add(new DataObject()
                        .setChatting(chattingObject.message)
                        .setFrom(chattingObject.from)
                        .setTime(chattingObject.time)
                        .setPicture(chattingObject.from ? userObject.getUser_picture() : riderObject.getUser_picture())
                        .setDataType(chattingObject.from ? Constant.DATA_TYPE.FROM_CHAT : Constant.DATA_TYPE.TO_CHAT));

                chattingAdapter.notifyDataSetChanged();
                recyclerViewChatting.scrollToPosition((objectArrayList.size() - 1));

                dataSnapshot.child("read").getRef().setValue(false);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        //chatRef.addValueEventListener(chatEventListener);
        chatRef.addChildEventListener(chatChildEventListener);

        statusRef = rootReference.child("order").child(orderId).child("typingObject");
        statusEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "LocationEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                TypingObject value = dataSnapshot.getValue(TypingObject.class);
                if (value.isTo()) {
                    txtTyping.setText(riderObject.getUser_name() + " " + Utility.getStringFromRes(getApplicationContext(), R.string.typing_tagline));
                    txtTyping.setVisibility(View.VISIBLE);
                } else {
                    txtTyping.setVisibility(View.GONE);
                }
                Utility.Logger(TAG, "Status = " + value.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in LocationEvent = " + databaseError.toException());
            }
        };
        statusRef.addValueEventListener(statusEventListener);

        layoutSend.setOnClickListener(this);
        editChat.addTextChangedListener(this);
        editChat.setOnEditorActionListener(this);
        imageBack.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }

        if (v == layoutSend) {

            DateTimeObject dateTimeObject = Utility.parseTimeDate(new DateTimeObject()
                    .setDatetimeType(Constant.DATETIME.BOTH12)
                    .setCurrentDate(true));

            chatRef.push().setValue(new ChattingObject((objectArrayList.size() + 1), editChat.getText().toString(), dateTimeObject.getTime(), dateTimeObject.getDate(), true, true));
            editChat.setText(null);

        }

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isStartTyping) {
            //statusRef.child("from").setValue(true);
            isStartTyping = false;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        ///statusRef.child("from").setValue(false);
        isStartTyping = true;
        if (s.length() > 1)
            statusRef.child("from").setValue(true);
        else
            statusRef.child("from").setValue(false);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (event == null || !event.isShiftPressed()) {
                // the user is done typing.
                Utility.Logger(TAG, "Finish Typing...");

                return true; // consume.
            }
        }
        return false; // pass on to other listeners.
    }
}
