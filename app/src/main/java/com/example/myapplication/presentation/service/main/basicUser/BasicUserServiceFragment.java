package com.example.myapplication.presentation.service.main.basicUser;

import static com.example.myapplication.presentation.utils.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import androidx.activity.result.ActivityResultLauncher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBasicUserServiceBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.dialogFragments.needAccountDialog.NeedAccountDialogFragment;
import com.example.myapplication.presentation.employee.becomeEmployee.BecomeEmployeeActivity;
import com.example.myapplication.presentation.service.main.ScanCode;
import com.example.myapplication.presentation.service.main.recyclerView.QueueButtonAdapter;
import com.example.myapplication.presentation.service.main.recyclerView.QueueButtonModel;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicUserServiceFragment extends Fragment {

    private BasicUserViewModel viewModel;
    private FragmentBasicUserServiceBinding binding;
    private final List<QueueButtonModel> list = new ArrayList<>();
    private final QueueButtonAdapter adapter = new QueueButtonAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBasicUserServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
    }

    private void initRecycler() {
        buildList(new QueueButtonModel[]{
                new QueueButtonModel(1, R.drawable.queue_action_background, getResources().getString(R.string.queue), () -> {
                    ((MainActivity) requireActivity()).openQueueActivity();
                }),
                new QueueButtonModel(2, R.drawable.restaurant_action_background, getResources().getString(R.string.restaurant), () -> {

                }),
                new QueueButtonModel(3, R.drawable.on_board_catering_background, getResources().getString(R.string.on_board_catering), () -> {

                }),
                new QueueButtonModel(4, R.drawable.become_employee_background, getResources().getString(R.string.become_employee), () -> {
                    if (getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(APP_STATE, ANONYMOUS).equals(ANONYMOUS)) {
                        NeedAccountDialogFragment dialogFragment = new NeedAccountDialogFragment();
                        dialogFragment.show(getActivity().getSupportFragmentManager(), "NEED_ACCOUNT_DIALOG");
                    } else {
                        ((MainActivity) requireActivity()).openBecomeEmployeeOptionsActivity();
                    }
                })
        });
    }

    private void buildList(QueueButtonModel[] models) {
        list.addAll(Arrays.asList(models));
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(list);
    }
}