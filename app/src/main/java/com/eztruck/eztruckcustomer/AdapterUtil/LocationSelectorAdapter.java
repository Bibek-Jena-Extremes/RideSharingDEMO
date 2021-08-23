package com.eztruck.eztruckcustomer.AdapterUtil;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.InterfaceUtil.LocationCallback;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.EmptyObject;
import com.eztruck.eztruckcustomer.ObjectUtil.ProgressObject;
import com.eztruck.eztruckcustomer.R;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public class LocationSelectorAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int LOCATION_VIEW = 2;
    private int LABEL_VIEW = 3;
    private int PROGRESS_VIEW = 4;
    private int CITY_VIEW = 5;
    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private LocationCallback locationCallback;


    public LocationSelectorAdapter(Context context, ArrayList<Object> dataArray) {
        this.context = context;
        this.dataArray = dataArray;
    }

    public LocationSelectorAdapter(Context context, ArrayList<Object> dataArray, LocationCallback locationCallback) {
        this.context = context;
        this.dataArray = dataArray;
        this.locationCallback = locationCallback;
    }


    @Override
    public int getItemViewType(int position) {

        if (dataArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (dataArray.get(position) instanceof DataObject) {

            DataObject dataObject = (DataObject) dataArray.get(position);

            if (dataObject.getDataType() == Constant.DATA_TYPE.LABEL_VIEW)
                return LABEL_VIEW;
            else if (dataObject.getDataType() == Constant.DATA_TYPE.LOCATION_SELECTOR_VIEW
                    || dataObject.getDataType() == Constant.DATA_TYPE.AUTO_COMPLETE_VIEW)
                return LOCATION_VIEW;
            else if (dataObject.getDataType() == Constant.DATA_TYPE.CITY_VIEW)
                return CITY_VIEW;

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

        } else if (viewType == LOCATION_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_layout, parent, false);
            viewHolder = new LocationHolder(view);

        } else if (viewType == LABEL_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.label_item_layout, parent, false);
            viewHolder = new LabelHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == CITY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_layout, parent, false);
            viewHolder = new CityHolder(view);

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


        } else if (holder instanceof LocationHolder) {


            final LocationHolder locationHolder = (LocationHolder) holder;
            DataObject dataObject = (DataObject) dataArray.get(position);

            locationHolder.txtFrom.setText(dataObject.getLocation_name());
            locationHolder.txtFromAddress.setText(dataObject.getLocation_Tagline());
            locationHolder.txtFrom.setTag(position);

            locationHolder.layoutLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) locationHolder.txtFrom.getTag();
                    if (locationCallback != null) {
                        locationCallback.onLocationSelectedListener(pos);
                    }
                }
            });


        } else if (holder instanceof LabelHolder) {

            LabelHolder labelHolder = (LabelHolder) holder;
            DataObject dataObject = (DataObject) dataArray.get(position);

            labelHolder.txtLabel.setText(dataObject.getLocation_label());

        } else if (holder instanceof CityHolder) {

            final CityHolder cityHolder = (CityHolder) holder;
            DataObject dataObject = (DataObject) dataArray.get(position);

            cityHolder.txtCity.setText(dataObject.getCity_name());
            cityHolder.txtCity.setTag(position);

            if (dataObject.isLocationSelected()){
                cityHolder.imageDone.setVisibility(View.VISIBLE);
            }else {
                cityHolder.imageDone.setVisibility(View.GONE);
            }

            cityHolder.layoutLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) cityHolder.txtCity.getTag();
                    if (locationCallback != null) {
                        locationCallback.onLocationSelectedListener(pos);
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

    protected class LocationHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutFrom;
        private RelativeLayout layoutLocation;
        private TextView txtFrom;
        private TextView txtFromAddress;

        public LocationHolder(View view) {
            super(view);

            layoutFrom = view.findViewById(R.id.layout_from);
            txtFrom = view.findViewById(R.id.txt_from);
            txtFromAddress = view.findViewById(R.id.txt_from_address);
            layoutLocation = view.findViewById(R.id.layout_location);

        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = view.findViewById(R.id.progressView);
        }

    }


    protected class CityHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutLocation;
        private RelativeLayout layoutFrom;
        private TextView txtCity;
        private ImageView imageDone;

        public CityHolder(View view) {
            super(view);

            layoutLocation = view.findViewById(R.id.layout_location);
            layoutFrom = view.findViewById(R.id.layout_from);
            txtCity = view.findViewById(R.id.txt_city);
            imageDone = view.findViewById(R.id.image_done);

        }

    }


    protected class LabelHolder extends RecyclerView.ViewHolder {
        private TextView txtLabel;

        public LabelHolder(View view) {
            super(view);
            txtLabel = view.findViewById(R.id.txt_label);
        }
    }


}
