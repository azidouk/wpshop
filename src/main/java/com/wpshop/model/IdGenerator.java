package com.wpshop.model;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IdGenerator {

    private static AtomicLong nextId = new AtomicLong(1);

    public static long getNextId() {
        return nextId.getAndIncrement();
    }

    public static void reset() {
        nextId = new AtomicLong(1);
    }
}