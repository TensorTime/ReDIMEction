package com.tensortime.redimection.compat.crafttweaker;

import java.util.List;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.reDIMection")
public class RDIMCraftTweaker {
    
    @ZenMethod
    public static void addSourceRestriction (List<Integer> sources, Integer destination) {
        
        CraftTweakerAPI.apply(new ActionAddDimensionSourceRestriction(sources, destination));
    }
    
    @ZenMethod
    public static void addDestinationRestriction (Integer source, Integer destination) {
        
        CraftTweakerAPI.apply(new ActionAddDimensionDestinationRestriction(source, destination));
    }
}
