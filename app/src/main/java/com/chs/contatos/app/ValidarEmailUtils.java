package com.chs.contatos.app;


import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarEmailUtils {

    public boolean isValidEmailAddressRegex(String email){
       boolean isEmailValid = false;
        if (email != null && email.length()>0){
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()){
                isEmailValid = true;
            }
        }
        return isEmailValid;
    }

}
