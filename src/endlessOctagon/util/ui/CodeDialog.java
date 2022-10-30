package endlessOctagon.util.ui;

import mindustry.ui.dialogs.*;
import mindustry.Vars;

import arc.scene.ui.*;
import arc.Core;
import arc.input.*;

/** Only for testing and debug*/
public final class CodeDialog extends BaseDialog {
	
	private TextArea codeArea;
	private Label outputLabel = new Label("");
	
	public CodeDialog(){
		super("Code Dialog");
		
		Runnable runAction = ()->{
					String out = Vars.mods.getScripts().runConsole(codeArea.getText());
					outputLabel.setText(out);
				};
		this.cont.table(t -> {
				codeArea = t.add(new TextArea("")).size(540, 450).get();
				t.row();
				t.add(outputLabel);
				t.row();
				t.button("Run!", runAction).size(100,50);
				t.button("Clear!", ()->{
						codeArea.setText("");
				});
		});
		
		keyDown(key -> {
				if(key == KeyCode.n){
					Core.app.post(this::hide);
				}
		});
		
		this.addCloseButton();
	}
}
