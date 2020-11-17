package de.deeagle.ubt.plato.counterview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    public static final String TAG = LocationAdapter.class.getSimpleName();

    private ArrayList<LocationItem> locations = new ArrayList<>();
    private Context context;

    public LocationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.counter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationItem location = locations.get(position);
        Log.d(TAG, String.format("onBindViewHolder: got location <%s> on position <%d>", location.getName(), position));
        holder.getTxtTitle().setText(location.getName());
        holder.setCurrentPercentMsg(location.getCurrentPercent());
        holder.getParent().setOnClickListener((v) -> {
            Intent intent = new Intent(context, WebsiteActivity.class);
            intent.putExtra(WebsiteActivity.TRANSFER_KEY_URL_NAME, "https://ub-plato.uni-trier.de/visitorDisplay.php");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void setLocations(ArrayList<LocationItem> locations) {
        Log.d(TAG, String.format("setLocations: new list with %d elements.", locations.size()));
        this.locations = locations;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView curPercent;
        private CardView parent;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            curPercent = itemView.findViewById(R.id.txtCurPercent);
            parent = itemView.findViewById(R.id.parent);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public TextView getCurPercent() {
            return curPercent;
        }

        public CardView getParent() {
            return parent;
        }

        public void setCurrentPercentMsg(int currentPercent) {
            curPercent.setText(String.format("Current workload is %d%%", currentPercent));
            progressBar.setProgress(currentPercent);
            setProgressBarColor(currentPercent);
        }

        private void setProgressBarColor(int currentPercent) {
            if (currentPercent > 80) {
                progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            } else if (currentPercent > 50) {
                progressBar.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
            } else {
                progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            }
        }
    }
}
