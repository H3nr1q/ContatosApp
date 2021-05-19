package com.chs.contatos.app;

import androidx.multidex.MultiDexApplication;

public class ContatosApp extends MultiDexApplication {
    private static ContatosApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static ContatosApp getInstance() {
        return instance;
    }

}
