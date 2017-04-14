package com.dm.asde.util;

import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Created by yinfante on 10/24/16.
 */
@Component
public class SessionManager {

    public String createSessionId(){

        StringBuilder randomNum = new StringBuilder();

        Calendar calendar = Calendar.getInstance();
        randomNum.append(calendar.get(Calendar.YEAR));
        randomNum.append(String.format("%02d", calendar.get(Calendar.MONTH) + 1));
        randomNum.append(String.format("%02d", calendar.get(Calendar.DATE)));
        randomNum.append(String.format("%02d", calendar.get(Calendar.HOUR)));
        randomNum.append(String.format("%02d", calendar.get(Calendar.MINUTE)));
        randomNum.append(String.format("%02d", calendar.get(Calendar.SECOND)));
        randomNum.append(String.format("%03d", calendar.get(Calendar.MILLISECOND)));
        randomNum.append((int) (9 * Math.random()));
        randomNum.append((int) (9 * Math.random()));
        randomNum.append((int) (9 * Math.random()));
        randomNum.append((int) (9 * Math.random()));
        randomNum.append((int) (9 * Math.random()));
        randomNum.append((int) (9 * Math.random()));
        randomNum.append((int) (9 * Math.random()));

        return randomNum.toString();
    }


}
