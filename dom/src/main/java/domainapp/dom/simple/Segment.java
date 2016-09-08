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
package domainapp.dom.simple;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findBySegmentId", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Segment "
                        + "WHERE segmentId == :segmentId ")
})
@javax.jdo.annotations.Unique(name="SewerSegment_segmentId_UNQ", members = {"segmentId"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Segment implements Comparable<Segment> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{segmentId}", "segmentId", getSegmentId());
    }
    //endregion

    //region > constructor
    public Segment(final String segmentId) {
        setSegmentId(segmentId);
    }
    //endregion

    @Column(allowsNull = "false")
    @Property()
    @lombok.Getter @lombok.Setter
    private String segmentId;

    @Persistent(mappedBy = "segment", dependentElement = "false")
    @Collection()
    @Getter @Setter
    private SortedSet<ElementSpec> elements = new TreeSet<ElementSpec>();


    @Action
    @MemberOrder(name = "elements", sequence = "1")
    public Segment createSpec(
            @ParameterLayout(named = "Position")
            final int position) {
        final ElementSpec spec = new ElementSpec(this, position);
        getElements().add(spec);
        return this;
    }

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "segmentId");
    }
    @Override
    public int compareTo(final Segment other) {
        return ObjectContracts.compare(this, other, "segmentId");
    }

    //endregion

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;

    //endregion

}
