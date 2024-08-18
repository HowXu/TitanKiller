package com.takaranoao.titankiller.Core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public class TKCore implements IFMLLoadingPlugin {
    //Coremod基本
    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "com.takaranoao.titankiller.Core.TKTransformer" };
        //com.takaranoao.titankiller.Core.MCTransformer 降智操作
    }

    @Override
    public String getModContainerClass() {
        return "com.takaranoao.titankiller.Core.TKContainer";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
