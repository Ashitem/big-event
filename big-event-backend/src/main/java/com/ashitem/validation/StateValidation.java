package com.ashitem.validation;

import com.ashitem.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {//第一个参数给那个注解提供校验规则，第二个参数校验的规则类型

    /**
     *
     * @param s 将来要校验的数据
     * @param constraintValidatorContext 1
     * @return 如果返回false，校验不通过，反之，通过
     */

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //提供校验规则
        if (s==null){
            return false;
        }
        if (s.equals("草稿")||s.equals("已发布")){
            return true;
        }
      return false;
    }


}
