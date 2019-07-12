package com.fjc.demo.upload;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadApplicationTests {

    @Test
    public void contextLoads() {

        String[] strs = {"1","2","3","4"};
        for(int i=0; i<strs.length; i++){
            strs[i] = "22" + strs[i];
        }
        System.out.println(Arrays.toString(strs));
    }

}
