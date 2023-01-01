package com.takaranoao.titankiller.Event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class CancelAblePlayerEvent1 extends PlayerEvent {
    public CancelAblePlayerEvent1(EntityPlayer player) {
        super(player);
    }
}
