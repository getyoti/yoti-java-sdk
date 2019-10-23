package com.yoti.api.client.shareurl.constraint;

import com.yoti.api.client.shareurl.policy.WantedAnchor;

import java.util.List;

public interface PreferredSources {

    /**
     * Get the list of {@link WantedAnchor}
     *
     * @return the list of {@link WantedAnchor}
     */
    List<WantedAnchor> getWantedAnchors();

    /**
     * Get the soft preference
     *
     * @return the soft preference
     */
    boolean getSoftPreference();

}
