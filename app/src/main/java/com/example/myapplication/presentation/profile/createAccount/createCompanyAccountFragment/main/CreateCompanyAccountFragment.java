package com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.main;

import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.arguments.email;
import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.arguments.name;
import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.arguments.phoneNumber;
import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.arguments.service;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.PAGE_3;
import static com.example.myapplication.presentation.utils.Utils.PAGE_4;
import static com.example.myapplication.presentation.utils.Utils.PAGE_5;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.Utils.stringsServicesArray;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCreateCompanyAccountBinding;
import com.example.myapplication.presentation.utils.workers.BasicQueueFinishWorker;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextModel;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextModel;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.horizontalRecycler.HorizontalRecyclerDelegate;
import myapplication.android.ui.recycler.ui.items.items.roundImageView.RoundImageViewDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderModel;

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
        onPageInit(page);

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
            navigateNext(page);
        });

        binding.buttonBack.setOnClickListener(v -> {
            navigateBack(page);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
               navigateBack(page);
            }
        });
    }

    private void setupObserves() {
        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);

        viewModel.finished.observe(getViewLifecycleOwner(), companyId -> {
            if (companyId != null) {

                initWorker(companyId);

                viewModel.setArgumentsNull();
                NavHostFragment.findNavController(this)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentToApprovalFragment());
            }
        });

    }

    public void onPageInit(String page) {
        switch (page) {
            case PAGE_1:

                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(0, R.string.enter_company_name, 24)),
                        new EditTextDelegateItem(new EditTextModel(1, R.string.company_name, name, InputType.TYPE_CLASS_TEXT, true, stringName -> {
                            name = stringName;
                        }))
                });
                break;
            case PAGE_2:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(0, R.string.add_work_email, 24)),
                        new EditTextDelegateItem(new EditTextModel(1, R.string.work_email, email, InputType.TYPE_CLASS_TEXT, true, stringEmail -> {
                            email = stringEmail;
                        }))
                });
                break;
            case PAGE_3:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(0, R.string.add_work_phone, 24)),
                        new EditTextDelegateItem(new EditTextModel(1, R.string.work_phone_number, phoneNumber, InputType.TYPE_CLASS_PHONE, true, stringPhone -> {
                            phoneNumber = stringPhone;
                        }))
                });
                break;

            case PAGE_4:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(0, R.string.choose_service, 24)),
                        new AutoCompleteTextDelegateItem(new AutoCompleteTextModel(1, R.array.services, R.string.select_item, serviceString -> {
                            for (String item : stringsServicesArray) {
                                if (serviceString.equals(item)) {
                                    service = item;
                                    break;
                                }
                            }
                        }))
                });
                break;
        }

    }

    private void navigateNext(String page) {
        switch (page) {
            case PAGE_1:
                if (name == null) {
                    Snackbar.make(requireView(), "You field is required!", Snackbar.LENGTH_LONG).show();
                } else {
                    NavHostFragment.findNavController(this)
                            .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_2));
                }
                break;
            case PAGE_2:
                if (email == null) {
                    Snackbar.make(requireView(), "You field is required!", Snackbar.LENGTH_LONG).show();
                }
                NavHostFragment.findNavController(this)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_3));
                break;
            case PAGE_3:
                NavHostFragment.findNavController(this)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_4));
                break;

            case PAGE_4:
                if (service == null) {
                    Snackbar.make(requireView(), "You field is required!", Snackbar.LENGTH_LONG).show();
                } else {
                    viewModel.initData();
                }
                break;
        }
    }

    private void navigateBack(String page) {
        switch (page) {
            case PAGE_1:
                viewModel.setArgumentsNull();
                requireActivity().finish();
                break;
            case PAGE_2:
                NavHostFragment.findNavController(this)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_1));
                break;
            case PAGE_3:
                NavHostFragment.findNavController(this)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_2));
                break;
            case PAGE_4:
                NavHostFragment.findNavController(this)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_3));
                break;
            case PAGE_5:
                NavHostFragment.findNavController(this)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_4));
        }
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

    private void initWorker(String companyId){
        Data data = new Data.Builder()
                .putString(COMPANY_ID, companyId)
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BasicQueueFinishWorker.class)
                .setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(10, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(requireContext()).enqueue(request);
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = Arrays.asList(items);
        mainAdapter.submitList(list);
    }


}