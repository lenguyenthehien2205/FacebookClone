package com.project.facebook.utils;

import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;

public class StringFormatUtils {
    public static String getProfileFullName(Profile profile) {
        if (profile.getDisplayFormat().equals("firstname_lastname")) {
            return profile.getFirstName() + " " + profile.getLastName();
        }
        return profile.getLastName() + " " + profile.getFirstName();
    }
}
