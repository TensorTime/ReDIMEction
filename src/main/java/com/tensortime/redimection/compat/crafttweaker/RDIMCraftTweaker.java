package com.tensortime.redimection.compat.crafttweaker;

import java.util.List;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ReDIMEction")
public class RDIMCraftTweaker {
    
    @ZenMethod
    public static void addSourceRestriction (List<Integer> sources, int destination) {
        CraftTweakerAPI.apply(new ActionAddSourceRestriction(sources, destination));
    }
    
    @ZenMethod
    public static void addDestinationRestriction (int source, int destination) {
        
        CraftTweakerAPI.apply(new ActionAddDestinationRestriction(source, destination));
    }
}
