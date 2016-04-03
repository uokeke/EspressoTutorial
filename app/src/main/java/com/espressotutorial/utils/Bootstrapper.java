package com.espressotutorial.utils;

import com.espressotutorial.note.NoteRepository;

/**
 * Created by Uche on 2016-04-04.
 */
public class Bootstrapper {
    public static void registerAllDependencies(Dependency dependency) {
        dependency.register(NoteRepository.class, new NoteRepository());
    }

    public static void unregisterAllDependencies(Dependency dependency) {
        dependency.clear();
    }
}
