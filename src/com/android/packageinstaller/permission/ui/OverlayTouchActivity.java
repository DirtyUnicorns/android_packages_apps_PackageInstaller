/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.packageinstaller.permission.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.provider.Settings;
import android.view.MotionEvent;
import android.util.Log;

public class OverlayTouchActivity extends Activity {

    private boolean mObscuredTouch;

    public boolean isObscuredTouch() {
        return mObscuredTouch;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final boolean overlayCheckDisabled = Settings.Secure.getInt(getContentResolver(),
                Settings.Secure.PACKAGE_INSTALL_OVERLAY_CHECK_DISABLED, 0) != 0;
        if (overlayCheckDisabled) {
            mObscuredTouch = false;
        } else {
            mObscuredTouch = (event.getFlags() & (MotionEvent.FLAG_WINDOW_IS_OBSCURED
                | MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED)) != 0;
        }
        return super.dispatchTouchEvent(event);
    }

    public void showOverlayDialog() {
        startActivity(new Intent(this, OverlayWarningDialog.class));
    }
}
