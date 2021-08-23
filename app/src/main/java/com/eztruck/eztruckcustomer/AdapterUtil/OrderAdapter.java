package com.eztruck.eztruckcustomer.AdapterUtil;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eztruck.eztruckcustomer.InterfaceUtil.OrderCallback;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.EmptyObject;
import com.eztruck.eztruckcustomer.ObjectUtil.ProgressObject;
import com.eztruck.eztruckcustomer.R;


import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public class OrderAdapter extends RecyclerView.Adapter {
    private boolean isProfile;
    private int NO_DATA_VIEW = 1;
    private int ORDER_HISTORY_VIEW = 2;
    private int PROGRESS_VIEW = 3;
    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private OrderCallback orderCallback;


    public OrderAdapter(Context context, ArrayList<Object> dataArray) {
        this.context = context;
        this.dataArray = dataArray;
    }

    public OrderAdapter(Context context, ArrayList<Object> dataArray, OrderCallback orderCallback) {
        this.context = context;
        this.dataArray = dataArray;
        this.orderCallback = orderCallback;
    }


    @Override
    public int getItemViewType(int position) {

        if (dataArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (dataArray.get(position) instanceof DataObject) {
            return ORDER_HISTORY_VIEW;
        } else if (dataArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        }

        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == ORDER_HISTORY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
            viewHolder = new OrderHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProgressHolder) {

            //LookUpObject lookUpObject = (LookUpObject) dataArray.get(position);
            ProgressHolder lookUpHolder = (ProgressHolder) holder;


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyObject = (EmptyObject) dataArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyObject.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyObject.getTitle());
            emptyHolder.txtDescription.setText(emptyObject.getDescription());


        } else if (holder instanceof OrderHolder) {

            final OrderHolder orderHolder = (OrderHolder) holder;
            DataObject dataObject = (DataObject) dataArray.get(position);

            orderHolder.txtFromAddress.setText(dataObject.getHistory_from_address());
            orderHolder.txtToAddress.setText(dataObject.getHistory_to_address());
            orderHolder.txtDateTime.setText(dataObject.getRide_date_time());
            orderHolder.txtPrice.setText(dataObject.getRide_fare());

            orderHolder.layoutSelection.setTag(position);
            orderHolder.layoutSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) orderHolder.layoutSelection.getTag();
                    if (orderCallback !=null){
                        orderCallback.onOrderClickListener(pos);
                    }
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private ImageView imageIcon;
        private TextView txtTitle;
        private TextView txtDescription;
        private LinearLayout layoutContainer;

        public EmptyHolder(View view) {
            super(view);

            imageIcon = view.findViewById(R.id.image_icon);
            txtTitle = view.findViewById(R.id.txt_title);
            txtDescription = view.findViewById(R.id.txt_description);
            layoutContainer = view.findViewById(R.id.layout_container);
        }
    }

    protected class OrderHolder extends RecyclerView.ViewHolder {
        private TextView txtDateTime;
        private TextView txtPrice;
        private TextView txtFromAddress;
        private TextView txtToAddress;
        private LinearLayout layoutSelection;

        public OrderHolder(View view) {
            super(view);

            txtDateTime = view.findViewById(R.id.txt_date_time);
            txtPrice = view.findViewById(R.id.txt_price);
            txtFromAddress = view.findViewById(R.id.txt_from_address);
            txtToAddress = view.findViewById(R.id.txt_to_address);
            layoutSelection = view.findViewById(R.id.layout_selection);

        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = view.findViewById(R.id.progressView);
        }

    }

}
