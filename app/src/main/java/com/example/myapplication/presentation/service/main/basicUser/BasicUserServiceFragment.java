package com.example.myapplication.presentation.service.main.basicUser;

import static com.example.myapplication.presentation.utils.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.APP_STATE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBasicUserServiceBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.dialogFragments.needAccountDialog.NeedAccountDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.ui.items.items.optionImageButton.OptionImageButtonAdapter;
import myapplication.android.ui.recycler.ui.items.items.optionImageButton.OptionImageButtonModel;

public class BasicUserServiceFragment extends Fragment {

    private BasicUserViewModel viewModel;
    private FragmentBasicUserServiceBinding binding;
    private final List<OptionImageButtonModel> list = new ArrayList<>();
    private final OptionImageButtonAdapter adapter = new OptionImageButtonAdapter();

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
        buildList(new OptionImageButtonModel[]{
                new OptionImageButtonModel(1, R.drawable.queue_action_background, getResources().getString(R.string.queue), () -> {
                    ((MainActivity) requireActivity()).openQueueActivity();
                }),
                new OptionImageButtonModel(2, R.drawable.restaurant_action_background, getResources().getString(R.string.restaurant), () -> {

                }),
                new OptionImageButtonModel(3, R.drawable.on_board_catering_background, getResources().getString(R.string.on_board_catering), () -> {

                }),
                new OptionImageButtonModel(4, R.drawable.become_employee_background, getResources().getString(R.string.become_employee), () -> {
                    if (getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(APP_STATE, ANONYMOUS).equals(ANONYMOUS)) {
                        NeedAccountDialogFragment dialogFragment = new NeedAccountDialogFragment();
                        dialogFragment.show(getActivity().getSupportFragmentManager(), "NEED_ACCOUNT_DIALOG");
                    } else {
                        ((MainActivity) requireActivity()).openBecomeEmployeeOptionsActivity();
                    }
                })
        });
    }

    private void buildList(OptionImageButtonModel[] models) {
        list.addAll(Arrays.asList(models));
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(list);
    }
}