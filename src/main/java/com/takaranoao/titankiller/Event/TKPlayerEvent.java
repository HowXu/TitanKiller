package com.takaranoao.titankiller.Event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class TKPlayerEvent extends PlayerEvent {
    public TKPlayerEvent(EntityPlayer player){
        super(player);
    }
}
