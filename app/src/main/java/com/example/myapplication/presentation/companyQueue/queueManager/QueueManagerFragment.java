package com.example.myapplication.presentation.companyQueue.queueManager;

import static com.example.myapplication.presentation.utils.constants.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentQueueManagerBinding;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.recycler.AddWorkerRecyclerModel;
import com.example.myapplication.presentation.companyQueue.queueManager.model.QueueManagerModel;
import com.example.myapplication.presentation.companyQueue.queueManager.recycler_view.ManagerItemAdapter;
import com.example.myapplication.presentation.companyQueue.queueManager.recycler_view.ManagerItemModel;
import com.example.myapplication.presentation.companyQueue.queueManager.state.QueueManagerState;
import com.example.myapplication.presentation.dialogFragments.chooseCity.ChooseCityFullScreenDialog;

import java.util.ArrayList;
import java.util.List;

public class QueueManagerFragment extends Fragment {

    private QueueManagerViewModel viewModel;
    private FragmentQueueManagerBinding binding;
    private String companyId;
    private final List<ManagerItemModel> models = new ArrayList<>();
    ManagerItemAdapter adapter = new ManagerItemAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QueueManagerViewModel.class);
        companyId = requireActivity().getIntent().getStringExtra(COMPANY_ID);
        viewModel.getList(companyId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentQueueManagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initChooseCity();
        initBackButton();
        initSearchView();
    }

    private void initSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListBySearchView(newText);
                return true;
            }

        });
    }

    private void filterListBySearchView(String key) {
        if (!key.isEmpty()) {
            List<ManagerItemModel> filteredList = new ArrayList<>();
            for (ManagerItemModel model : models) {
                if (model.getQueueName().toLowerCase().contains(key.toLowerCase())) {
                    filteredList.add(model);
                }
            }
            adapter.submitList(filteredList);
        } else {
            adapter.submitList(models);
        }
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void initChooseCity() {

        binding.chooseCityLayout.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            ChooseCityFullScreenDialog dialogFragment = new ChooseCityFullScreenDialog();

            fragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.queue_manager_container, dialogFragment)
                    .addToBackStack(null)
                    .commit();

            dialogFragment.onDismissListener(bundle -> {
                String city = bundle.getString(CITY_KEY);
                assert city != null;
                filterList(city);
            });
        });

    }

    private void filterList(String city) {
        List<ManagerItemModel> newList = new ArrayList<>();

        if (city.equals(getResources().getString(R.string.all_cities))) {
            adapter.submitList(models);
            binding.chooseCity.setText(getResources().getString(R.string.all_cities));
        } else {
            binding.chooseCity.setText(city);
            for (int i = 0; i < models.size(); i++) {
                if (models.get(i).getCity().equals(city) || models.get(i).getLocation().toLowerCase().contains(city.toLowerCase())) {
                    newList.add(models.get(i));
                }
            }
            adapter.submitList(newList);
        }

    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof QueueManagerState.Success) {
                if (models.isEmpty()) {
                    List<QueueManagerModel> queueManagerModels = ((QueueManagerState.Success) state).data;
                    if (!queueManagerModels.isEmpty()) {
                        initRecycler(queueManagerModels);
                    } else {
                        binding.progressBar.getRoot().setVisibility(View.GONE);
                        binding.emptyLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.emptyLayout.title.setText(getString(R.string.you_don_t_have_any_queues_yet));
                        binding.emptyLayout.infoBox.body.setText(getString(R.string.create_new_queues_in_your_company_and_then_come_back));
                        binding.emptyLayout.buttonAdd.setOnClickListener(v -> {
                            requireActivity().finish();
                        });
                    }
                }
            } else if (state instanceof QueueManagerState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof QueueManagerState.Error) {
                setErrorLayout();
            }
        });
    }

    private void initRecycler(List<QueueManagerModel> queueManagerModels) {
        for (int i = 0; i < queueManagerModels.size(); i++) {
            QueueManagerModel current = queueManagerModels.get(i);
            String queueId = current.getId();
            models.add(new ManagerItemModel(
                    i,
                    queueId,
                    current.getName(),
                    current.getWorkersCount(),
                    current.getLocation(),
                    current.getCity(),
                    () -> {
                        ((QueueManagerActivity) requireActivity()).openQueueDetails(companyId, queueId);
                    })
            );
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(models);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.GONE);
    }

    private void setErrorLayout() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getList(companyId);
        });
    }

}