package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LOCATION_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;

import android.app.Activity;
import android.content.Intent;
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
import com.example.myapplication.presentation.companyQueue.queueDetails.recycler.QueueEmployeeAdapter;

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
        viewModel.getWorkers(companyId, queueId);


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

        viewModel.location.observe(getViewLifecycleOwner(), string -> {
            binding.locationEdit.setText(string);
        });

        viewModel.name.observe(getViewLifecycleOwner(), string -> {
            binding.header.setText(string);
        });
    }
}