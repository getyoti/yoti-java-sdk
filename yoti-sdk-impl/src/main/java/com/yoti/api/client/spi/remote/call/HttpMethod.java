package com.yoti.api.client.spi.remote.call;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.List;

public class HttpMethod {

    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_PATCH = "PATCH";
    public static final String HTTP_DELETE = "DELETE";

    public static final List<String> SUPPORTED_HTTP_METHODS = unmodifiableList(asList(HTTP_POST, HTTP_PUT, HTTP_PATCH, HTTP_GET, HTTP_DELETE));

}
