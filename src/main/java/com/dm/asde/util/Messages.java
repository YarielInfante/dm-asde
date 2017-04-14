package com.dm.asde.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * Created by yarielinfante on 1/5/17.
 */
@Component
public class Messages {


    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor sourceAccessor;


    @PostConstruct
    private void init(){
        sourceAccessor = new MessageSourceAccessor(messageSource, new Locale("es"));
    }

    public String get(String code) {
        return sourceAccessor.getMessage(code);
    }

}
