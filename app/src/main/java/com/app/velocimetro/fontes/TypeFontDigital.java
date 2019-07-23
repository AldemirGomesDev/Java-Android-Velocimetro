package com.app.velocimetro.fontes;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Criado por AFTI Soluções Tecnológicas em 30/10/2018.
 *
 * @Author AFTI Soluções Tecnológicas
 * @Email appafti@aftisolucoes.com.br
 * @Web http://aftisolucoes.com.br
 */

@SuppressLint("AppCompatCustomView")
public class TypeFontDigital extends TextView {

    public TypeFontDigital(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public TypeFontDigital(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public TypeFontDigital(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FonteCache.getTypeface("digital-7.ttf", context);
        setTypeface(customFont);
    }

}