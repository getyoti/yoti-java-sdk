package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface BreakdownResponse {

    String getSubCheck();

    String getResult();

    List<? extends DetailsResponse> getDetails();

}
