package com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.Utils.PAGE_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentCreateCompanyAccountBinding;

import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.horizontalRecycler.HorizontalRecyclerDelegate;
import myapplication.android.ui.recycler.ui.items.items.roundImageView.RoundImageViewDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewDelegate;

public class CreateCompanyAccountFragment extends Fragment {

    private CreateCompanyAccountViewModel viewModel;
    private FragmentCreateCompanyAccountBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private String page;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = CreateCompanyAccountFragmentArgs.fromBundle(getArguments()).getPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(CreateCompanyAccountViewModel.class);
        binding = FragmentCreateCompanyAccountBinding.inflate(inflater, container, false);
        viewModel.onPageInit(page);

        if (page.equals(PAGE_3)){
            binding.nextButton.setText("Send for review");
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupObserves();
        initMainAdapter();

        binding.nextButton.setOnClickListener(v -> {
            viewModel.navigateNext(page, this);
        });

        binding.imageButtonBack.setOnClickListener(v -> {
            viewModel.navigateBack(page, this);
        });

    }

    private void initMainAdapter() {
        mainAdapter.addDelegate(new HorizontalRecyclerDelegate());
        mainAdapter.addDelegate(new AutoCompleteTextDelegate());
        mainAdapter.addDelegate(new FloatingActionButtonDelegate());
        mainAdapter.addDelegate(new RoundImageViewDelegate());
        mainAdapter.addDelegate(new TextViewDelegate());
        mainAdapter.addDelegate(new EditTextDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);

    }
}