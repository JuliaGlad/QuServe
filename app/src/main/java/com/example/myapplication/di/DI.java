package com.example.myapplication.di;

import com.example.myapplication.data.FirebaseUserService;
import com.example.myapplication.data.repository.CommonCompanyRepository;
import com.example.myapplication.data.repository.CompanyQueueRepository;
import com.example.myapplication.data.repository.CompanyQueueUserRepository;
import com.example.myapplication.data.repository.ProfileRepository;
import com.example.myapplication.data.repository.QueueRepository;
import com.example.myapplication.data.repository.RestaurantOwnerRepository;

public class DI {
    public static FirebaseUserService service = FirebaseUserService.getInstance();
    public static QueueRepository queueRepository = new QueueRepository();
    public static ProfileRepository profileRepository = new ProfileRepository();
    public static CompanyQueueUserRepository companyQueueUserRepository = new CompanyQueueUserRepository();
    public static CompanyQueueRepository companyQueueRepository = new CompanyQueueRepository();
    public static RestaurantOwnerRepository restaurantOwnerRepository = new RestaurantOwnerRepository();
    public static CommonCompanyRepository commonCompanyRepository = new CommonCompanyRepository();

}
