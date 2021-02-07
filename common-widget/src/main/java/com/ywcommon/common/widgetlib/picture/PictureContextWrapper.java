package com.ywcommon.common.widgetlib.picture;

import android.content.Context;
import android.content.ContextWrapper;

import com.ywcommon.common.widgetlib.picture.language.PictureLanguageUtils;

/**
 * @author：luck
 * @date：2019-12-15 19:34
 * @describe：ContextWrapper
 */
public class PictureContextWrapper extends ContextWrapper {

    public PictureContextWrapper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context, int language) {
        PictureLanguageUtils.setAppLanguage(context, language);
        return new PictureContextWrapper(context);
    }
}
