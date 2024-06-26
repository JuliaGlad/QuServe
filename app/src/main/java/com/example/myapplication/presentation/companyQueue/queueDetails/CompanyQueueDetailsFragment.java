package com.example.myapplication.presentation.companyQueue.queueDetails;

import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.IS_DEFAULT;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_NAME_KEY;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCompanyQueueDetailsBinding;
import com.example.myapplication.presentation.companyQueue.queueDetails.model.CompanyQueueDetailModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.state.CompanyQueueDetailsState;
import com.example.myapplication.presentation.dialogFragments.finishQueue.FinishQueueDialogFragment;

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

public class CompanyQueueDetailsFragment extends Fragment {

    private String companyId, queueId, queueName;
    private CompanyQueueDetailsViewModel viewModel;
    private FragmentCompanyQueueDetailsBinding binding;
    private List<DelegateItem> list = new ArrayList<>();
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyId = requireActivity().getIntent().getStringExtra(COMPANY_ID);
        queueId = requireActivity().getIntent().getStringExtra(QUEUE_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(CompanyQueueDetailsViewModel.class);
        binding = FragmentCompanyQueueDetailsBinding.inflate(inflater, container, false);

        viewModel.getQueueRecycler(queueId, companyId);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMainAdapter();
        setupObserves();
        initBackButtonPressed();
        initBackButton();
        initMenuButton();
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            String name = getArguments().getString(QUEUE_NAME_KEY);
            binding.queueName.setText(name);
        } catch (NullPointerException e) {
            Log.d("NullPointerException", e.getMessage().toString());
        }

    }

    private void setMainAdapter() {
        mainAdapter.addDelegate(new ImageViewDelegate());
        mainAdapter.addDelegate(new QueueDetailButtonDelegate());
        mainAdapter.addDelegate(new AdviseBoxDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
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

    private void showFinishQueueDialog() {
        final FinishQueueDialogFragment dialogFragment = new FinishQueueDialogFragment(queueId, COMPANY, companyId);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "FINISH_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {
            Intent intent = new Intent();
            intent.putExtra(IS_DEFAULT, false);
            intent.putExtra(QUEUE_ID, queueId);
            requireActivity().setResult(Activity.RESULT_OK, intent);
            requireActivity().finish();
        });
    }

    private void initRecyclerView(String queueId, Uri uri) {
        buildList(new DelegateItem[]{
                new ImageViewDelegateItem(new ImageViewModel(1, uri)),
                new AdviseBoxDelegateItem(new AdviseBoxModel(2, R.string.here_you_can_see_queue_details)),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(3, R.string.download_pdf, R.string.dowload_pdf_description, R.drawable.ic_qrcode,
                        () -> {
                            viewModel.getQrCodePdf(queueId);
                        })),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(4, R.string.edit_queue, R.string.edit_queue_description, R.drawable.ic_edit, this::openEditQueueFragment)),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(5, R.string.participants_list, R.string.participants_list_description, R.drawable.ic_group, this::openParticipantsListFragment)),
        });
    }

    private void buildList(DelegateItem[] items) {
        list = Arrays.asList(items);
        mainAdapter.submitList(list);
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

    private void initBackButton() {
        binding.imageButton.setOnClickListener(v -> {
            navigateBack();
        });
    }

    private void navigateBack() {
        Intent intent = new Intent();
        intent.putExtra(QUEUE_NAME_KEY, queueName);
        intent.putExtra(QUEUE_ID, queueId);
        intent.putExtra(IS_DEFAULT, true);
        requireActivity().setResult(Activity.RESULT_OK, intent);
        requireActivity().finish();
    }

    private void openEditQueueFragment() {
        Bundle bundle = new Bundle();

        bundle.putString(QUEUE_ID, queueId);
        bundle.putString(COMPANY_ID, companyId);

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_companyQueueDetailsFragment_to_editQueueFragment, bundle);
    }

    private void openParticipantsListFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(COMPANY_ID, companyId);
        bundle.putString(QUEUE_ID, queueId);

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_companyQueueDetailsFragment_to_companyQueueParticipantsListFragment, bundle);
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof CompanyQueueDetailsState.Success) {
                if (list.isEmpty()) {
                    CompanyQueueDetailModel model = ((CompanyQueueDetailsState.Success) state).data;
                    queueName = model.getName();
                    binding.queueName.setText(queueName);
                    initRecyclerView(queueId, model.getUri());
                }
            } else if (state instanceof CompanyQueueDetailsState.Loading) {
                binding.progressLayout.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof CompanyQueueDetailsState.Error) {
                setErrorLayout();
            }
        });

        viewModel.pdfUri.observe(getViewLifecycleOwner(), uri -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            requireActivity().startActivity(intent);
        });
    }

    private void setErrorLayout() {
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getQueueRecycler(queueId, companyId);
        });
    }

    private void initBackButtonPressed(){
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
              navigateBack();
            }
        });
    }
}