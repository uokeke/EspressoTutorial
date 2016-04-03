package com.espressotutorial;

import android.app.Application;

import com.espressotutorial.utils.Bootstrapper;
import com.espressotutorial.utils.Dependency;

/**
 * Created by Uche on 2016-04-04.
 */
public class NoteApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Bootstrapper.registerAllDependencies(Dependency.getInstance());
    }
}
