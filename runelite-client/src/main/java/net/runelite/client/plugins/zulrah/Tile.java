package net.runelite.client.plugins.zulrah;

import lombok.Getter;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

import javax.inject.Inject;
import java.awt.*;

public class Tile {
    @Getter
    private Color color;
    @Getter
    private LocalPoint point;

    @Inject
    public Tile(LocalPoint point, Color color){
        this.color=color;
        this.point = point;
    }
}
