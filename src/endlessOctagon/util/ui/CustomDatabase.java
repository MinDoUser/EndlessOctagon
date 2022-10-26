package endlessOctagon.util.ui;

import arc.*;
import arc.graphics.*;
import arc.input.*;
import arc.math.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.ui.dialogs.*;
import mindustry.content.*;
import mindustry.maps.planet.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class CustomDatabase extends DatabaseDialog {
  private TextField searchField;
  private Table allTable = new Table();
  
  public CustomDatabase(){
    super();
    
    cont.clear();
    shown(this::rebuildDialog);
    onResize(this::rebuildDialog);

    allTable.margin(20).marginTop(0f);

    cont.table(s -> {
        s.image(Icon.zoom).padRight(8);
        searchField = s.field(null, text -> rebuildDialog()).growX().get();
        searchField.setMessageText("@players.search");
    }).fillX().padBottom(4).row();

        cont.pane(allTable).scrollX(false);
  }
  //@Override
  public void rebuildDialog(){
        allTable.clear();
        var text = searchField.getText();

        Seq<Content>[] allContent = Vars.content.getContentMap();

        for(int j = 0; j < allContent.length; j++){
            ContentType type = ContentType.all[j];

            Seq<Content> array = allContent[j]
                .select(c -> c instanceof UnlockableContent u &&
                    (!u.isHidden() || u.techNode != null || (u instanceof Planet p && p.accessible)) &&
                    (text.isEmpty() || u.localizedName.toLowerCase().contains(text.toLowerCase())));
            if(array.size == 0) continue;
          
          /*array.sort((p1,p2)->{
            
          });*/

            allTable.add("@content." + type.name() + ".name").growX().left().color(Color.white);
            allTable.row();
            allTable.add(new WarningBar()).growX().pad(5).padLeft(0).padRight(0).height(40).color(Color.white);
            allTable.row();
            allTable.table(list -> {
                list.left();

                int cols = (int)Mathf.clamp((Core.graphics.getWidth() - Scl.scl(30)) / Scl.scl(32 + 12), 1, 22);
                int count = 0;

                for(int i = 0; i < array.size; i++){
                    UnlockableContent unlock = (UnlockableContent)array.get(i);

                    Image image = unlocked(unlock) || Vars.state.isMenu() || Core.settings.getBool("showlockedblocks")? new Image(unlock.uiIcon).setScaling(Scaling.fit) : new Image(Icon.lock, Pal.gray);
                    if(unlock.uiIcon.found()){
                      image = Core.bundle.find("eo-esag");
                    }
                    //banned cross
                    if(state.isGame() && (unlock instanceof UnitType u && u.isBanned() || unlock instanceof Block b && state.rules.bannedBlocks.contains(b))){
                        list.stack(image, new Image(Icon.cancel){{
                            setColor(Color.scarlet);
                            touchable = Touchable.disabled;
                        }}).size(8 * 4).pad(3);
                    }else{
                        list.add(image).size(8 * 4).pad(3);
                    }

                    ClickListener listener = new ClickListener();
                    image.addListener(listener);
                    if(!mobile && (unlocked(unlock) || Vars.state.isMenu())){
                        image.addListener(new HandCursorListener());
                        image.update(() -> image.color.lerp(!listener.isOver() ? Color.lightGray : Color.white, Mathf.clamp(0.4f * Time.delta)));
                    }

                    if(unlocked(unlock) || Vars.state.isMenu()){
                        image.clicked(() -> {
                            if(Core.input.keyDown(KeyCode.shiftLeft) && Fonts.getUnicode(unlock.name) != 0){
                                Core.app.setClipboardText((char)Fonts.getUnicode(unlock.name) + "");
                                ui.showInfoFade("@copied");
                            }else{
                                ui.content.show(unlock);
                            }
                        }); 
                        image.addListener(new Tooltip(t -> t.background(Tex.button).add(unlock.localizedName + (settings.getBool("console") ? "\n[gray]" + unlock.name : ""))));
                    }

                    if((++count) % cols == 0){
                        list.row();
                    }
                }
            }).growX().left().padBottom(10);
            allTable.row();
        }

        if(allTable.getChildren().isEmpty()){
            allTable.add("@none.found");
        }
  }
                        
  //@Override                      
  public boolean unlocked(UnlockableContent content){
        return (!Vars.state.isCampaign() && !Vars.state.isMenu()) || content.unlocked() || Core.settings.getBool("showlockedblocks");
    }
}
