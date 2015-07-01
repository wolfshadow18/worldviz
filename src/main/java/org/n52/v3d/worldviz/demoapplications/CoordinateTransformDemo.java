package org.n52.v3d.worldviz.demoapplications;

import org.n52.v3d.triturus.gisimplm.GmPoint;
import org.n52.v3d.triturus.t3dutil.T3dVector;
import org.n52.v3d.triturus.vgis.VgPoint;
import org.n52.v3d.worldviz.projections.AxisSwitchTransform;
import org.n52.v3d.worldviz.projections.ChainedTransform;
import org.n52.v3d.worldviz.projections.NormTransform;
import org.n52.v3d.worldviz.projections.Wgs84ToSphereCoordsTransform;

//TODO: This class could be migrated to the 52N Triturus core package in the future.

/**
 * Simple demonstrator illustrating how to use <tt>CoordinateTransform</tt> 
 * objects inside this framework.
 *    
 * @author Benno Schmidt
 */
public class CoordinateTransformDemo 
{
	public static void main(String[] args) 
	{
		CoordinateTransformDemo app = new CoordinateTransformDemo();

		VgPoint[] geoPos = app.generateSomeLocations();
		
		System.out.println("Transform lat/lon to sphere coordinates:");
		app.runDemo1(geoPos);  
		
		System.out.println("\nSimply switch coordinate-axes:");
		app.runDemo2(geoPos); 
		
		System.out.println("\nNormalize to unit bounding-box:");
		app.runDemo3(geoPos); 
		
		System.out.println("\nNormalize to unit bounding-box and switch coordinates:");
		app.runDemo4(geoPos);
	}

	public VgPoint[] generateSomeLocations() 
	{
		VgPoint[] geoPos = new VgPoint[3];
		geoPos[0] = new GmPoint(7.267, 51.447, 0.); /// lat/lon coordinates of Bochum,
		geoPos[1] = new GmPoint(13.083, 80.267, 0.); /// Chennay,
		geoPos[2] = new GmPoint(52. /*52N!*/, 7.633, 0.); /// and Muenster
		for (VgPoint g : geoPos) {
			g.setSRS(VgPoint.SRSLatLonWgs84);
		}
		return geoPos;
	}
	
	public void runDemo1(VgPoint[] geoPos) 
	{	
		// Current implementation:
		VgPoint[] visPos = new VgPoint[geoPos.length];
		for (int i = 0; i < geoPos.length; i++) {
			visPos[i] = Wgs84ToSphereCoordsTransform.wgs84ToSphere(geoPos[i], 6370.);
			System.out.println(geoPos[i] + " -> " + visPos[i]);
		}
		// Better (?) alternative / my recommendation:
		/*
		T3dVector[] visPos = new T3dVector[geoPos.length];
		GeoCoordTransform t = new Wgs84ToSphereCoordsTransform(6370.);
		for (int i = 0; i < geoPos.length; i++) {
			visPos[i] = t.transform(geoPos[i]);
			System.out.println(geoPos[i] + " -> " + visPos[i]);
		}
		*/
	}

	public void runDemo2(VgPoint[] geoPos) 
	{
		// Recommendation to map x -> X, y -> -Z, z -> Y:
		T3dVector[] visPos = new T3dVector[geoPos.length];
		AxisSwitchTransform t = new AxisSwitchTransform();
		for (int i = 0; i < geoPos.length; i++) {
			visPos[i] = t.transform(geoPos[i]);
			System.out.println(geoPos[i] + " -> " + visPos[i]);
		}
	}	
	
	public void runDemo3(VgPoint[] geoPos) 
	{
		// Recommendation to perform normalization:
		T3dVector[] visPos = new T3dVector[geoPos.length];	
		NormTransform t1 = new NormTransform(geoPos);
		for (int i = 0; i < geoPos.length; i++) {
			visPos[i] = t1.transform(geoPos[i]);
			System.out.println(geoPos[i] + " -> " + visPos[i]);
		}
	}	
	
	public void runDemo4(VgPoint[] geoPos) 
	{
		// Recommendation to implement transformation chains:
		T3dVector[] visPos = new T3dVector[geoPos.length];

		NormTransform t1 = new NormTransform(geoPos);
		AxisSwitchTransform t2 = new AxisSwitchTransform();

		ChainedTransform t = new ChainedTransform(t1, t2);
		
		for (int i = 0; i < geoPos.length; i++) {
			visPos[i] = t.transform(geoPos[i]);
			System.out.println(geoPos[i] + " -> " + visPos[i]);
		}
	}	
}