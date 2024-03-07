package com.example.myapplication.presentation.profile.createAccount.chooseFragment;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.STATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentChooseBinding;

public class ChooseFragment extends Fragment {

    FragmentChooseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChooseBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initCompanyButton();
        initYourselfButton();
    }

    private void initYourselfButton() {
        binding.forYourselfLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(STATE, BASIC);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_chooseFragment_to_profileLoggedFragment, bundle);
        });
    }

    private void initCompanyButton(){
        binding.forCompanyLayout.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(ChooseFragmentDirections.actionChooseFragmentToCreateCompanyAccountFragment(PAGE_1));
        });
    }
}