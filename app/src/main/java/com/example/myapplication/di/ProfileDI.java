package com.example.myapplication.di;

import com.example.myapplication.domain.usecase.profile.AddActiveRestaurantOrderUseCase;
import com.example.myapplication.domain.usecase.profile.AddEmployeeRoleUseCase;
import com.example.myapplication.domain.usecase.profile.AddSnapshotProfileUseCase;
import com.example.myapplication.domain.usecase.profile.ChangePasswordUseCase;
import com.example.myapplication.domain.usecase.profile.CheckAuthenticationUseCase;
import com.example.myapplication.domain.usecase.profile.CheckBooleanDataUseCase;
import com.example.myapplication.domain.usecase.profile.CheckPasswordUseCase;
import com.example.myapplication.domain.usecase.profile.CheckVerificationUseCase;
import com.example.myapplication.domain.usecase.profile.CreateAccountUseCase;
import com.example.myapplication.domain.usecase.profile.DeleteAccountUseCase;
import com.example.myapplication.domain.usecase.profile.DeleteActiveQueueUseCase;
import com.example.myapplication.domain.usecase.profile.DeleteEmployeeRoleUseCase;
import com.example.myapplication.domain.usecase.profile.GetActiveQueuesByEmployeeIdUseCase;
import com.example.myapplication.domain.usecase.profile.GetActiveQueuesUseCase;
import com.example.myapplication.domain.usecase.profile.GetActiveRestaurantOrderUseCase;
import com.example.myapplication.domain.usecase.profile.GetBackgroundImageUseCase;
import com.example.myapplication.domain.usecase.profile.GetEmployeeRolesUseCase;
import com.example.myapplication.domain.usecase.profile.GetHistoryUseCase;
import com.example.myapplication.domain.usecase.profile.GetParticipantQueuePathUseCase;
import com.example.myapplication.domain.usecase.profile.GetProfileEditUseCase;
import com.example.myapplication.domain.usecase.profile.GetProfileImageUseCase;
import com.example.myapplication.domain.usecase.profile.GetUserBooleanDataUseCase;
import com.example.myapplication.domain.usecase.profile.GetUserEmailAndNameDataUseCase;
import com.example.myapplication.domain.usecase.profile.IsEmployeeUseCase;
import com.example.myapplication.domain.usecase.profile.IsNullUseCase;
import com.example.myapplication.domain.usecase.profile.SendResetPasswordEmailUseCase;
import com.example.myapplication.domain.usecase.profile.SendVerificationEmailUseCase;
import com.example.myapplication.domain.usecase.profile.SignInWithEmailAndPasswordUseCase;
import com.example.myapplication.domain.usecase.profile.SignOutUseCase;
import com.example.myapplication.domain.usecase.profile.UpdateEmailFieldUseCase;
import com.example.myapplication.domain.usecase.profile.UpdateEmployeeRoleUseCase;
import com.example.myapplication.domain.usecase.profile.UpdateOwnQueueUseCase;
import com.example.myapplication.domain.usecase.profile.UpdateParticipateInQueueUseCase;
import com.example.myapplication.domain.usecase.profile.UpdateUserDataUseCase;
import com.example.myapplication.domain.usecase.profile.UploadBackgroundImageUseCase;
import com.example.myapplication.domain.usecase.profile.UploadProfileImageToFireStorageUseCase;
import com.example.myapplication.domain.usecase.profile.VerifyBeforeUpdateEmailUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetOwnQueueData;
import com.example.myapplication.domain.usecase.queue.usecase.GetParticipateInQueueData;

public class ProfileDI {
    public static GetActiveRestaurantOrderUseCase getActiveRestaurantOrderUseCase = new GetActiveRestaurantOrderUseCase();
    public static AddActiveRestaurantOrderUseCase addActiveRestaurantOrderUseCase = new AddActiveRestaurantOrderUseCase();
    public static GetParticipantQueuePathUseCase getParticipantQueuePathUseCase = new GetParticipantQueuePathUseCase();
    public static GetActiveQueuesByEmployeeIdUseCase getActiveQueuesByEmployeeIdUseCase = new GetActiveQueuesByEmployeeIdUseCase();
    public static UpdateEmployeeRoleUseCase updateEmployeeRoleUseCase = new UpdateEmployeeRoleUseCase();
    public static DeleteActiveQueueUseCase deleteActiveQueueUseCase = new DeleteActiveQueueUseCase();
    public static DeleteEmployeeRoleUseCase deleteEmployeeRoleUseCase = new DeleteEmployeeRoleUseCase();
    public static GetActiveQueuesUseCase getActiveQueuesUseCase = new GetActiveQueuesUseCase();
    public static GetEmployeeRolesUseCase getEmployeeRolesUseCase = new GetEmployeeRolesUseCase();
    public static IsEmployeeUseCase isEmployeeUseCase = new IsEmployeeUseCase();
    public static AddEmployeeRoleUseCase addEmployeeRoleUseCase = new AddEmployeeRoleUseCase();
    public static GetHistoryUseCase getHistoryUseCase = new GetHistoryUseCase();
    public static IsNullUseCase isNullUseCase = new IsNullUseCase();
    public static CheckPasswordUseCase checkPasswordUseCase = new CheckPasswordUseCase();
    public static UpdateParticipateInQueueUseCase updateParticipateInQueueUseCase = new UpdateParticipateInQueueUseCase();
    public static GetParticipateInQueueData getParticipateInQueueData = new GetParticipateInQueueData();
    public static GetOwnQueueData getOwnQueueData = new GetOwnQueueData();
    public static CheckBooleanDataUseCase checkBooleanDataUseCase = new CheckBooleanDataUseCase();
    public static GetUserBooleanDataUseCase getUserBooleanDataUseCase = new GetUserBooleanDataUseCase();
    public static UpdateOwnQueueUseCase updateOwnQueueUseCase = new UpdateOwnQueueUseCase();
    public static AddSnapshotProfileUseCase addSnapshotProfileUseCase = new AddSnapshotProfileUseCase();
    public static CheckVerificationUseCase checkVerificationUseCase = new CheckVerificationUseCase();
    public static GetUserEmailAndNameDataUseCase getUserEmailAndNameDataUseCase = new GetUserEmailAndNameDataUseCase();
    public static GetProfileImageUseCase getProfileImageUseCase = new GetProfileImageUseCase();
    public static GetProfileEditUseCase getProfileEditUseCase = new GetProfileEditUseCase();
    public static UpdateUserDataUseCase updateUserDataUseCase = new UpdateUserDataUseCase();
    public static UploadProfileImageToFireStorageUseCase uploadProfileImageToFireStorageUseCase = new UploadProfileImageToFireStorageUseCase();
    public static SignInWithEmailAndPasswordUseCase signInWithEmailAndPasswordUseCase = new SignInWithEmailAndPasswordUseCase();
    public static SendResetPasswordEmailUseCase sendResetPasswordEmailUseCase = new SendResetPasswordEmailUseCase();
    public static SignOutUseCase signOutUseCase = new SignOutUseCase();
    public static CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase();
    public static SendVerificationEmailUseCase sendVerificationEmailUseCase = new SendVerificationEmailUseCase();
    public static DeleteAccountUseCase deleteAccountUseCase = new DeleteAccountUseCase();
    public static CheckAuthenticationUseCase checkAuthenticationUseCase = new CheckAuthenticationUseCase();
    public static UpdateEmailFieldUseCase updateEmailFieldUseCase = new UpdateEmailFieldUseCase();
    public static VerifyBeforeUpdateEmailUseCase verifyBeforeUpdateEmailUseCase = new VerifyBeforeUpdateEmailUseCase();
    public static ChangePasswordUseCase changePasswordUseCase = new ChangePasswordUseCase();
    public static UploadBackgroundImageUseCase uploadBackgroundImageUseCase = new UploadBackgroundImageUseCase();
    public static GetBackgroundImageUseCase getBackgroundImageUseCase = new GetBackgroundImageUseCase();
}
