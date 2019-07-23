package com.app.velocimetro.fontes;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Criado por AFTI Soluções Tecnológicas em 30/10/2018.
 *
 * @Author AFTI Soluções Tecnológicas
 * @Email appafti@aftisolucoes.com.br
 * @Web http://aftisolucoes.com.br
 */
public  class FonteCache {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }

}
