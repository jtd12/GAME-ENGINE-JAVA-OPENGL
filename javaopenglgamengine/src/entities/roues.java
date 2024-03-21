package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.texturedModel;
import renderEngine.DisplayManager;
import terrains.terrain;

public class roues extends entity {

	public roues(texturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		
		// TODO Auto-generated constructor stub
	}
	
	public void setParent(player p,terrain t,float pos)
	{
	
	this.getPosition().x=(float) ((p.getPosition().x)+(pos*Math.cos((-Math.toRadians(p.getRotY())))));
	this.getPosition().y=(p.getPosition().y-1);
	this.getPosition().z=(float) ((p.getPosition().z)+(pos*Math.sin((-Math.toRadians(p.getRotY())))));
	

	 this.setRotY(p.getRotY());
	
	
	//this.setIncrementeRotZ(0.5f);
	
	/*float terrainHeights=t.getHeightTerrain(this.getPosition().x, this.getPosition().z);
	
	if(this.getPosition().y<terrainHeights-4)
	{
		this.getPosition().y=terrainHeights-4;
	}
	*/
  }
	public void turnRotZ()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			this.increaseRotation(0,0,0.95f);
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.increaseRotation(0,0,-0.95f);
			
		}
	}
		
	public void turnRotY()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.increaseRotation(0,12.5f,0);
			
		
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			
			this.increaseRotation(0,-12.5f,0);
		}
		else
		{
			
			
		}
	}
	
	
}