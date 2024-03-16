package com.example.myapplication.presentation.queue.basic.createQueue.mainFragment;

import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCreateQueueBinding;

import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.button.ButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.progressBar.ProgressBarDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;

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

        viewModel.onPageInit(page, getResources().getStringArray(R.array.lifetime));
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
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            viewModel.navigateBack(page, this);
        });
    }

    private void initNextButton() {
        binding.buttonNext.setOnClickListener(v -> {
            viewModel.navigateNext(page, this);
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
        viewModel.items.observe(getViewLifecycleOwner(), delegateItems -> {
            mainAdapter.submitList(delegateItems);
        });
    }

}

