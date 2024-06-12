package com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.recycler;

import static com.example.myapplication.presentation.utils.constants.Utils.CHOSEN;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_CHOSEN;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewChooseWorkerItemBinding;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;

public class WorkerItemAdapter extends ListAdapter<WorkerItemModel, RecyclerView.ViewHolder> {

    public WorkerItemAdapter() {
        super(new WorkerItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewChooseWorkerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewChooseWorkerItemBinding binding;

        public ViewHolder(RecyclerViewChooseWorkerItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(WorkerItemModel model) {

            binding.name.setText(model.name);

            if (model.state.equals(CHOSEN)) {
                binding.workerItem.setStrokeColor(itemView.getResources().getColor(R.color.colorPrimary, itemView.getContext().getTheme()));
            }

            binding.workerItem.setOnClickListener(v -> {
                if (model.state.equals(NOT_CHOSEN)) {

                    model.state = CHOSEN;
                    binding.workerItem.setStrokeColor(itemView.getResources().getColor(R.color.colorPrimary, itemView.getContext().getTheme()));
                    model.chosen.add(new EmployeeModel(model.workerId, model.name, model.queueCount));

                } else {

                    model.state = NOT_CHOSEN;
                    for (int i = 0; i < model.chosen.size(); i++) {
                        if (model.chosen.get(i).getUserId().equals(model.workerId)){
                            model.chosen.remove(i);
                            break;
                        }
                    }

                    binding.workerItem.setStrokeColor(Color.TRANSPARENT);
                }
            });
        }
    }
}
