package com.androdu.bananaSeller.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.response.notifications.Notification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_NAME;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ARABIC;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ENGLISH;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final Activity activity;
    private final List<Notification> modelList;
    private OnItemClickListener mItemClickListener;
    private Integer todayPosition = -1;
    private Integer earlierPosition = -1;


    public NotificationAdapter(Activity activity, List<Notification> modelList) {
        this.activity = activity;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notifications_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            final Notification model = getItem(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            if (getLanguagePref(activity).equals(LANGUAGE_KEY_ENGLISH)) {
                viewHolder.notificationItemTvTitle.setText(model.getNotificationDetails().getTitleEn());
                viewHolder.notificationItemTvBody.setText(model.getNotificationDetails().getBodyEn());
            } else if (getLanguagePref(activity).equals(LANGUAGE_KEY_ARABIC)){
                viewHolder.notificationItemTvTitle.setText(model.getNotificationDetails().getTitleAr());
                viewHolder.notificationItemTvBody.setText(model.getNotificationDetails().getBodyAr());
            }else{
                viewHolder.notificationItemTvTitle.setText(model.getNotificationDetails().getTitleUr());
                viewHolder.notificationItemTvBody.setText(model.getNotificationDetails().getBodyUr());
            }

            viewHolder.notificationItemTvDateTime.setText(getTimeBetween(Long.valueOf(model.getDate()), position));
            if (todayPosition == position) {
                viewHolder.notificationItemTvPositionType.setVisibility(View.VISIBLE);
                viewHolder.notificationItemTvPositionType.setText(activity.getString(R.string.today));
            } else if (earlierPosition == position) {
                viewHolder.notificationItemTvPositionType.setVisibility(View.VISIBLE);
                viewHolder.notificationItemTvPositionType.setText(activity.getString(R.string.earlier));
            } else
                viewHolder.notificationItemTvPositionType.setVisibility(View.GONE);
        }
    }

    private String getTimeBetween(Long notificationTime, int currentPosition) {
        Date cal1 = new Date(notificationTime);
        Date cal2 = new Date(System.currentTimeMillis());


        long diff = cal2.getTime() - cal1.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (todayPosition == -1 && DateUtils.isToday(notificationTime)) {
            todayPosition = currentPosition;
        }

        if (earlierPosition == -1 && !DateUtils.isToday(notificationTime)) {
            earlierPosition = currentPosition;
        }


        if (seconds <= 60) {
            if (getLanguagePref(activity).equals(LANGUAGE_KEY_ENGLISH))
                return seconds + " " + activity.getString(R.string.seconds_ago);
            else
                return activity.getString(R.string.ago) + " " + seconds + " " + activity.getString(R.string.seconds_ago);

        } else if (minutes <= 60) {
            if (getLanguagePref(activity).equals(LANGUAGE_KEY_ENGLISH))
                return minutes + " " + activity.getString(R.string.minutes_ago);
            else
                return activity.getString(R.string.ago) + " " + minutes + " " + activity.getString(R.string.minutes_ago);

        } else if (hours <= 24) {
            if (getLanguagePref(activity).equals(LANGUAGE_KEY_ENGLISH))
                return hours + " " + activity.getString(R.string.hours_ago);
            else
                return activity.getString(R.string.ago) + " " + hours + " " + activity.getString(R.string.hours_ago);

        } else if (days <= 7) {
            if (getLanguagePref(activity).equals(LANGUAGE_KEY_ENGLISH))
                return days + " " + activity.getString(R.string.days_ago);
            else
                return activity.getString(R.string.ago) + " " + days + " " + activity.getString(R.string.days_ago);

        } else {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(notificationTime);
            return formatter.format(calendar.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface OnItemClickListener {
        void onClick(int position, Notification model);
    }

    private Notification getItem(int position) {
        return modelList.get(position);
    }

    @SuppressLint("NonConstantResourceId")
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_item_tv_title)
        TextView notificationItemTvTitle;
        @BindView(R.id.notification_item_tv_body)
        TextView notificationItemTvBody;
        @BindView(R.id.notification_item_tv_date_time)
        TextView notificationItemTvDateTime;
        @BindView(R.id.notification_item_tv_position_type)
        TextView notificationItemTvPositionType;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> mItemClickListener.onClick(getAdapterPosition(), modelList.get(getAdapterPosition())));
        }

    }
}
