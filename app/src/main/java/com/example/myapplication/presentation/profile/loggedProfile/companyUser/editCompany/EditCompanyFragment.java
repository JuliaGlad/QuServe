package com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;
import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

        initEditTextPhone();
        initChangeLogo();
        initBackButton();
        initSaveButton();
    }

    private void initEditTextPhone() {
        setupPhoneEditText();
    }

    private void setupPhoneEditText() {
        binding.editLayoutCompanyPhone.setText("+7");
        binding.editLayoutCompanyPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onPhoneTextChanged(s, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onPhoneTextChanged(CharSequence text, int dir) {
        int size = text.length();
        int last = size - 1;

        Log.d("Text change test", "Size:" + size + "Dir:" + dir + "Text:" + text);

        StringBuilder t = new StringBuilder(binding.editLayoutCompanyPhone.getText());

        if (size <= 2 && dir == 0) {
            setTextAndSelection("+7", 2);
        }

        if (size == 3 && dir == 1) {
            setTextAndSelection(t.substring(0, last) + " (" + text.charAt(last), 5);
        }

        if (size == 7 && dir == 1) {
            setTextAndSelection(t + ")", 8);
        }

        if ((size == 8 && dir == 1)  && t.charAt(last - 1) != '-') {
            setTextAndSelection(t.substring(0, 7) + ") " + t.charAt(last), 10);
        }

        if (size == 9 && dir == 1 && t.charAt(last - 1) != ' ') {
            setTextAndSelection(t.substring(0, 8) + " " + t.charAt(last), 10);
        }

        checkZeroOneDirectional(size, 13, '-', last, dir, t.toString());

        if ((size == 16 && dir == 0) || (size == 13 && dir == 0) || (size == 8 && dir == 0)){
            setTextAndSelection(text.toString().substring(0, text.length() - 1), text.length() - 1);
        }

        if ((size == 4 && dir == 0)){
            setTextAndSelection(text.toString().substring(0, text.length() - 2), text.length() - 2);
        }

        if (size > 18 && dir == 1) {
            setTextAndSelection(t.substring(0, 18), 18);
        }
    }

    private void setTextAndSelection(String text, int selection) {
        binding.editLayoutCompanyPhone.setText(text);
        binding.editLayoutCompanyPhone.setSelection(selection);
    }

    private void checkZeroOneDirectional(int size, int maxSize, char symbol, int lastIndex, int direction, String text) {
        if ((size == maxSize && direction == 1 && text.charAt(lastIndex - 1) != symbol)   ) {
            setTextAndSelection(text.substring(0, maxSize - 1) + symbol + text.substring(maxSize - 1), maxSize + 1);
        }

        if ((size == 15 && direction == 1)){
            setTextAndSelection( text + symbol, maxSize + 3);
        }

        if (size == 16 && direction == 1){
            setTextAndSelection(text.substring(0, text.length() - 1) + symbol + text.substring(text.length() - 1), maxSize + 4);
        }
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
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonSave.setEnabled(false);

            name = binding.editLayoutCompanyName.getText().toString();
            email = binding.editLayoutCompanyEmail.getText().toString();
            phone = binding.editLayoutCompanyPhone.getText().toString();
            if (name != null && (phone.length() == 18 || phone.length() == 2)) {
                switch (state) {
                    case COMPANY:
                        viewModel.updateData(companyId, name, phone, imageUri);
                        break;
                    case RESTAURANT:
                        viewModel.updateRestaurantData(companyId, email, name, phone, imageUri);
                        break;
                }
            } else if (name == null){
                binding.loader.setVisibility(View.GONE);
                binding.buttonSave.setEnabled(true);
                Snackbar.make(requireView(), getString(R.string.name_cannot_be_null), LENGTH_LONG).show();
            } else {
                binding.loader.setVisibility(View.GONE);
                binding.buttonSave.setEnabled(true);
                Snackbar.make(requireView(), getString(R.string.phone_must_be_full), LENGTH_LONG).show();
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
                String service = model.getService();
                switch (service){
                    case QUEUE:
                        binding.editLayoutService.setText(getString(R.string.queue));
                        break;
                    case RESTAURANT:
                        binding.editLayoutService.setText(getString(R.string.restaurant));
                        break;
                }
               
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