package io.jonuuh.leapmodetracker.event;

import io.jonuuh.leapmodetracker.LeapMode;
import io.jonuuh.leapmodetracker.LeapModeTracker;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SneakListener
{
    private final Minecraft mc;
    /** Leap mode before starting to sneak */
    private LeapMode initialMode;
    private boolean sneaking;

    public SneakListener()
    {
        this.mc = LeapModeTracker.mc;
        this.sneaking = false;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.START || this.mc.theWorld == null || mc.thePlayer == null)
        {
            return;
        }

        if (mc.thePlayer.isSneaking() && !sneaking)
        {
            sneaking = true;
            initialMode = LeapModeTracker.getCurrentMode();
            LeapModeTracker.setCurrentMode(LeapMode.ARCED);
        }
        else if (!mc.thePlayer.isSneaking() && sneaking)
        {
            sneaking = false;
            LeapModeTracker.setCurrentMode(initialMode);
        }
    }
}
