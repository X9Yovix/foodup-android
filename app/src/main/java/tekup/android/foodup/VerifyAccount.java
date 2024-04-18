package tekup.android.foodup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class VerifyAccount extends AppCompatActivity {
    private EditText editTextCode1;
    private EditText editTextCode2;
    private EditText editTextCode3;
    private EditText editTextCode4;
    private EditText editTextCode5;
    private Button buttonValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);
        editTextCode1 = (EditText) findViewById(R.id.editTextCode1);
        editTextCode2 = (EditText) findViewById(R.id.editTextCode2);
        editTextCode3 = (EditText) findViewById(R.id.editTextCode3);
        editTextCode4 = (EditText) findViewById(R.id.editTextCode4);
        editTextCode5 = (EditText) findViewById(R.id.editTextCode5);

        buttonValidate = (Button) findViewById(R.id.buttonValidate);

        editTextCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    editTextCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    editTextCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    editTextCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    editTextCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextCode1.setOnLongClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                ClipData.Item item = clipData.getItemAt(0);
                if (item != null && item.getText() != null) {
                    String clipboardText = item.getText().toString().trim();
                    if (isValidCode(clipboardText)) {
                        String[] codeParts = clipboardText.split("(?!^)");
                        //if (codeParts.length == 5) {
                        editTextCode1.setText(codeParts[0]);
                        editTextCode2.setText(codeParts[1]);
                        editTextCode3.setText(codeParts[2]);
                        editTextCode4.setText(codeParts[3]);
                        editTextCode5.setText(codeParts[4]);
                        //}
                    }
                }
            }
            return true;
        });


        buttonValidate.setOnClickListener(v -> {

            String otpCode = editTextCode1.getText().toString()
                    + editTextCode2.getText().toString()
                    + editTextCode3.getText().toString()
                    + editTextCode4.getText().toString()
                    + editTextCode5.getText().toString();

            editTextCode1.setEnabled(false);
            editTextCode2.setEnabled(false);
            editTextCode3.setEnabled(false);
            editTextCode4.setEnabled(false);
            editTextCode5.setEnabled(false);
            Toast.makeText(this, otpCode, Toast.LENGTH_SHORT).show();

        });
    }
    private boolean isValidCode(String code) {
        return code.matches("[a-zA-Z0-9]{5}");
    }
}