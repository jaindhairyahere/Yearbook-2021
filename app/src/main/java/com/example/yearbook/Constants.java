package com.example.yearbook;

public class Constants {
    public static final String[] ACTIVITIES = {"AUV", "AZeotropy", "Aakaar", "Abhyuday", "Academic Council", "Adventure Club", "Aeromodelling Club", "Analytics Club", "Aquatics", "Astronomy Club", "Athletics", "Badminton", "Basketball", "BioTech Club", "Board Games", "Consult Club", "Cricket", "Department Council", "Design Club", "E-Cell", "Electronics Club", "Finance Club", "Football", "Fourthwall", "GRA", "Hockey", "Hostel Council", "IITB Racing", "IITB Student Satellite Program", "ISCP", "InSync", "Innovation Cell", "Insight", "Institute Cultural Council", "Institute Sports Council", "Institute Technical Council", "Kho-Kho", "Lawn Tennis", "LifeStyle Club", "Lit Club", "Litzkrieg", "Mars Society India", "Maths and Physics Club", "Mood Indigo", "Music Club", "NSS", "PT Cell", "Pixels", "Radiance", "Rang", "Robotics Club", "Roots", "SARC", "SMP", "STAB", "Saathi", "Saaz", "ShARE", "Silverscreen", "Squash", "Staccato", "Table Tennis", "Team Shunya", "Techfest", "Tinkerers Lab", "Vaani", "Volleyball", "WeSpeak", "Web and Coding Club", "Weightlifting"};
    public static final String[] DEPARTMENTS = {"Aerospace Engineering","Animation","Application Software Centre","Applied Geophysics","Applied Statistics and Informatics","Biomedical Engineering","Biosciences and Bioengineering","Biotechnology","Centre for Aerospace Systems Design and Engineering","Centre for Distance Engineering Education Programme","Centre for Environmental Science and Engineering","Centre for Formal Design and Verification of Software","Centre for Research in Nanotechnology and Science","Centre for Technology Alternatives for Rural Areas","Centre for Urban Science and Engineering","Centre of Studies in Resources Engineering","Chemical Engineering","Chemistry","Civil Engineering","Climate Studies","Computer Centre","Computer Science & Engineering","Continuing Education Programme","Corrosion Science and Engineering","Desai Sethi Centre for Entrepreneurship","Earth Sciences","Educational Technology","Electrical Engineering","Energy Science and Engineering","Humanities & Social Science","IITB-Monash Research Academy","Industrial Design Centre","Industrial Design Centre","Industrial Engineering and Operations Research","Industrial Management","Interaction Design","Kanwal Rekhi School of Information Technology","Material Science","Materials, Manufacturing and Modelling","Mathematics","Mechanical Engineering","Metallurgical Engineering & Materials Science","Mobility and Vehicle Design","National Centre for Aerospace Innovation and Research","National Centre for Mathematics","Physical Education","Physics","Physics, Material Science","Preparatory Course","Reliability Engineering","Shailesh J. Mehta School of Management","Sophisticated Analytical Instrument Facility","Systems and Control Engineering","Tata Center for Technology and Design","Technology and Development","Visual Communication","Wadhwani Research Centre for Bioengineering"};
    public static final String BASE_URL = "https://ybtest.sarc-iitb.org";
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 3;
    public static final int RESULT_LOAD_IMAGE = 11;
    public static final int REQUEST_CAMERA_INT_ID = 101;
    public static final String DARK_THEME = "dark_theme";
    public static final String CALENDAR_DIALOG = "calendar_dialog";
    public static final String CALENDAR_DIALOG_YES = "Yes";
    public static final String CALENDAR_DIALOG_NO = "No";
    public static final String CALENDAR_DIALOG_ALWAYS_ASK = "Always ask";
    public static final String NOTIFICATIONS_RESPONSE_JSON = "notifications_json";
    public static final String EVENT_ID = "event_id";
    public static final String EVENT_LATITUDE = "event_latitude";
    public static final String EVENT_LONGITUDE = "event_longitude";
    public static final String EVENT_JSON = "event_json";
    public static final String EVENT_LIST_JSON = "event_list_json";
    public static final String USER_ID = "user_id";
    public static final String USER_JSON = "user_json";
    public static final String USER_HOSTEL = "user_hostel";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PREF_NAME = "LoggedInPref";
    public static final String IS_LOGGED_IN = "IsLoggedIn";
    public static final String GCM_ID = "GcmId";
    public static final String CURRENT_USER = "current_user";
    public static final String CURRENT_USER_PROFILE_PICTURE = "current_user_profile_picture";
    public static final String SESSION_ID = "session_id";
    public static final int STATUS_GOING = 2;
    public static final int STATUS_INTERESTED = 1;
    public static final int STATUS_NOT_GOING = 0;
    public static final String BODY_JSON = "body_json";
    public static final String BODY_LIST_JSON = "body_list_json";
    public static final String ROLE_LIST_JSON = "role_list_json";
    public static final String NO_SHARED_ELEM = "no_shared_elem";

    public static final String LOGIN_MESSAGE = "Please login to continue!";

    public static final String MAIN_INTENT_EXTRAS = "MAIN_EXTRA";
    public static final String FCM_BUNDLE_TYPE = "type";
    public static final String FCM_BUNDLE_ID = "id";
    public static final String FCM_BUNDLE_EXTRA = "extra";
    public static final String FCM_BUNDLE_NOTIFICATION_ID = "notification_id";
    public static final String FCM_BUNDLE_ACTION = "action";
    public static final String FCM_BUNDLE_RICH = "rich";
    public static final String FCM_BUNDLE_TITLE = "title";
    public static final String FCM_BUNDLE_VERB = "verb";
    public static final String FCM_BUNDLE_IMAGE = "image_url";
    public static final String FCM_BUNDLE_LARGE_ICON = "large_icon";
    public static final String FCM_BUNDLE_LARGE_CONTENT = "large_content";
    public static final String FCM_BUNDLE_TOTAL_COUNT = "total_count";
    public static final String NOTIF_CANCELLED = "notification_cancelled";

    public static final String FCM_BUNDLE_ACTION_STARTING = "starting";

    public static final String DATA_TYPE_EVENT = "event";
    public static final String DATA_TYPE_BODY = "body";
    public static final String DATA_TYPE_USER = "userprofile";
    public static final String DATA_TYPE_NEWS = "newsentry";
    public static final String DATA_TYPE_PT = "blogentry";
    public static final String DATA_TYPE_MAP= "map";
    public static final String DATA_TYPE_MESS = "mess";

    public static final String CARD_TYPE_TITLE = "card_type_title";
    public static final String CARD_TYPE_BODY_HEAD = "card_type_body_head";

    /* Webview */
    public static final String WV_TYPE = "webview_type";
    public static final String WV_TYPE_ADD_EVENT = "add_event";
    public static final String WV_TYPE_UPDATE_EVENT = "update_event";
    public static final String WV_TYPE_UPDATE_BODY = "update_body";
    public static final String WV_TYPE_ACHIEVEMENTS = "achievements";
    public static final String WV_TYPE_NEW_OFFERED_ACHIEVEMENT = "achievements_new_offered";
    public static final String WV_TYPE_URL = "url_type";
    public static final String WV_ID = "id";
    public static final String WV_URL = "url";

    /* Map */
    public static final double MAP_Xn = 19.133691, MAP_Yn = 72.916984, MAP_Zn = 4189, MAP_Zyn = 1655;
    public static final double[] MAP_WEIGHTS_X = {-7.769917472065843, 159.26978694839946, 244.46989575495544, -6.003894110679995, -0.28864271213341297, 0.010398324019718075, 4.215508849724247, -0.6078830146963545, -7.0400449629241395};
    public static final double[] MAP_WEIGHTS_Y = {14.199431377059842, -158.80601990819815, 68.9630034040724, 5.796703402034644, 1.1348242200568706, 0.11891051684489184, -0.2930832938484276, 0.1448231125788526, -5.282895700923075};
    public static final String MAP_INITIAL_MARKER = "initial_marker";
    public static String client = "sarc-yearbook";
    public static String client_secret = "sarc";
}
