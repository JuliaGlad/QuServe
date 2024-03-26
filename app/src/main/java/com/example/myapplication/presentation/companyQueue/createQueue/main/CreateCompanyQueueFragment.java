package com.example.myapplication.presentation.companyQueue.createQueue.main;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCreateCompanyQueueBinding;
import com.example.myapplication.presentation.basicQueue.createQueue.mainFragment.CreateQueueFragment;
import com.example.myapplication.presentation.companyQueue.createQueue.CreateCompanyQueueActivity;
import com.example.myapplication.presentation.companyQueue.createQueue.delegates.chooseLocation.LocationDelegate;
import com.example.myapplication.presentation.companyQueue.createQueue.delegates.workers.WorkerDelegate;
import com.example.myapplication.presentation.companyQueue.createQueue.map.MapActivity;

import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;

public class CreateCompanyQueueFragment extends Fragment {

    private CreateCompanyQueueViewModel viewModel;
    private final EditTextDelegate delegate = new EditTextDelegate();
    private FragmentCreateCompanyQueueBinding binding;
    private String page, companyId;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getString(PAGE_KEY);
        companyId = requireActivity().getIntent().getStringExtra(COMPANY_ID);

        if (page == null){
            page = requireActivity().getIntent().getStringExtra(PAGE_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(CreateCompanyQueueViewModel.class);
        binding = FragmentCreateCompanyQueueBinding.inflate(inflater, container, false);
        viewModel.onPageInit(page, companyId, getResources().getStringArray(R.array.lifetime), this);

        initBackButtonPressed();

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
        binding.buttonClose.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }


    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            viewModel.navigateBack(page, this);
        });
    }

    private void initNextButton() {
        binding.buttonNext.setOnClickListener(v -> {
            viewModel.navigateNext(page, this, companyId);
        });
    }

    private void setAdapter() {
        mainAdapter.addDelegate(delegate);
        mainAdapter.addDelegate(new LocationDelegate());
        mainAdapter.addDelegate(new WorkerDelegate());
        mainAdapter.addDelegate(new TextViewHeaderDelegate());
        mainAdapter.addDelegate(new AutoCompleteTextDelegate());
        mainAdapter.addDelegate(new FloatingActionButtonDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);
    }

    private void initBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                viewModel.navigateBack(page, CreateCompanyQueueFragment.this);
            }
        });
    }
}