package com.redhat.jielicious.storage;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.SecurityContext;

public interface StorageHandler {
    void getSystems(SecurityContext context, AsyncResponse asyncResponse, String namespace, String systemId, String offset, String limit, String sort);

    void putSystems(String body, SecurityContext context, AsyncResponse asyncResponse, String namespace, String systemId);

    void postSystems(String body, SecurityContext context, AsyncResponse asyncResponse, String namespace, String systemId, String offset, String limit, String sort);

    void deleteSystems(String body, SecurityContext context, AsyncResponse asyncResponse, String namespace, String systemId);

    void getAgents(SecurityContext securityContext, AsyncResponse asyncResponse, String namespace, String systemId, String agentId, String offset, String limit, String sort);

    void putAgents(String body, SecurityContext context, AsyncResponse asyncResponse, String namespace, String systemId, String agentId);

    void postAgents(String body, SecurityContext context, AsyncResponse asyncResponse, String namespace, String systemId, String agentId, String offset, String limit, String sort);

    void deleteAgents(String body, SecurityContext context, AsyncResponse asyncResponse, String namespace, String systemId, String agentId);

    void getJvms(SecurityContext securityContext, AsyncResponse asyncResponse, String namespace, String systemId, String agentId, String jvmId, String offset, String limit, String sort);

    void putJvms(String body, SecurityContext context, AsyncResponse asyncResponse, String namespace, String systemId, String agentId, String jvmId);

    void postJvms(String body, SecurityContext context, AsyncResponse asyncResponse, String namespace, String systemId, String agentId, String jvmId, String offset, String limit, String sort);

    void deleteJvms(String body, SecurityContext context, AsyncResponse asyncResponse, String namespace, String systemId, String agentId, String jvmId);

    void getNamespaces(SecurityContext context, AsyncResponse asyncResponse, String offset, String limit);

    // ChunkedOutput<String> streamAgents(SecurityContext securityContext, String namespace, String systemId, String limit);
}
