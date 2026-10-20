package com.leno.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    final int bg = Color.rgb(10, 12, 28);
    final int panel = Color.rgb(23, 26, 49);
    final int purple = Color.rgb(157, 92, 255);
    final int white = Color.WHITE;
    final int muted = Color.rgb(165, 169, 194);

    LinearLayout root, content;
    String currentPage = "chats";

    int dp(float n) {
        return (int)(n * getResources().getDisplayMetrics().density + 0.5f);
    }
GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        return d;
    }

    GradientDrawable gradient(int radius) {
        GradientDrawable d = new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(105, 63, 220), Color.rgb(190, 83, 255), Color.rgb(70, 90, 220)}
        );
        d.setCornerRadius(dp(radius));
        return d;
    }

    TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        t.setFontFeatureSettings("kern");
        return t;
    }

    LinearLayout vertical() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        return l;
    }  @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);
        getWindow().getDecorView().setSystemUiVisibility(0);
        showPage("chats");
    }

    void showPage(String page) {
        currentPage = page;
        root = vertical();
        root.setBackgroundColor(bg);
        setContentView(root);

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(dp(20), dp(12), dp(20), dp(10));

        TextView logo = text("L", 30, white);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        logo.setBackground(gradient(16));
        header.addView(logo, new LinearLayout.LayoutParams(dp(48), dp(48)));

        LinearLayout brand = vertical();
        brand.setPadding(dp(12), 0, 0, 0);
        TextView title = text("LENO", 23, white);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        brand.addView(title);
        TextView subtitle = text("YOUR WORLD. YOUR SPACE.", 9, muted);
        brand.addView(subtitle);
        header.addView(brand, new LinearLayout.LayoutParams(0, -2, 1));TextView action = text(page.equals("chats") ? "+" : "⋯", 28, white);
        action.setGravity(Gravity.CENTER);
        action.setBackground(shape(panel, 18));
        header.addView(action, new LinearLayout.LayoutParams(dp(46), dp(46)));
        action.setOnClickListener(v -> showPage(page.equals("chats") ? "contacts" : "settings"));
        root.addView(header);

        if (page.equals("chats")) {
            EditText search = new EditText(this);
            search.setSingleLine(true);
            search.setTextColor(white);
            search.setHintTextColor(muted);
            search.setHint("⌕   جست‌وجوی گفتگوها");
            search.setTextSize(14);
            search.setPadding(dp(16), 0, dp(16), 0);
            search.setBackground(shape(panel, 16));
            LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, dp(48));
            sp.setMargins(dp(18), dp(6), dp(18), dp(16));
            root.addView(search, sp);
        }

        String headingText;
        switch (page) {
            case "contacts": headingText = "مخاطبین"; break;
            case "calls": headingText = "تماس‌ها"; break;
            case "profile": headingText = "حساب کاربری"; break;
            case "settings": headingText = "تنظیمات"; break;
            default: headingText = "گفتگوهای شما";
        }

        TextView heading = text(headingText, 21, white);
        heading.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        heading.setPadding(dp(20), dp(8), dp(20), dp(12));
        root.addView(heading);
ScrollView scroll = new ScrollView(this);
        content = vertical();
        content.setPadding(dp(16), dp(4), dp(16), dp(18));
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        switch (page) {
            case "chats":
                addChat("دوست من", "به دنیای لنو خوش آمدی", "د", "۱۴:۳۰", true);
                addChat("گفتگوی شخصی", "گفتگوهای تو اینجا نمایش داده می‌شوند", "L", "دیروز", false);
                addChat("گروه دوستان", "فضایی برای گفتگوی گروهی", "گ", "دوشنبه", false);
                addChat("یادداشت‌های من", "پیام‌های ذخیره‌شده", "★", "", false);
                break;
            case "contacts":
                addChat("افزودن مخاطب", "جست‌وجو و افزودن مخاطب", "+", "", false);
                addChat("مخاطبین من", "پس از اتصال مجوز مخاطبین فعال می‌شود", "م", "", false);
                break;
            case "calls":
                addChat("تماس‌های اخیر", "تاریخچه تماس‌ها در این بخش قرار می‌گیرد", "☎", "", false);
                break;
            case "profile":
                TextView avatar = text("L", 36, white);
                avatar.setGravity(Gravity.CENTER);
                avatar.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                avatar.setBackground(gradient(50));
                LinearLayout.LayoutParams ap = new LinearLayout.LayoutParams(dp(92), dp(92));
                ap.gravity = Gravity.CENTER_HORIZONTAL;
                ap.setMargins(0, dp(12), 0, dp(12));
                content.addView(avatar, ap);
                TextView name = text("کاربر لنو", 20, white);
                name.setGravity(Gravity.CENTER);
                content.addView(name);
                TextView status = text("هنوز وارد حساب نشده‌اید", 13, muted);
                status.setGravity(Gravity.CENTER);
                content.addView(status);
                addChat("ورود یا ثبت‌نام", "ساخت حساب کاربری در مرحله بعد", "→", "", false);break;
            case "settings":
                addChat("اعلان‌ها", "مدیریت اعلان‌ها", "♧", "", false);
                addChat("حریم خصوصی", "تنظیمات امنیتی حساب", "◆", "", false);
                addChat("ظاهر برنامه", "طراحی تیره لنو", "◐", "", false);
                addChat("درباره لنو", "نسخه ۱.۰ آزمایشی", "L", "", false);
                break;
        }

        LinearLayout nav = new LinearLayout(this);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(dp(4), dp(6), dp(4), dp(6));
        nav.setBackground(shape(panel, 20));

        String[] names = {"گفتگو", "مخاطب", "تماس", "حساب", "تنظیمات"};
        String[] icons = {"▤", "♙", "☎", "◉", "⚙"};
        String[] pages = {"chats", "contacts", "calls", "profile", "settings"};

        LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(-1, dp(68));
        np.setMargins(dp(12), dp(4), dp(12), dp(10));

        for (int i = 0; i < pages.length; i++) {
            final String target = pages[i];
            LinearLayout item = vertical();
            item.setGravity(Gravity.CENTER);

            TextView icon = text(icons[i], 21, page.equals(target) ? purple : muted);
            icon.setGravity(Gravity.CENTER);
            item.addView(icon);

            TextView caption = text(names[i], 10, page.equals(target) ? white : muted);
            caption.setGravity(Gravity.CENTER);
            item.addView(caption);

            nav.addView(item, new LinearLayout.LayoutParams(0, -1, 1));
            item.setOnClickListener(v -> showPage(target));
        }

        root.addView(nav, np);
    }
void addChat(String name, String message, String avatar, String time, boolean online) {
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(12), dp(13), dp(12), dp(13));
        row.setBackground(shape(panel, 19));

        TextView icon = text(avatar, 22, white);
        icon.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        icon.setGravity(Gravity.CENTER);
        icon.setBackground(gradient(40));
        row.addView(icon, new LinearLayout.LayoutParams(dp(50), dp(50)));

        LinearLayout details = vertical();
        details.setPadding(dp(12), 0, dp(8), 0);

        TextView title = text(name, 15, white);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        details.addView(title);

        TextView preview = text(message, 12, muted);
        details.addView(preview);

        row.addView(details, new LinearLayout.LayoutParams(0, -2, 1));

        LinearLayout side = vertical();
        side.setGravity(Gravity.CENTER);
        if (!time.isEmpty()) {
            TextView clock = text(time, 10, online ? purple : muted);
            side.addView(clock);
        }
        if (online) {
            TextView dot = text("●", 9, Color.rgb(74, 222, 128));
            dot.setGravity(Gravity.CENTER);
            side.addView(dot);
        }
        row.addView(side);

        LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(-1, -2);
        rp.setMargins(0, 0, 0, dp(10));
        content.addView(row, rp);

        row.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(this)
                .setTitle(name)
                .setMessage("این بخش از رابط کاربری آماده است؛ ارسال پیام واقعی پس از اتصال سرور و سامانه پیام‌رسان فعال می‌شود.")
                .setPositiveButton("باشه", null)
                .show();
        });
    }
}
