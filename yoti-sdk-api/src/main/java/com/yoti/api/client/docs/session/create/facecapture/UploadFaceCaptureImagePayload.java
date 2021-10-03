package com.yoti.api.client.docs.session.create.facecapture;

import org.apache.http.entity.ContentType;

public class UploadFaceCaptureImagePayload {

    private final String imageContentType;
    private final byte[] imageContents;

    private UploadFaceCaptureImagePayload(String imageContentType, byte[] imageContents) {
        this.imageContentType = imageContentType;
        this.imageContents = imageContents;
    }

    public static UploadFaceCaptureImagePayload.Builder builder() {
        return new UploadFaceCaptureImagePayload.Builder();
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public byte[] getImageContents() {
        return imageContents;
    }

    public static class Builder {

        private String imageContentType;
        private byte[] imageContents;

        private Builder() { }

        /**
         * Sets the content type for uploading a JPEG image
         *
         * @return the builder
         */
        public Builder forJpegImage() {
            this.imageContentType = ContentType.IMAGE_JPEG.toString();
            return this;
        }

        /**
         * Sets the content type for uploading a PNG image
         *
         * @return the builder
         */
        public Builder forPngImage() {
            this.imageContentType = ContentType.IMAGE_PNG.toString();
            return this;
        }

        /**
         * Sets the contents of the image to be uploaded
         *
         * @param imageContents the content of the image
         * @return the builder
         */
        public Builder withImageContents(byte[] imageContents) {
            this.imageContents = imageContents == null ? null : imageContents.clone();
            return this;
        }

        public UploadFaceCaptureImagePayload build() {
            return new UploadFaceCaptureImagePayload(imageContentType, imageContents);
        }

    }

}
