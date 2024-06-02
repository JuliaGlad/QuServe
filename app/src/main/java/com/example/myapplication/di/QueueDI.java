package com.example.myapplication.di;

import com.example.myapplication.domain.usecase.profile.CheckUserIdUseCase;
import com.example.myapplication.domain.usecase.profile.SignInAnonymouslyUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.AddInProgressDocumentSnapshotUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.AddPeopleBeforeYouSnapshot;
import com.example.myapplication.domain.usecase.queue.usecase.AddQueueSizeModelSnapShot;
import com.example.myapplication.domain.usecase.queue.usecase.AddQueueToHistoryUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.AddSnapshotQueueUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.AddToParticipantsListUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.CheckParticipantIndexUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.ContinueQueueUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.CreateQueueDocumentUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.DeleteQrCodeUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.FinishQueueUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetParticipantsListUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQrCodeImageUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQrCodePdfUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQueueByAuthorUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQueueByParticipantPathUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQueueByQueuePathUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQueueInProgressModelUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.GetQueueMidTimeModel;
import com.example.myapplication.domain.usecase.queue.usecase.GetQueueTimeModelUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.NextParticipantUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.OnContainParticipantUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.OnParticipantLeftUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.OnPausedUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.PauseQueueUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.RemoveParticipantById;
import com.example.myapplication.domain.usecase.queue.usecase.UpdateMidTimeUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.UploadBytesToFireStorageUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.UploadFileToFireStorageUseCase;
import com.example.myapplication.domain.usecase.queue.usecase.onParticipantServedUseCase;

public class QueueDI {
    public static AddInProgressDocumentSnapshotUseCase addInProgressDocumentSnapshotUseCase = new AddInProgressDocumentSnapshotUseCase();
    public static AddQueueToHistoryUseCase addQueueToHistoryUseCase = new AddQueueToHistoryUseCase();
    public static DeleteQrCodeUseCase deleteQrCodeUseCase = new DeleteQrCodeUseCase();
    public static GetQueueMidTimeModel getQueueMidTimeModel = new GetQueueMidTimeModel();
    public static UpdateMidTimeUseCase updateMidTimeUseCase = new UpdateMidTimeUseCase();
    public static GetQueueInProgressModelUseCase getQueueInProgressModelUseCase = new GetQueueInProgressModelUseCase();
    public static CheckParticipantIndexUseCase checkParticipantIndexUseCase = new CheckParticipantIndexUseCase();
    public static AddPeopleBeforeYouSnapshot addPeopleBeforeYouSnapshot = new AddPeopleBeforeYouSnapshot();
    public static OnPausedUseCase onPausedUseCase = new OnPausedUseCase();
    public static AddSnapshotQueueUseCase addSnapshotQueueUseCase = new AddSnapshotQueueUseCase();
    public static ContinueQueueUseCase continueQueueUseCase = new ContinueQueueUseCase();
    public static PauseQueueUseCase pauseQueueUseCase = new PauseQueueUseCase();
    public static com.example.myapplication.domain.usecase.queue.usecase.onParticipantServedUseCase onParticipantServedUseCase = new onParticipantServedUseCase();
    public static GetQueueTimeModelUseCase getQueueTimeModelUseCase = new GetQueueTimeModelUseCase();
    public static FinishQueueUseCase finishQueueUseCase = new FinishQueueUseCase();
    public static GetQueueByAuthorUseCase getQueueByAuthorUseCase = new GetQueueByAuthorUseCase();
    public static GetQueueByQueuePathUseCase getQueueByPathUseCase = new GetQueueByQueuePathUseCase();
    public static CreateQueueDocumentUseCase createQueueDocumentUseCase = new CreateQueueDocumentUseCase();
    public static UploadBytesToFireStorageUseCase uploadBytesToFireStorageUseCase = new UploadBytesToFireStorageUseCase();
    public static UploadFileToFireStorageUseCase uploadFileToFireStorageUseCase = new UploadFileToFireStorageUseCase();
    public static GetQrCodeImageUseCase getQrCodeImageUseCase = new GetQrCodeImageUseCase();
    public static AddToParticipantsListUseCase addToParticipantsListUseCase = new AddToParticipantsListUseCase();
    public static SignInAnonymouslyUseCase signInAnonymouslyUseCase = new SignInAnonymouslyUseCase();
    public static GetQrCodePdfUseCase getQrCodePdfUseCase = new GetQrCodePdfUseCase();
    public static GetParticipantsListUseCase getParticipantsListUseCase = new GetParticipantsListUseCase();
    public static AddQueueSizeModelSnapShot addQueueSizeModelSnapShot = new AddQueueSizeModelSnapShot();
    public static GetQueueByParticipantPathUseCase getQueueByParticipantPathUseCase = new GetQueueByParticipantPathUseCase();
    public static NextParticipantUseCase nextParticipantUseCase = new NextParticipantUseCase();
    public static RemoveParticipantById removeParticipantById = new RemoveParticipantById();
    public static OnContainParticipantUseCase onContainParticipantUseCase = new OnContainParticipantUseCase();
    public static OnParticipantLeftUseCase onParticipantLeftUseCase = new OnParticipantLeftUseCase();
}
