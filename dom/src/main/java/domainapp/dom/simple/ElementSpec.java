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
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.DomainObject;
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
//        @javax.jdo.annotations.Query(
//                name = "findBySegmentAndPosition", language = "JDOQL",
//                value = "SELECT "
//                        + "FROM domainapp.dom.simple.ElementSpec "
//                        + "WHERE segment == :segment && position == :position")
})
@javax.jdo.annotations.Unique(name="ElementSpec_segment_position_UNQ", members = {"segment", "position"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class ElementSpec implements Comparable<ElementSpec> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Element Spec: {segment} {position}", "segment", getSegment(), "position", getPosition());
    }
    //endregion

    //region > constructor
    public ElementSpec(final Segment segment, final int position) {
        setSegment(segment);
        setPosition(position);
    }
    //endregion

    @Column(allowsNull = "false")
    @Property()
    @Getter @Setter
    private Segment segment;

    @Column(allowsNull = "false")
    @Property()
    @Getter @Setter
    private int position;

    @Join
    @Element(dependent = "false")
    @Collection()
    @Getter @Setter
    private SortedSet<ProductionStep> steps = new TreeSet<ProductionStep>();

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "segment", "position");
    }
    @Override
    public int compareTo(final ElementSpec other) {
        return ObjectContracts.compare(this, other, "segment", "position");
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
