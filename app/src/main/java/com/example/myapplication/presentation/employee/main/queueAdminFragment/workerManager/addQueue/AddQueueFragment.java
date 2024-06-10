package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue;

import static com.example.myapplication.presentation.utils.constants.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_EMPLOYEE;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_NAME;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_CHOSEN;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAddQueueBinding;
import com.example.myapplication.presentation.companyQueue.queueManager.recycler_view.ManagerItemModel;
import com.example.myapplication.presentation.dialogFragments.chooseCity.ChooseCityFullScreenDialog;
import com.example.myapplication.presentation.employee.main.ActiveQueueModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.model.AddQueueModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.recycler.AddQueueItemAdapter;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.recycler.AddQueueItemModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.state.AddQueueState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddQueueFragment extends Fragment {

    private AddQueueViewModel viewModel;
    private FragmentAddQueueBinding binding;
    private String companyId, employeeName, employeeId;
    private final AddQueueItemAdapter adapter = new AddQueueItemAdapter();
    private final List<String> ids = new ArrayList<>();
    private final List<ActiveQueueModel> chosen = new ArrayList<>();
    private final List<AddQueueItemModel> models = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddQueueViewModel.class);
        if (getArguments() != null) {
            ids.addAll(Objects.requireNonNull(requireArguments().getStringArrayList(QUEUE_ID)));

            companyId = getArguments().getString(COMPANY_ID);
            employeeName = getArguments().getString(EMPLOYEE_NAME);
            employeeId = getArguments().getString(COMPANY_EMPLOYEE);

            viewModel.getCompanyQueues(companyId, ids);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddQueueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initButtonBack();
        handleBackButtonPressed();
        initChooseCity();
        initButtonOk();
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
                filterListBySearchView(newText);
                return true;
            }

        });
    }

    private void initChooseCity() {

        binding.chooseCityLayout.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            ChooseCityFullScreenDialog dialogFragment = new ChooseCityFullScreenDialog();

            fragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.worker_manager_container, dialogFragment)
                    .addToBackStack(null)
                    .commit();

            dialogFragment.onDismissListener(bundle -> {
                String city = bundle.getString(CITY_KEY);
                assert city != null;
                filterList(city);
            });
        });

    }

    private void filterList(String city) {
        List<AddQueueItemModel> newList = new ArrayList<>();

        if (city.equals(getResources().getString(R.string.all_cities))) {
            adapter.submitList(models);
            binding.chooseCity.setText(getResources().getString(R.string.all_cities));
        } else {
            binding.chooseCity.setText(city);
            for (int i = 0; i < models.size(); i++) {
                if (models.get(i).getCity().equals(city)) {
                    newList.add(models.get(i));
                }
            }
            adapter.submitList(newList);
        }
    }

    private void filterListBySearchView(String key) {
        if (!key.isEmpty()) {
            List<AddQueueItemModel> modelList = adapter.getCurrentList();
            List<AddQueueItemModel> filteredList = new ArrayList<>();
            for (AddQueueItemModel model : modelList) {
                if (model.getCity().toLowerCase().contains(key.toLowerCase()) || model.getLocation().contains(key.toLowerCase())) {
                    filteredList.add(model);
                }
            }
            adapter.submitList(filteredList);
        } else {
            adapter.submitList(models);
        }
    }

    private void handleBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack();
            }
        });
    }

    private void initButtonOk() {
        binding.buttonOk.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonOk.setEnabled(false);
            viewModel.addEmployeeToQueue(chosen, companyId, employeeId, employeeName);
        });
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> navigateBack());
    }

    private void navigateBack() {
        Bundle bundle = new Bundle();
        bundle.putString(COMPANY_ID, companyId);
        bundle.putString(EMPLOYEE_NAME, employeeName);
        bundle.putString(COMPANY_EMPLOYEE, employeeId);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_addQueueFragment_to_workerDetailsFragment, bundle);
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof AddQueueState.Success) {
                List<AddQueueModel> queues = ((AddQueueState.Success) state).data;
                initRecycler(queues);

            } else if (state instanceof AddQueueState.Loading) {
                binding.loadingLayout.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof AddQueueState.Error) {
                setError();
            }
        });

        viewModel.isAdded.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {

                Bundle bundle = new Bundle();
                bundle.putString(EMPLOYEE_NAME, employeeName);
                bundle.putString(COMPANY_EMPLOYEE, employeeId);
                bundle.putString(COMPANY_ID, companyId);

                binding.loader.setVisibility(View.GONE);
                binding.buttonOk.setEnabled(true);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_addQueueFragment_to_workerDetailsFragment, bundle);
            }
        });
    }

    private void setError() {
        binding.loader.setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getCompanyQueues(companyId, ids);
        });
    }

    private void initRecycler(List<AddQueueModel> queues) {
        for (int i = 0; i < queues.size(); i++) {
            AddQueueModel current = queues.get(i);
            models.add(new AddQueueItemModel(
                    i,
                    current.getQueueId(),
                    current.getName(),
                    current.getWorkersCount(),
                    current.getLocation(),
                    current.getCity(),
                    NOT_CHOSEN,
                    chosen,
                    queue -> {
                        chosen.add((ActiveQueueModel) queue);
                    },
                    chosen::remove
            ));
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(models);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
        binding.loadingLayout.getRoot().setVisibility(View.GONE);
    }
}