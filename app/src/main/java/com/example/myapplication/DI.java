package com.example.myapplication;

import com.example.myapplication.data.FirebaseUserService;
import com.example.myapplication.data.repository.ProfileRepository;
import com.example.myapplication.data.repository.QueueRepository;
import com.example.myapplication.domain.usecase.profile.ChangePasswordUseCase;
import com.example.myapplication.domain.usecase.profile.CheckAuthentificationUseCase;
import com.example.myapplication.domain.usecase.profile.CheckUserIdUseCase;
import com.example.myapplication.domain.usecase.profile.CheckVerificationUseCase;
import com.example.myapplication.domain.usecase.profile.CreateAccountUseCase;
import com.example.myapplication.domain.usecase.profile.DeleteAccountUseCase;
import com.example.myapplication.domain.usecase.profile.GetProfileEditUseCase;
import com.example.myapplication.domain.usecase.profile.GetProfileImageUseCase;
import com.example.myapplication.domain.usecase.profile.SendVerificationEmailUseCase;
import com.example.myapplication.domain.usecase.profile.SignInAnonymouslyUseCase;
import com.example.myapplication.domain.usecase.profile.UpdateEmailFieldUseCase;
import com.example.myapplication.domain.usecase.profile.VerifyBeforeUpdateEmailUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.AddDocumentSnapShot;
import com.example.myapplication.domain.usecase.queue.usecase.AddToParticipantsListUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.CreateQueueDocumentUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetParticipantsList;
import com.example.myapplication.domain.usecase.queue.usecase.GetQrCodeImageUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQrCodePdfUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQueueByAuthorUseCase;
import com.example.myapplication.domain.usecase.profile.GetUserEmailAndPasswordDataUseCase;
import com.example.myapplication.domain.usecase.profile.SendResetPasswordEmailUseCase;
import com.example.myapplication.domain.usecase.profile.SignInWithEmailAndPasswordUseCase;
import com.example.myapplication.domain.usecase.profile.SignOutUseCase;
import com.example.myapplication.domain.usecase.profile.UpdateUserDataUseCase;
import com.example.myapplication.domain.usecase.profile.UploadToFireStorageUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQueueByParticipantIdUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQueueByQueueIdUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.UploadBytesToFireStorageUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.UploadFileToFireStorageUseCase;

public class DI {
    public static FirebaseUserService service = FirebaseUserService.getInstance();

    public static QueueRepository queueRepository = new QueueRepository();
    public static ProfileRepository profileRepository = new ProfileRepository();

    //PROFILE USECASE
    public static CheckVerificationUseCase checkVerificationUseCase = new CheckVerificationUseCase();
    public static GetUserEmailAndPasswordDataUseCase getUserEmailAndPasswordDataUseCase = new GetUserEmailAndPasswordDataUseCase();
    public static GetProfileImageUseCase getProfileImageUseCase = new GetProfileImageUseCase();
    public static GetProfileEditUseCase getProfileEditUseCase = new GetProfileEditUseCase();
    public static UpdateUserDataUseCase updateUserDataUseCase = new UpdateUserDataUseCase();
    public static UploadToFireStorageUseCase uploadToFireStorageUseCase = new UploadToFireStorageUseCase();
    public static SignInWithEmailAndPasswordUseCase signInWithEmailAndPasswordUseCase = new SignInWithEmailAndPasswordUseCase();
    public static SendResetPasswordEmailUseCase sendResetPasswordEmailUseCase = new SendResetPasswordEmailUseCase();
    public static SignOutUseCase signOutUseCase = new SignOutUseCase();
    public static CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase();
    public static SendVerificationEmailUseCase sendVerificationEmailUseCase = new SendVerificationEmailUseCase();
    public static DeleteAccountUseCase deleteAccountUseCase = new DeleteAccountUseCase();
    public static CheckAuthentificationUseCase checkAuthentificationUseCase = new CheckAuthentificationUseCase();
    public static UpdateEmailFieldUseCase updateEmailFieldUseCase = new UpdateEmailFieldUseCase();
    public static VerifyBeforeUpdateEmailUseCase verifyBeforeUpdateEmailUseCase = new VerifyBeforeUpdateEmailUseCase();
    public static ChangePasswordUseCase changePasswordUseCase = new ChangePasswordUseCase();

    //QUEUE USECASE
    public static GetQueueByAuthorUseCase getQueueByAuthorUseCase = new GetQueueByAuthorUseCase();
    public static GetQueueByQueueIdUseCase getQueueByQueueIdUseCase = new GetQueueByQueueIdUseCase();
    public static CreateQueueDocumentUseCase createQueueDocumentUseCase = new CreateQueueDocumentUseCase();
    public static UploadBytesToFireStorageUseCase uploadBytesToFireStorageUseCase = new UploadBytesToFireStorageUseCase();
    public static UploadFileToFireStorageUseCase uploadFileToFireStorageUseCase = new UploadFileToFireStorageUseCase();
    public static GetQrCodeImageUseCase getQrCodeImageUseCase = new GetQrCodeImageUseCase();
    public static AddToParticipantsListUseCase addToParticipantsListUseCase = new AddToParticipantsListUseCase();
    public static SignInAnonymouslyUseCase signInAnonymouslyUseCase = new SignInAnonymouslyUseCase();
    public static CheckUserIdUseCase checkUserIdUseCase = new CheckUserIdUseCase();
    public static GetQrCodePdfUseCase getQrCodePdfUseCase = new GetQrCodePdfUseCase();
    public static GetParticipantsList getParticipantsList = new GetParticipantsList();
    public static AddDocumentSnapShot addDocumentSnapShot = new AddDocumentSnapShot();
    public static GetQueueByParticipantIdUseCase getQueueByParticipantIdUseCase = new GetQueueByParticipantIdUseCase();
}
