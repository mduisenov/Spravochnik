package kz.test.spravochnik.helper;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.widget.EditText;
import android.widget.TextView;

import kz.test.spravochnik.R;
import kz.test.spravochnik.SpravochnikApp;

public final class UiHelper {
    private UiHelper() {
    }

    public static boolean isEmptyField(EditText editText, TextInputLayout textInputLayout) {
        String errorText = SpravochnikApp.get().getString(R.string.empty_field);
        boolean isEmpty = TextUtils.isEmpty(editText.getText().toString());
        if (isEmpty) {
            setEditTextError(errorText, editText, textInputLayout);
        }
        return isEmpty;
    }

    public static boolean isEmptyField(EditText editText) {
        return isEmptyField(editText, null);
    }

    public static void setEditTextError(String errorText, EditText editText) {
        setEditTextError(errorText, editText, null);
    }

    public static void setEditTextError(String errorText, EditText editText, TextInputLayout textInputLayout) {
        if (textInputLayout == null) {
            editText.setError(errorText);
            return;
        }
        textInputLayout.setError(errorText);
        textInputLayout.setErrorEnabled(true);
    }

    public static void markSearchTerm(TextView textView, String searchTerm) {
        if (!TextUtils.isEmpty(textView.getText()) && !TextUtils.isEmpty(searchTerm)) {
            String wholeText = textView.getText().toString();
            int indexOf = textView.getText().toString().toLowerCase().indexOf(searchTerm.toLowerCase());
            SpannableStringBuilder sb = new SpannableStringBuilder(wholeText);
            StyleSpan bss = new StyleSpan(1);
            if (indexOf >= 0 && indexOf <= wholeText.length()) {
                sb.setSpan(bss, indexOf, searchTerm.length() + indexOf, 18);
                textView.setText(sb);
            }
        }
    }

    public static void setTitle(FragmentActivity activity, int title) {
        activity.setTitle(title);
    }

    public static void setTitle(FragmentActivity activity, String title) {
        activity.setTitle(title);
    }

}
