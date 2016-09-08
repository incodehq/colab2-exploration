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

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.simple.ProductionStepRepository;
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

        createSegments(oxfStreetSeg, 5);
        createSegments(highStreetSeg, 3);
        createSegments(railwayRoadSeg, 10);

        productionStepRepository.create("Setup Form");
        productionStepRepository.create("Reinforcements");
        productionStepRepository.create("Pour the concrete");
        productionStepRepository.create("Quality check");

    }

    private void createSegments(final Segment seg, final int num) {
        for (int i = 0; i < num; i++) {
            seg.createSpec(i);
        }
    }

    @Inject
    SegmentRepository segmentRepository;

    @Inject
    ProductionStepRepository productionStepRepository;

}
