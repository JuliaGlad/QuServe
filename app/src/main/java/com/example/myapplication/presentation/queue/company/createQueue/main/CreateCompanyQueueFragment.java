package com.example.myapplication.presentation.queue.company.createQueue.main;

import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCreateCompanyQueueBinding;
import com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.CreateCompanyAccountFragmentArgs;

import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.button.ButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.progressBar.ProgressBarDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewDelegate;

public class CreateCompanyQueueFragment extends Fragment {

    private CreateCompanyQueueViewModel viewModel;
    private final EditTextDelegate delegate = new EditTextDelegate();
    private FragmentCreateCompanyQueueBinding binding;
    private String page;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        try {
//            page = CreateCompanyAccountFragmentArgs.fromBundle(getArguments()).getPage();
//        }catch (IllegalArgumentException e){
//            page = requireActivity().getIntent().getStringExtra(PAGE_KEY);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(CreateCompanyQueueViewModel.class);
        binding = FragmentCreateCompanyQueueBinding.inflate(inflater, container, false);
//        viewModel.onPageInit(page, getResources().getStringArray(R.array.lifetime));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupObserves();
        setAdapter();
        initNextButton();
        initBackButton();
        initCloseButton();
    }

    private void initCloseButton() {
        binding.imageButtonClose2.setOnClickListener(v -> {

        });
    }


    private void initBackButton() {
        binding.imageButtonBack3.setOnClickListener(v -> {
            viewModel.navigateBack(page, this);
        });
    }

    private void initNextButton() {
        binding.buttonNext.setOnClickListener(v -> {
            viewModel.navigateNext(page, this);
        });
    }

    private void setAdapter() {
        mainAdapter.addDelegate(new ProgressBarDelegate());
        mainAdapter.addDelegate(delegate);
        mainAdapter.addDelegate(new TextViewDelegate());
        mainAdapter.addDelegate(new AutoCompleteTextDelegate());
        mainAdapter.addDelegate(new ButtonDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {

    }
}