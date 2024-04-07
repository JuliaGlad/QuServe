package com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails;

import static com.example.myapplication.presentation.utils.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;

import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentWorkerQueueDetailsBinding;
import com.example.myapplication.presentation.basicQueue.queueDetails.QueueDetailsFragment;
import com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.model.WorkerQueueDetailsModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.state.WorkerQueueDetailsState;
import com.example.myapplication.presentation.dialogFragments.finishQueue.FinishQueueDialogFragment;
import com.example.myapplication.presentation.dialogFragments.pauseQueue.PauseQueueDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegate;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewModel;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailButtonModel;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailsButtonDelegateItem;

public class WorkerQueueDetailsFragment extends Fragment {
    private FragmentWorkerQueueDetailsBinding binding;
    private WorkerQueueDetailsViewModel viewModel;
    private final MainAdapter mainAdapter = new MainAdapter();
    private List<DelegateItem> list = new ArrayList<>();
    private String queueId, companyId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WorkerQueueDetailsViewModel.class);
        queueId = getActivity().getIntent().getStringExtra(QUEUE_ID);
        companyId = getActivity().getIntent().getStringExtra(COMPANY_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel.getCompanyQueueById(companyId, queueId);
        binding = FragmentWorkerQueueDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setupObserves();
        initBackButton();
        initMenuButton();
    }

    private void setAdapter() {
        mainAdapter.addDelegate(new ImageViewDelegate());
        mainAdapter.addDelegate(new QueueDetailButtonDelegate());
        mainAdapter.addDelegate(new AdviseBoxDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof WorkerQueueDetailsState.Success){
                WorkerQueueDetailsModel model = ((WorkerQueueDetailsState.Success)state).data;

                if (!model.isPaused()) {
                    binding.queueName.setText(model.getName());
                    Uri uri = model.getUri();
                    initRecycler(uri);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(QUEUE_ID, queueId);
                    bundle.putString(COMPANY_ID, companyId);
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_workerQueueDetailsFragment_to_pauseWorkerQueueFragment, bundle);
                }

            } else if (state instanceof WorkerQueueDetailsState.Loading){

            } else if (state instanceof WorkerQueueDetailsState.Error){

            }
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

    private void initRecycler(Uri uri) {
        buildList(new DelegateItem[]{
                new ImageViewDelegateItem(new ImageViewModel(1, uri)),

                new AdviseBoxDelegateItem(new AdviseBoxModel(2, R.string.here_you_can_see_queue_details)),

                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(3, R.string.download_pdf, R.string.dowload_pdf_description, R.drawable.ic_qrcode,
                        () -> { viewModel.getQrCodePdf(queueId); })),

                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(4, R.string.pause_queue,
                        R.string.pause_queue_description, R.drawable.ic_time, () -> {
                    showTimePickerDialog(queueId);
                })),

                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(5, R.string.participants_list, R.string.participants_list_description, R.drawable.ic_group, () -> {

                    Bundle bundle = new Bundle();
                    bundle.putString(COMPANY_ID, companyId);
                    bundle.putString(QUEUE_ID, queueId);
                    bundle.putString(APP_STATE, COMPANY);

                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_workerQueueDetailsFragment_to_workerParticipantsListFragment, bundle);
                })),
        });
    }

    private void showTimePickerDialog(String queueId) {
        PauseQueueDialogFragment dialogFragment = new PauseQueueDialogFragment(queueId, companyId, COMPANY);

        dialogFragment.show(getActivity().getSupportFragmentManager(), "PAUSE_QUEUE_DIALOG");

        dialogFragment.onDismissListener(bundle -> {
            bundle.putString(QUEUE_ID, queueId);
            bundle.putString(COMPANY_ID, companyId);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_workerQueueDetailsFragment_to_pauseWorkerQueueFragment, bundle);
        });
    }

    private void showFinishQueueDialog() {
        final FinishQueueDialogFragment dialogFragment = new FinishQueueDialogFragment(queueId, COMPANY, companyId);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "FINISH_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {
            requireActivity().finish();
        });
    }

    private void buildList(DelegateItem[] items) {
        list = Arrays.asList(items);
        mainAdapter.submitList(list);
        binding.progressBar.setVisibility(View.GONE);
    }
}