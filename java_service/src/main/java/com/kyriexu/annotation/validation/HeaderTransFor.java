package com.kyriexu.annotation.validation;

import com.kyriexu.contraint.HeaderTransForValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author KyrieXu
 * @since 2021/4/12 15:33
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HeaderTransForValidator.class)
public @interface HeaderTransFor {
    String message() default "请求头处理不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
