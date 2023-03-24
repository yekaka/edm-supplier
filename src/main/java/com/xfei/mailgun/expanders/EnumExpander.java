package com.xfei.mailgun.expanders;

import com.xfei.mailgun.enums.EnumWithValue;
import feign.Param;

public class EnumExpander implements Param.Expander {

    @Override
    public String expand(Object enumWithValue) {
        return ((EnumWithValue) enumWithValue).getValue();
    }
}
