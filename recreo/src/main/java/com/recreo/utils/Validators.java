package com.recreo.utils;

import com.recreo.exceptions.RecreoApiException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static String validateEmail(String email) throws RecreoApiException {
        if (email == null || email.isEmpty()) {
            throw new RecreoApiException("La dirección de correo electrónico no puede estar vacía.");
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new RecreoApiException("La dirección de correo electrónico no es válida.");
        }

        return null;
    }

}
