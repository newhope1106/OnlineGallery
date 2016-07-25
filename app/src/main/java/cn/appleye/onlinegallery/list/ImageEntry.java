package cn.appleye.onlinegallery.list;

import android.net.Uri;

/**
 * Created by iSpace on 2016/7/25.
 */
public class ImageEntry {
    public Uri uri;

    public String imageName;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((uri == null) ? 0 : uri.hashCode());

        return result;
    }

    public static ImageEntry createFromUri(Uri uri, String imageName) {
        ImageEntry entry = new ImageEntry();
        entry.uri = uri;
        entry.imageName = imageName;

        return entry;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final ImageEntry that = (ImageEntry)obj;

        if (uri == null && that.uri==null) {
            return true;
        }

        return uri.equals(that.uri);
    }

    public Object getKey() {
        return uri == null ? -1 : uri;
    }
}
