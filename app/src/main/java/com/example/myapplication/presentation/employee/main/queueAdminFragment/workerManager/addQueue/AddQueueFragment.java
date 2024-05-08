package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMPLOYEE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_NAME;
import static com.example.myapplication.presentation.utils.Utils.NOT_CHOSEN;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAddQueueBinding;
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
    private final List<ActiveQueueModel> chosen = new ArrayList();

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
        initButtonOk();
    }

    private void initButtonOk() {
        binding.buttonOk.setOnClickListener(v -> {
            viewModel.addEmployeeToQueue(chosen, companyId, employeeId, employeeName);
        });
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_addQueueFragment_to_workerDetailsFragment);
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof AddQueueState.Success){
                List<AddQueueModel> queues = ((AddQueueState.Success)state).data;
                initRecycler(queues);

            } else if (state instanceof AddQueueState.Loading){

            } else if (state instanceof AddQueueState.Error){

            }
        });

        viewModel.isAdded.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){

                Bundle bundle = new Bundle();
                bundle.putString(EMPLOYEE_NAME, employeeName);
                bundle.putString(COMPANY_EMPLOYEE, employeeId);
                bundle.putString(COMPANY_ID, companyId);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_addQueueFragment_to_workerDetailsFragment, bundle);
            }
        });
    }

    private void initRecycler(List<AddQueueModel> queues) {
        List<AddQueueItemModel> models = new ArrayList<>();
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
                    chosen
            ));
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(models);
    }
}