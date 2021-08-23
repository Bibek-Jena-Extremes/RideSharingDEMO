package com.eztruck.eztruckcustomer.AdapterUtil;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eztruck.eztruckcustomer.DateUtil.DateBuilder;
import com.eztruck.eztruckcustomer.DateUtil.DateConstraint;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.EmptyObject;
import com.eztruck.eztruckcustomer.ObjectUtil.ProgressObject;
import com.eztruck.eztruckcustomer.R;


import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public class ScheduleAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int PROGRESS_VIEW = 2;
    private int SCHEDULE_VIEW = 3;
    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();


    public ScheduleAdapter(Context context, ArrayList<Object> dataArray) {
        this.context = context;
        this.dataArray = dataArray;
    }

    @Override
    public int getItemViewType(int position) {


        if (dataArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (dataArray.get(position) instanceof DataObject) {
            return SCHEDULE_VIEW;
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

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == SCHEDULE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item_layout, parent, false);
            viewHolder = new ScheduleHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final StaggeredGridLayoutManager.LayoutParams layoutParams =
                new StaggeredGridLayoutManager.LayoutParams(
                        holder.itemView.getLayoutParams());

        if (holder instanceof ProgressHolder) {

            ProgressHolder lookUpHolder = (ProgressHolder) holder;
            layoutParams.setFullSpan(true);

        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyState = (EmptyObject) dataArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyState.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyState.getTitle());
            emptyHolder.txtDescription.setText(emptyState.getDescription());

            layoutParams.setFullSpan(true);

        } else if (holder instanceof ScheduleHolder) {

            DataObject dataObject = (DataObject) dataArray.get(position);
            final ScheduleHolder scheduleHolder = (ScheduleHolder) holder;

            String day = new DateBuilder()
                    .setDateTimAction(DateConstraint.DateTimAction.FIND_DATE_TIME)
                    .setDateTimeConstraint(DateConstraint.DateTimeConstraint.PARSE_DAY)
                    .setDateTimeFormatConstraint(DateConstraint.DateTimeFormatConstraint.eeee)
                    .setGivenDateTime(dataObject.getDay())
                    .setCustomFormat("EEE")
                    .buildDateTime();

            scheduleHolder.txtDay.setText(day);

            String fromResult = new DateBuilder()
                    .setDateTimAction(DateConstraint.DateTimAction.FIND_DATE_TIME)
                    .setDateTimeConstraint(DateConstraint.DateTimeConstraint.PARSE_TIME)
                    .setDateTimeFormatConstraint(DateConstraint.DateTimeFormatConstraint.hh_mm)
                    .setGivenDateTime(dataObject.getFromTime())
                    .setCustomFormat("hh:mm a")
                    .buildDateTime();

            scheduleHolder.txtFromTime.setText(fromResult);

            String toResult = new DateBuilder()
                    .setDateTimAction(DateConstraint.DateTimAction.FIND_DATE_TIME)
                    .setDateTimeConstraint(DateConstraint.DateTimeConstraint.PARSE_TIME)
                    .setDateTimeFormatConstraint(DateConstraint.DateTimeFormatConstraint.hh_mm)
                    .setGivenDateTime(dataObject.getToTime())
                    .setCustomFormat("hh:mm a")
                    .buildDateTime();

            scheduleHolder.txtToTime.setText(toResult);

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

        public EmptyHolder(View view) {
            super(view);

            imageIcon = view.findViewById(R.id.image_icon);
            txtTitle = view.findViewById(R.id.txt_title);
            txtDescription = view.findViewById(R.id.txt_description);
        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = view.findViewById(R.id.progressView);
        }

    }

    protected class ScheduleHolder extends RecyclerView.ViewHolder {
        private TextView txtDay;
        private TextView txtFromTime;
        private TextView txtToTime;

        public ScheduleHolder(View view) {
            super(view);

            txtDay = view.findViewById(R.id.txt_day);
            txtFromTime = view.findViewById(R.id.txt_from_time);
            txtToTime = view.findViewById(R.id.txt_to_time);
        }
    }

}
