package com.example.myapplication.presentation.employee.main.queueAdminFragment;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentQueueAdminBinding;
import com.example.myapplication.presentation.MainActivity;

public class QueueAdminFragment extends Fragment {

    FragmentQueueAdminBinding binding;
    String companyId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQueueAdminBinding.inflate(inflater, container, false);
        companyId = getArguments().getString(COMPANY_ID);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initQueueManagerButton();
        initCompanyWorkersButton();
    }

    private void initCompanyWorkersButton() {
        binding.buttonCompanyWorkers.headLine.setText(getResources().getString(R.string.company_workers));
        binding.buttonCompanyWorkers.description.setText(getResources().getString(R.string.view_all_company_workers_and_assign_them_queues));
        binding.buttonCompanyWorkers.icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_employee_list, requireView().getContext().getTheme()));

        binding.buttonCompanyWorkers.item.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openWorkerManagerActivity(companyId);
        });
    }

    private void initQueueManagerButton(){
        binding.buttonQueueManager.headLine.setText(getResources().getString(R.string.queue_manager));
        binding.buttonQueueManager.description.setText(getResources().getString(R.string.view_all_active_company_queues_and_manage_them));
        binding.buttonQueueManager.icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_list, requireView().getContext().getTheme()));

        binding.buttonQueueManager.item.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openQueueManagerActivity(companyId);
        });
    }
}
