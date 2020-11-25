package com.ElderCare.ElderCareFD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
