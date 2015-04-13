package com.fmf.mypoem.util;

import android.test.AndroidTestCase;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by fmf on 15/4/12.
 */
public class DateUtilTest extends TestCase {

    @Test
    public void testFormatDateToMyPome() throws Exception {
        final int year = 2015;
        final int month = 4;
        final int day = 12;

        Assert.assertEquals("2015年4月12日", DateUtil.formatDateToMyPome(year, month, day));
    }
}
