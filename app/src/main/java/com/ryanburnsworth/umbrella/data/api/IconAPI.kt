package com.ryanburnsworth.umbrella.data.api

/**
 * API for getting custom Nerdery icon URLs for weather conditions
 *
 * This really isn't so much an API as a utility, but we will treat it as an API
 *
 */
class IconAPI {

    /**
     * Get the URL to an icon suitable for use as a replacement for the icons given by Dark Sky
     * @param icon The name of the icon provided by the DarkSky API (e.g. "clear").
     * @param highlighted True to get the highlighted version, false to get the outline version
     * @return A URL to an icon
     */
    fun getUrlForIcon(icon: String, highlighted: Boolean): String {
        val highlightParam = if (highlighted) "-selected" else ""
        return String.format("https://codechallenge.nerderylabs.com/mobile-nat/%s%s.png", icon, highlightParam)
    }
}
