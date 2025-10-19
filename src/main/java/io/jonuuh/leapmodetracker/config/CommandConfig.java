package io.jonuuh.leapmodetracker.config;

import io.jonuuh.leapmodetracker.util.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandConfig extends CommandBase
{
    private static Settings settingsInstance;

    public CommandConfig()
    {
        settingsInstance = Settings.getInstance();
    }

    @Override
    public String getCommandName()
    {
        return "leapmodetracker";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (!(sender.getCommandSenderEntity() instanceof EntityPlayerSP))
        {
            return;
        }

        if (args.length != 2 || (!args[0].equals("hudXPos") && !args[0].equals("hudYPos") && !args[0].equals("hudColor")))
        {
            return;
        }

        String msg;

        switch (args[0])
        {
            case "hudXPos":
                try
                {
                    settingsInstance.setHudXPosOffset(parseInt(args[1]));
                    msg = "Hud X Position offset set to: " + settingsInstance.getHudXPosOffset();
                }
                catch (NumberInvalidException e)
                {
                    msg = "Error parsing '" + args[1] + "' as an integer";
                }
                break;
            case "hudYPos":
                try
                {
                    settingsInstance.setHudYPosOffset(parseInt(args[1]));
                    msg = "Hud Y Position offset set to: " + settingsInstance.getHudYPosOffset();
                }
                catch (NumberInvalidException e)
                {
                    msg = "Error parsing '" + args[1] + "' as an integer";
                }
                break;
            case "hudColor":
                if (args[1].matches("^#?[0-9a-fA-F]{1,8}$"))
                {
                    settingsInstance.setHudColor(new Color(args[1]));
                    msg = "Hud Color set to: " + settingsInstance.getHudColor().toString();
                }
                else
                {
                    msg = "Error parsing '" + args[1] + "' as a color";
                }
                break;
            default:
                msg = "Unknown command";
        }

        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
        settingsInstance.save();
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, "hudXPos", "hudYPos", "hudColor");
        }
        return null;
    }
}