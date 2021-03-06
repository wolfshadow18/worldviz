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
package org.n52.v3d.worldviz.featurenet;

import org.n52.v3d.triturus.vgis.VgFeature;

/**
 * A VgRelation holds a relation between two geo-objects ("features" in OGC-jargon).
 * 
 * The kind of relations between geo-objects will depend on your application scenario. Prominent examples are:
 * Flows of trade between regions, flight connections between airports, or the relation "same official language",
 * which would connect Australia and the United States.
 * 
 * <table border='1'>
 *   <tr><td>edge-values:</td><td>not present</td><td>nominally scaled value</td><td>quantitative values</td></tr>
 *   <tr><td>undirected</td><td><tt>WvizConnection</tt></td><td>not yet implemented</td><td><tt>WvizConnection</tt></td></tr>
 *   <tr><td>directed</td><td><tt>WvizArc</tt></td><td><tt>WvizArc</tt></td><td><tt>WvizFlow</tt></td></tr>
 * </table>
 * 
 * @author Benno Schmidt
 */
public interface VgRelation 
{
	/**
	 * gets the first of the two related geo-objects. 
	 * For directed relations (<tt>isDirected() == true</tt>), this object gives the from-node inside the 
	 * corresponding feature graph.
	 *  
	 * @return Geo-object
	 */
	 public VgFeature getFrom();

	/**
	 * gets the second of the two related geo-objects.
	 * For directed relations (<tt>isDirected() == true</tt>), this object gives the to-node inside the 
	 * corresponding feature graph.
	 *  
	 * @return Geo-object
	 */
	 public VgFeature getTo();
	 
	 /**
	  * gets the information whether the relation is directed.
	  * @return <i>true</i> if the relation directs from the first to the second geo-object njode, else <i>false</i>
	  */
	 public boolean isDirected();
	 
	 /**
	  * TODO e.g., a weight-value
	  * TODO: I don't really like this method signature and name yet...
	  * @return
	  */
	 public Object getValue();
}
