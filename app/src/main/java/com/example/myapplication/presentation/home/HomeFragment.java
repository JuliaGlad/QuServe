package com.example.myapplication.presentation.home;

import static com.example.myapplication.presentation.utils.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.presentation.home.basicUser.HomeBasisUserFragment;
import com.example.myapplication.presentation.home.companyUser.HomeQueueCompanyUserFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
    }

    private void setView() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String type = sharedPreferences.getString(APP_STATE, ANONYMOUS);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch (type){
            case ANONYMOUS:
                break;
            case BASIC:
                fragmentManager.beginTransaction()
                        .replace(R.id.home_container, HomeBasisUserFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case COMPANY:
                String companyId = sharedPreferences.getString(COMPANY_ID, null);

                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, companyId);

                fragmentManager.beginTransaction()
                        .replace(R.id.home_container, HomeQueueCompanyUserFragment.class, bundle)
                        .setReorderingAllowed(true)
                        .commit();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}