package com.kyriexu.contraint;

import com.kyriexu.annotation.validation.IpList;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author KyrieXu
 * @since 2021/4/12 15:56
 **/
public class IpListValidator implements ConstraintValidator<IpList, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        for (String s : value.split(",")) {
            if (!s.matches("^\\S+$")) {
                return false;
            }
        }
        return true;
    }
}
