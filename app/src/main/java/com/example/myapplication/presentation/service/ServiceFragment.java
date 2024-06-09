package com.example.myapplication.presentation.service;

import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.BASIC;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;

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
import com.example.myapplication.databinding.FragmentServiceBinding;
import com.example.myapplication.presentation.service.basicUser.BasicUserServiceFragment;
import com.example.myapplication.presentation.service.queueCompanyOwner.CompanyQueueServiceFragment;
import com.example.myapplication.presentation.service.restaurantService.ServiceRestaurant;

public class ServiceFragment extends Fragment {
    private FragmentServiceBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
    }

    private void setView() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String type = sharedPreferences.getString(APP_STATE, ANONYMOUS);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        switch (type) {
            case ANONYMOUS:
            case BASIC:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                        .replace(R.id.service_container, BasicUserServiceFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case COMPANY:
                String companyId = sharedPreferences.getString(COMPANY_ID, null);

                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, companyId);

                fragmentManager.beginTransaction()
                        .replace(R.id.service_container, CompanyQueueServiceFragment.class, bundle)
                        .setReorderingAllowed(true)
                        .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                        .commit();
                break;
            case RESTAURANT:

                fragmentManager.beginTransaction()
                        .replace(R.id.service_container, ServiceRestaurant.class, null)
                        .setReorderingAllowed(true)
                        .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                        .commit();

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}