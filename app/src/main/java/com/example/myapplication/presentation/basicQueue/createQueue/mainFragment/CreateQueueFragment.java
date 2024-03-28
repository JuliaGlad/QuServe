package com.example.myapplication.presentation.basicQueue.createQueue.mainFragment;

import static android.os.Build.VERSION.SDK_INT;
import static com.example.myapplication.presentation.basicQueue.createQueue.arguments.Arguments.queueName;
import static com.example.myapplication.presentation.basicQueue.createQueue.arguments.Arguments.queueTime;
import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.FINE_PERMISSION_CODE;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.Utils.STATE;
import static com.example.myapplication.presentation.utils.Utils.stringsTimeArray;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
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
import com.example.myapplication.databinding.FragmentCreateQueueBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextModel;
import myapplication.android.ui.recycler.ui.items.items.button.ButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextModel;
import myapplication.android.ui.recycler.ui.items.items.progressBar.ProgressBarDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderModel;

public class CreateQueueFragment extends Fragment {

    private final EditTextDelegate delegate = new EditTextDelegate();
    private CreateQueueViewModel viewModel;
    private FragmentCreateQueueBinding binding;
    private MainAdapter mainAdapter = new MainAdapter();

    private String page;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            page = CreateQueueFragmentArgs.fromBundle(getArguments()).getPage();
        }catch (IllegalArgumentException e){
            page = requireActivity().getIntent().getStringExtra(PAGE_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(CreateQueueViewModel.class);
        binding = FragmentCreateQueueBinding.inflate(inflater, container, false);
        onPageInit(page, getResources().getStringArray(R.array.lifetime));

        initBackButtonPressed();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (page.equals(PAGE_2)){
            binding.buttonNext.setText(R.string.finish);
            binding.companyProgressBar.setProgress(50);
        }

        setMainAdapter();
        initNextButton();
        initCloseButton();
        initBackButton();

        setupObserves();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainAdapter = null;
        viewModel.setArgumentsNull();
    }

    private void onPageInit(String page, String[] array) {
        switch (page) {
            case PAGE_1:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_name, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.name, queueName, InputType.TYPE_CLASS_TEXT, true, stringName -> {
                            queueName = stringName;
                        }))
                });
                break;

            case PAGE_2:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.set_queue_life_time, 24)),
                        new AutoCompleteTextDelegateItem(new AutoCompleteTextModel(3, R.array.lifetime, R.string.no_set_lifetime, stringTime -> {
                            for (int i = 0; i < array.length; i++) {
                                if (array[i].equals(stringTime)) {
                                    queueTime = stringsTimeArray[i];
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
                if (queueName == null) {
                    Snackbar.make(getView(), "You need to write name", Snackbar.LENGTH_LONG).show();
                } else {
                    NavHostFragment.findNavController(this)
                            .navigate(CreateQueueFragmentDirections.actionCreateQueueFragmentSelf(PAGE_2));
                }
                break;
            case PAGE_2:

                if (askPermission()) {
                    viewModel.initQueueData();
                    viewModel.setArgumentsNull();
                }
                break;
        }
    }

    public void navigateBack(String page) {

        switch (page) {
            case PAGE_1:
                requireActivity().finish();
                break;
            case PAGE_2:
                NavHostFragment.findNavController(this)
                        .navigate(CreateQueueFragmentDirections.actionCreateQueueFragmentSelf(PAGE_1));
                break;

        }
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

    private void initCloseButton() {
        binding.buttonClose.setOnClickListener(v -> {
            requireActivity().finish();
            viewModel.setArgumentsNull();
        });
    }

    private void setMainAdapter() {

        mainAdapter.addDelegate(new ProgressBarDelegate());
        mainAdapter.addDelegate(delegate);
        mainAdapter.addDelegate(new TextViewHeaderDelegate());
        mainAdapter.addDelegate(new AutoCompleteTextDelegate());
        mainAdapter.addDelegate(new ButtonDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {

        viewModel.onComplete.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                Bundle bundle = new Bundle();
                bundle.putString(STATE, BASIC);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_createQueueFragment_to_finishedQueueCreationFragment, bundle);
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

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = new ArrayList<>(Arrays.asList(items));
        mainAdapter.submitList(list);
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
}

