package com.example.myapplication.presentation.companyQueue.createQueue.main;

import static android.os.Build.VERSION.SDK_INT;
import static com.example.myapplication.presentation.companyQueue.createQueue.main.Arguments.city;
import static com.example.myapplication.presentation.companyQueue.createQueue.main.Arguments.employeeModels;
import static com.example.myapplication.presentation.companyQueue.createQueue.main.Arguments.queueId;
import static com.example.myapplication.presentation.companyQueue.createQueue.main.Arguments.queueLocation;
import static com.example.myapplication.presentation.companyQueue.createQueue.main.Arguments.queueName;
import static com.example.myapplication.presentation.companyQueue.createQueue.main.Arguments.queueTime;
import static com.example.myapplication.presentation.utils.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.FINE_PERMISSION_CODE;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.PAGE_3;
import static com.example.myapplication.presentation.utils.Utils.PAGE_4;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LOCATION_KEY;
import static com.example.myapplication.presentation.utils.Utils.STATE;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_LIST;
import static com.example.myapplication.presentation.utils.Utils.stringsTimeArray;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCreateCompanyQueueBinding;
import myapplication.android.ui.recycler.ui.items.items.chooseLocation.LocationDelegate;
import myapplication.android.ui.recycler.ui.items.items.chooseLocation.LocationDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.chooseLocation.LocationModel;
import com.example.myapplication.presentation.companyQueue.createQueue.delegates.workers.WorkerDelegate;
import com.example.myapplication.presentation.companyQueue.createQueue.delegates.workers.WorkerDelegateItem;
import com.example.myapplication.presentation.companyQueue.createQueue.delegates.workers.WorkerModel;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextModel;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextModel;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonModel;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderModel;

public class CreateCompanyQueueFragment extends Fragment {

    private CreateCompanyQueueViewModel viewModel;
    private final EditTextDelegate delegate = new EditTextDelegate();
    private FragmentCreateCompanyQueueBinding binding;
    private String page, companyId;
    List<DelegateItem> itemList = new ArrayList<>();
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
        onPageInit(page, getResources().getStringArray(R.array.lifetime));

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
            navigateBack(page);
        });
    }

    private void initNextButton() {
        binding.buttonNext.setOnClickListener(v -> {
            navigateNext(page);
        });
    }

    public void onPageInit(String page, String[] array) {

        switch (page) {
            case PAGE_1:
                itemList.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.enter_name, 24)));
                itemList.add(new EditTextDelegateItem(new EditTextModel(3, R.string.name, queueName, InputType.TYPE_CLASS_TEXT, true, stringName -> {
                    queueName = stringName;
                })));
                break;

            case PAGE_2:
                binding.companyProgressBar.setProgress(25, true);
                itemList.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.set_queue_life_time, 24)));
                itemList.add(new AutoCompleteTextDelegateItem(new AutoCompleteTextModel(3, R.array.lifetime, R.string.no_set_lifetime, stringTime -> {
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].equals(stringTime)) {
                            queueTime = stringsTimeArray[i];
                        }
                    }
                })));
                break;

            case PAGE_3:
                binding.companyProgressBar.setProgress(50, true);
                if (getArguments().getString(QUEUE_LOCATION_KEY) != null) {
                    queueLocation = getArguments().getString(QUEUE_LOCATION_KEY);
                    city = getArguments().getString(CITY_KEY);
                }
                itemList.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.choose_queue_location, 24)));
                itemList.add(new LocationDelegateItem(new LocationModel(2, queueLocation, () -> {
                    if (checkSelfMapPermission()) {

                        Bundle bundle = new Bundle();
                        bundle.putString(STATE, COMPANY);

                        NavHostFragment.findNavController(this)
                                .navigate(R.id.action_createCompanyQueueFragment_to_mapFragment, bundle);
                    }
                })));
                break;
            case PAGE_4:
                binding.companyProgressBar.setProgress(75, true);
                if (itemList.isEmpty()) {
                    if (getArguments().getString(WORKERS_LIST) != null) {
                        String workers = getArguments().getString(WORKERS_LIST);
                        employeeModels = new Gson().fromJson(workers, new TypeToken<List<EmployeeModel>>() {
                        }.getType());
                    }

                    itemList.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.add_workers, 24)));
                    addWorkers();
                    itemList.add(new FloatingActionButtonDelegateItem(new FloatingActionButtonModel(3, () -> {
                        Bundle bundle = new Bundle();
                        bundle.putString(COMPANY_ID, companyId);
                        bundle.putString(WORKERS_LIST, new Gson().toJson(employeeModels));
                        NavHostFragment.findNavController(this)
                                .navigate(R.id.action_createCompanyQueueFragment_to_chooseWorkersFragment, bundle);
                    })));
                }
                break;
        }
        mainAdapter.submitList(itemList);
    }

    private void addWorkers() {
        if (!employeeModels.isEmpty()) {
            for (int i = 0; i < employeeModels.size(); i++) {
                itemList.add(new WorkerDelegateItem(new WorkerModel(i + 1, employeeModels.get(i).getName())));
            }
        }
    }

    public void navigateNext(String page) {
        switch (page) {
            case PAGE_1:
                if (queueName == null) {
                    Snackbar.make(requireView(), getString(R.string.name_cannot_be_null), Snackbar.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(PAGE_KEY, PAGE_2);
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_createCompanyQueueFragment_self, bundle);
                }
                break;
            case PAGE_2:
                if (queueTime != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PAGE_KEY, PAGE_3);
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_createCompanyQueueFragment_self, bundle);
                } else {
                    Snackbar.make(requireView(), getString(R.string.this_data_is_required), Snackbar.LENGTH_LONG).show();
                }
                break;

            case PAGE_3:
                if (queueLocation != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PAGE_KEY, PAGE_4);
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_createCompanyQueueFragment_self, bundle);
                } else {
                    Snackbar.make(requireView(), getString(R.string.this_data_is_required), Snackbar.LENGTH_LONG).show();
                }
                break;

            case PAGE_4:
                if (askPermission()) {
                    binding.loader.setVisibility(View.VISIBLE);
                    binding.buttonNext.setEnabled(false);
                    viewModel.initQueueData(companyId);
                }
                break;
        }
    }

    public void navigateBack(String page) {
        switch (page) {

            case PAGE_1:
                viewModel.setArgumentsNull();
                requireActivity().finish();
                break;

            case PAGE_2:
                Bundle bundleSecond = new Bundle();
                bundleSecond.putString(PAGE_KEY, PAGE_1);
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_createCompanyQueueFragment_self, bundleSecond);
                break;

            case PAGE_3:
                Bundle bundleThird = new Bundle();
                bundleThird.putString(PAGE_KEY, PAGE_2);
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_createCompanyQueueFragment_self, bundleThird);
                break;

            case PAGE_4:
                Bundle bundleFourth = new Bundle();
                bundleFourth.putString(PAGE_KEY, PAGE_3);
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_createCompanyQueueFragment_self, bundleFourth);
                break;
        }
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
        viewModel.isComplete.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                Bundle bundle = new Bundle();
                bundle.putString(STATE, COMPANY);
                bundle.putString(COMPANY_ID, companyId);
                bundle.putString(QUEUE_ID, queueId);
                bundle.putString(COMPANY_NAME, queueName);

                binding.loader.setVisibility(View.GONE);
                binding.buttonNext.setEnabled(true);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_createCompanyQueueFragment_to_finishedQueueCreationFragment, bundle);
            }
        });
    }

    private void initBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack(page);
            }
        });
    }

    private boolean askPermission() {
        boolean permission = false;
        if (SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                try {

                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", requireContext().getApplicationContext().getPackageName())));
                    requireActivity().startActivity(intent);

                } catch (Exception e) {

                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    requireActivity().startActivity(intent);

                }

            } else {
                permission = true;
            }
        } else {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FINE_PERMISSION_CODE);
            }
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, FINE_PERMISSION_CODE);
            }
            permission = true;
        }
        return permission;
    }

    private boolean checkSelfMapPermission() {
        boolean permission = false;
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        } else {
            permission = true;
        }
        return permission;
    }
}