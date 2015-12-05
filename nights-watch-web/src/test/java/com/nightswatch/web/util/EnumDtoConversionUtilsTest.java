package com.nightswatch.web.util;

import com.nightswatch.api.dto.violation.DangerLevelTypeDto;
import com.nightswatch.api.dto.violation.FrequencyLevelTypeDto;
import com.nightswatch.api.dto.violation.ViolationStatusTypeDto;
import com.nightswatch.dal.entity.violation.DangerLevelType;
import com.nightswatch.dal.entity.violation.FrequencyLevelType;
import com.nightswatch.dal.entity.violation.ViolationStatusType;
import org.junit.Assert;
import org.junit.Test;

public class EnumDtoConversionUtilsTest {

    @Test
    public void testConvert() throws Exception {
        Assert.assertEquals(DangerLevelType.HIGH, EnumDtoConversionUtils.convert(DangerLevelTypeDto.HIGH));
    }

    @Test
    public void testConvert1() throws Exception {
        Assert.assertEquals(DangerLevelTypeDto.HIGH, EnumDtoConversionUtils.convert(DangerLevelType.HIGH));
    }

    @Test
    public void testConvert2() throws Exception {
        Assert.assertEquals(FrequencyLevelType.HIGH, EnumDtoConversionUtils.convert(FrequencyLevelTypeDto.HIGH));
    }

    @Test
    public void testConvert3() throws Exception {
        Assert.assertEquals(FrequencyLevelTypeDto.HIGH, EnumDtoConversionUtils.convert(FrequencyLevelType.HIGH));
    }

    @Test
    public void testConvert4() throws Exception {
        Assert.assertEquals(ViolationStatusType.FIXED, EnumDtoConversionUtils.convert(ViolationStatusTypeDto.FIXED));
    }

    @Test
    public void testConvert5() throws Exception {
        Assert.assertEquals(ViolationStatusTypeDto.FIXED, EnumDtoConversionUtils.convert(ViolationStatusType.FIXED));
    }

}