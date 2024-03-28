package com.example.myapplication.presentation.companyQueue.queueManager;

import static com.example.myapplication.presentation.utils.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentQueueManagerBinding;
import com.example.myapplication.presentation.companyQueue.queueManager.model.QueueManagerModel;
import com.example.myapplication.presentation.companyQueue.queueManager.recycler_view.ManagerItemAdapter;
import com.example.myapplication.presentation.companyQueue.queueManager.recycler_view.ManagerItemModel;
import com.example.myapplication.presentation.companyQueue.queueManager.state.QueueManagerState;
import com.example.myapplication.presentation.dialogFragments.chooseCity.ChooseCityFullScreenDialog;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.LinearLayoutManagerWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class QueueManagerFragment extends Fragment {

    private QueueManagerViewModel viewModel;
    private FragmentQueueManagerBinding binding;
    private String companyId;
    private List<ManagerItemModel> models = new ArrayList<>();
    ManagerItemAdapter adapter = new ManagerItemAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QueueManagerViewModel.class);
        companyId = getActivity().getIntent().getStringExtra(COMPANY_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel.getList(companyId);

        binding = FragmentQueueManagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initChooseCity();
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
               filterList(city);
            });
        });

    }

    private void filterList(String city) {
        List<ManagerItemModel> newList = new ArrayList<>();

        if (city.equals(getResources().getString(R.string.all_cities))) {
            adapter.submitList(models);
            binding.chooseCity.setText(getResources().getString(R.string.all_cities));
        }else {
            binding.chooseCity.setText(city);
            for (int i = 0; i < models.size(); i++) {
                if (models.get(i).getCity().equals(city)) {
                    newList.add(models.get(i));
                }
            }
            adapter.submitList(newList);
        }

    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof QueueManagerState.Success){
                if (models.size() == 0) {
                    List<QueueManagerModel> queueManagerModels = ((QueueManagerState.Success) state).data;
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
                    binding.recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(requireContext(), LinearLayoutManager.VERTICAL, false));
                    adapter.submitList(models);
                    binding.progressBar.setVisibility(View.GONE);
                }
            } else if (state instanceof QueueManagerState.Loading){
                binding.progressBar.setVisibility(View.VISIBLE);

            } else if (state instanceof  QueueManagerState.Error){

            }
        });
    }

}