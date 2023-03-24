package com.edm.mailgun.expanders;

import com.edm.mailgun.enums.EnumWithValue;
import feign.Param;

public class EnumExpander implements Param.Expander {

    @Override
    public String expand(Object enumWithValue) {
        return ((EnumWithValue) enumWithValue).getValue();
    }
}
