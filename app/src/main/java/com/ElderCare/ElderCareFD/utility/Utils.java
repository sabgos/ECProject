package com.ElderCare.ElderCareFD.utility;

import android.content.Context;
import android.content.Intent;

import com.ElderCare.ElderCareFD.activity.MainActivity;

public class Utils {
    static void logout(Context context){
        SaveSharedPreference.setLoggedIn(context, false);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
