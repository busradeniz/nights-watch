package com.nightswatch.web.service;

import com.nightswatch.api.dto.violation.DangerLevelTypeDto;
import com.nightswatch.api.dto.violation.FrequencyLevelTypeDto;
import com.nightswatch.api.dto.violation.ViolationStatusTypeDto;
import com.nightswatch.dal.entity.violation.DangerLevelType;
import com.nightswatch.dal.entity.violation.FrequencyLevelType;
import com.nightswatch.dal.entity.violation.ViolationStatusType;
import com.nightswatch.web.service.impl.EnumConversionServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class EnumConversionServiceTest {

    private final EnumConversionService enumConversionService = new EnumConversionServiceImpl();

    @Test
    public void testConvert() throws Exception {
        Assert.assertEquals(DangerLevelType.HIGH, enumConversionService.convert(DangerLevelTypeDto.HIGH));
    }

    @Test
    public void testConvert1() throws Exception {
        Assert.assertEquals(DangerLevelTypeDto.HIGH, enumConversionService.convert(DangerLevelType.HIGH));
    }

    @Test
    public void testConvert2() throws Exception {
        Assert.assertEquals(FrequencyLevelType.HIGH, enumConversionService.convert(FrequencyLevelTypeDto.HIGH));
    }

    @Test
    public void testConvert3() throws Exception {
        Assert.assertEquals(FrequencyLevelTypeDto.HIGH, enumConversionService.convert(FrequencyLevelType.HIGH));
    }

    @Test
    public void testConvert4() throws Exception {
        Assert.assertEquals(ViolationStatusType.FIXED, enumConversionService.convert(ViolationStatusTypeDto.FIXED));
    }

    @Test
    public void testConvert5() throws Exception {
        Assert.assertEquals(ViolationStatusTypeDto.FIXED, enumConversionService.convert(ViolationStatusType.FIXED));
    }
}