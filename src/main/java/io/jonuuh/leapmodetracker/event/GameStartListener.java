package io.jonuuh.leapmodetracker.event;

import io.jonuuh.leapmodetracker.LeapMode;
import io.jonuuh.leapmodetracker.LeapModeTracker;
import io.jonuuh.leapmodetracker.util.ScoreboardUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GameStartListener
{
    private final Minecraft mc;
    private int clientTicks = 0;
    private int timer = 0;

    public GameStartListener()
    {
        this.mc = LeapModeTracker.mc;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.START || this.mc.theWorld == null)
        {
            return;
        }

        // Only bother running everything once a second rather than 20 per sec
        if (++this.clientTicks % 20 == 0)
        {
            this.clientTicks = 0;
        }
        else
        {
            return;
        }

        if (timer > 0)
        {
            timer--;
        }

        Scoreboard sb = this.mc.theWorld.getScoreboard();

        if (sb != null && ScoreboardUtils.getScoreboardHeader(sb).equals("MEGA WALLS"))
        {
            for (String cleanScore : ScoreboardUtils.getScoreboardScores(sb, true))
            {
                if (cleanScore.contains("Gates Open:") && timer == 0)
                {
                    LeapModeTracker.setCurrentMode(LeapMode.ARROW);
                    timer = 15;
                }
            }
        }
    }
}
