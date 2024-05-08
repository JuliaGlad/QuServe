package com.example.myapplication.di;

import com.example.myapplication.data.FirebaseUserService;
import com.example.myapplication.data.repository.CommonCompanyRepository;
import com.example.myapplication.data.repository.companyQueue.CompanyQueueRepository;
import com.example.myapplication.data.repository.companyQueue.CompanyQueueUserRepository;
import com.example.myapplication.data.repository.profile.ProfileRepository;
import com.example.myapplication.data.repository.QueueRepository;

public class DI {
    public static FirebaseUserService service = FirebaseUserService.getInstance();
    public static QueueRepository queueRepository = new QueueRepository();
    public static CompanyQueueUserRepository companyQueueUserRepository = new CompanyQueueUserRepository();
    public static CompanyQueueRepository companyQueueRepository = new CompanyQueueRepository();
    public static CommonCompanyRepository commonCompanyRepository = new CommonCompanyRepository();

}
