package com.example.myapplication.di.company;

import com.example.myapplication.domain.usecase.companyQueue.company.AddCompanySnapshotUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.CheckCompanyExistUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.CreateCompanyDocumentUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.DeleteCompanyUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.DeleteEmployeeFromCompanyUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.GetCompaniesLogosUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.GetCompanyLogoUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.GetCompanyModelUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.GetCompanyUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.GetEmployeeRoleCompanyUseCaseByEmployee;
import com.example.myapplication.domain.usecase.companyQueue.company.GetSingleCompanyUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.UpdateApprovedUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.UpdateCompanyDataUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.UpdateRoleUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.UploadCompanyBytesUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.UploadCompanyLogoToFireStorageUseCase;
import com.example.myapplication.domain.usecase.companyQueue.employees.AddEmployeeSnapshotUseCase;
import com.example.myapplication.domain.usecase.companyQueue.employees.AddEmployeeUseCase;
import com.example.myapplication.domain.usecase.companyQueue.employees.GetEmployeePhotoUseCase;
import com.example.myapplication.domain.usecase.companyQueue.employees.GetEmployeeQrCodeUseCase;
import com.example.myapplication.domain.usecase.companyQueue.employees.GetEmployeesUseCase;
import com.example.myapplication.domain.usecase.companyQueue.queues.ContinueCompanyQueueUseCase;

public class CompanyQueueUserDI {
    public static GetEmployeeRoleCompanyUseCaseByEmployee getCompanyNameUseCaseByEmployee = new GetEmployeeRoleCompanyUseCaseByEmployee();
    public static DeleteEmployeeFromCompanyUseCase deleteEmployeeFromCompanyUseCase = new DeleteEmployeeFromCompanyUseCase();
    public static UpdateApprovedUseCase updateApprovedUseCase = new UpdateApprovedUseCase();
    public static GetEmployeePhotoUseCase getEmployeePhotoUseCase = new GetEmployeePhotoUseCase();
    public static AddEmployeeSnapshotUseCase addEmployeeSnapshotUseCase = new AddEmployeeSnapshotUseCase();
    public static AddCompanySnapshotUseCase addCompanySnapshotUseCase = new AddCompanySnapshotUseCase();
    public static UpdateRoleUseCase updateRoleUseCase = new UpdateRoleUseCase();
    public static UploadCompanyBytesUseCase uploadCompanyBytesUseCase = new UploadCompanyBytesUseCase();
    public static CreateCompanyDocumentUseCase createCompanyDocumentUseCase = new CreateCompanyDocumentUseCase();
    public static GetSingleCompanyUseCase getSingleCompanyUseCase = new GetSingleCompanyUseCase();
    public static GetEmployeesUseCase getEmployeesUseCase = new GetEmployeesUseCase();
    public static GetEmployeeQrCodeUseCase getEmployeeQrCodeUseCase = new GetEmployeeQrCodeUseCase();
    public static AddEmployeeUseCase addEmployeeUseCase = new AddEmployeeUseCase();
    public static UploadCompanyLogoToFireStorageUseCase uploadCompanyLogoToFireStorageUseCase = new UploadCompanyLogoToFireStorageUseCase();
    public static GetCompanyLogoUseCase getCompanyLogoUseCase = new GetCompanyLogoUseCase();
    public static UpdateCompanyDataUseCase updateCompanyDataUseCase = new UpdateCompanyDataUseCase();
    public static GetCompanyModelUseCase getCompanyModelUseCase = new GetCompanyModelUseCase();
    public static DeleteCompanyUseCase deleteCompanyUseCase = new DeleteCompanyUseCase();
}
