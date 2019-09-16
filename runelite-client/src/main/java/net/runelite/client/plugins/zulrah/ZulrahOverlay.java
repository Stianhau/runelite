package net.runelite.client.plugins.zulrah;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

public class ZulrahOverlay extends Overlay {

    private Client client;
    private ZulrahPlugin plugin;
    private boolean swapped;
    @Getter
    private ArrayList<Tile> list;

    @Setter
    private  int currentPhase;
    @Setter
    private int currentRotation;
    @Inject
    private ZulrahOverlay(Client client, ZulrahPlugin plugin)
    {
        super(plugin);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.MED);
        this.client = client;
        this.plugin = plugin;
        getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Zulrah Overlay"));
        swapped = false;
        list = new ArrayList<>();
    }
    @Override
    public Dimension render(Graphics2D graphics) {
    NPC zulrah = plugin.getZulrah();
    if(zulrah == null){
        return null;
    }
    Tile tile;
    if(zulrah.getAnimation()==5073 && !swapped){
        swapped = true;
        ++currentPhase;
        if(currentRotation!=0){
        }
        else if(currentPhase==1){

            rotation(zulrah,currentPhase);
        }else if(currentPhase ==4 && currentRotation == 0){

            rotation(zulrah,currentPhase);
        }
    } else if (zulrah.getAnimation()!=5073){
        swapped=false;
    }

    if(list.isEmpty()){
        return null;
    }
    if(currentRotation==4){
        tile = list.get((currentPhase+1)%12);
    }else {
        tile = list.get((currentPhase+1)%11);
    }
    renderTile(graphics,tile.getPoint(),tile.getColor());
    return null;
    }


    public void rotation(NPC zulrah,int i){
        if(i == 1){
            if(zulrah.getId() == NpcID.ZULRAH){
                currentRotation = 3;
                three();
            }else if (zulrah.getId()==NpcID.ZULRAH_2044){
                currentRotation = 4;
                four();
            }
        }else if(i==4){
            if(zulrah.getId()==NpcID.ZULRAH_2044){
                currentRotation = 2;
                two();
            }else {
                currentRotation = 1;
            }
        }
    }

    private void renderTile(Graphics2D graphics, LocalPoint point, Color color)
    {
        if(point == null){
            return;
        }
        Polygon poly = Perspective.getCanvasTilePoly(client,point);
        OverlayUtil.renderPolygon(graphics, poly, color);
    }

    public void one(){
        list.clear();
        list.add(new Tile(new LocalPoint(7232,8000),Color.GREEN)); //0
        list.add(new Tile(new LocalPoint(7232,8000),Color.RED)); //1
        list.add(new Tile(new LocalPoint(6208,7232),Color.BLUE)); //2
        list.add(new Tile(new LocalPoint(6208,7232),Color.GREEN)); //3
        list.add(new Tile(new LocalPoint(6208,7232),Color.RED)); //4
        list.add(new Tile(new LocalPoint(6208,7232),Color.BLUE)); //5
        list.add(new Tile(new LocalPoint(7232,7232),Color.GREEN)); //6
        list.add(new Tile(new LocalPoint(6208,7232),Color.BLUE)); //7
        list.add(new Tile(new LocalPoint(6208,8000),Color.YELLOW)); //8
        list.add(new Tile(new LocalPoint(6208,8000),Color.RED)); //9
        list.add(new Tile(new LocalPoint(7232,8000),Color.GREEN)); //10
    }
    public void two(){
        list.clear();
        list.add(new Tile(new LocalPoint(7232,8000),Color.GREEN)); //0
        list.add(new Tile(new LocalPoint(7232,8000),Color.RED)); //1
        list.add(new Tile(new LocalPoint(6208,7232),Color.BLUE)); //2
        list.add(new Tile(new LocalPoint(6208,7232),Color.GREEN)); //3
        list.add(new Tile(new LocalPoint(7232,7232),Color.BLUE)); //4
        list.add(new Tile(new LocalPoint(7232,8000),Color.RED)); //5
        list.add(new Tile(new LocalPoint(7232,7232),Color.GREEN)); //6
        list.add(new Tile(new LocalPoint(6208,7232),Color.BLUE)); //7
        list.add(new Tile(new LocalPoint(6080,7872),Color.YELLOW)); //8
        list.add(new Tile(new LocalPoint(6080,7872),Color.RED)); //9
        list.add(new Tile(new LocalPoint(7232,8000),Color.GREEN)); //10
    }
    public void three(){
        list.clear();
        list.add(new Tile(new LocalPoint(7232,8000),Color.GREEN)); //0
        list.add(new Tile(new LocalPoint(7232,8000),Color.GREEN)); //1
        list.add(new Tile(new LocalPoint(6208,8000),Color.RED)); //2
        list.add(new Tile(new LocalPoint(6208,7232),Color.BLUE)); //3
        list.add(new Tile(new LocalPoint(6208,7232),Color.GREEN)); //4
        list.add(new Tile(new LocalPoint(7232,7232),Color.BLUE)); //5
        list.add(new Tile(new LocalPoint(6208,7232),Color.GREEN)); //6
        list.add(new Tile(new LocalPoint(6208,7232),Color.GREEN)); //7
        list.add(new Tile(new LocalPoint(7232,8000),Color.BLUE)); //8
        list.add(new Tile(new LocalPoint(7232,8000),Color.PINK)); //9
        list.add(new Tile(new LocalPoint(7232,8000),Color.BLUE)); //10
    }
    public void four(){
        list.clear();
        list.add(new Tile(new LocalPoint(7232,8000),Color.GREEN)); //0
        list.add(new Tile(new LocalPoint(7232,8000),Color.BLUE)); //1
        list.add(new Tile(new LocalPoint(6208,7232),Color.GREEN)); //2
        list.add(new Tile(new LocalPoint(6208,7232),Color.BLUE)); //3
        list.add(new Tile(new LocalPoint(7232,7232),Color.RED)); //4
        list.add(new Tile(new LocalPoint(7232,7232),Color.GREEN)); //5
        list.add(new Tile(new LocalPoint(6208,7232),Color.GREEN)); //6
        list.add(new Tile(new LocalPoint(6208,7232),Color.BLUE)); //7
        list.add(new Tile(new LocalPoint(7232,8000),Color.GREEN)); //8
        list.add(new Tile(new LocalPoint(7232,8000),Color.BLUE)); //9
        list.add(new Tile(new LocalPoint(7232,8000),Color.PINK)); //10
        list.add(new Tile(new LocalPoint(7232,8000),Color.BLUE)); //11
    }
}
