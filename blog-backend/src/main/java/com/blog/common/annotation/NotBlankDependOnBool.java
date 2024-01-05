package com.blog.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import lombok.SneakyThrows;

/**
 * @author zouzhangpeng
 * @desc 校验字段根据有另一个字段的值判断是否不为空
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBlankDependOnBool.NotBlankDependOnValidator.class)
public @interface NotBlankDependOnBool {

    String message() default "Field is required when condition is met";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dependentField();

    boolean expectedValue();

    class NotBlankDependOnValidator implements ConstraintValidator<NotBlankDependOnBool, String> {


        /**
         * 依赖的字段
         */
        private String dependentField;

        /**
         * 期望结果
         */
        private Boolean expectedValue;

        @Override
        public void initialize(NotBlankDependOnBool notBlankDependOnBool) {
            this.dependentField = notBlankDependOnBool.dependentField();
            this.expectedValue = notBlankDependOnBool.expectedValue();
        }

        @SneakyThrows
        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            //todo:


            return false;
        }
    }
}
