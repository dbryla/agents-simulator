package org.agents.simulator.agents;

import madkit.kernel.Agent;
import org.agents.simulator.Groups;
import org.agents.simulator.organisations.Organization;
import org.agents.simulator.organisations.OrganizationType;

import java.util.concurrent.atomic.AtomicInteger;

public class OrganizedAgent extends Agent{

    private static final AtomicInteger agentsCounter = new AtomicInteger(0);
    private final OrganizationType organization;
    private final String group;
    private final String role;

    public String getRole() {
        return role;
    }

    private int id;

    public OrganizedAgent(OrganizationType organization, String group, String role) {
        this.organization = organization;
        this.group = group;
        this.role = role;
        id = agentsCounter.getAndIncrement();
    }

    public OrganizedAgent(OrganizationType organization, String role) {
        this(organization, Groups.NONE.toString(), role);
    }

    @Override
    protected void activate() {
        createGroupIfAbsent(organization.toString(), group);
        requestRole(organization.toString(), group, role);
        logger.info(String.format("Agent %d activated in organization: %s, group: %s and with role: %s.",
                id, organization, group, role));
    }

    protected void live() {
        Organization.getOrganizationPerType(organization).communicate(this);
    }
}
