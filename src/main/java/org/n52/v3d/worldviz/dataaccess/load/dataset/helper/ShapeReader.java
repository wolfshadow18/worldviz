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
 * icense version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.v3d.worldviz.dataaccess.load.dataset.helper;

import java.io.IOException;
import java.util.List;

import org.n52.v3d.worldviz.helper.RelativePaths;

import org.opengis.feature.simple.SimpleFeature;

/**
 * Used to extract the geometry of all MultiPolygon-features from a shape file.
 * Note: As of now (28.05.2014), only the geometry of the shape's features is
 * relevant to the ENE-project. Thus the thematic attributes provided by the
 * SimpleFeatures of the shape-file are not extracted. <br />
 * <br />
 * 
 * @author Christian Danowski
 * 
 */
public interface ShapeReader {

	/**
	 * Gets all simple features (only JTS (Java Topology Suite) MultiPolygons)
	 * from the default shape file that is referenced by the attribute
	 * {@link RelativePaths#COUNTRIES_SHAPE_ESRI_SHAPE}
	 * 
	 * @return a list of simple features
	 * @throws IOException 
	 */
	public List<SimpleFeature> getSimpleFeatureCollection() throws IOException;
	
}
