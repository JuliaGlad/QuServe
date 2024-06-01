package com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;
import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEditCompanyBinding;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany.model.EditCompanyModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany.state.EditCompanyState;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;

public class EditCompanyFragment extends Fragment {

    private EditCompanyViewModel viewModel;
    private FragmentEditCompanyBinding binding;
    private String companyId, state, name, phone, email;
    private Uri imageUri = Uri.EMPTY;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EditCompanyViewModel.class);

        setActivityResultLauncher();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        companyId = sharedPreferences.getString(COMPANY_ID, null);
        state = sharedPreferences.getString(APP_STATE, ANONYMOUS);

        switch (state) {
            case COMPANY:
                viewModel.getCompanyData(companyId);
                break;
            case RESTAURANT:
                viewModel.getRestaurantData(companyId);
                break;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCompanyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();

        initChangeLogo();
        initBackButton();
        initSaveButton();
    }

    private void initChangeLogo() {
        binding.companyLogo.setOnClickListener(v -> {
            initImagePicker();
        });
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v ->
                requireActivity().finish());
    }

    private void initImagePicker() {
        ImagePicker.with(this)
                .cropSquare()
                .compress(512)
                .maxResultSize(512, 512)
                .createIntent(intent -> {
                    activityResultLauncher.launch(intent);
                    return null;
                });
    }

    private void setActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            imageUri = data.getData();
                            if (imageUri != null) {
                                Glide.with(EditCompanyFragment.this).load(imageUri).apply(RequestOptions.circleCropTransform())
                                        .into(binding.companyLogo);
                            }
                        }
                    }
                });
    }

    private void initSaveButton() {
        binding.buttonSave.setOnClickListener(v -> {
            name = binding.editLayoutCompanyName.getText().toString();
            email = binding.editLayoutCompanyEmail.getText().toString();
            phone = binding.editLayoutCompanyPhone.getText().toString();
            if (name != null) {
                switch (state) {
                    case COMPANY:
                        viewModel.updateData(companyId, name, phone, imageUri);
                        break;
                    case RESTAURANT:
                        viewModel.updateRestaurantData(companyId, email, name, phone, imageUri);
                        break;
                }
            } else {
                Snackbar.make(requireView(), getString(R.string.name_cannot_be_null), LENGTH_LONG).show();
            }
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof EditCompanyState.Success) {
                EditCompanyModel model = ((EditCompanyState.Success) state).data;
                binding.editLayoutCompanyName.setText(model.getName());
                binding.editLayoutCompanyEmail.setText(model.getEmail());
                binding.editLayoutCompanyPhone.setText(model.getPhone());
                binding.editLayoutService.setText(model.getService());

                Uri uri = model.getUri();
                if (uri != Uri.EMPTY) {
                    Glide.with(this)
                            .load(uri)
                            .addListener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                                    binding.progressLayout.getRoot().setVisibility(View.GONE);
                                    binding.errorlayout.getRoot().setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .apply(RequestOptions.circleCropTransform())
                            .into(binding.companyLogo);

                } else {
                    binding.progressLayout.getRoot().setVisibility(View.GONE);
                    binding.errorlayout.getRoot().setVisibility(View.GONE);
                }

            } else if (state instanceof EditCompanyState.Loading) {
                binding.progressLayout.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof EditCompanyState.Error) {
                setErrorLayout();
            }
        });

        viewModel.navigateBack.observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                requireActivity().finish();
            }
        });
    }

    private void setErrorLayout() {
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorlayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorlayout.getRoot().setOnClickListener(v -> {
            switch (state) {
                case COMPANY:
                    viewModel.getCompanyData(companyId);
                    break;
                case RESTAURANT:
                    viewModel.getRestaurantData(companyId);
                    break;
            }
        });
    }
}