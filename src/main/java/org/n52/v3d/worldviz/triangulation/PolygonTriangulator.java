/**
 * Copyright (C) 2015-2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *  - Apache License, version 2.0
 *  - Apache Software License, version 1.0
 *  - GNU Lesser General Public License, version 3
 *  - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *  - Common Development and Distribution License (CDDL), version 1.0.
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * License version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.v3d.worldviz.triangulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.n52.v3d.worldviz.extensions.VgLinearRing;
import org.n52.v3d.worldviz.extensions.VgMultiPolygon;
import org.n52.v3d.worldviz.extensions.VgPolygon;

import org.n52.v3d.triturus.gisimplm.GmPoint;
import org.n52.v3d.triturus.gisimplm.GmSimpleTINGeometry;
import org.n52.v3d.triturus.vgis.VgIndexedTIN;
import org.n52.v3d.triturus.vgis.VgPoint;
import org.poly2tri.Poly2Tri;
import org.poly2tri.geometry.polygon.Polygon;
import org.poly2tri.geometry.polygon.PolygonPoint;
import org.poly2tri.triangulation.TriangulationPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;

/**
 * <b>Note that the triangulation is only possible for flat geometries that have
 * their spatial extent in the XY-plane. Any z-coordinate value if ignored in
 * the external library poly2tri.</b>
 * 
 * @author Christian Danowski
 *
 */
public class PolygonTriangulator {

	private static String srs;

	/**
	 * <b>Note that the triangulation is only possible for flat geometries that
	 * have their spatial extent in the XY-plane. Any z-coordinate value if
	 * ignored in the external library poly2tri.</b>
	 * 
	 * @param vgMultiPolygons
	 * @return
	 */
	public static List<VgIndexedTIN> triangulateMultiPolygons(
			Collection<VgMultiPolygon> vgMultiPolygons) {

		List<VgIndexedTIN> vgTins = new ArrayList<VgIndexedTIN>();

		for (VgMultiPolygon vgMultiPolygon : vgMultiPolygons) {
			vgTins.addAll(triangulateMultiPolygon(vgMultiPolygon));
		}

		return vgTins;

	}

	/**
	 * <b>Note that the triangulation is only possible for flat geometries that
	 * have their spatial extent in the XY-plane. Any z-coordinate value if
	 * ignored in the external library poly2tri.</b>
	 * 
	 * @param vgMultiPolygon
	 * @return
	 */
	public static List<VgIndexedTIN> triangulateMultiPolygon(
			VgMultiPolygon vgMultiPolygon) {

		int numberOfGeometries = vgMultiPolygon.getNumberOfGeometries();

		List<VgIndexedTIN> vgTINs = new ArrayList<VgIndexedTIN>(
				numberOfGeometries);

		for (int i = 0; i < numberOfGeometries; i++) {

			VgPolygon vgPolygon = (VgPolygon) vgMultiPolygon.getGeometry(i);

			vgTINs.add(triangulatePolygon(vgPolygon));
		}

		return vgTINs;
	}

	/**
	 * <b>Note that the triangulation is only possible for flat geometries that
	 * have their spatial extent in the XY-plane. Any z-coordinate value if
	 * ignored in the external library poly2tri.</b>
	 * 
	 * @param vgPolygon
	 * @return
	 */
	public static VgIndexedTIN triangulatePolygon(VgPolygon vgPolygon) {

		srs = vgPolygon.getSRS();

		// transform VgPolygonvertices to
		List<PolygonPoint> polygonBorderPoints = transformIntoPolygonPoints(vgPolygon
				.getOuterBoundary());

		List<PolygonPoint> polygonHolePoints = new ArrayList<PolygonPoint>();

		Polygon p2tPolygon = new Polygon(polygonBorderPoints);

		// process holes
		int numberOfHoles = vgPolygon.getNumberOfHoles();
		for (int i = 0; i < numberOfHoles; i++) {

			VgLinearRing triturusHole = vgPolygon.getHole(i);

			Polygon hole = new Polygon(transformIntoPolygonPoints(triturusHole));
			p2tPolygon.addHole(hole);

			polygonHolePoints.addAll(transformIntoPolygonPoints(triturusHole));
		}

		Poly2Tri.triangulate(p2tPolygon);

		List<DelaunayTriangle> triangles = p2tPolygon.getTriangles();

		// List<PolygonPoint> allPoints = new ArrayList<PolygonPoint>(
		// polygonBorderPoints.size() + polygonHolePoints.size());
		// allPoints.addAll(polygonBorderPoints);
		// allPoints.addAll(polygonHolePoints);

		return createTriturusTIN(triangles);

	}

	/**
	 * Takes a polygon and a list of additional points that have to be inside of
	 * the polygon! These inner points will be used as additional nodes for the
	 * constrained triangulation! The outer boundary of the polygon will still
	 * be used for guaranteed triangle edges that will not be crossed by any
	 * edges constructed through the inner points.
	 * 
	 * <b>Note that the triangulation is only possible for flat geometries that
	 * have their spatial extent in the XY-plane. Any z-coordinate value if
	 * ignored in the external library poly2tri.</b>
	 * 
	 * @param vgPolygon
	 * @param additionalInnerPoints
	 * @return
	 */
	public static VgIndexedTIN triangulatePolygon(VgPolygon vgPolygon,
			List<VgPoint> additionalInnerPoints) {

		srs = vgPolygon.getSRS();

		// transform VgPolygonvertices to
		List<PolygonPoint> polygonBorderPoints = transformIntoPolygonPoints(vgPolygon
				.getOuterBoundary());

		List<PolygonPoint> polygonHolePoints = new ArrayList<PolygonPoint>();

		Polygon p2tPolygon = new Polygon(polygonBorderPoints);

		// process holes
		int numberOfHoles = vgPolygon.getNumberOfHoles();
		for (int i = 0; i < numberOfHoles; i++) {

			VgLinearRing vgPolygonHole = vgPolygon.getHole(i);
			Polygon triangulationHole = new Polygon(
					transformIntoPolygonPoints(vgPolygonHole));

			p2tPolygon.addHole(triangulationHole);

			// add polygonHolePoints to corresponding list
			polygonHolePoints.addAll(transformIntoPolygonPoints(vgPolygonHole));
		}

		// add Steiner points
		List<TriangulationPoint> polygonInnerPoints = transformIntoTriangulationPoints(additionalInnerPoints);

		// // a list is needed, in which ALL points that are used to triangulate
		// // the polygon are inside. That means all borderPoints and inner
		// Steiner
		// // points
		// List<TriangulationPoint> allPoints = new
		// ArrayList<TriangulationPoint>();
		// allPoints.addAll(polygonBorderPoints);
		// allPoints.addAll(polygonHolePoints);
		// allPoints.addAll(polygonInnerPoints);

		p2tPolygon.addSteinerPoints(polygonInnerPoints);

		Poly2Tri.triangulate(p2tPolygon);

		List<DelaunayTriangle> triangles = p2tPolygon.getTriangles();

		return createTriturusTIN(triangles);
	}

	private static List<TriangulationPoint> transformIntoTriangulationPoints(
			List<VgPoint> triturusPoints) {

		List<TriangulationPoint> points = new ArrayList<TriangulationPoint>(
				triturusPoints.size());

		for (VgPoint vgPoint : triturusPoints) {

			points.add(convert2PolygonPoint(vgPoint));

		}

		return points;

	}

	private static List<PolygonPoint> transformIntoPolygonPoints(
			VgLinearRing vgLinearRing) {

		int numberOfVertices = vgLinearRing.getNumberOfVertices();

		List<PolygonPoint> polygonPoints = new ArrayList<PolygonPoint>(
				numberOfVertices);

		// TODO check!!!
		// as the triangulation library expects that no polygon point may be
		// insertetd twice
		// we check, if the first and last point of the polygon is the same; if
		// so, then only insert it once!
		for (int i = 0; i < numberOfVertices; i++) {

			// if clause only for the last point
			if (i == (numberOfVertices - 1)) {
				VgPoint firstPolygonPoint = vgLinearRing.getVertex(0);
				VgPoint lastPolygonPoint = vgLinearRing.getVertex(i);
				if (firstPolygonPoint.equals(lastPolygonPoint)) {
					break;
				}
			}

			VgPoint nextVertex = vgLinearRing.getVertex(i);
			polygonPoints.add(convert2PolygonPoint(nextVertex));

		}

		return polygonPoints;
	}

	/**
	 * converts a Triturus point into a PolygonPoint (point class of the
	 * triangulation-lib) that is needed for the triangulation.
	 * 
	 * @param triturusPoint
	 * @return
	 */
	private static PolygonPoint convert2PolygonPoint(VgPoint triturusPoint) {
		PolygonPoint nextPoint = new PolygonPoint(triturusPoint.getX(),
				triturusPoint.getY(), triturusPoint.getZ());
		return nextPoint;
	}

	private static VgIndexedTIN createTriturusTIN(
			List<DelaunayTriangle> triangles) {

		Map<TriangulationPoint, Integer> pointIndicesMap = createPointIndicesMap(triangles);

		// GmSimpleTINGeometry implements GvIndexedTIN
		GmSimpleTINGeometry triturusTIN = new GmSimpleTINGeometry(
				pointIndicesMap.size(), triangles.size());

		triturusTIN.setSRS(srs);

		// Map<TriangulationPoint, Integer> pointIndicesMap =
		// createPointIndicesMap(allPoints);

		setPointsForTriturusTIN((GmSimpleTINGeometry) triturusTIN,
				pointIndicesMap);

		int triangleIndex = 0;

		for (DelaunayTriangle delaunayTriangle : triangles) {

			TriangulationPoint[] trianglePoints = delaunayTriangle.points;

			// each Point has to be in the pointsIndicesMap
			// thus we can get the corresponding index from the map

			triturusTIN.setTriangle(triangleIndex,
					pointIndicesMap.get(trianglePoints[0]),
					pointIndicesMap.get(trianglePoints[1]),
					pointIndicesMap.get(trianglePoints[2]));

			triangleIndex++;

		}

		return triturusTIN;
	}

	private static Map<TriangulationPoint, Integer> createPointIndicesMap(
			List<DelaunayTriangle> triangles) {

		Map<TriangulationPoint, Integer> pointIndicesMap = new HashMap<TriangulationPoint, Integer>();
		int pointIndex = 0; // first indexPosition that will be incremented for
		// each new point

		for (DelaunayTriangle delaunayTriangle : triangles) {
			TriangulationPoint[] triangulationPoints = delaunayTriangle.points;

			for (TriangulationPoint triangulationPoint : triangulationPoints) {

				// only if the actual point is NOT already as a key in the map
				if (!pointIndicesMap.containsKey(triangulationPoint)) {
					pointIndicesMap.put(triangulationPoint, pointIndex);
					pointIndex++;
				}
			}

		}

		return pointIndicesMap;

	}

	// private static Map<TriangulationPoint, Integer> createPointIndicesMap(
	// List<TriangulationPoint> allPoints) {
	//
	// Map<TriangulationPoint, Integer> pointIndicesMap = new
	// HashMap<TriangulationPoint, Integer>();
	// int pointIndex = 0; // first indexPosition that will be incremented for
	// // each new point
	//
	// for (TriangulationPoint polygonPoint : allPoints) {
	//
	// pointIndicesMap.put(polygonPoint, pointIndex);
	// pointIndex++;
	//
	// }
	//
	// return pointIndicesMap;
	// }

	private static void setPointsForTriturusTIN(
			GmSimpleTINGeometry triturusTIN,
			Map<TriangulationPoint, Integer> pointIndicesMap) {

		Set<Entry<TriangulationPoint, Integer>> entrySet = pointIndicesMap
				.entrySet();

		// each ENtry maps an index to a PolygonPoint
		for (Entry<TriangulationPoint, Integer> entry : entrySet) {

			TriangulationPoint polygonPoint = entry.getKey();
			Integer index = entry.getValue();

			VgPoint triturusPoint = new GmPoint(polygonPoint.getX(),
					polygonPoint.getY(), polygonPoint.getZ());

			triturusPoint.setSRS(srs);

			triturusTIN.setPoint(index, triturusPoint);

		}

	}

}
