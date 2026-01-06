package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareCodeResourceResponse extends ResourceResponse {

    @JsonProperty("lookup_profile")
    private LookupProfileResponse lookupProfile;

    @JsonProperty("returned_profile")
    private ReturnedProfileResponse returnedProfile;

    @JsonProperty("id_photo")
    private IdPhotoResponse idPhoto;

    @JsonProperty("file")
    private FileResponse file;

    public LookupProfileResponse getLookupProfile() {
        return lookupProfile;
    }

    public ReturnedProfileResponse getReturnedProfile() {
        return returnedProfile;
    }

    public IdPhotoResponse getIdPhoto() {
        return idPhoto;
    }

    public FileResponse getFile() {
        return file;
    }

    public List<ShareCodeTaskResponse> getVerifyShareCodeTasks() {
        return filterTasksByType(ShareCodeTaskResponse.class);
    }

}
