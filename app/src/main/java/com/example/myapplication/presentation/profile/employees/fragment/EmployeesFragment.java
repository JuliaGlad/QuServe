package com.example.myapplication.presentation.profile.employees.fragment;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEmployeesBinding;
import com.example.myapplication.presentation.profile.employees.delegateRecycler.RecyclerDelegate;

import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.searchView.SearchViewDelegate;
import myapplication.android.ui.recycler.ui.items.items.tabLayout.TabLayoutDelegate;

public class EmployeesFragment extends Fragment {

    private EmployeesViewModel viewModel;
    private FragmentEmployeesBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(EmployeesViewModel.class);
        binding = FragmentEmployeesBinding.inflate(inflater, container, false);
        viewModel.getEmployees(getActivity().getIntent().getStringExtra(COMPANY_ID));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        setAdapter();
    }

    private void setAdapter(){
        mainAdapter.addDelegate(new SearchViewDelegate());
        mainAdapter.addDelegate(new TabLayoutDelegate());
        mainAdapter.addDelegate(new FloatingActionButtonDelegate());
        mainAdapter.addDelegate(new RecyclerDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);

        viewModel.image.observe(getViewLifecycleOwner(), uriTask -> {
            uriTask.addOnCompleteListener(task -> {
                final View dialogView = getLayoutInflater().inflate(R.layout.dialog_employee_qr_code, null);
                AlertDialog qrCodeDialog = new AlertDialog.Builder(getContext())
                        .setView(dialogView).create();

                qrCodeDialog.show();

                Button ok = dialogView.findViewById(R.id.ok_button);
                ImageView imageView = dialogView.findViewById(R.id.qr_code);

                Glide.with(requireContext()).load(task.getResult()).into(imageView);

                ok.setOnClickListener(view -> {
                    qrCodeDialog.dismiss();
                });
            });
        });
    }
}