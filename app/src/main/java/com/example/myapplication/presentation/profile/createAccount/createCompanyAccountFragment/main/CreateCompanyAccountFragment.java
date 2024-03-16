package com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.main;

import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.PAGE_3;
import static com.example.myapplication.presentation.utils.Utils.PAGE_4;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;

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
import com.example.myapplication.databinding.FragmentCreateCompanyAccountBinding;

import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.horizontalRecycler.HorizontalRecyclerDelegate;
import myapplication.android.ui.recycler.ui.items.items.roundImageView.RoundImageViewDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;

public class CreateCompanyAccountFragment extends Fragment {

    private CreateCompanyAccountViewModel viewModel;
    private FragmentCreateCompanyAccountBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private String page;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            page = CreateCompanyAccountFragmentArgs.fromBundle(getArguments()).getPage();
        } catch (IllegalArgumentException e) {
            page = requireActivity().getIntent().getStringExtra(PAGE_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(CreateCompanyAccountViewModel.class);
        binding = FragmentCreateCompanyAccountBinding.inflate(inflater, container, false);
        viewModel.onPageInit(page);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupObserves();
        initMainAdapter();

        switch (page) {
            case PAGE_2:
                binding.companyProgressBar.setProgress(25);
                break;
            case PAGE_3:
                binding.companyProgressBar.setProgress(50);
                break;
            case PAGE_4:
                binding.companyProgressBar.setProgress(75);
                binding.buttonNext.setText("Send for review");
                break;
        }

        binding.buttonNext.setOnClickListener(v -> {
            viewModel.navigateNext(page, this);
        });

        binding.buttonBack.setOnClickListener(v -> {
            viewModel.navigateBack(page, this);
        });

    }

    private void initMainAdapter() {
        mainAdapter.addDelegate(new HorizontalRecyclerDelegate());
        mainAdapter.addDelegate(new AutoCompleteTextDelegate());
        mainAdapter.addDelegate(new FloatingActionButtonDelegate());
        mainAdapter.addDelegate(new RoundImageViewDelegate());
        mainAdapter.addDelegate(new TextViewHeaderDelegate());
        mainAdapter.addDelegate(new EditTextDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);

        viewModel.finished.observe(getViewLifecycleOwner(), string -> {
            if (string != null) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_createCompanyAccountFragment_to_approvalFragment);
            }
        });

    }
}