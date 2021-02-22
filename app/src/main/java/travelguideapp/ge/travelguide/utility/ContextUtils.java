package travelguideapp.ge.travelguide.utility;

import android.content.Context;
import android.content.ContextWrapper;

public class ContextUtils extends ContextWrapper {
    public ContextUtils(Context base) {
        super(base);
    }
}
