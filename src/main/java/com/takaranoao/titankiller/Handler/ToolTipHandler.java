package com.takaranoao.titankiller.Handler;

import com.takaranoao.titankiller.Loader.ItemLoader;
import com.takaranoao.titankiller.tools.RainbowText;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

@SideOnly(Side.CLIENT)
public class ToolTipHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void Tooltip(ItemTooltipEvent event){
        if(event.itemStack.getItem() == ItemLoader.titankiller){
            for(int i = 0;i < event.toolTip.size();i++){

                String thetooltip = event.toolTip.get(i);
                String AttackDamage = StatCollector.translateToLocal("attribute.name.generic.attackDamage");

                if(thetooltip.contains(AttackDamage) || thetooltip.contains(StatCollector.translateToLocal(AttackDamage))){
                    StringBuilder Builder = new StringBuilder();
                    Builder.append(EnumChatFormatting.BLUE);
                    Builder.append("+");
                    Builder.append(RainbowText.makeRainbow("无限"));
                    Builder.append(" ");
                    Builder.append(EnumChatFormatting.BLUE);
                    Builder.append(AttackDamage);
                    event.toolTip.set(i,Builder.toString());
                }
            }

        }
  }
}
