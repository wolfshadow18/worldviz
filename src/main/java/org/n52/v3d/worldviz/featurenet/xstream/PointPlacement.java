package org.n52.v3d.worldviz.featurenet.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

@XStreamAlias("PointPlacement")
public class PointPlacement{
    @XStreamImplicit
    protected List<Displacement> displacement;
    
    public List getDisplacement(){
        return displacement;
    }
    
}