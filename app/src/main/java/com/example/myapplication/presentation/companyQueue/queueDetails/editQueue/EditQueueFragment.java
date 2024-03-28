package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;

import android.net.Uri;
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
import com.example.myapplication.databinding.FragmentEditQueueBinding;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EditQueueModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EmployeeModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.state.EditQueueState;
import com.example.myapplication.presentation.companyQueue.queueDetails.recycler.QueueEmployeeAdapter;
import com.example.myapplication.presentation.companyQueue.queueDetails.recycler.RecyclerEmployeeModel;

import java.util.ArrayList;
import java.util.List;

public class EditQueueFragment extends Fragment {

    private EditQueueViewModel viewModel;
    private FragmentEditQueueBinding binding;
    private String companyId, queueId, name, location;
    private final QueueEmployeeAdapter adapter = new QueueEmployeeAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        companyId = getArguments().getString(COMPANY_ID);
        queueId = getArguments().getString(QUEUE_ID);
        viewModel = new ViewModelProvider(this).get(EditQueueViewModel.class);
        binding = FragmentEditQueueBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        viewModel.getQueueData(companyId, queueId);

        initSaveButton();
    }

    private void initSaveButton() {
        binding.buttonSave.setOnClickListener(v -> {
            location = binding.locationEdit.getText().toString();
            name = binding.header.getText().toString();

            viewModel.saveData(companyId, queueId, name, location);
        });
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof EditQueueState.Success){

                List<RecyclerEmployeeModel> list = new ArrayList<>();

                EditQueueModel model = ((EditQueueState.Success)state).data;
                List<EmployeeModel> employees = model.getModels();
                binding.header.setText(model.getName());
                binding.locationEdit.setText(model.getLocation());
                binding.recyclerView.setAdapter(adapter);
                for (int i = 0; i < employees.size(); i++) {
                    EmployeeModel current = employees.get(i);
                    list.add(new RecyclerEmployeeModel(i, current.getId(), current.getName(), current.getRole(), Uri.EMPTY));
                }
                adapter.submitList(list);
                binding.progressBar.setVisibility(View.GONE);

            } else if (state instanceof EditQueueState.Loading){
                binding.progressBar.setVisibility(View.VISIBLE);

            } else if (state instanceof EditQueueState.Error){

            }
        });

        viewModel.items.observe(getViewLifecycleOwner(), models -> {
            binding.recyclerView.setAdapter(adapter);
            adapter.submitList(models);
        });

        viewModel.saved.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){

                Bundle bundle = new Bundle();

                bundle.putString(QUEUE_NAME_KEY, name);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_editQueueFragment_to_companyQueueDetailsFragment, bundle);
            }
        });

    }
}