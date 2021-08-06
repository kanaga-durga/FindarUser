package org.findar.user.Helper;

public class Constant {

    public static String MAINBASEUrl = "http://staging.findar.co.za/"; //Admin panel url
    public static String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=";
    public static String BaseUrl = MAINBASEUrl + "webservice/api/";

    //**********APIS**********
    public static String MOBILE_VALIDATION = BaseUrl + "mobile_validation";
    public static String REGISTER = BaseUrl + "register";
    public static String LOGIN = BaseUrl + "login";
    //public static String GET_USER_DETAILS = BaseUrl + "getuserdetails";
    public static String GET_USER_DETAILS = BaseUrl + "getclientdetails";
    public static String GET_SERVICES_LIST = BaseUrl + "getserviceslist";
    public static String GET_PROBLEMS_LIST = BaseUrl + "getproblemslist";
    public static String GET_SUB_PROBLEMS_LIST = BaseUrl + "getsubproblemslist";
    public static String CHANGE_PASSWORD = BaseUrl + "changepassword";
    public static String GET_PROVINCE_LIST = BaseUrl + "getProvinceList";
    public static String GET_CITY_LIST = BaseUrl + "getCityList";
    public static String GET_SUBURB_LIST = BaseUrl + "getSuburbList";
    public static String UPDATE_USER_DETAILS = BaseUrl + "updateuserdetails";

    public static String REQUESTOTP = BaseUrl + "requestOTP";
    public static String CHECKOTP = BaseUrl + "checkOTP";
    public static String TERMS = BaseUrl + "client_terms_conditions";
    public static String CREATEJOB = BaseUrl + "createjob";
    public static String JOBLIST = BaseUrl + "job_list";
    public static String COMPANYDETAILS = BaseUrl + "getCompany";

    public static  String BOOKJOB = BaseUrl + "bookJob";
    public static String JOBINFORMATION = BaseUrl + "jobinformation";
    public static String REASONCANCEL = BaseUrl + "reasons";
    public static String CANCELJOB = BaseUrl + "canceljob";

    public static String ORDERHISTORY = BaseUrl + "jobHistory";
    public static String CHATLIST = BaseUrl + "chatList";
    public static String CHATACTION = BaseUrl + "chatAction";
    public static String UPDATE_DEVICEID = BaseUrl + "update_deviceid";

    // Params for
    // MOBILE_VALIDATION, REGISTER
    public static String PMOBILE = "mobile";
    public static String PID = "id";
    public static String P_USER_DETAIL_ID = "usersdetailid";
    public static String PUSER_ID = "user_id";
    public static String PFILE = "file";
    public static String PFILE_NAME = "file_name";
    public static String PSERVICES_ID = "services_id";
    public static String PPROBLEMS_ID = "problems_id";
    public static String POTPCODE ="otpcode";

    public static String DEVICEID = "";

    // REGISTER, LOGIN
    public static String PFIRST_NAME = "first_name";
    public static String PLAST_NAME = "last_name";
    public static String PEMAIL = "email";             // Login
    public static String PDOB = "dob";
    public static String PPASSWORD = "password";       // Login
    public static String P_OLD_PASSWORD = "oldpassword";       // Change password
    public static String PPROVINCE_ID = "province_id";
    public static String PCITY_ID = "city_id";

    public static String P_ADDRESS_ID_1 = "addressid1";
    public static String P_ADDRESS_1 = "address1";
    public static String P_PROVINCE_1 = "province1";
    public static String P_CITY_1 = "city1";
    public static String P_SUBURB_1 = "suburb1";
    public static String P_POST_1 = "postal_code1";

    public static String P_ADDRESS_ID_2 = "addressid2";
    public static String P_ADDRESS_2 = "address2";
    public static String P_PROVINCE_2 = "province2";
    public static String P_CITY_2 = "city2";
    public static String P_SUBURB_2 = "suburb2";
    public static String P_POST_2 = "postal_code2";

    //CREATE JOB PARAM
    public static String P_SERVICES = "services";
    public static String P_PROBLEMS = "problems";
    public static String P_SUBPROBLEMS = "subproblems";
    public static String P_ADDITIONALINFO = "additional_info";
    public static String  P_ORDERNO = "order_number";
    public static String  P_ADDRESS = "address";
    public static String P_PROVINCE  ="province";
    public static String P_CITY = "city";
    public static String P_SUBURB = "suburb";
    public static String P_POSTALCODE = "postal_code";
    public static String P_ASSOONAS = "as_soon_as";
    public static String P_JOBDATE = "jobs_date";
    public static String P_JOBTIME = "jobs_time";
    public static String P_LATITUDE = "latitude";
    public static String P_LOGITUDE = "longitude";
    public static String P_FILECOUNT = "filecount";
    public static String P_FILE = "file";
    public static String P_FILENAME = "file_name";

    public static String P_COMPANYID="company_id";
    public static String P_COMPANY_ID = "company id";
    public static  String P_JOB_ID = "job id";
    public static String P_REASON_ID = "reason_id";

    public static String P_JOBID = "job_id";
    public static String P_FROMID = "from_id";
    public static String P_TOID = "to_id";
    public static String P_MESSAGE = "message";

    public static  String P_DEVICEID =  "device_id";
    public static  String P_DEVICETYPE = "device_type";








    //   Common String

    public static String Home_ID = "";
    public static String Home_Address = "";
    public static String Home_Province_ID = "";
    public static String Home_Province_Name = "";
    public static String Home_City_ID = "";
    public static String Home_City_Name = "";
    public static String Home_Suburb_ID = "";
    public static String Home_Suburb_Name = "";
    public static String Home_Postal = "";

    public static String WORK_ID = "";
    public static String WORK_Address = "";
    public static String WORK_Province_ID = "";
    public static String WORK_Province_Name = "";
    public static String WORK_City_ID = "";
    public static String WORK_City_Name = "";
    public static String WORK_Suburb_ID = "";
    public static String WORK_Suburb_Name = "";
    public static String WORK_Postal = "";

    public static String USER_EMAIL = "";
    public static String USER_DOB = "";
    public static String USER_MOBILE = "";
    public static String USER_F_NAME = "";
    public static String USER_L_NAME = "";
    public static String USER_P_FILE = "";
    public static String USER_P_FILE_NAME = "";


    public static String PL_ID = "";
    public static String PL_NAME = "";
    public static String PL_IMG = "";
    public static String SL_ID = "";
    public static String SL_NAME = "";
    public static String SPL_ID = "";
    public static String SPL_NAME = "";
    public static String USER_DETAIL_ID = "";
    public static String USER_ID = "";
    public static String MOBILE_NO = "";
    public static String DESCRIBE_PROBLEM = "";
    public static String ORDERNO = "";
    public static String ADDRESS ="";
    public static String PROVINCE = "";
    public static String CITY = "";
    public static String SUBURB = "";
    public static String POSTALCODE = "";
    public static String ASSOONAS = "";
    public static String JOBDATE = "";
    public static String JOBTIME = "";
    public static String LATITUDE = "";
    public static String LONGITUDE = "";
    public static String COMPANYID = "";
    public static String JOBID = "";
    public static String JOBSTATUS = "";

    public static  String FILECOUNT = "";
    public static String FILENAME = "";
    public static String FILE ="";

    public static String REASONID ="";
    public static String COMPANYNAME = "";

    public static String Please_enter_this_field = "Please enter this field";
    public static String Invalid_Password = "Passwords must 8-16 characters long, with at least 1 uppercase, 1 lowercase and 1 number!";
    public static String Invalid_Email = "Invalid email";
    public static final String ERROR = "error";
    public static String Password_not_match = "Passwords not match!";
    public static String DatePick_Error = "Please select date and time";


    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static int POTENTIAL_END =0;
    public static int YOURJOB_START = 0;
    public static String JOBSTATUS_MSG ="";

}
