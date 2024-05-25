package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.main;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMPLOYEE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_NAME;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentWorkerManagerBinding;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.main.state.WorkerManagerState;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.model.CompanyEmployeeModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.recycler.WorkerManagerAdapter;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.recycler.WorkerManagerModel;

import java.util.ArrayList;
import java.util.List;

public class WorkerManagerFragment extends Fragment {

    private WorkerManagerViewModel viewModel;
    private FragmentWorkerManagerBinding binding;
    private final WorkerManagerAdapter adapter = new WorkerManagerAdapter();
    private String companyId;
    List<WorkerManagerModel> models;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WorkerManagerViewModel.class);
        companyId = requireActivity().getIntent().getStringExtra(COMPANY_ID);
        viewModel.getEmployees(companyId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentWorkerManagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initSearchView();
        initButtonBack();
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
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
        List<WorkerManagerModel> filteredList = new ArrayList<>();
        for (WorkerManagerModel model : models) {
            if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(model);
            }
        }
        adapter.submitList(filteredList);
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof WorkerManagerState.Success) {
                models = new ArrayList<>();
                List<CompanyEmployeeModel> previous = ((WorkerManagerState.Success) state).data;
                if (!previous.isEmpty()) {
                    initRecycler(previous);
                } else {
                    initEmptyLayout();
                }
                binding.progressBar.getRoot().setVisibility(View.GONE);
                binding.errorLayout.getRoot().setVisibility(View.GONE);

            } else if (state instanceof WorkerManagerState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof WorkerManagerState.Error) {
                setErrorLayout();
            }
        });
    }

    private void initEmptyLayout() {
        binding.emptyLayout.getRoot().setVisibility(View.VISIBLE);
        binding.emptyLayout.title.setText(getString(R.string.there_are_no_workers_in_this_company_yet));
        binding.emptyLayout.infoBox.body.setText(getString(R.string.wait_until_any_new_employees_will_join_your_company));
        binding.emptyLayout.buttonAdd.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void initRecycler(List<CompanyEmployeeModel> previous) {
        for (int i = 0; i < previous.size(); i++) {
            CompanyEmployeeModel current = previous.get(i);
            String name = current.getName();
            String id = current.getEmployeeId();
            models.add(new WorkerManagerModel(
                    i, id, name, current.getCount(),
                    () -> {
                        Bundle bundle = new Bundle();
                        bundle.putString(EMPLOYEE_NAME, name);
                        bundle.putString(COMPANY_EMPLOYEE, id);
                        bundle.putString(COMPANY_ID, companyId);
                        NavHostFragment.findNavController(this)
                                .navigate(R.id.action_workerManagerFragment_to_workerDetailsFragment, bundle);
                    }
            ));
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(models);
    }

    private void setErrorLayout() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getEmployees(companyId);
        });
    }
}