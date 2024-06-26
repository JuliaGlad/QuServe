package com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers;

import static com.example.myapplication.presentation.utils.constants.Utils.CHOSEN;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_CHOSEN;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_4;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.PNG;
import static com.example.myapplication.presentation.utils.constants.Utils.WORKER;
import static com.example.myapplication.presentation.utils.constants.Utils.WORKERS_LIST;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
        companyId = requireArguments().getString(COMPANY_ID);
        try {
            chosen = new Gson().fromJson(requireArguments().getString(WORKERS_LIST), new TypeToken<List<EmployeeModel>>() {
            }.getType());
        } catch (NullPointerException e) {
            Log.d("Chosen is null", e.getMessage());
        }
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ChooseWorkersViewModel.class);
        binding = FragmentChooseWorkersBinding.inflate(inflater, container, false);

        viewModel.getEmployees(companyId);

        initOkButton();
        initBackButtonPressed();
        initBackButton();
        initSearchView();

        return binding.getRoot();
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
        if (!newText.isEmpty()) {
            List<WorkerItemModel> filteredList = new ArrayList<>();

            for (WorkerItemModel model : workerItems) {
                if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(model);
                }
            }

            adapter.submitList(filteredList);
        } else {
            adapter.submitList(workerItems);
        }
    }

    private void initBackButton() {
         binding.buttonBack.setOnClickListener(v -> {
             Bundle bundle = new Bundle();
             bundle.putString(PAGE_KEY, PAGE_4);
             navigateBack(bundle);
         });
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
                if (!models.isEmpty()) {
                    for (int i = 0; i < models.size(); i++) {
                        EmployeeStateModel current = models.get(i);
                        if (current.getRole().equals(WORKER)) {
                            String type = NOT_CHOSEN;

                            if (!chosen.isEmpty()) {
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
                                    current.getQueueCount(),
                                    type,
                                    chosen
                            ));
                        }
                    }

                    binding.recyclerView.setAdapter(adapter);
                    adapter.submitList(workerItems);
                } else {
                    initEmptyLayout();
                }

                binding.progressLayout.getRoot().setVisibility(View.GONE);
                binding.errorLayout.getRoot().setVisibility(View.GONE);

            } else if (state instanceof ChooseWorkersState.Loading) {
                binding.progressLayout.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof ChooseWorkersState.Error) {
                setErrorLayout();
            }
        });
    }

    private void initEmptyLayout() {
        binding.emptyLayout.getRoot().setVisibility(View.VISIBLE);
        binding.emptyLayout.title.setText(getString(R.string.you_don_t_have_any_employees_yet));
        binding.emptyLayout.infoBox.body.setText(getString(R.string.wait_until_any_new_employees_will_join_your_company));
        binding.emptyLayout.buttonAdd.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(PAGE_KEY, PAGE_4);
            navigateBack(bundle);
        });
    }

    private void setErrorLayout() {
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getEmployees(companyId);
        });
    }
}