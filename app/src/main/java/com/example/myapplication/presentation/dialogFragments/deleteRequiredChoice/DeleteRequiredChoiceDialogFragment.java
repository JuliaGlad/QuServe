package com.example.myapplication.presentation.dialogFragments.deleteRequiredChoice;

import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogDeleteCheckBinding;
import com.example.myapplication.presentation.dialogFragments.deleteEmployeeFromCompany.DeleteEmployeeFromCompanyViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.checkerframework.checker.units.qual.A;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class DeleteRequiredChoiceDialogFragment extends DialogFragment {
    DeleteRequiredChoiceViewModel viewModel;
    DialogDismissedListener listener;
    private String restaurantId, categoryId, dishId, choiceId;
    private boolean isDelete = false;

    public DeleteRequiredChoiceDialogFragment(String restaurantId, String categoryId, String dishId, String choiceId) {
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.dishId = dishId;
        this.choiceId = choiceId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        DialogDeleteCheckBinding binding = DialogDeleteCheckBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(DeleteRequiredChoiceViewModel.class);

        binding.textMain.setText(getString(R.string.are_you_sure_you_want_to_delete_this_choice));

        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            viewModel.deleteChoice(restaurantId, categoryId, dishId, choiceId);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isDelete){
            listener.handleDialogClose(null);
        }
    }

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    private void setupObserves(){
        viewModel.isChoiceDelete.observe(this, aBoolean -> {
            if (aBoolean){
                isDelete = true;
                dismiss();
            }
        });
    }
}
