package com.example.myapplication.presentation.service.main.basicUser.becomeEmployeeOptions;

import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_DATA;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBecomeEmployeeOptionsBinding;
import com.example.myapplication.presentation.service.JoinQueueFragment.JoinQueueActivity;
import com.example.myapplication.presentation.service.main.basicUser.recyclerView.ButtonWithDescriptionAdapter;
import com.example.myapplication.presentation.service.main.basicUser.recyclerView.ButtonWithDescriptionModel;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Arrays;
import java.util.List;

public class BecomeEmployeeOptionsFragment extends Fragment {

    private FragmentBecomeEmployeeOptionsBinding binding;
    private ActivityResultLauncher<ScanOptions> launcher;
    private final ButtonWithDescriptionAdapter adapter = new ButtonWithDescriptionAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLauncher();
    }

    private void initLauncher() {
        launcher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), JoinQueueActivity.class);
                intent.putExtra(EMPLOYEE_DATA, result.getContents());
                requireActivity().startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBecomeEmployeeOptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
    }

    private void initRecycler() {
        buildList(new ButtonWithDescriptionModel[]{
                new ButtonWithDescriptionModel(1, R.drawable.ic_worker, R.string.worker, R.string.become_worker_description, launcher),
                new ButtonWithDescriptionModel(2, R.drawable.ic_waiter, R.string.waiter, R.string.become_waiter_description, launcher),
                new ButtonWithDescriptionModel(3, R.drawable.ic_chef_hat, R.string.cooker, R.string.become_cooker_description, launcher)
        });
    }

    private void buildList(ButtonWithDescriptionModel[] models){
        List<ButtonWithDescriptionModel> recyclerList = Arrays.asList(models);
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(recyclerList);
    }
}