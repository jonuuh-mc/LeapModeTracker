package io.jonuuh.leapmodetracker;

import io.jonuuh.leapmodetracker.config.CommandConfig;
import io.jonuuh.leapmodetracker.config.Settings;
import io.jonuuh.leapmodetracker.event.GameStartListener;
import io.jonuuh.leapmodetracker.event.ModeChangeListener;
import io.jonuuh.leapmodetracker.event.render.ModeHUDRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = "leapmodetracker",
        version = "1.0.1",
        acceptedMinecraftVersions = "[1.8.9]"
)
public class LeapModeTracker
{
    public static Minecraft mc;
    private static LeapMode currentMode;

    @Mod.EventHandler
    public void FMLPreInit(FMLPreInitializationEvent event)
    {
        mc = Minecraft.getMinecraft();
        currentMode = LeapMode.ARROW;

        Settings.createInstance(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void FMLInit(FMLInitializationEvent event)
    {
        ClientCommandHandler.instance.registerCommand(new CommandConfig());

        MinecraftForge.EVENT_BUS.register(new GameStartListener());
        MinecraftForge.EVENT_BUS.register(new ModeChangeListener());
        MinecraftForge.EVENT_BUS.register(new ModeHUDRenderer());
    }

    public static LeapMode getCurrentMode()
    {
        return currentMode;
    }

    public static void setCurrentMode(LeapMode currentMode)
    {
        LeapModeTracker.currentMode = currentMode;
//        if (mc.thePlayer != null)
//        {
//            mc.thePlayer.addChatMessage(new ChatComponentText("DEBUG: Leap Mode now tracked as: " + currentMode.toString()));
//        }
    }

    public void switchLeapMode()
    {
        if (currentMode.equals(LeapMode.ARROW))
        {
            setCurrentMode(LeapMode.ARCED);
        }
        else if (currentMode.equals(LeapMode.ARCED))
        {
            setCurrentMode(LeapMode.ARROW);
        }
        else
        {
            setCurrentMode(LeapMode.ARROW);
        }
    }
}
