package com.example.myapplication.presentation.restaurantMenu.addDish.recycler;

import static com.example.myapplication.presentation.utils.constants.Utils.HOURS;
import static com.example.myapplication.presentation.utils.constants.Utils.MINUTES;
import static com.example.myapplication.presentation.utils.constants.Utils.SECONDS;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewTimeChooseBinding;

import org.checkerframework.checker.units.qual.A;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class NumberPickerDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewTimeChooseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((NumberPickerModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof NumberPickerDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewTimeChooseBinding binding;

        public ViewHolder(RecyclerViewTimeChooseBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(NumberPickerModel model) {
            initHoursPicker();
            initMinutesPicker();
            initSecondsPicker();

            try {
                int hours = Integer.parseInt(model.bundle.getString(HOURS));
                int minutes = Integer.parseInt(model.bundle.getString(MINUTES));
                int seconds = Integer.parseInt(model.bundle.getString(SECONDS));

                binding.hoursPicker.setValue(hours);
                binding.minutesPicker.setValue(minutes);
                binding.secondPicker.setValue(seconds);

            } catch (NumberFormatException e) {
                Log.e("NumberFormatException", e.getMessage());
            }

            binding.hoursPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
                Bundle bundle = new Bundle();
                bundle.putString(HOURS, String.valueOf(newVal));
                String minutes = String.valueOf(binding.minutesPicker.getValue());
                String seconds = String.valueOf(binding.secondPicker.getValue());
                bundle.putString(MINUTES, minutes);
                bundle.putString(SECONDS, seconds);
                model.hoursListener.onClick(bundle);
            });

            binding.minutesPicker.setOnValueChangedListener(((picker, oldVal, newVal) -> {
                Bundle bundle = new Bundle();
                bundle.putString(MINUTES, String.valueOf(newVal));
                String hours = String.valueOf(binding.hoursPicker.getValue());
                String seconds = String.valueOf(binding.secondPicker.getValue());
                bundle.putString(HOURS, hours);
                bundle.putString(SECONDS, seconds);
                model.hoursListener.onClick(bundle);
            }));

            binding.secondPicker.setOnValueChangedListener(((picker, oldVal, newVal) -> {
                Bundle bundle = new Bundle();
                bundle.putString(SECONDS, String.valueOf(newVal));
                String hours = String.valueOf(binding.hoursPicker.getValue());
                String minutes = String.valueOf(binding.minutesPicker.getValue());
                bundle.putString(HOURS, hours);
                bundle.putString(MINUTES, minutes);
                model.hoursListener.onClick(bundle);
            }));
        }

        private void initHoursPicker() {
            binding.hoursPicker.setMinValue(0);
            binding.hoursPicker.setMaxValue(99);
            binding.hoursPicker.setValue(0);
            binding.hoursPicker.setWrapSelectorWheel(false);
        }

        private void initMinutesPicker() {
            binding.minutesPicker.setMinValue(0);
            binding.minutesPicker.setMaxValue(59);
            binding.minutesPicker.setValue(10);
            binding.minutesPicker.setWrapSelectorWheel(false);
        }

        private void initSecondsPicker() {
            binding.secondPicker.setMinValue(0);
            binding.secondPicker.setMaxValue(59);
            binding.secondPicker.setValue(0);
            binding.secondPicker.setWrapSelectorWheel(false);
        }
    }
}
