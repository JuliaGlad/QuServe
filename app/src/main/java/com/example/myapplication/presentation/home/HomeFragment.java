package com.example.myapplication.presentation.home;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.presentation.home.basicUser.HomeBasisUserFragment;
import com.example.myapplication.presentation.home.companyUser.HomeQueueCompanyUserFragment;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel.getAppState();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupMainObserves();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void setupMainObserves() {

        viewModel.appState.observe(getViewLifecycleOwner(), appState -> {

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            if (appState instanceof AppState.BasicState) {

                fragmentManager.beginTransaction()
                        .replace(R.id.home_container, HomeBasisUserFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();

            } else if (appState instanceof AppState.CompanyState) {
                String companyId = ((AppState.CompanyState) appState).companyId;

                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, companyId);

                fragmentManager.beginTransaction()
                        .replace(R.id.home_container, HomeQueueCompanyUserFragment.class, bundle)
                        .setReorderingAllowed(true)
                        .commit();
            }

        });
    }

}