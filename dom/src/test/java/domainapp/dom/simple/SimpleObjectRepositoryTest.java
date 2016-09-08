/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package domainapp.dom.simple;

public class SimpleObjectRepositoryTest {

//    @Rule
//    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);
//
//    @Mock
//    ServiceRegistry2 mockServiceRegistry;
//
//    @Mock
//    RepositoryService mockRepositoryService;
//
//    SegmentRepository segmentRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        segmentRepository = new SegmentRepository();
//        segmentRepository.repositoryService = mockRepositoryService;
//        segmentRepository.serviceRegistry = mockServiceRegistry;
//    }
//
//    public static class Create extends SimpleObjectRepositoryTest {
//
//        @Test
//        public void happyCase() throws Exception {
//
//            final String someName = "Foobar";
//
//            // given
//            final Sequence seq = context.sequence("create");
//            context.checking(new Expectations() {
//                {
//                    oneOf(mockServiceRegistry).injectServicesInto(with(any(Segment.class)));
//                    inSequence(seq);
//
//                    oneOf(mockRepositoryService).persist(with(nameOf(someName)));
//                    inSequence(seq);
//                }
//
//            });
//
//            // when
//            final Segment obj = segmentRepository.create(someName);
//
//            // then
//            assertThat(obj).isNotNull();
//            assertThat(obj.getName()).isEqualTo(someName);
//        }
//
//        private static Matcher<Segment> nameOf(final String name) {
//            return new TypeSafeMatcher<Segment>() {
//                @Override
//                protected boolean matchesSafely(final Segment item) {
//                    return name.equals(item.getName());
//                }
//
//                @Override
//                public void describeTo(final Description description) {
//                    description.appendText("has name of '" + name + "'");
//                }
//            };
//        }
//    }
//
//    public static class ListAll extends SimpleObjectRepositoryTest {
//
//        @Test
//        public void happyCase() throws Exception {
//
//            // given
//            final List<Segment> all = Lists.newArrayList();
//
//            context.checking(new Expectations() {
//                {
//                    oneOf(mockRepositoryService).allInstances(Segment.class);
//                    will(returnValue(all));
//                }
//            });
//
//            // when
//            final List<Segment> list = segmentRepository.listAll();
//
//            // then
//            assertThat(list).isEqualTo(all);
//        }
//    }
}
