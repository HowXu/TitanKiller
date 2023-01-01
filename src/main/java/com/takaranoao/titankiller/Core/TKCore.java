package com.takaranoao.titankiller.Core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public class TKCore implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "com.takaranoao.titankiller.Core.TKTransformer" };
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
