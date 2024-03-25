package com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.PAGE_4;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_LIST;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentChooseWorkersBinding;
import com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.recycler.WorkerItemAdapter;
import com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.recycler.WorkerItemModel;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.EmployeeItemAdapter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChooseWorkersFragment extends Fragment {

    private ChooseWorkersViewModel viewModel;
    private FragmentChooseWorkersBinding binding;
    private List<EmployeeModel> chosen = new ArrayList<>();
    private String companyId;
    private final WorkerItemAdapter adapter = new WorkerItemAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        companyId = getArguments().getString(COMPANY_ID);

        try {
            chosen = new Gson().fromJson(getArguments().getString(WORKERS_LIST), new TypeToken<List<EmployeeModel>>() {
            }.getType());
        } catch (NullPointerException e){
            Log.d("Chosen is null", e.getMessage());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ChooseWorkersViewModel.class);
        binding = FragmentChooseWorkersBinding.inflate(inflater, container, false);

        viewModel.getEmployees(companyId, chosen);

        initOkButton();

        return binding.getRoot();
    }

    private void initOkButton() {
        binding.buttonOk.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(WORKERS_LIST, new Gson().toJson(chosen));
            bundle.putString(PAGE_KEY, PAGE_4);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_chooseWorkersFragment_to_createCompanyQueueFragment, bundle);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
    }


    private void setupObserves(){
       viewModel.employees.observe(getViewLifecycleOwner(), employeeMainModels -> {
           adapter.submitList(employeeMainModels);
           binding.recyclerView.setAdapter(adapter);
       });
    }
}