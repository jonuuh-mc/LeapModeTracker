package io.jonuuh.leapmodetracker.config;

import io.jonuuh.leapmodetracker.util.Color;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class Settings
{
    private static Settings instance;
    private final Configuration configuration;
    private int hudXPosOffset;
    private int hudYPosOffset;
    private Color hudColor;

    public static void createInstance(File configFile)
    {
        if (instance != null)
        {
            throw new IllegalStateException("Settings instance has already been created");
        }

        instance = new Settings(configFile);
    }

    public static Settings getInstance()
    {
        if (instance == null)
        {
            throw new NullPointerException("Settings instance has not been created");
        }

        return instance;
    }

    public int getHudXPosOffset()
    {
        return hudXPosOffset;
    }

    public void setHudXPosOffset(int hudXPosOffset)
    {
        this.hudXPosOffset = hudXPosOffset;
    }

    public int getHudYPosOffset()
    {
        return hudYPosOffset;
    }

    public void setHudYPosOffset(int hudYPosOffset)
    {
        this.hudYPosOffset = hudYPosOffset;
    }

    public Color getHudColor()
    {
        return hudColor;
    }

    public void setHudColor(Color hudColor)
    {
        this.hudColor = hudColor;
    }

    // Load settings (read data from Configuration, write it into each setting)
    private Settings(File configFile)
    {
        this.configuration = new Configuration(configFile);

        hudXPosOffset = getIntProperty("hudXPosOffset", 0).getInt();
        hudYPosOffset = getIntProperty("hudYPosOffset", 0).getInt();
        hudColor = new Color(getStringProperty("hudColor", "#AA00AA").getString());
    }

    // Save settings (read data from each setting, write it into Configuration)
    public void save()
    {
        getIntProperty("hudXPosOffset", 0).setValue(hudXPosOffset);
        getIntProperty("hudYPosOffset", 0).setValue(hudYPosOffset);
        getStringProperty("hudColor", "#AA00AA").setValue(hudColor.toString());

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    private Property getIntProperty(String settingKey, int defaultVal)
    {
        return configuration.get("all", settingKey, defaultVal);
    }

    private Property getStringProperty(String settingKey, String defaultVal)
    {
        return configuration.get("all", settingKey, defaultVal);
    }
}
