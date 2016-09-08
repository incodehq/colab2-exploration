/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package domainapp.fixture.scenarios;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.simple.ElementSpec;
import domainapp.dom.simple.ProductionStep;
import domainapp.dom.simple.ProductionStepRepository;
import domainapp.dom.simple.ProductionStepType;
import domainapp.dom.simple.Segment;
import domainapp.dom.simple.SegmentRepository;

public class RecreateSewerSystem extends FixtureScript {

    public RecreateSewerSystem() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }


    @Override
    protected void execute(final ExecutionContext ec) {

        final Segment oxfStreetSeg = segmentRepository.create("Oxford Street");
        final Segment highStreetSeg = segmentRepository.create("High Street");
        final Segment railwayRoadSeg = segmentRepository.create("Railway Road");

        final List<ElementSpec> elementSpecs = createSpecs(oxfStreetSeg, 5);
        createSpecs(highStreetSeg, 3);
        createSpecs(railwayRoadSeg, 10);

        int sequence = 0;
        productionStepRepository.create("Setup Form", ProductionStepType.BASIC, sequence++);
        productionStepRepository.create("Reinforcements", ProductionStepType.BASIC, sequence++);
        productionStepRepository.create("Pour the concrete", ProductionStepType.BASIC, sequence++);

        final ProductionStep legStep = productionStepRepository.create("Legs", ProductionStepType.EXTRA, sequence++);
        final ProductionStep entryPointStep = productionStepRepository.create("Entry point", ProductionStepType.EXTRA,
                sequence++);
        final ProductionStep ladderStep = productionStepRepository.create("Ladder", ProductionStepType.EXTRA, sequence++);

        productionStepRepository.create("Quality check", ProductionStepType.BASIC, sequence = 999);

        elementSpecs.get(0).associateBasicSteps();
        elementSpecs.get(0).associateStep(legStep);

        elementSpecs.get(1).associateBasicSteps();

        elementSpecs.get(1).associateStep(entryPointStep);
        elementSpecs.get(1).associateStep(ladderStep);


    }

    private List<ElementSpec> createSpecs(final Segment seg, final int num) {
        for (int i = 0; i < num; i++) {
            seg.createSpec(i);
        }
        return Lists.newArrayList(seg.getElements());
    }

    @Inject
    SegmentRepository segmentRepository;

    @Inject
    ProductionStepRepository productionStepRepository;

}
