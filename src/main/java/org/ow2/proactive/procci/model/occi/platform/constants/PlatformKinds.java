package org.ow2.proactive.procci.model.occi.platform.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelKinds;
import org.ow2.proactive.procci.model.occi.platform.Component;

public class PlatformKinds {

    public static final Kind COMPONENT = new Kind.Builder(PlatformIdentifiers.PLATFORM_SCHEME,
            PlatformIdentifiers.COMPONENT_TERM)
            .addAttribute(Component.createAttributeSet())
            .addParent(MetamodelKinds.RESOURCE)
            .build();


}