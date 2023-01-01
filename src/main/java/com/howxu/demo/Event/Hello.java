package com.howxu.demo.Event;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class Hello {


    //击杀之后嘲讽
    public static void ChatPrint(EntityPlayer player,String Message){
        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append(player.getCommandSenderName());
        builder.append("> ");
        builder.append(Message);
        ChatComponentText text = new ChatComponentText(builder.toString());
        for(EntityPlayer p2 : (List<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList){
            p2.addChatComponentMessage(text);
        }
    }
    
}
