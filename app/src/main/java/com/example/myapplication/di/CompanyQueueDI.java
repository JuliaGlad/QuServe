package com.example.myapplication.di;

import com.example.myapplication.domain.usecase.companyQueue.employees.AddListEmployeesToQueue;
import com.example.myapplication.domain.usecase.companyQueue.employees.DeleteEmployeeFromQueueUseCase;
import com.example.myapplication.domain.usecase.companyQueue.employees.GetAdminsUseCase;
import com.example.myapplication.domain.usecase.companyQueue.employees.RemoveAdminFromAllQueuesAsWorkerUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.AddCompanyQueueParticipantsSizeSnapshot;
import com.example.myapplication.domain.usecase.companyQueue.queues.AddEmployeeToListQueues;
import com.example.myapplication.domain.usecase.companyQueue.queues.ContinueCompanyQueueUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.CreateCompanyQueueDocumentUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.DeleteCompanyQueueUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.DeleteEmployeeFromAllQueuesUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.GetCompaniesQueuesUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.GetCompanyQueueFinishTimeModelUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.GetCompanyQueueMidTmeModelUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.GetCompanyQueueParticipantsListUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.GetQueueByIdUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.GetQueueNameAndLocationById;
import com.example.myapplication.domain.usecase.companyQueue.queues.GetQueueWorkersListUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.GetQueuesParticipantSizeAndNameUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.NextCompanyQueueParticipantUpdateListUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.PauseCompanyQueueUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.UpdateCompanyQueueMidTimeUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.UpdateQueueDataUseCase;

public class CompanyQueueDI {
    public static ContinueCompanyQueueUseCase continueCompanyQueueUseCase = new ContinueCompanyQueueUseCase();
    public static PauseCompanyQueueUseCase pauseCompanyQueueUseCase = new PauseCompanyQueueUseCase();
    public static UpdateCompanyQueueMidTimeUseCase updateCompanyQueueMidTimeUseCase = new UpdateCompanyQueueMidTimeUseCase();
    public static GetCompanyQueueMidTmeModelUseCase getCompanyQueueMidTmeModelUseCase = new GetCompanyQueueMidTmeModelUseCase();
    public static GetCompanyQueueFinishTimeModelUseCase getCompanyQueueTimeModelUseCase = new GetCompanyQueueFinishTimeModelUseCase();
    public static AddEmployeeToListQueues addEmployeeToListQueues = new AddEmployeeToListQueues();
    public static RemoveAdminFromAllQueuesAsWorkerUseCase removeAdminFromAllQueuesAsWorkerUseCase = new RemoveAdminFromAllQueuesAsWorkerUseCase();
    public static AddListEmployeesToQueue addListEmployeesToQueue = new AddListEmployeesToQueue();
    public static DeleteEmployeeFromAllQueuesUseCase deleteEmployeeFromAllQueuesUseCase = new DeleteEmployeeFromAllQueuesUseCase();
    public static DeleteEmployeeFromQueueUseCase deleteEmployeeFromQueueUseCase = new DeleteEmployeeFromQueueUseCase();
    public static GetQueuesParticipantSizeAndNameUseCase getQueuesParticipantSizeAndNameUseCase = new GetQueuesParticipantSizeAndNameUseCase();
    public static DeleteCompanyQueueUseCase deleteCompanyQueueUseCase = new DeleteCompanyQueueUseCase();
    public static CreateCompanyQueueDocumentUseCase createCompanyQueueDocumentUseCase = new CreateCompanyQueueDocumentUseCase();
    public static GetCompaniesQueuesUseCase getCompaniesQueuesUseCase = new GetCompaniesQueuesUseCase();
    public static GetQueueWorkersListUseCase getQueueWorkersListUseCase = new GetQueueWorkersListUseCase();
    public static GetQueueByIdUseCase getQueueByIdUseCase = new GetQueueByIdUseCase();
    public static NextCompanyQueueParticipantUpdateListUseCase nextParticipantUseCompanyUseCase = new NextCompanyQueueParticipantUpdateListUseCase();
    public static GetCompanyQueueParticipantsListUseCase getCompanyQueueParticipantsListUseCase = new GetCompanyQueueParticipantsListUseCase();
    public static AddCompanyQueueParticipantsSizeSnapshot addCompanyQueueParticipantsSizeSnapshot = new AddCompanyQueueParticipantsSizeSnapshot();
    public static GetQueueNameAndLocationById getQueueNameAndLocationById = new GetQueueNameAndLocationById();
    public static GetAdminsUseCase getAdminsUseCase = new GetAdminsUseCase();
    public static UpdateQueueDataUseCase updateQueueDataUseCase = new UpdateQueueDataUseCase();
}
