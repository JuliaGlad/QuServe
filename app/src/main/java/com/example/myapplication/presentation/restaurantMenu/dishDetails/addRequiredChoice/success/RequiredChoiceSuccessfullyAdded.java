package com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice.success;

import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_VARIANT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRequireChoiceSuccessfullyAddedBinding;

import java.util.ArrayList;
import java.util.List;

public class RequiredChoiceSuccessfullyAdded extends Fragment {

    private FragmentRequireChoiceSuccessfullyAddedBinding binding;
    private String name;
    private ArrayList<String> variant;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString(CHOICE_NAME);
        variant = getArguments().getStringArrayList(CHOICE_VARIANT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRequireChoiceSuccessfullyAddedBinding.inflate(inflater, container, false);
        binding.infoBoxLayout.body.setText(getString(R.string.your_visitors_will_have_to_choose_one_of_added_options_before_ordering_this_dish));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        super.onViewCreated(view, savedInstanceState);
        initButtonOk();
        handleBackButtonPressed();
    }

    private void handleBackButtonPressed() {
        getActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack();
            }
        });
    }

    private void initButtonOk() {
        binding.buttonOk.setOnClickListener(v -> {
            navigateBack();
        });
    }

    private void navigateBack() {
        Intent intent = new Intent();
        intent.putExtra(CHOICE_NAME, name);
        intent.putStringArrayListExtra(CHOICE_VARIANT, variant);

        getActivity().setResult(Activity.RESULT_OK, intent);
        requireActivity().finish();
    }
}
