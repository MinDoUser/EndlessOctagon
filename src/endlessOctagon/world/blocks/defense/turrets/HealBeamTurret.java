package endlessOctagon.world.blocks.defense.turrets;

import mindustry.world.blocks.defense.turrets.BaseTurret;
import mindustry.world.meta.*;
import mindustry.entities.*;
import mindustry.world.meta.BlockFlag;
import mindustry.graphics.Pal;
import mindustry.graphics.Layer;
import mindustry.gen.*;
import mindustry.type.Liquid;
import mindustry.content.Fx;
import mindustry.Vars;

import arc.graphics.g2d.*;
import arc.util.io.*;
import arc.util.Time;
import arc.struct.EnumSet;
import arc.graphics.Color;
import arc.math.Angles;
import arc.Core;

public class HealBeamTurret extends  BaseTurret {
	public TextureRegion base, laser, laserEnd;
	
	public float heal = 80f;
	public float healEffectReload = 12;
	
	public float shootLength = 5f;
	public float recentDamageMultiplier = 0.75f;
	
	public Color laserColor = Pal.heal, healColor = Pal.heal;
	
	public HealBeamTurret(String name){
		super(name);
		
		attacks = false; //no.
		
		priority = TargetPriority.base;
        group = BlockGroup.projectors; //TODO: Use none instead?
        flags = EnumSet.of(BlockFlag.repair, BlockFlag.turret);
	}
	
	@Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{baseRegion, region};
    }
	
	@Override
	public void load(){
		//Reason: The "reinforced" set of bases for turrets doesn't has a block for 1x1 size turrets.
		TextureRegion texture;
		if(size > 1){
			texture = Core.atlas.find("reinforced-block-"+size);
		}else{
			texture = Core.atlas.find("block-1");
		}
		base = Core.atlas.find(name+"-base", texture); // Other base?
		laser = Core.atlas.find(name+"-laser");
		laserEnd = Core.atlas.find(name+"-laser-end");
	}
	
	@Override
    public void init(){
        super.init();

        updateClipRadius(range + Vars.tilesize);
    }
	
	
	
	public class HealBeamTurretBuild extends BaseTurretBuild {
		
		public Bilding target;
		
		//public float lastX, lastY;
		public float coolantMult = 1f;
		public final float retargetTime = 10; 
		
		public float effectTimer = 0f, retargetTimer = 0f;
		
		@Override
		public void update(){
			//Retarget
			if((retargetTimer += Time.delta) > retargetTime){
				//Only if required.
				if(target == null || target.dead() || !target.isDamaged()){
					target = Vars.indexer.findTile(
						this.team, this.x, this.y, range()+1, b -> b.damaged() && !b.isHealSuppressed() 
					);
				}
				retargetTimer = 0f;
			}
			
			//consume coolant
            if(target != null && coolant != null){
                float maxUsed = coolant.amount;

                Liquid liquid = liquids.current();

                float used = Math.min(Math.min(liquids.get(liquid), maxUsed * Time.delta), Math.max(0, (1f / coolantMultiplier) / liquid.heatCapacity));

                liquids.remove(liquid, used);

                if(Mathf.chance(0.06 * used)){
                    coolEffect.at(x + Mathf.range(size * tilesize / 2f), y + Mathf.range(size * tilesize / 2f));
                }

                coolantMultiplier = 1f + (used * liquid.heatCapacity * coolantMultiplier);
            }
            
            if(target != null && target.within(this, range + target.hitSize/2f)
            	&& target.team() == team && efficiency > 0.02f){
            
            float dest = angleTo(target);
            rotation = Angles.moveToward(rotation, dest, rotateSpeed * edelta);
            
			healTarget: {
				if(target != null && target.isDamaged){
					if(target.isHealSuppressed()){
						target = null;
						break healTarget;
					}
					if((effectTimer += Time.delta) > healEffectReload){
						Fx.healBlockFull.at(target.x, target.y, 0f, healColor, target.block);
						effectTimer = 0f;
					}
					
					if(!target.isHealSuppressed()){
						float baseAmount;
						baseAmount = (repairSpeed * Time.delta + Time.delta * u.maxHealth() / 100f) * coolantMutiplier;
						target.heal((target.wasRecentlyDamaged() ? recentDamageMultiplier : 1f) * baseAmount);
					}
				}
			}
		}
		
		@Override
		public void draw(){
			Draw.rect(baseRegion, x, y);
            		Drawf.shadow(region, x - (size / 2f), y - (size / 2f), rotation - 90);
            		Draw.rect(region, x, y, rotation - 90);
            		if(target != null){
            			Draw.z(Layer.bullet);
                		float ang = angleTo(target.x, target.y);
            	
            			Drawf.laser(laser, laserEnd, laserEnd,
                		x + Angles.trnsx(ang, shootLength), y + Angles.trnsy(ang, shootLength),
                		target.x, target.y, efficiency * laserWidth);
            		}
		}
	}
		
	@Override
        public void write(Writes write){
            super.write(write);

            write.f(rotation);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            rotation = read.f();
        }
		
}
	
}
