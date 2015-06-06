package org.n52.v3d.worldviz.featurenet.scene;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

@XStreamAlias("LineVisualizer")
class LineVisualizer{
    @XStreamImplicit
    private List <Geometry> geometry;
    @XStreamImplicit
    private List <Stroke> stroke;
    
    public List getGeometry(){
        return geometry;
    }
    
    public List getStroke(){
        return stroke;
    }
    
}