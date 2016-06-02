/*
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 2013-2015 ActiveEon
 * 
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * $$ACTIVEEON_INITIAL_DEV$$
 */

package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes;
import com.google.common.collect.ImmutableList;
import lombok.Getter;

/**
 * Mixin is an extension mecanism which enables to new resource capablilities
 */
public class Mixin extends Category {

    @Getter
    private final ImmutableList<Action> actions;
    @Getter
    private final ImmutableList<Mixin> depends;
    @Getter
    private final ImmutableList<Kind> applies;
    @Getter
    private List<Entity> entities;


    /**
     * Mixin constructor
     *
     * @param scheme     is the categorisation scheme
     * @param term       is the unique identifier of the category instance
     * @param title      is the display name of the instance
     * @param attributes is the set of resource attribute name
     * @param actions    are the actions defined by this mixin instances
     * @param depends    are the depends mixin instances
     * @param applies    is the list of kind this mixin instance applies to
     * @param entities   is the set of resource instances
     */
    public Mixin(String scheme, String term, String title, Set<Attribute> attributes,
            List<Action> actions, List<Mixin> depends, List<Kind> applies,
            List<Entity> entities) {
        super(scheme, term, title, setAttributes(attributes));
        this.actions = new ImmutableList.Builder<Action>().addAll(actions).build();
        this.depends = new ImmutableList.Builder<Mixin>().addAll(depends).build();
        this.applies = new ImmutableList.Builder<Kind>().addAll(applies).build();
        this.entities = entities;
    }

    private static Set<Attribute> setAttributes(Set<Attribute> paramAttributes) {
        Set<Attribute> attributes = new HashSet<>();
        attributes.addAll(paramAttributes);
        attributes.add(Attributes.DEPENDS);
        attributes.add(Attributes.APPLIES);
        attributes.add(Attributes.MIXIN_ENTITIES);
        attributes.add(Attributes.MIXIN_ACTIONS);
        return attributes;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void deleteEntity(Entity entity) {
        entities.remove(entity);
    }

}