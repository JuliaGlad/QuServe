package com.example.myapplication.presentation.employee.main.queueWorkerFragment;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentQueueWorkerBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.delegates.WorkerActiveQueueAdapter;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.delegates.WorkerActiveQueueModel;
import com.example.myapplication.presentation.employee.main.ActiveQueueModel;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.model.QueueWorkerStateModel;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.state.QueueWorkerState;

import java.util.ArrayList;
import java.util.List;

public class QueueWorkerFragment extends Fragment {

    private QueueWorkerViewModel viewModel;
    private FragmentQueueWorkerBinding binding;
    private String companyId;
    private final WorkerActiveQueueAdapter adapter = new WorkerActiveQueueAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QueueWorkerViewModel.class);
        companyId = getArguments().getString(COMPANY_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentQueueWorkerBinding.inflate(inflater, container, false);
        viewModel.getEmployeeActiveQueues(companyId);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof QueueWorkerState.Success) {
                QueueWorkerStateModel model = ((QueueWorkerState.Success) state).data;
                binding.companyName.setText(model.getCompanyName());
                List<ActiveQueueModel> queues = model.getModels();
                initRecycler(queues);
            } else if (state instanceof QueueWorkerState.Loading){
                binding.progressBar.setVisibility(View.VISIBLE);
            } else if (state instanceof QueueWorkerState.Error){
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.progressBar.setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getEmployeeActiveQueues(companyId);
        });
    }

    private void initRecycler(List<ActiveQueueModel> models) {
        List<WorkerActiveQueueModel> list = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            ActiveQueueModel current = models.get(i);
            list.add(new WorkerActiveQueueModel(
                    i,
                    current.getName(),
                    current.getLocation(),
                    () -> {
                        ((MainActivity)requireActivity()).openQueueWorkerDetailsActivity(current.getId(), companyId);
                    }
            ));
        }
        submitList(list);
    }

    private void submitList(List<WorkerActiveQueueModel> models) {
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(models);
        binding.progressBar.setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }
}