package com.example.myapplication.presentation.common.waitingInQueue.recycler.items.waitingDelegateItem;

import static com.example.myapplication.presentation.utils.constants.Utils.EDIT_ESTIMATED_TIME;
import static com.example.myapplication.presentation.utils.constants.Utils.EDIT_PEOPLE_BEFORE_YOU;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewWaitingItemBinding;
import com.example.myapplication.di.QueueDI;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class WaitingItemDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewWaitingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((WaitingItemDelegate.ViewHolder) holder).bind((WaitingItemModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof WaitingItemDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewWaitingItemBinding binding;

        public ViewHolder(RecyclerViewWaitingItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(WaitingItemModel model) {
            binding.icon.setImageResource(model.drawable);
            binding.headLine.setText(model.headerText);

            if (model.editable) {
                if (model.flag.equals(EDIT_ESTIMATED_TIME)) {
                    setTime(model.descriptionText);
                } else {
                    binding.description.setText(model.descriptionText);
                }
            } else{
                binding.description.setText(model.descriptionText);
            }

            if (model.editable && (model.flag.equals(EDIT_PEOPLE_BEFORE_YOU) || model.flag.equals(EDIT_ESTIMATED_TIME))) {
                QueueDI.addPeopleBeforeYouSnapshot.invoke(model.path, model.size)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Integer integer) {
                                if (model.flag.equals(EDIT_PEOPLE_BEFORE_YOU)) {
                                    if (integer != 0) {
                                        binding.description.setText(String.valueOf(integer));
                                    } else {
                                        binding.description.setText(itemView.getContext().getString(R.string.you_are_next));
                                    }
                                    model.size -= 1;
                                } else if (model.flag.equals(EDIT_ESTIMATED_TIME)){
                                    if (integer != 0){
                                        String timeLeft = String.valueOf(Integer.parseInt(model.descriptionText) - model.startValue);
                                        model.setDescriptionText(timeLeft);
                                        setTime(timeLeft);
                                    }
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }

        private void setTime(String descriptionText) {
            int midTime = Integer.parseInt(descriptionText);
            if (midTime != 0) {
                if (midTime >= 60) {
                    double time = midTime / 60.0;
                    String timeFormatted = new DecimalFormat("#0.00").format(time);
                    int pointIndex = timeFormatted.indexOf(",");
                    String hours = timeFormatted.substring(0, pointIndex);
                    String minutes = timeFormatted.substring(pointIndex + 1);
                    if (minutes.endsWith("0")) {
                        minutes = minutes.substring(0, minutes.length() - 1);
                    }
                    String text = hours.concat(itemView.getResources().getString(R.string.hours_description)).concat(" ").concat(minutes).concat(itemView.getResources().getString(R.string.minutes_description));
                    binding.description.setText(text);

                } else if (midTime >= 0) {
                    binding.description.setText(descriptionText.concat(itemView.getResources().getString(R.string.minutes_description)));
                }
            } else {
                binding.description.setText(itemView.getResources().getString(R.string.no_estimated_time_yet));
            }
        }
    }

}
