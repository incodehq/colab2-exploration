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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Auditing;
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
//                name = "findByType", language = "JDOQL",
//                value = "SELECT "
//                        + "FROM domainapp.dom.simple.ConcreteProductionStep "
//                        + "WHERE type == :type ")
})
@javax.jdo.annotations.Unique(name="ConcreteProductionStep_element_spec_UNQ", members = {"element", "spec"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class ConcreteProductionStep implements Comparable<ConcreteProductionStep> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{element} {spec}", "element", getElement().getProductionId(), getSpec().getName());
    }
    //endregion

    //region > constructor
    public ConcreteProductionStep(final ConcreteElement element, final ProductionStepSpec spec, final int sequence) {
        setElement(element);
        setSpec(spec);
        setSequence(sequence);
    }
    //endregion

    @Column(allowsNull = "false")
    @Property()
    @Getter @Setter
    private ConcreteElement element;

    @Column(allowsNull = "false")
    @Property()
    @Getter @Setter
    private ProductionStepSpec spec;

    @Column(allowsNull = "false")
    @Property()
    @Getter @Setter
    private int sequence;

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "element", "spec", "sequence");
    }
    @Override
    public int compareTo(final ConcreteProductionStep other) {
        return ObjectContracts.compare(this, other, "element", "spec", "sequence");
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
