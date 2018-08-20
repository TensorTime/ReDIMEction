package com.tensortime.redimection.compat.crafttweaker;

import crafttweaker.IAction;
import java.util.List;
import com.tensortime.redimection.ReDIMEction;

public class ActionAddDimensionDestinationRestriction implements IAction {
    
    private final Integer source;
    private final Integer destination;
    
    public ActionAddDimensionDestinationRestriction (Integer source, Integer destination) {
        
        this.source = source;
        this.destination = destination;
    }
    
    @Override
    public void apply () {
        
        ReDIMEction.SOURCE_MAP.put(this.source, this.destination);
    }
    
    @Override
    public String describe () {
        
        return String.format("Dimension %d has new source restrictions");
    }
}
