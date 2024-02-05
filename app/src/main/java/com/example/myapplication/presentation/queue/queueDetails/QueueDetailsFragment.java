package com.example.myapplication.presentation.queue.queueDetails;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentQueueDetailsBinding;
import com.example.myapplication.presentation.queue.queueDetails.finishQueueButton.FinishQueueButtonDelegate;
import com.example.myapplication.presentation.queue.queueDetails.queueDetailsButton.QueueDetailButtonDelegate;

import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegate;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;

public class QueueDetailsFragment extends Fragment {

    private QueueDetailsViewModel viewModel;
    private FragmentQueueDetailsBinding binding;
    private Button yes, no;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(QueueDetailsViewModel.class);

        binding = FragmentQueueDetailsBinding.inflate(inflater, container, false);
        viewModel.getQueueRecycler(() -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_queueDetailsFragment_to_participantsListFragment));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMainAdapter();
        setupObserves();

        initBackButton();
    }

    private void initBackButton() {
        binding.imageButtonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }


    private void setMainAdapter() {
        mainAdapter.addDelegate(new StringTextViewDelegate());
        mainAdapter.addDelegate(new ImageViewDelegate());
        mainAdapter.addDelegate(new QueueDetailButtonDelegate());
        mainAdapter.addDelegate(new FinishQueueButtonDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);


        viewModel.pdfUri.observe(getViewLifecycleOwner(), uriTask -> {
            uriTask.addOnSuccessListener(uri -> {

                String uriString = uri.toString();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
                requireActivity().startActivity(intent);
            });
        });
    }
}