package com.cvs.crm.util;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Data
@Slf4j
public class GenerateRandom
    {
        public Integer randomNumberString() {
            // Generate random integers in range 0 to 999
            int randNum = ThreadLocalRandom.current().nextInt();
            return randNum;
        }
    }
