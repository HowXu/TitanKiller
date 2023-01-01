package com.takaranoao.titankiller.Core;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;

public class TKContainer extends DummyModContainer {
    public TKContainer() {
      super(new ModMetadata());
      ModMetadata meta = getMetadata();
      meta.modId = "titankillercore";
      meta.name = "TitanKillerCore";
      meta.version = "2.0.0";
      meta.authorList = Arrays.asList(new String[] { "Takaranoao" });
      meta.description = "A Core Mod for TitanKiller";
    }


 public boolean registerBus(EventBus bus, LoadController controller) {
    bus.register(this);
   return true;
    }
}
