package io.jonuuh.leapmodetracker.event;

import io.jonuuh.leapmodetracker.LeapMode;
import io.jonuuh.leapmodetracker.LeapModeTracker;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

public class ModeChangeListener
{
    private final Pattern arrowModePattern;
    private final Pattern arcedModePattern;

    public ModeChangeListener()
    {
        this.arrowModePattern = Pattern.compile("^Your primary Leap skill switched to Arrow mode.$");
        this.arcedModePattern = Pattern.compile("^Your primary Leap skill switched to Arced mode.$");
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event)
    {
        String msg = event.message.getUnformattedText();

        if (arrowModePattern.matcher(msg).matches())
        {
            LeapModeTracker.setCurrentMode(LeapMode.ARROW);
        }
        else if (arcedModePattern.matcher(msg).matches())
        {
            LeapModeTracker.setCurrentMode(LeapMode.ARCED);
        }
    }
}
