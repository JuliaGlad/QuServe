package com.example.myapplication.presentation.basicQueue.queueDetails;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.PAUSED_TIME;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.presentation.basicQueue.queueDetails.model.QueueDetailsModel;
import com.example.myapplication.presentation.basicQueue.queueDetails.state.QueueDetailsState;
import com.example.myapplication.presentation.dialogFragments.finishQueue.FinishQueueDialogFragment;
import com.example.myapplication.presentation.dialogFragments.pauseQueue.PauseQueueDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.listeners.QueueDetailButtonItemListener;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewModel;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailButtonDelegate;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegate;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailButtonModel;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailsButtonDelegateItem;

public class QueueDetailsFragment extends Fragment {

    private QueueDetailsViewModel viewModel;
    private FragmentQueueDetailsBinding binding;
    private String queueId;
    private List<DelegateItem> list = new ArrayList<>();;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(QueueDetailsViewModel.class);
        binding = FragmentQueueDetailsBinding.inflate(inflater, container, false);

        viewModel.getQueue(this);
        viewModel.getQueueRecycler();

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

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof QueueDetailsState.Success){
                if (list.size() == 0) {
                    QueueDetailsModel model = ((QueueDetailsState.Success) state).data;
                    binding.queueName.setText(model.getName());
                    Uri uri = model.getUri();
                    queueId = model.getId();
                    initRecycler(uri, queueId);
                }

            } else if (state instanceof QueueDetailsState.Loading){
                binding.progressBar.setVisibility(View.VISIBLE);

            } else if (state instanceof QueueDetailsState.Error){

            }
        });

        viewModel.pdfUri.observe(getViewLifecycleOwner(), uri -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            requireActivity().startActivity(intent);
        });
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

    private void showTimePickerDialog(String queueId) {
        PauseQueueDialogFragment dialogFragment = new PauseQueueDialogFragment(queueId);

        dialogFragment.show(getActivity().getSupportFragmentManager(), "PAUSE_QUEUE_DIALOG");

        dialogFragment.onDismissListener(bundle -> {
            NavHostFragment.findNavController(QueueDetailsFragment.this)
                    .navigate(R.id.action_queueDetailsFragment_to_pausedQueueFragment, bundle);
        });
    }

    private void showFinishQueueDialog() {
        final FinishQueueDialogFragment dialogFragment = new FinishQueueDialogFragment(queueId, BASIC, null);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "FINISH_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {
            requireActivity().finish();
        });
    }

    private void initRecycler(Uri uri, String queueId) {
        buildList(new DelegateItem[]{
                new ImageViewDelegateItem(new ImageViewModel(1, uri)),

                new AdviseBoxDelegateItem(new AdviseBoxModel(2, R.string.here_you_can_see_queue_details)),

                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(3, R.string.download_pdf, R.string.dowload_pdf_description, R.drawable.ic_qrcode,
                        () -> { viewModel.getQrCodePdf(); })),

                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(4, R.string.pause_queue,
                        R.string.pause_queue_description, R.drawable.ic_time, () -> {
                            showTimePickerDialog(queueId);
                        })),

                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(5, R.string.participants_list, R.string.participants_list_description, R.drawable.ic_group, () -> {
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_queueDetailsFragment_to_participantsListFragment);
                })),
        });
    }

    private void buildList(DelegateItem[] items) {
        list = Arrays.asList(items);
        mainAdapter.submitList(list);
        binding.progressBar.setVisibility(View.GONE);
    }
}