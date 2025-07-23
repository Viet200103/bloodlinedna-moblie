package com.prm.android.bloodlinedna.dnaservices.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prm.android.bloodlinedna.R;
import com.prm.android.bloodlinedna.dnaservices.listener.OnServiceClickListener;
import com.prm.android.bloodlinedna.dnaservices.listener.ServiceListCallback;
import com.prm.android.bloodlinedna.dnaservices.adapters.ServiceListAdapter;
import com.prm.android.bloodlinedna.dnaservices.ServiceViewModel;
import com.prm.android.bloodlinedna.models.services.DnaService;
import com.prm.android.bloodlinedna.models.services.ServiceApiResponse;

import java.util.concurrent.atomic.AtomicInteger;

public class DnaServiceHomeFragment extends Fragment implements ServiceListCallback, OnServiceClickListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private ServiceViewModel serviceViewModel;

    private ServiceListAdapter serviceListAdapter;

    private MutableLiveData<Boolean> loading = new MutableLiveData<>(true);
    private String searchQuery = null;

    public DnaServiceHomeFragment() {
        super(R.layout.fragment_dna_service_home);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceListAdapter = new ServiceListAdapter(this);
        serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

        loading.observe(this, isLoading -> {
            if (!isLoading) {
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                return;
            }
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            serviceViewModel.getServices(searchQuery, 0, 1, 6, this);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.fragment_dna_service_home_list);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false)
        );
        recyclerView.setAdapter(serviceListAdapter);

        progressBar = view.findViewById(R.id.fragment_dna_loading_progress_view);
        view.findViewById(R.id.activity_dna_service_app_name_view).setOnClickListener(v -> requireActivity().finish());
        AppCompatEditText searchEditText =view.findViewById(R.id.fragment_dna_service_home_search);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterServices(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterServices(String query) {
        if (!query.isBlank()) {
            searchQuery = query;
        } else  {
            searchQuery = null;
        }
        loading.setValue(true);
    }

    @Override
    public void onSuccess(ServiceApiResponse response) {
        requireActivity().runOnUiThread(() -> {
            serviceListAdapter.submitList(response.getItems());
            loading.setValue(false);
        });
    }

    @Override
    public void onError(Throwable t) {
        requireActivity().runOnUiThread(() -> loading.setValue(false));
    }

    @Override
    public void onServiceClick(DnaService service) {
        serviceViewModel.selectedServiceId = new AtomicInteger(service.getId());

        getParentFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.activity_dna_service_content_layout, ServiceDetailFragment.class, null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceViewModel.selectedServiceId = null;
    }
}
