package com.example.myapplication.presentation.profile.loggedProfile.basicUser;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.BASIC;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_KEY;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBasicUserBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.CreateCompanyActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.basicUserStateAndModel.BasicUserState;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.CompanyUserFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.ChooseCompanyActivity;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemModel;
import com.example.myapplication.presentation.profile.profileLogin.ProfileLoginFragment;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class BasicUserFragment extends Fragment {

    private BasicUserViewModel viewModel;
    private FragmentBasicUserBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private boolean companyExist = false;
    String companyId = null;
    private ActivityResultLauncher<Intent> activityResultLauncher, chooseCompanyLauncher, createCompanyLauncher, settingsLauncher;
    private List<DelegateItem> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        viewModel = new ViewModelProvider(this).get(BasicUserViewModel.class);
        setActivityResultLauncher();
        setChooseCompanyLauncher();
        setCreateCompanyLauncher();
        setSettingsLauncher();
        viewModel.retrieveUserNameData(connected);
        viewModel.setBackground();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentBasicUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        setAdapter();
        initSettingButton();
    }

    private void initBackground() {
        binding.background.setOnClickListener(v -> {
            initImagePicker();
        });
    }

    private void initSettingButton() {
        binding.buttonSettings.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).openBasicSettingsActivity(settingsLauncher);
        });
    }

    private void setAdapter() {
        mainAdapter.addDelegate(new MainItemDelegate());
        mainAdapter.addDelegate(new ServiceItemDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
        viewModel.item.observe(getViewLifecycleOwner(), mainAdapter::submitList);

        viewModel.checked.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                if (!companyExist && companyId != null) {
                    companyExist = true;
                    updateItem();
                }
            }
        });

        viewModel.uri.observe(getViewLifecycleOwner(), uri -> {
            if (!uri.equals(Uri.EMPTY)) {
                Glide.with(this)
                        .load(uri)
                        .into(binding.background);
                binding.addBackground.setVisibility(View.GONE);
                initBackground();
            } else {
                initAddBackgroundButton();
            }
        });

        viewModel.companyExist.observe(getViewLifecycleOwner(), aBoolean -> {
            companyExist = aBoolean;
        });

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof BasicUserState.Success) {
                BasicUserState.Success userModel = (BasicUserState.Success) state;
                initRecycler(userModel.data.getUri(), userModel.data.getName(), userModel.data.getEmail());
                binding.progressBar.getRoot().setVisibility(View.GONE);
                binding.errorLayout.errorLayout.setVisibility(View.GONE);

            } else if (state instanceof BasicUserState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof BasicUserState.Error) {
                setError();
            }
        });

        viewModel.companyId.observe(getViewLifecycleOwner(), s -> {
            companyId = s;
        });
    }

    private void initAddBackgroundButton() {
        binding.addBackground.setOnClickListener(v -> {
            initImagePicker();
        });
    }

    private void setError() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.retrieveUserNameData(false);
        });
    }

    private void updateItem() {
        if (!list.isEmpty()) {
            List<DelegateItem> listNew = new ArrayList<>(list);
            listNew.remove(listNew.size() - 1);
            listNew.add(new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_buisness_center_24, R.string.go_to_company, () -> {
                Intent intent = new Intent(requireActivity(), ChooseCompanyActivity.class);
                chooseCompanyLauncher.launch(intent);
            })));
            mainAdapter.submitList(listNew);
        }
    }

    private void initRecycler(Uri uri, String name, String email) {
        list = Arrays.asList(new MainItemDelegateItem(new MainItemModel(1, uri, name, email, BASIC, null)),
                new ServiceItemDelegateItem(new ServiceItemModel(2, R.drawable.ic_edit, R.string.edit_profile, () -> {
                    ((MainActivity) requireActivity()).openEditActivity();
                })),
                new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_history, R.string.history, () -> {
                    ((MainActivity) requireActivity()).openHistoryActivity();
                })),
                addCompanyServiceDelegateItem()
        );

        mainAdapter.submitList(list);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

    private ServiceItemDelegateItem addCompanyServiceDelegateItem() {
        if (companyExist) {
            return new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_buisness_center_24, R.string.go_to_company, () -> {
                Intent intent = new Intent(requireActivity(), ChooseCompanyActivity.class);
                chooseCompanyLauncher.launch(intent);
            }));
        } else {
            return new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_add_business_24, R.string.add_company, () -> {
                Intent intent = new Intent(requireActivity(), CreateCompanyActivity.class);
                intent.putExtra(PAGE_KEY, PAGE_1);
                createCompanyLauncher.launch(intent);
            }
            ));
        }
    }

    private void initImagePicker() {
        ImagePicker.with(this)
                .crop()
                .compress(512)
                .maxResultSize(1080, 1900)
                .createIntent(intent -> {
                    activityResultLauncher.launch(intent);
                    return null;
                });
    }

    private void setCreateCompanyLauncher() {
        createCompanyLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        companyExist = true;
                        updateItem();
                    }
                });
    }

    private void setSettingsLauncher() {
        settingsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .setReorderingAllowed(true)
                                .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                                .replace(R.id.logged_container, ProfileLoginFragment.class, null)
                                .commit();
                    }
                });
    }

    private void setChooseCompanyLauncher() {
        chooseCompanyLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager
                                .beginTransaction()
                                .setReorderingAllowed(true)
                                .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                                .replace(R.id.logged_container, CompanyUserFragment.class, null)
                                .commit();
                    }
                });
    }

    private void setActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri imageUri = data.getData();
                            viewModel.uploadToFireStorage(imageUri);
                            Glide.with(this).load(imageUri)
                                    .into(binding.background);
                            binding.addBackground.setVisibility(View.GONE);
                            initBackground();
                        }
                    }
                });
    }

}