package TestEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.camera;
import entities.entity;
import entities.light;
import entities.player;
import entities.roues;
import models.rawModel;
import models.texturedModel;
import postprocessing.Fbo;
import postprocessing.PostProcessing;
import renderEngine.DisplayManager;
import renderEngine.loader;
import renderEngine.masterRenderer;
import renderEngine.objloader;
import shaders.staticShader;
import terrains.terrain;
import textures.modelTexture;
import textures.terrainTexture;
import textures.terrainTexturePack;
import water.WaterShader;
import water.WaterTile;
import water.waterFrameBufferObject;
import water.waterRenderer;




public class gameLoop {


	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Display.setResizable(true);
		loader loader = new loader();
		//terrain terrains=new terrain(0,0,loader,new modelTexture(loader.loadTexture("grass")));
		//terrain terrains2=new terrain(1,0,loader,new modelTexture(loader.loadTexture("grass")));
		//renderer renderer = new renderer();
		//staticShader shader=new staticShader();
		//renderer renderer = new renderer(shader);
		rawModel model=objloader.loadObjModel("componentUnity/tree_01", loader);
		rawModel model2=objloader.loadObjModel("componentUnity/tree_02", loader);
		rawModel model3=objloader.loadObjModel("componentUnity/tree_03", loader);
		rawModel model4=objloader.loadObjModel("componentUnity/tree_04", loader);
		rawModel model5=objloader.loadObjModel("componentUnity/tree_05", loader);
		//rawModel model6=objloader.loadObjModel("componentUnity/tree_06", loader);
		texturedModel texture=new texturedModel(model,new modelTexture(loader.loadTextureTGA("textures_objectUnity/LowPoly/Sycamore_MiddleEastern_A")));
		texturedModel texture2=new texturedModel(model2,new modelTexture(loader.loadTextureTGA("textures_objectUnity/Beech_American_A")));
		texturedModel texture3=new texturedModel(model3,new modelTexture(loader.loadTextureTGA("textures_objectUnity/Beech_European_A")));
		texturedModel texture4=new texturedModel(model4,new modelTexture(loader.loadTextureTGA("textures_objectUnity/Billboard_Beech_American")));
		texturedModel texture5=new texturedModel(model5,new modelTexture(loader.loadTextureTGA("textures_objectUnity/WoodPainted_A")));
		texture.getModel_texture().setHasTransparency(true);
		texture2.getModel_texture().setHasTransparency(true);
		texture3.getModel_texture().setHasTransparency(true);
		texture4.getModel_texture().setHasTransparency(true);
		
		texture.getModel_texture().setFakeLight(true);
		texture2.getModel_texture().setFakeLight(true);
		texture3.getModel_texture().setFakeLight(true);
		texture4.getModel_texture().setFakeLight(true);
		
		terrainTexture backGroundTexture=new terrainTexture(loader.loadTextureJPG("grass"));
		
		terrainTexture rTexture=new terrainTexture(loader.loadTextureJPG("dirt"));
		terrainTexture gTexture=new terrainTexture(loader.loadTextureJPG("grass2"));
		terrainTexture bTexture=new terrainTexture(loader.loadTextureJPG("path"));
		
		terrainTexturePack texturePack=new terrainTexturePack(backGroundTexture,rTexture,gTexture,bTexture);
		terrainTexture blendMap=new terrainTexture(loader.loadTexture("blendMap"));
		
		
		Fbo fbo2=new Fbo(Display.getWidth(),Display.getHeight(),Fbo.DEPTH_RENDER_BUFFER);
		PostProcessing.init(loader);
		/*
		float[] vertices = {
				-0.5f, 0.5f, 0f,//v0
				-0.5f, -0.5f, 0f,//v1
				0.5f, -0.5f, 0f,//v2
				0.5f, 0.5f, 0f,//v3
		};
		
		int[] indices = {
				0,1,3,//top left triangle (v0, v1, v3)
				3,1,2//bottom right triangle (v3, v1, v2)
		};
		
		float[] textureCoords= {
				
				0,0,
				0,1,
				1,1,
				1,0
				
		};
		*/
		//rawModel model = objloader.loadObjModel("model", loader);
		//modelTexture texture=new modelTexture(loader.loadTexture("model"));
		//texturedModel textureModel=new texturedModel(model,new modelTexture(loader.loadTexture("model")));
		//modelTexture texture=textureModel.getModel_texture();
		//texture.setShineDamper(10);
		//texture.setRelectivity(1);
		//entity entities=new entity(textureModel,new Vector3f(0,0,-50),0,0,0,1);
		light light_=new light(new Vector3f(500,2,20),new Vector3f(3.1f,1.5f,1));
		List<entity> entities=new ArrayList<entity>();
		
		List<player> p=new ArrayList<player>();
		List<roues> roues_=new ArrayList<roues>();
		List<roues> roues2_=new ArrayList<roues>();
		List<terrain> terrains=new ArrayList<terrain>();
		
		terrains.add(new terrain(0,0,loader,texturePack,blendMap,"heightmap2"));
		terrains.add(new terrain(-1,0,loader,texturePack,blendMap,"heightmap2"));
		terrains.add(new terrain(0,-1,loader,texturePack,blendMap,"heightmap2"));
		terrains.add(new terrain(-1,-1,loader,texturePack,blendMap,"heightmap2"));
		//terrain terrains2=new terrain(1,0,loader,texturePack,blendMap,"heightmap2");
		//terrain terrains3=new terrain(0,1,loader,texturePack,blendMap,"heightmap2");
		//terrain terrains4=new terrain(1,1,loader,texturePack,blendMap,"heightmap2");
		
		Random rand=new Random();
		
		for(int i=0;i<60;i++)
		{
			float x=rand.nextFloat()*800;
			float z=rand.nextFloat()*600;
			float y=terrains.get(0).getHeightTerrain(x,z);
			
			entities.add(new entity(texture,new Vector3f(x,y,z),0,0,0,0.9f));
		
		}
		for(int i=0;i<40;i++)
		{
			float x=rand.nextFloat()*-2200;
			float z=rand.nextFloat()*-2200;
			float y=terrains.get(3).getHeightTerrain(x,z);
			
			entities.add(new entity(texture,new Vector3f(x,y,z),0,0,0,1.8f));
		
		}
		
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*-2000;
			float z=rand.nextFloat()*-2000;
			float y=terrains.get(3).getHeightTerrain(x,z);
			
			entities.add(new entity(texture2,new Vector3f(x,y,z),0,0,0,3.8f));
		
		}
		
		
		for(int i=0;i<40;i++)
		{
			float x=rand.nextFloat()*2200;
			float z=rand.nextFloat()*-2200;
			float y=terrains.get(2).getHeightTerrain(x,z);
			
			entities.add(new entity(texture,new Vector3f(x,y,z),0,0,0,1.8f));
		
		}
		
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*2000;
			float z=rand.nextFloat()*-2000;
			float y=terrains.get(2).getHeightTerrain(x,z);
			
			entities.add(new entity(texture2,new Vector3f(x,y,z),0,0,0,4.8f));
		
		}
		
		for(int i=0;i<40;i++)
		{
			float x=rand.nextFloat()*-2200;
			float z=rand.nextFloat()*2200;
			float y=terrains.get(1).getHeightTerrain(x,z);
			
			entities.add(new entity(texture,new Vector3f(x,y,z),0,0,0,1.8f));
		
		}
		
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*-2000;
			float z=rand.nextFloat()*2000;
			float y=terrains.get(1).getHeightTerrain(x,z);
			
			entities.add(new entity(texture3,new Vector3f(x,y,z),0,0,0,2.8f));
		
		}
		
		
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*2000;
			float z=rand.nextFloat()*2000 ;
			float y=terrains.get(0).getHeightTerrain(x,z);
			
			entities.add(new entity(texture2,new Vector3f(x,y,z),0,0,0,0.9f));
		
		}
		
	/*
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*800;
			float z=rand.nextFloat()*600+3600;
			float y=terrains.get(2).getHeightTerrain(x,z);
			
		entities.add(new entity(texture2,new Vector3f(x,y,z),0,0,0,1));
		
		}
		*/
		
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*2000;
			float z=rand.nextFloat()*2000;
			float y=terrains.get(0).getHeightTerrain(x,z);
			
			entities.add(new entity(texture3,new Vector3f(x,y,z),0,0,0,1));
		
		}
		
		for(int i=0;i<25;i++)
		{
			float x=rand.nextFloat()*2000;
			float z=rand.nextFloat()*2000;
			float y=terrains.get(0).getHeightTerrain(x,z);
			
			entities.add(new entity(texture4,new Vector3f(x,y,z),0,0,0,2));
		
		}
		
		for(int i=0;i<10;i++)
		{
			float x=rand.nextFloat()*2000;
			float z=rand.nextFloat()*2000;
			float y=terrains.get(0).getHeightTerrain(x,z);
			
			entities.add(new entity(texture5,new Vector3f(x,y,z),0,0,0,10));
			
		}
	
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*-2000;
			float z=rand.nextFloat()*-2000;
			float y=terrains.get(3).getHeightTerrain(x,z);
			
			entities.add(new entity(texture5,new Vector3f(x,y,z),0,0,0,20));
			
		}
	
	
		
		rawModel Model=objloader.loadObjModel("vehicule", loader);
		texturedModel textureModel=new texturedModel(Model,new modelTexture(loader.loadTextureJPG("map_vehicule")));
		
		rawModel ModelRoue=objloader.loadObjModel("roues", loader);
		texturedModel textureModelRoue=new texturedModel(ModelRoue,new modelTexture(loader.loadTextureJPG("map_vehicule")));
		
		rawModel ModelRoue2=objloader.loadObjModel("roues2", loader);
		texturedModel textureModelRoue2=new texturedModel(ModelRoue2,new modelTexture(loader.loadTextureJPG("map_vehicule")));
		
		p.add(new player(textureModel,new Vector3f(200,25,360),0,0,0,2));
		roues_.add(new roues(textureModelRoue,new Vector3f(380,25,360),0,0,0,2));
		roues_.add(new roues(textureModelRoue,new Vector3f(380,25,360),0,0,0,2));
		
		roues2_.add(new roues(textureModelRoue2,new Vector3f(380,25,360),0,0,0,2));
		roues2_.add(new roues(textureModelRoue2,new Vector3f(380,25,360),0,0,0,2));
		
		for (player player_: p)
		{
			
		camera cam=new camera(player_);
		
		Vector3f playerPos=player_.getPosition();
		waterFrameBufferObject fbo=new waterFrameBufferObject();
		masterRenderer master=new masterRenderer(loader,cam);
		WaterShader waterShader=new WaterShader();
		
		waterRenderer waterRender=new waterRenderer(loader,waterShader,master.getProjection(),fbo);
		WaterTile water=new WaterTile(800,800,-90);
		List<WaterTile> waters=new ArrayList<WaterTile>();
		waters.add(water);
		
		
		while (!Display.isCloseRequested()) {
			// game logic
			//entities.increaseRotation(0,1,0);
			cam.move();
			
			master.renderShadowMap(entities,p,light_);
			
			if(playerPos.getX()>0 && playerPos.getX()<3600 && playerPos.getZ()>0 && playerPos.getZ()<3600)
			{
				 terrains.get(0).setTerrain1(true);
				 terrains.get(1).setTerrain2(false);
				 terrains.get(2).setTerrain3(false);
				 terrains.get(3).setTerrain4(false);
				 System.out.println("terrain1!");
			}
			if(playerPos.getX()>-3600 && playerPos.getX()<0 && playerPos.getZ()>0 && playerPos.getZ()<3600)
			{
				 terrains.get(0).setTerrain1(false);
				 terrains.get(1).setTerrain2(true);
				 terrains.get(2).setTerrain3(false);
				 terrains.get(3).setTerrain4(false);
				 System.out.println("terrain2!");
			}
			if(playerPos.getX()>0 && playerPos.getX()<3600 && playerPos.getZ()>-3600 && playerPos.getZ()<0)
			{
				 terrains.get(0).setTerrain1(false);
				 terrains.get(1).setTerrain2(false);
				 terrains.get(2).setTerrain3(true);
				 terrains.get(3).setTerrain4(false);
				 System.out.println("terrain3!");
			}
			if(playerPos.getX()>-3600 && playerPos.getX()<0 && playerPos.getZ()>-3600 && playerPos.getZ()<0)
			{
				 terrains.get(0).setTerrain1(false);
				 terrains.get(1).setTerrain2(false);
				 terrains.get(2).setTerrain3(false);
				 terrains.get(3).setTerrain4(true);
				 System.out.println("terrain4!");
			}
			
			if(terrains.get(0).getTerrain1())
			{
			roues_.get(0).setParent(player_,terrains.get(0),9);
			roues_.get(1).setParent(player_,terrains.get(0),-6.5f);
			roues2_.get(0).setParent(player_,terrains.get(0),9);
			roues2_.get(1).setParent(player_,terrains.get(0),-6.5f);

			player_.move(terrains.get(0),roues_,roues2_);
			}
			if(terrains.get(1).getTerrain2())
			{
			roues_.get(0).setParent(player_,terrains.get(1),9);
			roues_.get(1).setParent(player_,terrains.get(0),-6.5f);
			roues2_.get(0).setParent(player_,terrains.get(0),9);
			roues2_.get(1).setParent(player_,terrains.get(0),-6.5f);

			player_.move(terrains.get(1),roues_,roues2_);
			}
			if(terrains.get(2).getTerrain3())
			{
			roues_.get(0).setParent(player_,terrains.get(2),9);
			roues_.get(1).setParent(player_,terrains.get(0),-6.5f);
			roues2_.get(0).setParent(player_,terrains.get(0),9);
			roues2_.get(1).setParent(player_,terrains.get(0),-6.5f);

			player_.move(terrains.get(2),roues_,roues2_);
			}
			if(terrains.get(3).getTerrain4())
			{
			roues_.get(0).setParent(player_,terrains.get(0),9);
			roues_.get(1).setParent(player_,terrains.get(3),-6.5f);
			roues2_.get(0).setParent(player_,terrains.get(0),9);
			roues2_.get(1).setParent(player_,terrains.get(0),-6.5f);
		
			player_.move(terrains.get(3),roues_,roues2_);
			}
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			//player_.move(terrains2);
			
			float distance=3*(cam.getPosition().y-water.getHeight());
			cam.getPosition().y-=distance;
			cam.invertPitch();
			
			//fbo.bindReflectionFrameBuffer();
			master.renderScene(roues_,roues2_,p,entities, terrains, light_, cam,new Vector4f(0,1,0,-waters.get(0).getHeight()+1));
			
			cam.getPosition().y+=distance;
			cam.invertPitch();
		
			fbo.bindReflectionFrameBuffer();
		    master.renderScene(roues_,roues2_,p,entities, terrains, light_, cam,new Vector4f(0,1,0,-water.getHeight()));
		
			fbo.bindRefractionFrameBuffer();
			master.renderScene(roues_,roues2_,p,entities, terrains, light_, cam,new Vector4f(0,-1,0,waters.get(0).getHeight()+0.2f));
			
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);

			
			fbo2.bindFrameBuffer();
			master.renderScene(roues_,roues2_,p,entities, terrains, light_, cam,new Vector4f(0,-1,0,10000));
			
			waterRender.render(waters, cam);
			fbo2.unbindFrameBuffer();
			
			PostProcessing.doPostProcessing(fbo2.getColourTexture());
			//master.render(light_, cam);
			
			//entities.increaseRotation(0,1,0);
			//renderer.prepare();
			//shader.start();
			//shader.loadLight(light_);
			//shader.loadViewMatrix(cam);
			//renderer.render(entities,shader);
			//shader.stop();
			//master.render(light_,cam);
			DisplayManager.updateDisplay();
		}

		//shader.cleanUp();
		PostProcessing.cleanUp();
		fbo.cleanUp();
		fbo2.cleanUp();
		waterShader.cleanUp();
		master.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	     }
		}
	  
	}

