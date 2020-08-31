package qazi.tooba.com.drawer.Common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import qazi.tooba.com.drawer.Model.Rider;
import qazi.tooba.com.drawer.Remote.FCMClient;
import qazi.tooba.com.drawer.Remote.IFCMService;

/**
 * Created by Ahmed on 2/5/2018.
 */

public class Common {
    public static final String drivers="Drivers";
    public static final String user_drivers="DriversInformation";
    public static final String user_rider="RidersInformation";
    public static final String pickup_request="PickupRequest";
    public static final String token_tbl="Tokens";
    public static final String fcmURL="https://fcm.googleapis.com/";

    public static Rider currentRider ;
    public static final String User_Field = "rider-usr";
    public static final String Pwd_Field = "rider-Pwd";


    public static IFCMService getFCMService()
    {
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
