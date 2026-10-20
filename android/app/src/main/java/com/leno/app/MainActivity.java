package com.leno.app;

import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.app.Activity;

public class MainActivity extends Activity {
    final int bg = Color.rgb(15, 23, 42);
    final int panel = Color.rgb(30, 41, 59);
    final int accent = Color.rgb(45, 212, 191);
    LinearLayout root, content;
    String currentPage = "chats";

    int dp(float n) {
        return (int)(n * getResources().getDisplayMetrics().density);
    }

    GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        return d;
    }

    TextView label(String text, int size, int color) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        return t;
    }

    LinearLayout column() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        return l;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);
        getWindow().getDecorView().setSystemUiVisibility(0);
        showPage("chats");
    }

    void showPage(String page) {
        currentPage = page;
        root = column();
        root.setBackgroundColor(bg);
        setContentView(root);

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(dp(20), dp(12), dp(20), dp(12));

        TextView logo = label("Leno", 28, accent);
        logo.setTypeface(null, Typeface.BOLD);
        header.addView(logo, new LinearLayout.LayoutParams(0, dp(52), 1));

        TextView newChat = label("+", 30, Color.WHITE);
        newChat.setGravity(Gravity.CENTER);
        newChat.setBackground(shape(panel, 30));
        header.addView(newChat, new LinearLayout.LayoutParams(dp(48), dp(48)));
        newChat.setOnClickListener(v -> showPage("contacts"));
        root.addView(header);

        if (page.equals("chats")) {
            EditText search = new EditText(this);
            search.setSingleLine(true);
            search.setTextColor(Color.WHITE);
            search.setHintTextColor(Color.LTGRAY);
            search.setHint("جست‌وجوی گفتگوها");
            search.setTextSize(15);
            search.setPadding(dp(16), 0, dp(16), 0);
            search.setBackground(shape(panel, 14));
            LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(
                -1, dp(48));
            sp.setMargins(dp(16), dp(4), dp(16), dp(18));
            root.addView(search, sp);
        }

        TextView heading = label(
            page.equals("chats") ? "گفتگوها" :
            page.equals("contacts") ? "مخاطبین" :
            page.equals("settings") ? "تنظیمات" : "حساب کاربری",
            21, Color.WHITE);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setPadding(dp(20), dp(8), dp(20), dp(12));
        root.addView(heading);

        ScrollView scroll = new ScrollView(this);
        content = column();
        content.setPadding(dp(12), dp(4), dp(12), dp(12));
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        if (page.equals("chats")) {
            addChat("دوست من", "به لنو خوش آمدی!", "د", "۱۴:۳۰");
            addChat("گفتگوی شخصی", "اینجا محل گفتگوهای توست", "ل", "دیروز");
            addChat("گروه دوستان", "گروه‌های تو اینجا نمایش داده می‌شوند", "گ", "دوشنبه");
            TextView note = label("این نسخه آزمایشی است؛ پیام‌ها هنوز به گوشی دیگری ارسال نمی‌شوند.", 13, Color.LTGRAY);
            note.setPadding(dp(12), dp(16), dp(12), dp(16));
            content.addView(note);
        } else if (page.equals("contacts")) {
            addChat("افزودن مخاطب", "قابلیت اتصال به مخاطبین در مرحله بعد", "+", "");
        } else if (page.equals("settings")) {
            addChat("اعلان‌ها", "تنظیمات اعلان‌ها", "ن", "");
            addChat("حریم خصوصی", "تنظیمات امنیتی", "ح", "");
            addChat("درباره لنو", "نسخه ۱.۰ آزمایشی", "ل", "");
        } else {
            addChat("حساب کاربری", "ورود و ثبت‌نام در مرحله بعد اضافه می‌شود", "ک", "");
        }

        LinearLayout nav = new LinearLayout(this);
        nav.setBackgroundColor(panel);
        String[] names = {"گفتگوها", "مخاطبین", "حساب", "تنظیمات"};
        String[] pages = {"chats", "contacts", "profile", "settings"};
        for (int i = 0; i < names.length; i++) {
            final String target = pages[i];
            TextView tab = label(names[i], 12,
                page.equals(target) ? accent : Color.LTGRAY);
            tab.setGravity(Gravity.CENTER);
            nav.addView(tab, new LinearLayout.LayoutParams(0, dp(60), 1));
            tab.setOnClickListener(v -> showPage(target));
        }
        root.addView(nav);
    }

    void addChat(String name, String message, String avatar, String time) {
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(10), dp(10), dp(10), dp(10));
        row.setBackground(shape(panel, 16));

        TextView icon = label(avatar, 22, accent);
        icon.setGravity(Gravity.CENTER);
        icon.setTypeface(null, Typeface.BOLD);
        icon.setBackground(shape(bg, 40));
        row.addView(icon, new LinearLayout.LayoutParams(dp(48), dp(48)));

        LinearLayout details = column();
        details.setPadding(dp(12), 0, dp(8), 0);
        TextView title = label(name, 16, Color.WHITE);
        title.setTypeface(null, Typeface.BOLD);
        details.addView(title);
        TextView preview = label(message, 12, Color.LTGRAY);
        details.addView(preview);
        row.addView(details, new LinearLayout.LayoutParams(0, -2, 1));

        TextView clock = label(time, 10, accent);
        row.addView(clock);

        LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(-1, -2);
        rp.setMargins(0, 0, 0, dp(10));
        content.addView(row, rp);

        row.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(this)
                .setTitle(name)
                .setMessage("رابط پیام‌رسان بومی لنو آماده‌سازی شده است. ارسال پیام واقعی در مرحله اتصال به بک‌اند اضافه می‌شود.")
                .setPositiveButton("باشه", null)
                .show();
        });
    }
}
