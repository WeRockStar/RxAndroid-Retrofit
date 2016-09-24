package com.werockstar.rxretrofit.util;


import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

public class RxSchedulerRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.getInstance().reset();

                RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
                    @Override
                    public Scheduler getMainThreadScheduler() {
                        return Schedulers.immediate();
                    }
                });
                callResetViaReflectionIn(RxJavaPlugins.getInstance());
                RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
                    @Override
                    public Scheduler getIOScheduler() {
                        return Schedulers.immediate();
                    }

                    @Override
                    public Scheduler getNewThreadScheduler() {
                        return Schedulers.immediate();
                    }
                });

                base.evaluate();
                RxAndroidPlugins.getInstance().reset();
                callResetViaReflectionIn(RxJavaPlugins.getInstance());
            }
        };
    }

    // Thanks hitherejoe repository on Github Android-Boilerplate
    private void callResetViaReflectionIn(RxJavaPlugins rxJavaPlugins)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = rxJavaPlugins.getClass().getDeclaredMethod("reset");
        method.setAccessible(true);
        method.invoke(rxJavaPlugins);
    }
}
