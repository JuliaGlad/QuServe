package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.main;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_SERVICE;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.myapplication.databinding.FragmentChooseCompanyBinding;
import com.example.myapplication.domain.model.common.ImageTaskModel;
import com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.CreateCompanyActivity;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState.ChooseCompanyState;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState.CompanyListModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState.ListItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.companyListItem.CompanyListItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.companyListItem.CompanyListItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.companyListItem.CompanyListItemDelegateModel;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;

public class ChooseCompanyFragment extends Fragment {

    private ChooseCompanyViewModel viewModel;
    private FragmentChooseCompanyBinding binding;
    List<CompanyListModel> models = new ArrayList<>();
    List<Task<Uri>> imageList = new ArrayList<>();
    List<DelegateItem> delegates = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCreateCompanyLauncher();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ChooseCompanyViewModel.class);
        binding = FragmentChooseCompanyBinding.inflate(inflater, container, false);
        viewModel.getAllCompaniesList();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        setAdapter();
        initBackButton();
        initAddButton();
    }

    private void addCompanyDelegateItems(String companyName, Task<Uri> image, String service, String companyId){
        delegates.add(new CompanyListItemDelegateItem(new CompanyListItemDelegateModel(
                delegates.size(),
                companyName,
                image, () -> {

            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            switch (service) {
                case COMPANY_QUEUE:
                    sharedPreferences.edit().putString(APP_STATE, COMPANY).apply();
                    sharedPreferences.edit().putString(COMPANY_ID, companyId).apply();
                    break;
                case RESTAURANT:
                    sharedPreferences.edit().putString(APP_STATE, RESTAURANT).apply();
                    sharedPreferences.edit().putString(COMPANY_ID, companyId).apply();
                    break;
            }
            initNavigation();
        })
        ));
    }

    private void initCreateCompanyLauncher(){
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String companyName = data.getStringExtra(COMPANY_NAME);
                        String companyId = data.getStringExtra(COMPANY_ID);
                        String companyService = data.getStringExtra(COMPANY_SERVICE);
                        addCompanyDelegateItems(companyName, null, companyService, companyId);;
                        mainAdapter.notifyItemInserted(delegates.size() - 1);
                    }
                });
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CreateCompanyActivity.class);
            intent.putExtra(PAGE_KEY, PAGE_1);
            launcher.launch(intent);
        });
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setAdapter() {
        mainAdapter.addDelegate(new FloatingActionButtonDelegate());
        mainAdapter.addDelegate(new CompanyListItemDelegate());
        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ChooseCompanyState.Success) {
                if (delegates.isEmpty()) {
                    ListItemModel model = ((ChooseCompanyState.Success) state).data;
                    models = model.getCompanyModels();
                    List<ImageTaskModel> tasks = model.getImageTaskModels();
                    for (int i = 0; i < tasks.size(); i++) {
                        imageList.add(tasks.get(i).getTask());
                    }
                    initRecycler();
                }
            } else if (state instanceof ChooseCompanyState.Loading) {
                binding.progressBar.setVisibility(View.VISIBLE);

            } else if (state instanceof ChooseCompanyState.Error) {
                setErrorLayout();
            }
        });

    }

    private void setErrorLayout() {
        binding.progressBar.setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getAllCompaniesList();
        });
    }

    private void initNavigation() {
        requireActivity().setResult(Activity.RESULT_OK);
        requireActivity().finish();
    }

    private void initRecycler() {
        for (int i = 0; i < models.size(); i++) {
            CompanyListModel current = models.get(i);
            String id = current.getId();
            addCompanyDelegateItems(current.getName(), imageList.get(i), current.getService(), id);
        }
        mainAdapter.submitList(delegates);
        binding.progressBar.setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.GONE);
    }
}