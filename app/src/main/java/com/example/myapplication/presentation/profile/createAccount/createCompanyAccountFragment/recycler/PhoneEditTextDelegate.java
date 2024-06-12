package com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.recycler;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewPhoneEdittextBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class PhoneEditTextDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewPhoneEdittextBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((PhoneEditTextModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof PhoneEditTextDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewPhoneEdittextBinding binding;

        public ViewHolder(RecyclerViewPhoneEdittextBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(PhoneEditTextModel model){
            binding.editLayout.setHint(model.hint);
            binding.editLayout.setInputType(model.inputType);
            if (model.text != null && !model.text.isEmpty()){
                binding.editLayout.setText(model.text);
            }
            setupPhoneEditText(model);
        }

        private void setupPhoneEditText(PhoneEditTextModel model) {
            binding.editLayout.setText("+7");
            binding.editLayout.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    onPhoneTextChanged(model, s, count);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        private void onPhoneTextChanged(PhoneEditTextModel model, CharSequence text, int dir) {
            int size = text.length();
            int last = size - 1;

            StringBuilder t = new StringBuilder(binding.editLayout.getText());

            if (size <= 2 && dir == 0) {
                setTextAndSelection(model,"+7", 2);
            }

            if (size == 3 && dir == 1) {
                setTextAndSelection(model,t.substring(0, last) + " (" + text.charAt(last), 5);
            }

            if (size == 7 && dir == 1) {
                setTextAndSelection(model,t + ")", 8);
            }

            if ((size == 8 && dir == 1)  && t.charAt(last - 1) != '-') {
                setTextAndSelection(model,t.substring(0, 7) + ") " + t.charAt(last), 10);
            }

            if (size == 9 && dir == 1 && t.charAt(last - 1) != ' ') {
                setTextAndSelection(model,t.substring(0, 8) + " " + t.charAt(last), 10);
            }

            checkZeroOneDirectional(model, size, 13, '-', last, dir, t.toString());

            if ((size == 16 && dir == 0) || (size == 13 && dir == 0) || (size == 8 && dir == 0)){
                setTextAndSelection(model, text.toString().substring(0, text.length() - 1), text.length() - 1);
            }

            if ((size == 4 && dir == 0)){
                setTextAndSelection(model, text.toString().substring(0, text.length() - 2), text.length() - 2);
            }

            if (size > 18 && dir == 1) {
                setTextAndSelection(model, t.substring(0, 18), 18);
            }
        }

        private void setTextAndSelection(PhoneEditTextModel model, String text, int selection) {
            model.resultListener.getResult(text);
            binding.editLayout.setText(text);
            binding.editLayout.setSelection(selection);
        }

        private void checkZeroOneDirectional(PhoneEditTextModel model, int size, int maxSize, char symbol, int lastIndex, int direction, String text) {
            if ((size == maxSize && direction == 1 && text.charAt(lastIndex - 1) != symbol)   ) {
                setTextAndSelection(model,text.substring(0, maxSize - 1) + symbol + text.substring(maxSize - 1), maxSize + 1);
            }

            if ((size == 15 && direction == 1)){
                setTextAndSelection(model, text + symbol, maxSize + 3);
            }

            if (size == 16 && direction == 1){
                setTextAndSelection(model,text.substring(0, text.length() - 1) + symbol + text.substring(text.length() - 1), maxSize + 4);
            }
        }

    }
}
