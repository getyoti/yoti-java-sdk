package com.yoti.api.client.docs.session.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yoti.api.client.docs.session.create.check.RequestedCheck;
import com.yoti.api.client.docs.session.create.filters.RequiredDocument;
import com.yoti.api.client.docs.session.create.task.RequestedTask;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleSessionSpecBuilder implements SessionSpecBuilder {

    private final List<RequestedCheck> requestedChecks = new ArrayList<>();
    private final List<RequestedTask> requestedTasks = new ArrayList<>();
    private final List<RequiredDocument> requiredDocuments = new ArrayList<>();
    private Integer clientSessionTokenTtl;
    private Integer resourcesTtl;
    private String userTrackingId;
    private NotificationConfig notifications;
    private SdkConfig sdkConfig;

    @Override
    public SessionSpecBuilder withClientSessionTokenTtl(Integer clientSessionTokenTtl) {
        this.clientSessionTokenTtl = clientSessionTokenTtl;
        return this;
    }

    @Override
    public SessionSpecBuilder withResourcesTtl(Integer resourcesTtl) {
        this.resourcesTtl = resourcesTtl;
        return this;
    }

    @Override
    public SessionSpecBuilder withUserTrackingId(String userTrackingId) {
        this.userTrackingId = userTrackingId;
        return this;
    }

    @Override
    public SessionSpecBuilder withNotifications(NotificationConfig notifications) {
        this.notifications = notifications;
        return this;
    }

    @Override
    public SessionSpecBuilder withRequestedCheck(RequestedCheck requestedCheck) {
        this.requestedChecks.add(requestedCheck);
        return this;
    }

    @Override
    public SessionSpecBuilder withRequestedTask(RequestedTask requestedTask) {
        this.requestedTasks.add(requestedTask);
        return this;
    }

    @Override
    public SessionSpecBuilder withSdkConfig(SdkConfig sdkConfig) {
        this.sdkConfig = sdkConfig;
        return this;
    }

    @Override
    public SessionSpecBuilder withRequiredDocument(RequiredDocument requiredDocument) {
        this.requiredDocuments.add(requiredDocument);
        return this;
    }

    @Override
    public SessionSpec build() {
        return new SimpleSessionSpec(clientSessionTokenTtl, resourcesTtl, userTrackingId, notifications, requestedChecks, requestedTasks, sdkConfig, requiredDocuments);
    }

}
