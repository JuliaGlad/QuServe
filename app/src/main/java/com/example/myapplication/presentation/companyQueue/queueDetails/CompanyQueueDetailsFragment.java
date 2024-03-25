package com.example.myapplication.presentation.companyQueue.queueDetails;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCompanyQueueDetailsBinding;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailButtonDelegate;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegate;

public class CompanyQueueDetailsFragment extends Fragment {

    private String companyId, queueId;
    private CompanyQueueDetailsViewModel viewModel;
    private FragmentCompanyQueueDetailsBinding binding;
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
        initMenuButton();
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            String name = getArguments().getString(QUEUE_NAME_KEY);
            binding.queueName.setText(name);
        }catch (NullPointerException e){
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
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_finish_queue, null);
        AlertDialog finishQueueDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        finishQueueDialog.show();

        Button finishQueue = dialogView.findViewById(R.id.finish_button);
        Button cancel = dialogView.findViewById(R.id.cancel_button);

        finishQueue.setOnClickListener(view -> {
            viewModel.finishQueue(queueId, companyId)
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

        viewModel.openParticipants.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){

                viewModel.setDataNull();

                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, companyId);
                bundle.putString(QUEUE_ID, queueId);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_companyQueueDetailsFragment_to_companyQueueParticipantsListFragment, bundle);


            }
        });

        viewModel.openEditQueue.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){

                Bundle bundle = new Bundle();

                bundle.putString(QUEUE_ID, queueId);
                bundle.putString(COMPANY_ID, companyId);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_companyQueueDetailsFragment_to_editQueueFragment, bundle);
            }
        });

        viewModel.name.observe(getViewLifecycleOwner(), s -> {
            binding.queueName.setText(s);
        });

        viewModel.pdfUri.observe(getViewLifecycleOwner(), uri -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            requireActivity().startActivity(intent);
        });
    }
}