package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMPLOYEE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_NAME;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentWorkerDetailsBinding;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;
import com.example.myapplication.presentation.employee.main.ActiveQueueModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.header.WorkerDetailsHeaderDelegate;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.header.WorkerDetailsHeaderDelegateItem;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.header.WorkerDetailsHeaderModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.queue.WorkerManageQueueDelegateItem;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.queue.WorkerManagerQueueDelegate;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.queue.WorkerManagerQueueModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.state.WorkerDetailsState;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;
import myapplication.android.ui.recycler.ui.items.items.imageDrawable.ImageViewDrawableDelegate;
import myapplication.android.ui.recycler.ui.items.items.imageDrawable.ImageViewDrawableDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageDrawable.ImageViewDrawableModel;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewModel;

public class WorkerDetailsFragment extends Fragment {

    private WorkerDetailsViewModel viewModel;
    private FragmentWorkerDetailsBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private String name, employeeId, companyId;
    List<ActiveQueueModel> models = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WorkerDetailsViewModel.class);
        if (getArguments() != null) {
            name = getArguments().getString(EMPLOYEE_NAME);
            employeeId = getArguments().getString(COMPANY_EMPLOYEE);
            companyId = getArguments().getString(COMPANY_ID);
            viewModel.getEmployeeData(companyId, employeeId);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentWorkerDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setupObserves();
        initAddQueueButton();
        initBackButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        try{
            String workers = getArguments().getString(QUEUE_LIST);
            models.addAll(Objects.requireNonNull(new Gson().fromJson(workers, new TypeToken<List<EmployeeModel>>() {
            }.getType())));
        } catch (NullPointerException e){
            Log.e("NullPointerArguments", e.getMessage());
        }
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_workerDetailsFragment_to_workerManagerFragment);
        });
    }

    private void initAddQueueButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            Bundle bundle = new Bundle();

            ArrayList<String> ids = new ArrayList<>();
            for (int i = 0; i < models.size(); i++) {
                ids.add(models.get(i).getId());
            }

            bundle.putString(COMPANY_ID, companyId);
            bundle.putStringArrayList(QUEUE_ID, ids);
            bundle.putString(COMPANY_EMPLOYEE, employeeId);
            bundle.putString(EMPLOYEE_NAME, name);

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_workerDetailsFragment_to_addQueueFragment, bundle);
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof WorkerDetailsState.Success) {
                models.addAll(((WorkerDetailsState.Success) state).data);
                initRecycler(models, employeeId, name);
            } else if (state instanceof WorkerDetailsState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof WorkerDetailsState.Error) {
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getEmployeeData(companyId, employeeId);
        });
    }

    private void initRecycler(List<ActiveQueueModel> models, String employeeId, String employeeName) {
        List<DelegateItem> delegates = new ArrayList<>();
        delegates.add(new WorkerDetailsHeaderDelegateItem(new WorkerDetailsHeaderModel(1, employeeId, employeeName, com.example.myapplication.presentation.utils.Utils.WORKER)));
        delegates.add(new AdviseBoxDelegateItem(new AdviseBoxModel(2, R.string.worker_details_advise_box_text)));
        delegates.addAll(addActiveQueuesDelegates(models));
        mainAdapter.submitList(delegates);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
        binding.progressBar.getRoot().setVisibility(View.GONE);
    }

    private List<DelegateItem> addActiveQueuesDelegates(List<ActiveQueueModel> models) {
        List<DelegateItem> delegates = new ArrayList<>();
        if (!models.isEmpty()) {
            for (int i = 0; i < models.size(); i++) {
                ActiveQueueModel current = models.get(i);
                delegates.add(new WorkerManageQueueDelegateItem(new WorkerManagerQueueModel(i + 2, current.getName(), current.getLocation())));
            }
        } else {
            delegates.add(new ImageViewDrawableDelegateItem(new ImageViewDrawableModel(3, R.drawable.questions_wall_paper)));
        }
        return delegates;
    }

    private void setAdapter() {
        mainAdapter.addDelegate(new AdviseBoxDelegate());
        mainAdapter.addDelegate(new WorkerDetailsHeaderDelegate());
        mainAdapter.addDelegate(new WorkerManagerQueueDelegate());
        mainAdapter.addDelegate(new ImageViewDrawableDelegate());
        binding.recyclerView.setAdapter(mainAdapter);
    }
}