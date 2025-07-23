package com.prm.android.bloodlinedna.dnaservices.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.prm.android.bloodlinedna.R;
import com.prm.android.bloodlinedna.dnaservices.ServiceViewModel;
import com.prm.android.bloodlinedna.dnaservices.adapters.ProcessTemplateStepAdapter;
import com.prm.android.bloodlinedna.dnaservices.listener.ServiceDetailCallback;
import com.prm.android.bloodlinedna.models.services.BookingSelection;
import com.prm.android.bloodlinedna.models.services.ProcessTemplate;
import com.prm.android.bloodlinedna.models.services.ServiceDetail;
import com.prm.android.bloodlinedna.models.services.ServicePrice;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ServiceDetailFragment extends Fragment implements ServiceDetailCallback {

    private TextView titleView;
    private TextView processTitleView;
    private TextView processDescriptionView;
    private TextView subtitleView;

    private ProgressBar loadingView;
    private RecyclerView stepList;

    private View infoContainer;
    private AppCompatButton civilButton;
    private AppCompatButton legalButton;
    private View legalDescriptionView;
    private TextView participantNumberView;
    private TextView turnaroundTimeView;
    private TextView priorityFreeView;
    private TextView specialFreeView;
    private TextView priceView;

    private ProcessTemplateStepAdapter stepAdapter = new ProcessTemplateStepAdapter();

    private ServiceViewModel serviceViewModel;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(true);

    private final MutableLiveData<Object> calPrice = new MutableLiveData<>();

    private ServiceDetail serviceDetail;
    private ServicePrice servicePrice;

    private BookingSelection.ServiceType selectedServiceType = BookingSelection.ServiceType.CIVIL;
    private boolean isPriority = false;
    private boolean isSpecialSample = false;

    private double totalPrice = 0;
    private SwitchMaterial specialSwitch;
    private SwitchMaterial fastSwitch;

    public ServiceDetailFragment() {
        super(R.layout.fragment_dna_service_detail);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
        if (serviceViewModel.selectedServiceId == null) {
            Toast.makeText(requireContext(), "Không thể tải dữ liệu, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
            return;
        }

        loading.observe(this, isLoading -> {
            if (isLoading) {
                loadingView.setVisibility(View.VISIBLE);
                infoContainer.setVisibility(View.INVISIBLE);
                serviceViewModel.getServiceById(serviceViewModel.selectedServiceId.get(), this);
                return;
            }
            loadingView.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        calPrice.observe(this, isCall -> {
            if (isCall != null && servicePrice != null) {
                totalPrice = calculatePrice(servicePrice, selectedServiceType, isPriority, isSpecialSample);
                priceView.setText(formatCurrency(totalPrice));
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);

        view.findViewById(R.id.fragment_dna_detail_service_back_button).setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        civilButton.setSelected(true);
        civilButton.setOnClickListener(v -> {
            v.setSelected(true);

            selectedServiceType = BookingSelection.ServiceType.CIVIL;
            calPrice.setValue(selectedServiceType);

            legalButton.setSelected(false);
            legalDescriptionView.setVisibility(View.INVISIBLE);
        });

        legalButton.setOnClickListener(v -> {
            v.setSelected(true);

            selectedServiceType = BookingSelection.ServiceType.LEGAL;
            calPrice.setValue(selectedServiceType);

            civilButton.setSelected(false);
            legalDescriptionView.setVisibility(View.VISIBLE);
        });

        fastSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isPriority = isChecked;
            calPrice.setValue("Priority: " + isPriority);
        });

        specialSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isSpecialSample = isChecked;
            calPrice.setValue("Special: " + isSpecialSample);
        });

        stepList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        stepList.setAdapter(stepAdapter);
    }

    private void bindView(View view) {
        titleView = view.findViewById(R.id.fragment_dna_detail_service_title_view);
        subtitleView = view.findViewById(R.id.fragment_dna_detail_service_subtitle_view);
        civilButton = view.findViewById(R.id.fragment_dna_detail_service_civil_button);
        legalButton = view.findViewById(R.id.fragment_dna_detail_service_administration_button);
        infoContainer = view.findViewById(R.id.fragment_dna_detail_service_info_container);
        loadingView = view.findViewById(R.id.fragment_dna_detail_service_loading_progress_view);
        stepList = view.findViewById(R.id.fragment_dna_service_detail_step_list);
        processTitleView = view.findViewById(R.id.fragment_dna_detail_service_process_title);
        processDescriptionView = view.findViewById(R.id.fragment_dna_detail_service_process_description);
        legalDescriptionView = view.findViewById(R.id.fragment_dna_detail_service_legal_description);
        participantNumberView = view.findViewById(R.id.fragment_dna_detail_service_participant_number);
        turnaroundTimeView = view.findViewById(R.id.fragment_dna_detail_service_turnaround_time_view);
        priorityFreeView = view.findViewById(R.id.fragment_dna_detail_service_priority_free_view);
        specialFreeView = view.findViewById(R.id.fragment_dna_detail_service_special_free_view);
        priceView = view.findViewById(R.id.fragment_dna_detail_service_price_view);
        specialSwitch = view.findViewById(R.id.fragment_dna_detail_service_special_switch);
        fastSwitch = view.findViewById(R.id.fragment_dna_detail_service_fast_switch);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(ServiceDetail serviceDetail) {
        this.serviceDetail = serviceDetail;
        requireActivity().runOnUiThread(() -> {
            loading.setValue(false);

            titleView.setText(serviceDetail.getName());
            subtitleView.setText(serviceDetail.getDescription());

            servicePrice = serviceDetail.getServicePriceModel().get(0);
            calPrice.setValue(servicePrice.getBasePrice());

            priorityFreeView.setText("(+" + formatCurrency(servicePrice.getPriorityFee()) + ")");
            specialFreeView.setText("(+" + formatCurrency(servicePrice.getSpecialSampleFee()) + ")");
            participantNumberView.setText(" " + servicePrice.getBaseParticipantCount() + " ");
            turnaroundTimeView.setText(" " + servicePrice.getTurnaroundTime());

            infoContainer.setVisibility(View.VISIBLE);
            List<ProcessTemplate> processTemplates = serviceDetail.getProcessTemplateModel();

            if (processTemplates != null) {
                ProcessTemplate processTemplate = processTemplates.get(0);
                processTitleView.setVisibility(View.VISIBLE);
                processDescriptionView.setVisibility(View.VISIBLE);
                processTitleView.setText(processTemplate.getName());
                if (processTemplate.getDescription() != null) {
                    processDescriptionView.setText(processTemplate.getDescription());
                }
                stepAdapter.setSteps(processTemplate.getProcessTemplateSteps());
            }
        });
    }

    public static String formatCurrency(Double value) {
        double numberValue = (value != null) ? value : 0.0;
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(numberValue);
    }

    @Override
    public void onError(Throwable throwable) {
        FragmentActivity activity = requireActivity();
        activity.runOnUiThread(() -> {
            Toast.makeText(activity, "Không thể tải dữ liệu, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            activity.getOnBackPressedDispatcher().onBackPressed();
        });
    }

    public double calculatePrice(
            ServicePrice pkg,
            BookingSelection.ServiceType serviceType,
            boolean isPriority, boolean isSpecialSample
    ) {
        double total = pkg.getBasePrice();

        if (serviceType == BookingSelection.ServiceType.LEGAL && pkg.getLegalFee() > 0) {
            total += pkg.getLegalFee();
        }
        if (isPriority) {
            total += pkg.getPriorityFee();
        }
        if (isSpecialSample) {
            total += pkg.getSpecialSampleFee();
        }

        return total;
    }
}
