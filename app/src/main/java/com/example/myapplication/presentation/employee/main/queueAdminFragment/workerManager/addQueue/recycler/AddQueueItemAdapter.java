package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.recycler;

import static com.example.myapplication.presentation.utils.Utils.CHOSEN;
import static com.example.myapplication.presentation.utils.Utils.NOT_CHOSEN;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewQueueManagerItemBinding;
import com.example.myapplication.presentation.employee.main.ActiveQueueModel;

public class AddQueueItemAdapter extends ListAdapter<AddQueueItemModel, RecyclerView.ViewHolder> {

    public AddQueueItemAdapter() {
        super(new AddQueueItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewQueueManagerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewQueueManagerItemBinding binding;

        public ViewHolder(RecyclerViewQueueManagerItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(AddQueueItemModel model){

            if (model.workers != null && Integer.parseInt(model.workers) > 3 ){
                model.workers = String.valueOf(Integer.parseInt(model.workers) - 3);
                binding.workersCount.setText("+".concat(model.workers));
            } else {
                binding.workersCount.setText(model.workers);
            }

            binding.queueName.setText(model.queueName);
            binding.location.setText(model.location);

            binding.queueItem.setOnClickListener(v -> {
                if (model.state.equals(NOT_CHOSEN)){
                    binding.queueItem.setStrokeColor(itemView.getResources().getColor(R.color.colorPrimary, itemView.getContext().getTheme()));
                    model.addListener.onClick(new ActiveQueueModel(
                            model.queueId,
                            model.queueName,
                            model.location
                    ));
                    model.state = CHOSEN;
                } else {
                    binding.queueItem.setStrokeColor(Color.TRANSPARENT);
                    for (int i = 0; i < model.chosen.size(); i++) {
                        if (model.chosen.get(i).getId().equals(model.queueId)){
                            model.removeListener.onClick(i);
                            break;
                        }
                    }
                    model.state = NOT_CHOSEN;
                }
            });
        }
    }
}
