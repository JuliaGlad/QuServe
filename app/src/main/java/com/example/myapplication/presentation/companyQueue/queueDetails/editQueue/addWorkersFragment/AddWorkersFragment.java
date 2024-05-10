package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.NOT_CHOSEN;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_LIST;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAddWorkersBinding;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model.AddWorkerModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.recycler.AddWorkerAdapter;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.recycler.AddWorkerRecyclerModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.state.AddWorkersState;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EmployeeModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AddWorkersFragment extends Fragment {

    private AddWorkersViewModel viewModel;
    private FragmentAddWorkersBinding binding;
    private String companyId, queueId;
    private List<EmployeeModel> alreadyChosen = new ArrayList<>();
    private List<AddWorkerRecyclerModel> recyclerItems = new ArrayList<>();
    private final AddWorkerAdapter adapter = new AddWorkerAdapter();
    private final List<AddWorkerModel> chosen = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddWorkersViewModel.class);
        companyId = getArguments().getString(COMPANY_ID);
        queueId = getArguments().getString(QUEUE_ID);
        alreadyChosen = new Gson().fromJson(getArguments().getString(WORKERS_LIST), new TypeToken<List<EmployeeModel>>() {
        }.getType());
        viewModel.getEmployees(companyId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddWorkersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initSearchView();
        initOkButton();
        initButtonBack();
    }

    private void initSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }

        });
    }

    private void filterList(String newText) {
        List<AddWorkerRecyclerModel> filteredList = new ArrayList<>();
        for (AddWorkerRecyclerModel model : recyclerItems) {
            if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(model);
            }
        }
        adapter.submitList(filteredList);
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(COMPANY_ID, companyId);
            bundle.putString(QUEUE_ID, queueId);
            navigateBack(bundle);
        });

    }

    private void initOkButton() {
        binding.buttonOk.setOnClickListener(v -> {
            viewModel.addEmployees(chosen, companyId, queueId);
        });
    }

    private void setupObserves() {

        viewModel.isAdded.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, companyId);
                bundle.putString(QUEUE_ID, queueId);
                navigateBack(bundle);
            }
        });

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof AddWorkersState.Success) {

                List<AddWorkerModel> models = ((AddWorkersState.Success) state).data;
                recyclerItems = new ArrayList<>();

                List<String> ids = new ArrayList<>();
                for (int i = 0; i < alreadyChosen.size(); i++) {
                    ids.add(alreadyChosen.get(i).getId());
                }

                for (int i = 0; i < models.size(); i++) {
                    AddWorkerModel current = models.get(i);
                    if (!ids.contains(current.getId())) {
                        recyclerItems.add(new AddWorkerRecyclerModel(
                                i,
                                current.getName(),
                                current.getId(),
                                NOT_CHOSEN,
                                chosen
                        ));
                    }
                }

                binding.recyclerView.setAdapter(adapter);
                adapter.submitList(recyclerItems);
                binding.progressBar.setVisibility(View.GONE);

            } else if (state instanceof AddWorkersState.Loading) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else if (state instanceof AddWorkersState.Error) {

            }
        });
    }

    private void navigateBack(Bundle bundle) {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_addWorkersFragment_to_editQueueFragment, bundle);
    }
}