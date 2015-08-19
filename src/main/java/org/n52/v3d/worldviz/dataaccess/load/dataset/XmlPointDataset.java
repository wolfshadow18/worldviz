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
package org.n52.v3d.worldviz.dataaccess.load.dataset;

import noNamespace.DatasetDocument;
import noNamespace.DatasetDocument.Dataset.TableStructure.Property;

import org.n52.v3d.triturus.gisimplm.GmAttrFeature;
import org.n52.v3d.triturus.gisimplm.GmPoint;
import org.n52.v3d.triturus.vgis.VgAttrFeature;
import org.n52.v3d.triturus.vgis.VgPoint;

/**
 * Specialization of XmlDataset that is used for point-datasets (datasets that
 * contain information that refers to a single point as geometry). The
 * coordinates of the point are provided by the ENE-dataset.
 * 
 * @author Christian Danowski
 * 
 */
public class XmlPointDataset extends AbstractXmlDataset {

	public XmlPointDataset(DatasetDocument doc) throws Exception {
		super(doc);
	}

	@Override
	protected void setGeometry(VgAttrFeature newFeature, Property property,
			String entryValue) {

		if (logger.isDebugEnabled())
			logger.debug(
					"Creating a point-geometry from the feature property '{}' with value '{}'.",
					property.getTitle().getStringValue(), entryValue);

		String crs = property.getGeoReference().getCRS().toString();

		VgPoint newPoint = createPoint(entryValue, crs);

		((GmAttrFeature) newFeature).setGeometry(newPoint);

	}

	/**
	 * Parses the coordinates from the comma-separated-string and creates a
	 * Triturus-'VgPoint'.
	 * 
	 * @param coordCommaSeparatedList
	 *            the string that looks like 'longitude,latitude'
	 * @param crs
	 *            the coordinate reference system from the ENE-dataset
	 * @return a VgPoint, that represents the feature's point-geometry
	 */
	private VgPoint createPoint(String coordCommaSeparatedList, String crs) {

		// coordCommaSeparatedList looks like:
		// "longitude,latitude"
		// optional: "longitude,latitude,altitude"
		// so: x,y,z

		VgPoint point = new GmPoint(coordCommaSeparatedList);
		point.setSRS(crs);

		return point;
	}

}
