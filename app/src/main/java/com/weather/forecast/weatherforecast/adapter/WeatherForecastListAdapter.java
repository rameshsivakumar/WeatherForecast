package com.weather.forecast.weatherforecast.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weather.forecast.R;
import com.weather.forecast.application.constants.Constants;
import com.weather.forecast.utils.DateUtils;
import com.weather.forecast.weatherforecast.model.WeatherForecastResponse;
import com.weather.forecast.utils.WeatherUtils;
import com.squareup.picasso.Picasso;

/**
 * Provide views to RecyclerView with data from WeatherForecastListResponse.
 */
public class WeatherForecastListAdapter extends RecyclerView.Adapter<WeatherForecastListAdapter.ViewHolder> {

    private WeatherForecastCallback mCallback;
    private Context mContext;
    private WeatherForecastResponse mWeatherForecastResponse;
    private boolean mUseTodayLayout;


    public interface WeatherForecastCallback {
        void selectedForecastItem(WeatherForecastResponse.WeatherData weatherData);
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView iconView;
        final TextView dateView;
        final TextView descriptionView;
        final TextView highTempView;
        final TextView lowTempView;

        ViewHolder(View view) {
            super(view);

            iconView = (ImageView) view.findViewById(R.id.weather_icon);
            dateView = (TextView) view.findViewById(R.id.date);
            descriptionView = (TextView) view.findViewById(R.id.weather_description);
            highTempView = (TextView) view.findViewById(R.id.high_temperature);
            lowTempView = (TextView) view.findViewById(R.id.low_temperature);

            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click. We fetch the date that has been
         * selected, and then call the onClick handler registered with this adapter, passing that
         * date.
         *
         * @param v the View that was clicked
         */
        @Override
        public void onClick(View v) {
            mCallback.selectedForecastItem(mWeatherForecastResponse.getWeatherList().get(getAdapterPosition()));
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param callback to give callback when user clicks on list item.
     */
    public WeatherForecastListAdapter(Context context, WeatherForecastCallback callback, WeatherForecastResponse weatherForecastResponse) {
        this.mContext = context;
        this.mCallback = callback;
        this.mWeatherForecastResponse = weatherForecastResponse;
        mUseTodayLayout = mContext.getResources().getBoolean(R.bool.use_today_layout);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutId;

        switch (viewType) {
            case Constants.VIEW_TYPE_TODAY: {
                layoutId = R.layout.weather_forecast_list_item_today;
                break;
            }

            case Constants.VIEW_TYPE_FUTURE_DAY: {
                layoutId = R.layout.weather_forecast_list_item;
                break;
            }

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }

        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutId, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        int weatherId = mWeatherForecastResponse.getWeatherList().get(position).getWeather().get(0).getId();
        int weatherImageId;

        int viewType = getItemViewType(position);

        switch (viewType) {
            case Constants.VIEW_TYPE_TODAY:
                weatherImageId = WeatherUtils
                        .getLargeArtResourceIdForWeatherCondition(weatherId);
                viewHolder.iconView.setImageResource(weatherImageId);
                break;

            case Constants.VIEW_TYPE_FUTURE_DAY:
                weatherImageId = WeatherUtils
                        .getSmallArtResourceIdForWeatherCondition(weatherId);
                Picasso.get().load(weatherImageId).into(viewHolder.iconView);
                break;

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }

        long dateInMillis = mWeatherForecastResponse.getWeatherList().get(position).getDt();
        /* Get human readable string using our utility method */
        String dateString = DateUtils.getFriendlyDateString(mContext, dateInMillis, false);

        /* Display friendly date string */
        viewHolder.dateView.setText(dateString);

        String description = WeatherUtils.getStringForWeatherCondition(mContext, weatherId);
        /* Create the accessibility (a11y) String from the weather description */
        String descriptionA11y = mContext.getString(R.string.a11y_forecast, description);

        /* Set the text and content description (for accessibility purposes) */
        viewHolder.descriptionView.setText(description);
        viewHolder.descriptionView.setContentDescription(descriptionA11y);

        double highInCelsius = mWeatherForecastResponse.getWeatherList().get(position).getTemp().getMax();

        String highString = WeatherUtils.formatTemperature(mContext, highInCelsius);
        /* Create the accessibility (a11y) String from the weather description */
        String highA11y = mContext.getString(R.string.a11y_high_temp, highString);

        viewHolder.highTempView.setText(highString);
        viewHolder.highTempView.setContentDescription(highA11y);

        double lowInCelsius = mWeatherForecastResponse.getWeatherList().get(position).getTemp().getMin();

        String lowString = WeatherUtils.formatTemperature(mContext, lowInCelsius);
        String lowA11y = mContext.getString(R.string.a11y_low_temp, lowString);

        /* Set the text and content description (for accessibility purposes) */
        viewHolder.lowTempView.setText(lowString);
        viewHolder.lowTempView.setContentDescription(lowA11y);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mWeatherForecastResponse.getWeatherList().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mUseTodayLayout && position == 0) {
            return Constants.VIEW_TYPE_TODAY;
        } else {
            return Constants.VIEW_TYPE_FUTURE_DAY;
        }
    }
}
