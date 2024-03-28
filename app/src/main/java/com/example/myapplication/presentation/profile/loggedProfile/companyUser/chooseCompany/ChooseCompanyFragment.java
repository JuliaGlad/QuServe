package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany;

import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_DETAILS;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.CREATE_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_MANAGER;
import static com.example.myapplication.presentation.utils.Utils.STATE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.app.App;
import com.example.myapplication.databinding.FragmentChooseCompanyBinding;
import com.example.myapplication.domain.model.common.ImageTaskModel;
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
    private String state;
    List<CompanyListModel> models = new ArrayList<>();
    List<Task<Uri>> imageList = new ArrayList<>();
    List<DelegateItem> delegates = new ArrayList<>();
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = getActivity().getIntent().getStringExtra(STATE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ChooseCompanyViewModel.class);
        binding = FragmentChooseCompanyBinding.inflate(inflater, container, false);
        viewModel.getCompaniesList();
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

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            ((ChooseCompanyActivity) requireActivity()).openCreateCompanyActivity();
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

                ListItemModel model = ((ChooseCompanyState.Success) state).data;
                models = model.getCompanyModels();
                List<ImageTaskModel> tasks = model.getImageTaskModels();
                for (int i = 0; i < tasks.size(); i++) {
                    imageList.add(tasks.get(i).getTask());
                }
                initRecycler();


            } else if (state instanceof ChooseCompanyState.Loading) {
                binding.progressBar.setVisibility(View.VISIBLE);

            } else if (state instanceof ChooseCompanyState.Error) {

            }
        });

    }

    private void initNavigation(String id) {
        switch (state) {
            case COMPANY_DETAILS:

                App.changeState(COMPANY, id);

                Intent intent = new Intent();
                intent.putExtra(COMPANY_ID, id);
                intent.putExtra(STATE, COMPANY);

                getActivity().setResult(Activity.RESULT_OK, intent);

                requireActivity().finish();
                break;

            case CREATE_QUEUE:
                ((ChooseCompanyActivity) requireActivity()).openCreateCompanyQueueActivity(id);
                break;

            case QUEUE_MANAGER:
                ((ChooseCompanyActivity) requireActivity()).openQueueManagerActivity(id);
                break;
        }
    }

    private void initRecycler(){
        for (int i = 0; i < models.size(); i++) {
            String id = models.get(i).getId();
            delegates.add(new CompanyListItemDelegateItem(new CompanyListItemDelegateModel(
                    i, models.get(i).getName(),
                    imageList.get(i), () -> initNavigation(id))
            ));
        }
        mainAdapter.submitList(delegates);
        binding.progressBar.setVisibility(getId());
    }
}