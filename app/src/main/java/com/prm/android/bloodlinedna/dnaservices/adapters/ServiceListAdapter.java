package com.prm.android.bloodlinedna.dnaservices.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.prm.android.bloodlinedna.R;
import com.prm.android.bloodlinedna.dnaservices.listener.OnServiceClickListener;
import com.prm.android.bloodlinedna.models.services.DnaService;

import java.util.Locale;

public class ServiceListAdapter extends ListAdapter<DnaService, ServiceListAdapter.ServiceViewHolder> {

    private OnServiceClickListener onServiceClickListener;
    public ServiceListAdapter(OnServiceClickListener onServiceClickListener) {
        super(DIFF_CALLBACK);
        this.onServiceClickListener = onServiceClickListener;
    }

    private static final DiffUtil.ItemCallback<DnaService> DIFF_CALLBACK = new DiffUtil.ItemCallback<DnaService>() {
        @Override
        public boolean areItemsTheSame(@NonNull DnaService oldItem, @NonNull DnaService newItem) {
            return oldItem.getId() == newItem.getId(); // Compare unique ID
        }

        @Override
        public boolean areContentsTheSame(@NonNull DnaService oldItem, @NonNull DnaService newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_card, parent, false);

        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        DnaService service = getItem(position);
        holder.bind(service);
        holder.btnAction.setOnClickListener(v -> {
            onServiceClickListener.onServiceClick(service);
        });
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvSubtitle;
        private final TextView tvAccuracy;
        private final TextView tvPrice;
        private final TextView tvCurrency;
        public final MaterialButton btnAction;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
            tvAccuracy = itemView.findViewById(R.id.tvAccuracy);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCurrency = itemView.findViewById(R.id.tvCurrency);
            btnAction = itemView.findViewById(R.id.btnAction);
        }

        @SuppressLint("SetTextI18n")
        public void bind(DnaService service) {
            tvTitle.setText(service.getName());
            tvSubtitle.setText(service.getDescription());

            tvAccuracy.setText("99% Chính xác");
//            if (service.getAccuracy() != null && !service.getAccuracy().isEmpty()) {
//                tvAccuracy.setText(service.getAccuracy());
//            } else {
//                tvAccuracy.setVisibility(View.GONE);
//            }

            tvPrice.setText(String.format(Locale.getDefault(), "%,.0f", service.getBasePrice()));
            tvCurrency.setText("VND");

            btnAction.setOnClickListener(v -> {
                // TODO: handle click, e.g., navigate or show details
            });
        }
    }
}
