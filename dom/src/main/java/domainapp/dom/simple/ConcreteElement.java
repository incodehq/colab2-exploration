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

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
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
                name = "findByProductionId", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.ConcreteElement "
                        + "WHERE productionId == :productionId ")
})
@javax.jdo.annotations.Unique(name="ConcreteElement_productionId_UNQ", members = {"productionId"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class ConcreteElement implements Comparable<ConcreteElement> {


    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("ConcreteElement: {productionId}", "productionId", getProductionId());
    }
    //endregion

    //region > constructor
    public ConcreteElement(final ElementSpec spec, final String productionId) {
        setProductionId(productionId);
        setElementSpec(spec);
    }

    ConcreteElement addSteps(final ElementSpec spec) {
        final SortedSet<ProductionStepSpec> steps = spec.getSteps();
        for (ProductionStepSpec stepSpec : steps) {
            final ProductionStep productionStep = repositoryService.instantiate(ProductionStep.class);
            productionStep.setSequence(stepSpec.getSequence());
            productionStep.setSpec(stepSpec);
            getSteps().add(productionStep);
        }
        return this;
    }
    //endregion

    @Column(allowsNull = "false")
    @Property()
    @Getter @Setter
    private String productionId;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private ElementSpec elementSpec;

    @Persistent(mappedBy = "element", dependentElement = "true")
    @Collection()
    @Getter @Setter
    private SortedSet<ProductionStep> steps = new TreeSet<ProductionStep>();


    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public ConcreteElement complete(
            final ProductionStep step,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Time spent (hours)")
            final Integer timeSpent,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Comments", multiLine = 5)
            final String comments) {
        step.setStatus(ProductionStepStatus.COMPLETED);
        step.setTimeSpent(timeSpent);
        step.setComments(comments);
        return this;
    }

    public List<ProductionStep> choices0Complete() {
        return Lists.newArrayList(
                getSteps().stream().filter(x -> x.getStatus() != ProductionStepStatus.COMPLETED).collect(Collectors.toList())
            );
    }

    public ProductionStep default0Complete() {
        final List<ProductionStep> choices = choices0Complete();
        return !choices.isEmpty() ? choices.get(0) : null;
    }
    

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "productionId");
    }

    @Override
    public int compareTo(final ConcreteElement other) {
        return ObjectContracts.compare(this, other, "productionId");
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
