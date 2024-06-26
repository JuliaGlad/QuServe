package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue;

import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.WORKERS_LIST;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEditQueueBinding;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model.AddWorkerModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EditQueueModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EmployeeModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.state.EditQueueState;
import com.example.myapplication.presentation.companyQueue.queueDetails.recycler.QueueEmployeeAdapter;
import com.example.myapplication.presentation.companyQueue.queueDetails.recycler.QueueEmployeeModel;
import com.example.myapplication.presentation.dialogFragments.deleteWorkerFromQueue.DeleteWorkerFromQueueDialogFragment;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EditQueueFragment extends Fragment {

    private EditQueueViewModel viewModel;
    private FragmentEditQueueBinding binding;
    private String companyId, queueId, name, location;
    List<QueueEmployeeModel> list = new ArrayList<>();
    List<EmployeeModel> employees = new ArrayList<>();
    private final QueueEmployeeAdapter adapter = new QueueEmployeeAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EditQueueViewModel.class);
        companyId = requireArguments().getString(COMPANY_ID);
        queueId = requireArguments().getString(QUEUE_ID);
        viewModel.getQueueData(companyId, queueId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        if (getArguments().getString(WORKERS_LIST) != null) {
            String workers = getArguments().getString(WORKERS_LIST);
            List<AddWorkerModel> models = new Gson().fromJson(workers,
                    new TypeToken<List<AddWorkerModel>>() {
                    }.getType());
            if (models != null) {
                for (int i = 0; i < models.size(); i++) {
                    AddWorkerModel current = models.get(i);
                    list.add(new QueueEmployeeModel(
                            i,
                            current.getId(),
                            current.getName(),
                            current.getRole(),
                            () -> showDeleteDialog(companyId, queueId, current.getId())
                    ));
                    adapter.notifyItemInserted(list.size() - 1);
                }
            }
        }
        binding = FragmentEditQueueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initAddButton();
        initBackButton();
        initSaveButton();
        handleBackButtonPressed();
    }

    private void initBackButton() {
        binding.imageButtonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void handleBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack();
            }
        });
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(COMPANY_ID, companyId);
            bundle.putString(QUEUE_ID, queueId);
            bundle.putString(WORKERS_LIST, new Gson().toJson(employees));
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_editQueueFragment_to_addWorkersFragment, bundle);
        });
    }

    private void initSaveButton() {
        binding.buttonSave.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonSave.setEnabled(false);
            location = binding.locationEdit.getText().toString();
            name = binding.header.getText().toString();

            viewModel.saveData(companyId, queueId, name, location);
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof EditQueueState.Success) {

                EditQueueModel model = ((EditQueueState.Success) state).data;
                employees = model.getModels();
                binding.header.setText(model.getName());
                binding.locationEdit.setText(model.getLocation());
                binding.recyclerView.setAdapter(adapter);
                if (list.isEmpty()) {
                    if (!employees.isEmpty()) {
                        for (int i = 0; i < employees.size(); i++) {
                            EmployeeModel current = employees.get(i);
                            list.add(new QueueEmployeeModel(
                                    i,
                                    current.getId(),
                                    current.getName(),
                                    current.getRole(),
                                    () -> showDeleteDialog(companyId, queueId, current.getId())));
                        }
                    } else {
                        binding.emptyLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.emptyLayout.infoBox.getRoot().setVisibility(View.GONE);
                        binding.emptyLayout.title.setText(getString(R.string.you_don_t_have_any_employees_yet));
                        binding.emptyLayout.buttonAdd.setVisibility(View.GONE);
                    }
                }
                adapter.submitList(list);
                binding.progressBar.getRoot().setVisibility(View.GONE);
                binding.errorLayout.getRoot().setVisibility(View.GONE);

            } else if (state instanceof EditQueueState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof EditQueueState.Error) {
                setError();
            }
        });

        viewModel.items.observe(getViewLifecycleOwner(), models -> {
            binding.recyclerView.setAdapter(adapter);
            adapter.submitList(models);
        });

        viewModel.saved.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                navigateBack();
            }
        });

    }

    private void navigateBack() {
        Bundle bundle = new Bundle();
        bundle.putString(QUEUE_NAME_KEY, name);

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_editQueueFragment_to_companyQueueDetailsFragment, bundle);
    }

    private void setError() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getQueueData(companyId, queueId);
        });
    }

    private void showDeleteDialog(String companyId, String queueId, String id) {
        DeleteWorkerFromQueueDialogFragment dialogFragment = new DeleteWorkerFromQueueDialogFragment(companyId, queueId, id);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "DELETE_WORKER_FROM_QUEUE");
        dialogFragment.onDismissListener(bundle -> {
            removeEmployee(id);
        });
    }

    private void removeEmployee(String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEmployeeId().equals(id)) {
                list.remove(i);
                adapter.notifyItemRemoved(i);
                for (int j = 0; j < employees.size(); j++) {
                    if (employees.get(j).getId().equals(id)){
                        employees.remove(j);
                        break;
                    }
                }
                break;
            }
        }
    }
}