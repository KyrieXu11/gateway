package com.kyriexu.contraint;

import com.kyriexu.annotation.validation.HeaderTransFor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author KyrieXu
 * @since 2021/4/12 15:34
 **/
public class HeaderTransForValidator implements ConstraintValidator<HeaderTransFor, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ("".equals(value)) {
            return true;
        } else {
            return value.split(" ").length == 3;
        }
    }
}
