package com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers;

import static com.example.myapplication.presentation.utils.Utils.CHOSEN;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.NOT_CHOSEN;
import static com.example.myapplication.presentation.utils.Utils.PAGE_4;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.Utils.WORKER;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_LIST;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.SearchView;
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
import com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.model.EmployeeStateModel;
import com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.recycler.WorkerItemAdapter;
import com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.recycler.WorkerItemModel;
import com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.state.ChooseWorkersState;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.EmployeeItemAdapter;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.EmployeeItemModel;
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
    private final List<WorkerItemModel> workerItems = new ArrayList<>();
    private String companyId;
    private final WorkerItemAdapter adapter = new WorkerItemAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        companyId = getArguments().getString(COMPANY_ID);

        try {
            chosen = new Gson().fromJson(getArguments().getString(WORKERS_LIST), new TypeToken<List<EmployeeModel>>() {
            }.getType());
        } catch (NullPointerException e) {
            Log.d("Chosen is null", e.getMessage());
        }

        initSearchView();
    }

    private void initSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }

        });
    }

    private void filterList(String newText) {
        List<WorkerItemModel> filteredList = new ArrayList<>();

        for (WorkerItemModel model : workerItems) {
            if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(model);
            }
        }

        adapter.submitList(filteredList);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ChooseWorkersViewModel.class);
        binding = FragmentChooseWorkersBinding.inflate(inflater, container, false);

        viewModel.getEmployees(companyId);

        initOkButton();
        initBackButtonPressed();

        return binding.getRoot();
    }

    private void navigateBack(Bundle bundle) {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_chooseWorkersFragment_to_createCompanyQueueFragment, bundle);
    }

    private void initOkButton() {
        binding.buttonOk.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(WORKERS_LIST, new Gson().toJson(chosen));
            bundle.putString(PAGE_KEY, PAGE_4);
            navigateBack(bundle);
        });
    }


    private void initBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putString(PAGE_KEY, PAGE_4);
                navigateBack(bundle);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ChooseWorkersState.Success) {

                List<EmployeeStateModel> models = ((ChooseWorkersState.Success) state).data;

                for (int i = 0; i < models.size(); i++) {
                    EmployeeStateModel current = models.get(i);
                    if (current.getRole().equals(WORKER)) {
                        String type = NOT_CHOSEN;

                        if (chosen.size() > 0) {
                            for (int j = 0; j < chosen.size(); j++) {
                                if (chosen.get(j).getUserId().equals(current.getId())) {
                                    type = CHOSEN;
                                }
                            }
                        }

                        workerItems.add(new WorkerItemModel(
                                i,
                                current.getName(),
                                current.getId(),
                                type,
                                chosen
                        ));
                    }
                }

                binding.recyclerView.setAdapter(adapter);
                adapter.submitList(workerItems);

                binding.progressBar.setVisibility(View.GONE);

            } else if (state instanceof ChooseWorkersState.Loading) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else if (state instanceof ChooseWorkersState.Error) {

            }
        });
    }
}