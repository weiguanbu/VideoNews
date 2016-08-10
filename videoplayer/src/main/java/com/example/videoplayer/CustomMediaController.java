package com.example.videoplayer;

import android.content.Context;
import android.util.AttributeSet;

import io.vov.vitamio.widget.MediaController;

/**
 * Created by wagaranai on 2016/8/10.
 */

public class CustomMediaController extends MediaController {
    public CustomMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomMediaController(Context context) {
        super(context);
    }
}
