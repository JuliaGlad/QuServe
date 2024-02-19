package com.example.myapplication.presentation.utils;

public class Utils {
    public static final String QUEUE_ID = "QueueId";
    public static final int FINE_PERMISSION_CODE = 1;
    public static final String JPG = ".jpg";
    public static final String PDF = ".pdf";
    public static final String QR_CODES = "qr-codes/";
    public static final String QUEUE_LIST = "QueueList"; //Collection
    public static final String PROFILE_IMAGES = "profileImages/";
    public static final String PROFILE_PHOTO = "profilePhoto_";
    public static final String QUEUE_AUTHOR_KEY = "QueueAuthor";
    public static final String QUEUE_PARTICIPANTS_LIST = "ParticipantsList";
    public static final String QUEUE_NAME_KEY = "QueueName";
    public static final String QUEUE_LIFE_TIME_KEY = "QueueLifeTime";
    public static final String QUEUE_IN_PROGRESS = "InProgress";
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
    public static final String PAUSED_TIME = "Time on Pause";
    public static final String EDIT_ESTIMATED_TIME = "Edit estimated time";
    public static final String EDIT_PEOPLE_BEFORE_YOU = "Edit people before you";
    public static final String QUEUE_DATA = "QueueData";
    public static final String NOTIFICATION_CHANNEL_ID = "NOTE_ID";
    public static final String NOTIFICATION_CHANNEL_NAME = "Notification channel";
    public static final String YOUR_TURN_CHANNEL_ID = "YOUR_TURN_ID";
    public static final String YOUR_TURN_CHANNEL_NAME = "Your turn";
    public static final String PAUSED = "Paused";
    public static final String[] stringsArray  = {"NO_SET_LIFE_TIME","3 HOURS", "12 HOURS", "1 DAYS", "30 DAYS", "182 DAYS","365 DAYS"};
}


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



