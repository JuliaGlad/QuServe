package com.example.myapplication.presentation.queue.main;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.app.AppState;
import com.example.myapplication.databinding.FragmentServiceBinding;
import com.example.myapplication.presentation.queue.main.basicUser.BasicUserServiceFragment;
import com.example.myapplication.presentation.queue.main.queueCompanyOwner.CompanyQueueServiceFragment;

public class ServiceFragment extends Fragment {
    private FragmentServiceBinding binding;
    private ServiceModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ServiceModel.class);
        viewModel.getState();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
    }

    private void setupObserves() {
        viewModel.appState.observe(getViewLifecycleOwner(), appState -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (appState instanceof AppState.BasicState) {
                fragmentManager.beginTransaction()
                        .replace(R.id.service_container, BasicUserServiceFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();

            } else if (appState instanceof AppState.CompanyState) {
                String companyId = ((AppState.CompanyState) appState).companyId;

                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, companyId);

                fragmentManager.beginTransaction()
                        .replace(R.id.service_container, CompanyQueueServiceFragment.class, bundle)
                        .setReorderingAllowed(true)
                        .commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}