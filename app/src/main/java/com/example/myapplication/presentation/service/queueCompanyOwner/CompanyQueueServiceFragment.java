package com.example.myapplication.presentation.service.queueCompanyOwner;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCompanyQueueServiceBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.dialogFragments.employeeQrCode.EmployeeQrCodeDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.ui.items.items.optionImageButton.OptionImageButtonAdapter;
import myapplication.android.ui.recycler.ui.items.items.optionImageButton.OptionImageButtonModel;

public class CompanyQueueServiceFragment extends Fragment {

    private FragmentCompanyQueueServiceBinding binding;
    private final List<OptionImageButtonModel> list = new ArrayList<>();
    private final OptionImageButtonAdapter adapter = new OptionImageButtonAdapter();
    private String companyId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyId = getArguments().getString(COMPANY_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCompanyQueueServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
    }

    private void initRecycler(){
        buildList(new OptionImageButtonModel[]{
                new OptionImageButtonModel(1, R.drawable.queue_action_background, getResources().getString(R.string.create_queue), () -> {
                    ((MainActivity) requireActivity()).openCreateCompanyQueueActivity(companyId);
                }),
                new OptionImageButtonModel(2, R.drawable.become_employee_background, getResources().getString(R.string.add_employees), () -> {
                    EmployeeQrCodeDialogFragment dialogFragment = new EmployeeQrCodeDialogFragment(companyId);
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "EMPLOYEE_QE_CODE_DIALOG");
                }),

                new OptionImageButtonModel(3, R.drawable.queue_manager_background, getResources().getString(R.string.queue_manager), () -> {
                    ((MainActivity)requireActivity()).openQueueManagerActivity(companyId);
                })

        });
    }

    private void buildList(OptionImageButtonModel[] models) {
        list.addAll(Arrays.asList(models));
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(list);
    }
}