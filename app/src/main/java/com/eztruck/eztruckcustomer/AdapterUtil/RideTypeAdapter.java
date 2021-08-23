package com.eztruck.eztruckcustomer.AdapterUtil;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eztruck.eztruckcustomer.ConstantUtil.Constant;
import com.eztruck.eztruckcustomer.ObjectUtil.DataObject;
import com.eztruck.eztruckcustomer.ObjectUtil.EmptyObject;
import com.eztruck.eztruckcustomer.ObjectUtil.ProgressObject;
import com.eztruck.eztruckcustomer.R;
import com.eztruck.eztruckcustomer.Utility.Utility;


import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class RideTypeAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int PROGRESS_VIEW = 2;
    private int RIDE_TYPE_VIEW = 3;
    private int CARD_TYPE_VIEW = 4;
    private int CARD_DETAIL_VIEW = 5;
    private int ADD_CARD_VIEW = 6;
    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();


    public RideTypeAdapter(Context context, ArrayList<Object> dataArray) {
        this.context = context;
        this.dataArray = dataArray;

    }

    @Override
    public int getItemViewType(int position) {


        if (dataArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (dataArray.get(position) instanceof DataObject) {

            DataObject dataObject = (DataObject) dataArray.get(position);

            if (dataObject.getDataType() == Constant.DATA_TYPE.RIDE_TYPE)
                return RIDE_TYPE_VIEW;
            else if (dataObject.getDataType() == Constant.DATA_TYPE.CARD_TYPE)
                return CARD_TYPE_VIEW;
            else if (dataObject.getDataType() == Constant.DATA_TYPE.CARD_DETAIL)
                return CARD_DETAIL_VIEW;
            else if (dataObject.getDataType() == Constant.DATA_TYPE.ADD_CARD)
                return ADD_CARD_VIEW;

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

        } else if (viewType == RIDE_TYPE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rider_type_item_layout, parent, false);
            viewHolder = new RideTypeHolder(view);

        } else if (viewType == CARD_TYPE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_type_item_layout, parent, false);
            viewHolder = new CardTypeHolder(view);

        } else if (viewType == CARD_DETAIL_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_type_item_layout, parent, false);
            viewHolder = new CardDetailHolder(view);

        } else if (viewType == ADD_CARD_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_card_item_layout, parent, false);
            viewHolder = new AddCardHolder(view);

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

        } else if (holder instanceof RideTypeHolder) {

            DataObject rideTypeObject = (DataObject) dataArray.get(position);
            final RideTypeHolder rideTypeHolder = (RideTypeHolder) holder;

            rideTypeHolder.txtRideType.setText(rideTypeObject.getRide_type_name());
            rideTypeHolder.txtRideTagline.setText(rideTypeObject.getRide_type_tagline());

            Glide.with(context).load(Constant.ServerInformation.PICTURE_URL + rideTypeObject.getRide_type_image())
                    .into(rideTypeHolder.imageRideType);

            if (rideTypeObject.isSelected_ride_type()) {
                rideTypeHolder.txtSelection.setVisibility(View.VISIBLE);
            } else
                rideTypeHolder.txtSelection.setVisibility(View.GONE);

            rideTypeHolder.txtRideType.setTag(position);
            rideTypeHolder.layoutRideType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) rideTypeHolder.txtRideType.getTag();
                    onRideTypeSelectionListener(pos);
                }
            });


        } else if (holder instanceof CardTypeHolder) {

            DataObject rideTypeObject = (DataObject) dataArray.get(position);
            final CardTypeHolder cardTypeHolder = (CardTypeHolder) holder;

            cardTypeHolder.txtRideType.setText(rideTypeObject.getPayment_type_name());
            cardTypeHolder.txtRideTagline.setVisibility(View.GONE);


            if (!rideTypeObject.isSelected_payment_type()) {

                cardTypeHolder.imageArrow.setVisibility(View.VISIBLE);
                cardTypeHolder.layoutRideType.setBackgroundColor(Utility.getAttrColor(context, R.attr.colorBackgroundLight));

            } else {

                if (rideTypeObject.isSelected_payment()) {
                    cardTypeHolder.txtSelection.setVisibility(View.VISIBLE);
                } else
                    cardTypeHolder.txtSelection.setVisibility(View.GONE);


                Glide.with(context).load(Constant.ServerInformation.PICTURE_URL + rideTypeObject.getPayment_type_picture())
                        .into(cardTypeHolder.imageRideType);

            }

            cardTypeHolder.txtRideType.setTag(position);
            cardTypeHolder.layoutRideType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) cardTypeHolder.txtRideType.getTag();
                    if (((DataObject) dataArray.get(pos)).isSelected_payment_type()) {
                        onRideTypeSelectionListener(pos);
                    }

                }
            });


        } else if (holder instanceof CardDetailHolder) {

            DataObject rideTypeObject = (DataObject) dataArray.get(position);
            final CardDetailHolder cardTypeHolder = (CardDetailHolder) holder;

            cardTypeHolder.txtRideType.setText(Utility.maskSomeCharacter(rideTypeObject.getPayment_card_no()));
            cardTypeHolder.txtRideTagline.setText(rideTypeObject.getPayment_expiry_date());

            if (!Utility.isEmptyString(rideTypeObject.getPayment_type_picture()))
            Glide.with(context).load(Constant.ServerInformation.PICTURE_URL + rideTypeObject.getPayment_type_picture())
                    .into(cardTypeHolder.imageRideType);
            else
                cardTypeHolder.imageRideType.setImageResource(R.drawable.ic_card);

            if (rideTypeObject.isSelected_payment()) {
                cardTypeHolder.txtSelection.setVisibility(View.VISIBLE);
            } else
                cardTypeHolder.txtSelection.setVisibility(View.GONE);

            cardTypeHolder.txtRideType.setTag(position);
            cardTypeHolder.layoutRideType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) cardTypeHolder.txtRideType.getTag();
                    onRideTypeSelectionListener(pos);
                }
            });


        } else if (holder instanceof AddCardHolder) {

            final AddCardHolder addCardHolder = (AddCardHolder) holder;

            addCardHolder.layoutAddCard.setTag(position);
            addCardHolder.layoutAddCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) addCardHolder.layoutAddCard.getTag();
                    onRideTypeSelectionListener(pos);
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }

    public abstract void onRideTypeSelectionListener(int position);


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

    protected class RideTypeHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutRideType;
        private TextView txtSelection;
        private ImageView imageRideType;
        private TextView txtRideType;
        private TextView txtRideTagline;

        public RideTypeHolder(View view) {
            super(view);

            layoutRideType = view.findViewById(R.id.layout_ride_type);
            txtSelection = view.findViewById(R.id.txt_selection);
            imageRideType = view.findViewById(R.id.image_ride_type);
            txtRideType = view.findViewById(R.id.txt_ride_type);
            txtRideTagline = view.findViewById(R.id.txt_ride_tagline);

        }
    }

    protected class CardTypeHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutRideType;
        private LinearLayout txtSelection;
        private ImageView imageRideType;
        private TextView txtRideType;
        private TextView txtRideTagline;
        private ImageView imageArrow;

        public CardTypeHolder(View view) {
            super(view);

            layoutRideType = view.findViewById(R.id.layout_ride_type);
            txtSelection = view.findViewById(R.id.layout_selection);
            imageRideType = view.findViewById(R.id.image_ride_type);
            txtRideType = view.findViewById(R.id.txt_ride_type);
            txtRideTagline = view.findViewById(R.id.txt_ride_tagline);
            imageArrow = view.findViewById(R.id.image_arrow);

        }
    }

    protected class CardDetailHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutRideType;
        private LinearLayout txtSelection;
        private ImageView imageRideType;
        private TextView txtRideType;
        private TextView txtRideTagline;
        private ImageView imageArrow;

        public CardDetailHolder(View view) {
            super(view);

            layoutRideType = view.findViewById(R.id.layout_ride_type);
            txtSelection = view.findViewById(R.id.layout_selection);
            imageRideType = view.findViewById(R.id.image_ride_type);
            txtRideType = view.findViewById(R.id.txt_ride_type);
            txtRideTagline = view.findViewById(R.id.txt_ride_tagline);
            imageArrow = view.findViewById(R.id.image_arrow);

        }
    }


    protected class AddCardHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutAddCard;


        public AddCardHolder(View view) {
            super(view);

            layoutAddCard = view.findViewById(R.id.layout_add_card);


        }
    }


}
