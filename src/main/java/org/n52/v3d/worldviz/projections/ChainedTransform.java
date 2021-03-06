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
package org.n52.v3d.worldviz.projections;

import org.n52.v3d.triturus.t3dutil.T3dVector;
import org.n52.v3d.triturus.vgis.VgPoint;

//TODO: Check, if this class should be migrated to the 52N Triturus core package in the future.

/**
 * Implementation of coordinate-transformation chains. 
 * <p />
 * This implementation allows to give two coordinate-transformations <tt>t1</tt> 
 * and <tt>t2</tt>. When calling the <tt>transform</tt> method, these two transformations 
 * will be applied to a given point <tt>p</tt> consecutively, i.e. the result will 
 * be <tt>t2.transform(t1.transform(p))</tt>.  
 * 
 * @author Benno Schmidt
 */
public class ChainedTransform 
{
	private CoordinateTransform t1;
	private CoordinateTransform t2;

	/**
	 * Constructor.
	 * 
	 * @param t1 First coordinate-transformation to be applied
	 * @param t2 Second coordinate-transformation to be applied
	 */
	public ChainedTransform(CoordinateTransform t1, CoordinateTransform t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public T3dVector transform(T3dVector pnt) 
	{
		T3dVector tmp = t1.transform(pnt);
		return t2.transform(tmp);
	}
	
	public T3dVector transform(VgPoint loc) 
	{
		T3dVector tmp = t1.transform(loc);
		return t2.transform(tmp);		
	}
}
