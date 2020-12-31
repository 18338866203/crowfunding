package com.it.crowd.test;

import com.it.crowd.util.CrowdUtil;
import org.junit.Test;

/**
 * @author wyj
 * @description
 * @create 2020-12-08
 */
public class StringTest {
    @Test
    public void testMD5(){
        String source = "123123";
        String encoded = CrowdUtil.MD5(source);
        System.out.println(encoded);
    }
}
