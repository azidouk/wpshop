package com.wpshop.utils;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import java.util.Locale;

@Component
public class MessageCatalogue {

    private MessageSourceAccessor accessor;

    public MessageCatalogue(){
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:messages");
        accessor = new MessageSourceAccessor(source, Locale.ENGLISH);
    }

    public String get(String code) {
        return accessor.getMessage(code);
    }

    public String get(String code, Object[] args) {
        return accessor.getMessage(code, args);
    }
}