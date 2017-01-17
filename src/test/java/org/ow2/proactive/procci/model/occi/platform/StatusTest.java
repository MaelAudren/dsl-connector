/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.procci.model.occi.platform;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.ow2.proactive.procci.model.exception.SyntaxException;


public class StatusTest {

    @Test
    public void getStatusfromStringTest() throws SyntaxException {
        assertThat(Status.getStatusFromString("active")).isEquivalentAccordingToCompareTo(Status.ACTIVE);
        assertThat(Status.getStatusFromString("ERROR")).isEquivalentAccordingToCompareTo(Status.ERROR);
        assertThat(Status.getStatusFromString("Inactive")).isEquivalentAccordingToCompareTo(Status.INACTIVE);

        Exception ex = null;
        try {
            Status.getStatusFromString("bob");
        } catch (SyntaxException e) {
            ex = e;
        }
        assertThat(ex).isNotNull();
        assertThat(ex).isInstanceOf(SyntaxException.class);
    }
}
