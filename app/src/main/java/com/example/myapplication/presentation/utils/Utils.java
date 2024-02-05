package com.example.myapplication.presentation.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Utils {
    public static FirebaseAuth auth = FirebaseAuth.getInstance();
    public static FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    public static StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    public static final int FINE_PERMISSION_CODE = 1;
    public static final String JPG = ".jpg";
    public static final String PDF = ".pdf";
    public static final String QR_CODES = "qr-codes/";
    public static final String QUEUE_LIST = "QueueList"; //Collection
    public static final String ANONYMOUS_ID = "Anonymous";
    public static final String PROFILE_IMAGES = "profileImages/";
    public static final String PROFILE_PHOTO = "profilePhoto_";
    public static final String QUEUE_AUTHOR_KEY = "QueueAuthor";
    public static final String QUEUE_PARTICIPANTS_LIST = "ParticipantsList";
    public static final String QUEUE_DESCRIPTION_KEY = "QueueDescription";
    public static final String QUEUE_NAME_KEY = "QueueName";
    public static final String QUEUE_LIFE_TIME_KEY = "QueueLifeTime";
    public static final String PAGE_KEY = "Page";
    public static final String QUEUE_LOCATION_KEY = "QueueLocation";
    public static final String USER_LIST = "UsersList";
    public static final String USER_NAME_KEY = "UserName";
    public static final String EMAIL_KEY = "Email";
    public static final String PHONE_NUMBER_KEY = "PhoneNumber";
    public static final String GENDER_KEY = "Gender";
    public static final String TAG_EXCEPTION = "Exception";
    public static final String MALE_KEY = "Male";
    public static final String FEMALE_KEY = "Female";
    public static final String PAGE_1 = "PAGE_1";
    public static final String PAGE_2 = "PAGE_2";
    public static final String QUEUE_DATA = "QueueData";

}

/* QuServe
 QuServe - сервис, который решает проблему неудобного обслуживания в популярных общественных местах.

Сервисы:
1 - умная очередь для физических лиц и компаний. Что это такое? Если говорить о простых людях, то они смогут создавать
электронные очереди и давать qr-код для вступления в них другим людям. Если говорить о компаниях, то компания сможет заводить
несколько постоянных очередей  и давать их в управление своим сотрудникам(Например, кассы в супермаркетах, регистрации на рейсы в
аэропортах, почта и т.д). Приложение позволяте людям не стоять в живой очереди, а также на основе опыта предыдущих людей рассчитвает примерное время
ожидания.

2 - умное обслуживание ресторанов и кафе. Клиенты с помощью приложения выбирают ресторан, указывают номер столика(либо пропускают эти 2 шага,
отсканировав qr-код, который владельцы ресторана разместили на столике) и выбирают блюда, которые хотят заказать. После этого заказ отображается
в приложении у повара. Когда блюдо готово, повар отмечает это в приложении, официант также видит это в приложении и готов отнести
заказ по указанному номеру столика. Когда весь заказ готов - у клиента в приложении появляется оплата.

3 - умное обслуживание в самолете. Пассажир через приложение выбирает свою авиакомпанию и рейс, затем номер места и блюдо из списка
предложенных. Также можно выбрать блюдо из бесплатного планового меню, либо заказть платную еду, если авиакомпания предоставляет
такую возможность. Это облегчить работу бортпроводников, а также решит сложности с пониманием иностранного языка.
*/

//case PAGE_3:
//        buildList(new DelegateItem[]{
//        new ProgressBarDelegateItem(new ProgressBarModel(1, 70)),
//        new TextViewDelegateItem(new TextViewModel(2, R.string.choose_queue_location, 24)),
//        new EditTextDelegateItem(new EditTextModel(3, R.string.location, queueLocation, InputType.TYPE_CLASS_TEXT, false, stringLocation -> {
//        })),
//        new ButtonDelegateItem(new ButtonModel(4, R.string.open_map, () -> {
//        NavHostFragment.findNavController(fragment).navigate(R.id.action_createQueueFragment_to_mapFragment);
//        }))
//        });
//        break;
// case PAGE_3:
//                NavHostFragment.findNavController(fragment)
//                        .navigate((NavDirections) CreateQueueFragmentDirections.actionCreateQueueFragmentSelf(PAGE.PAGE_2));
//                break;
//case PAGE_3:
//        buildList(new DelegateItem[]{
//        new ProgressBarDelegateItem(new ProgressBarModel(1, 50)),
//        new InfoTextItemDelegateItem(new TextViewModel(2, R.string.invite_users_to_your_queue, 24)),
//        new EditTextDelegateItem(new EditTextModel(3, R.string.name, InputType.TYPE_CLASS_NUMBER, true, stringInvitedUsers -> {
//              invitedUser.add(Integer.parseInt(stringInvitedUsers));
//        })),
//          new FloatingActionButtonDelegateItem(new FloatingActionButtonModel(4, () -> {
//          List<DelegateItem> newItems = new ArrayList<>();
//          newItems.addAll(_items.getValue());
//          newItems.add(newItems.size()-1, new EditTextDelegateItem(new EditTextModel(5, R.string.name, InputType.TYPE_CLASS_NUMBER, true,
//        stringInvitedUsers -> {
//              invitedUser.add(Integer.parseInt(stringInvitedUsers));
//        })));
//              _items.setValue(newItems);
//             }))
//        });
//        break;\



