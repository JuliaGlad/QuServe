package com.example.myapplication.presentation.dialogFragments.deleteEmployeeFromCompany;

import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogDeleteCheckBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class DeleteEmployeeFromCompanyDialogFragment extends DialogFragment {

    DeleteEmployeeFromCompanyViewModel viewModel;
    DialogDismissedListener listener;
    private final String employeeId;
    private final String companyId;
    String role = null;

    public DeleteEmployeeFromCompanyDialogFragment(String employeeId, String companyId) {
        this.employeeId = employeeId;
        this.companyId = companyId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        DialogDeleteCheckBinding binding = DialogDeleteCheckBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(DeleteEmployeeFromCompanyViewModel.class);
        
        binding.textMain.setText(getString(R.string.are_you_sure_you_want_to_delete_this_employee_from_your_company));
        
        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonDelete.setEnabled(false);
            viewModel.deleteEmployee(employeeId, companyId);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && role != null){

            Bundle bundle = new Bundle();
            bundle.putString(EMPLOYEE_ROLE, role);

            listener.handleDialogClose(bundle);
        }
    }

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    private void setupObserves(){
       viewModel.role.observe(this, role -> {
           if (role != null){
               this.role = role;
               dismiss();
           }
       });
    }
}
