package com.takaranoao.titankiller.Event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class TKPlayerEvent extends PlayerEvent {
    //重写一个玩家事件
    public TKPlayerEvent(EntityPlayer player){
        super(player);
    }
}
