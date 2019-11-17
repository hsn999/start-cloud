package com.start.base.common.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckDateValidator implements ConstraintValidator<CheckDate, String> {
    private String date;
    public void initialize(CheckDate checkDate) {
        this.date= checkDate.date();
    }

    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }

       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf.parse(s);
            this.date = sdf.parse(s).toString();
            return true;
        } catch (ParseException e) {
            //e.printStackTrace();
            this.date = null;
            
        }
		return false;

    }
}