package org.ow2.proactive.procci.model.occi.infrastructure;


import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.infrastructure.action.RestartCompute;
import org.ow2.proactive.procci.model.occi.infrastructure.action.SaveCompute;
import org.ow2.proactive.procci.model.occi.infrastructure.action.StartCompute;
import org.ow2.proactive.procci.model.occi.infrastructure.action.StopCompute;
import org.ow2.proactive.procci.model.occi.infrastructure.action.SuspendCompute;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.junit.Test;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/24/16.
 */
public class ComputeTest {

    private ComputeBuilder computeBuilder;

    @Before
    public void setUp(){
        computeBuilder = new ComputeBuilder().url("url")
                .architecture(Compute.Architecture.X64)
                .cores(5)
                .hostame("hostname")
                .memory(new Float(3))
                .state(ComputeState.SUSPENDED)
                .share(2)
                .summary("summary")
                .title("title");
    }

    @Test
    public void computeConstructorTest() {

        Compute compute = computeBuilder.build();

        assertThat(compute.getArchitecture()).isEquivalentAccordingToCompareTo(Compute.Architecture.X64);
        assertThat(compute.getCores()).isEqualTo(new Integer(5));
        assertThat(compute.getHostname()).isEqualTo("hostname");
        assertThat(compute.getMemory()).isWithin(new Float(0.0001).compareTo(new Float(3)));
        assertThat(compute.getState()).isEquivalentAccordingToCompareTo(ComputeState.SUSPENDED);
        assertThat(compute.getSummary()).isEqualTo("summary");
        assertThat(compute.getShare()).isEqualTo(new Integer(2));
        assertThat(compute.getTitle()).isEqualTo("title");
    }

    @Test
    public void ComputeBuilderUpdateTest(){

        Model model = new Model.Builder("modelTest","actionTypeTest")
                .addVariable("occi.compute.memory","2.0")
                .addVariable("occi.compute.cores","4")
                .addVariable("endpoint","10.0.0.1")
                .addVariable("status","RUNNING")
                .addVariable("occi.compute.architecture","x86")
                .addVariable("occi.entity.title","titleTest")
                .build();

        computeBuilder.update(model);

        assertThat(computeBuilder.getArchitecture()).isEqualTo(Compute.Architecture.X86);
        assertThat(computeBuilder.getCores()).isEqualTo(new Integer(4));
        assertThat(computeBuilder.getMemory()).isWithin(new Float(0.0001)).of(new Float(2));
        assertThat(computeBuilder.getHostname()).matches("10.0.0.1");
        assertThat(computeBuilder.getTitle()).matches("titleTest");
        assertThat(computeBuilder.getState()).isEqualTo(ComputeState.ACTIVE);
    }

    @Test
    public void toCloudAutomationModelTest(){
        Model model = computeBuilder.build().toCloudAutomationModel("create");
        assertThat(model.getServiceModel()).isEqualTo("occi.infrastructure.compute");
        assertThat(model.getActionType()).isEqualTo("create");
        assertThat(model.getVariables()).containsEntry("occi.compute.cores","5");
        assertThat(model.getVariables()).containsEntry("occi.compute.architecture","X64");
        assertThat(model.getVariables()).containsEntry("occi.compute.memory","3.0");
        assertThat(model.getVariables()).containsEntry("occi.entity.title","title");
    }

    @Test
    public void updateFromRenderingTest(){
        ResourceRendering computeRendering = new ResourceRendering
                .Builder("http://schemas.ogf.org/occi/infrastructure#compute",
                "urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29")
                .addAttribute("occi.compute.speed",2)
                .addAttribute("occi.compute.memory",4.0)
                .addAttribute("occi.compute.cores",2)
                .addAttribute("occi.compute.hostname","80.200.35.140")
                .addAttribute("occi.entity.title","titleTest")
                .addAttribute("occi.compute.architecture","x86")
                .addAttribute("occi.compute.state","ACTIVE")
                .build();


        Compute compute = new ComputeBuilder().update(computeRendering).build();

        assertThat(compute.getId()).matches("urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29");
        assertThat(compute.getKind().getTitle()).matches("http://schemas.ogf.org/occi/infrastructure#compute");
        assertThat(compute.getCores()).isEqualTo(new Integer(2));
        assertThat(compute.getMemory()).isWithin(new Float(0.001)).of(new Float(4.0));
        assertThat(compute.getHostname()).matches("80.200.35.140");
        assertThat(compute.getTitle()).matches("titleTest");
        assertThat(compute.getArchitecture()).isEqualTo(Compute.Architecture.X86);
        assertThat(compute.getState()).isEqualTo(ComputeState.ACTIVE);


    }
}
