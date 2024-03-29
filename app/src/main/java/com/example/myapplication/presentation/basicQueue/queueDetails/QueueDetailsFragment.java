package com.example.myapplication.presentation.basicQueue.queueDetails;

import static com.example.myapplication.presentation.utils.Utils.PAUSED_TIME;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentQueueDetailsBinding;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailButtonDelegate;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegate;

public class QueueDetailsFragment extends Fragment {

    private QueueDetailsViewModel viewModel;
    private FragmentQueueDetailsBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(QueueDetailsViewModel.class);
        binding = FragmentQueueDetailsBinding.inflate(inflater, container, false);

        viewModel.getQueue(this);
        viewModel.getQueueRecycler(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMainAdapter();
        setupObserves();
        initBackButton();
        initMenuButton();
    }

    private void initMenuButton() {
        binding.buttonMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.queue_details_menu, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
                showFinishQueueDialog();
                return false;
            });
        });
    }

    private void initBackButton() {
        binding.imageButton.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setMainAdapter() {
        mainAdapter.addDelegate(new ImageViewDelegate());
        mainAdapter.addDelegate(new QueueDetailButtonDelegate());
        mainAdapter.addDelegate(new AdviseBoxDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void showTimePickerDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_number_picker, null);
        AlertDialog pauseQueueDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        pauseQueueDialog.show();

        NumberPicker numberPicker = dialogView.findViewById(R.id.time_picker);
        numberPicker.setMinValue(5);
        numberPicker.setMaxValue(20);
        numberPicker.setValue(10);
        numberPicker.setWrapSelectorWheel(false);

        Button pauseQueue = dialogView.findViewById(R.id.pause_button);
        Button cancel = dialogView.findViewById(R.id.cancel_button);

        pauseQueue.setOnClickListener(view -> {
            viewModel.pauseQueue(String.valueOf(numberPicker.getValue()))
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            pauseQueueDialog.dismiss();
                            Bundle bundle = new Bundle();
                            bundle.putInt(PAUSED_TIME, numberPicker.getValue());
                            NavHostFragment.findNavController(QueueDetailsFragment.this)
                                    .navigate(R.id.action_queueDetailsFragment_to_pausedQueueFragment, bundle);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        });

        cancel.setOnClickListener(view -> {
            pauseQueueDialog.dismiss();
        });
    }

    private void showFinishQueueDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_finish_queue, null);
        AlertDialog finishQueueDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        finishQueueDialog.show();

        Button finishQueue = dialogView.findViewById(R.id.finish_button);
        Button cancel = dialogView.findViewById(R.id.cancel_button);

        finishQueue.setOnClickListener(view -> {
            viewModel.finishQueue()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            requireActivity().finish();
                            finishQueueDialog.dismiss();
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        });

        cancel.setOnClickListener(view -> {
            finishQueueDialog.dismiss();
        });

    }

    private void setupObserves() {
        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);

        viewModel.name.observe(getViewLifecycleOwner(), s -> {
            binding.queueName.setText(s);
        });

        viewModel.finishQueue.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showFinishQueueDialog();
            }
        });

        viewModel.pauseQueue.observe(getViewLifecycleOwner(), aBoolean -> {
            showTimePickerDialog();
        });

        viewModel.pdfUri.observe(getViewLifecycleOwner(), uri -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            requireActivity().startActivity(intent);
        });
    }
}