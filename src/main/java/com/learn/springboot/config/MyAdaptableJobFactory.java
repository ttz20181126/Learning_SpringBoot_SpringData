package com.learn.springboot.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;


@Component("myAdaptableJobFactory")
public class MyAdaptableJobFactory extends AdaptableJobFactory {

    //可以将一个对象添加到SpringIoc容器中，并且完成该对象注入。
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    /**
     * 该方法需要将其是实例化的任务对象手动的添加到SpringIoc容器中并且完成对象的注入
     * @param bundle
     * @return
     * @throws Exception
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //return super.createJobInstance(bundle);
        Object obj = super.createJobInstance(bundle);
        //将obj对象添加到spring ioc容器中，并完成注入====将反射得到的QuartzDemo注入
        this.autowireCapableBeanFactory.autowireBean(obj);
        return obj;
    }
}