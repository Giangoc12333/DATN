//package com.example.RealFilm.activity;
//
//import android.app.DatePickerDialog;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.RealFilm.R;
//import com.example.RealFilm.model.ApiResponse;
//import com.example.RealFilm.model.Status;
//import com.example.RealFilm.model.User;
//import com.example.RealFilm.service.ApiService;
//import com.example.RealFilm.service.UserService;
//import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class RegisterActivity extends AppCompatActivity {
//
//    private TextView Tv_login;
//    private Button Btn_register;
//    private EditText Edt_email, Edt_password, Edt_confirm_password, Edt_register_user_birthday, Edt_register_user_name;
//    private TextInputLayout tiplayout_email, tiplayout_pass, tiplayout_confirm_pass, tiplayout_register_user_name, tiplayout_register_user_birthday;
//    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;
//    private ProgressDialog progressDialog;
//    private String userId;
//    private CheckBox Cb_register_confirm;
//
//    final Calendar myCalendar = Calendar.getInstance();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        initUi();
//        pickDate();
//        initListener();
//    }
//
//
//    private void initUi() {
//        Tv_login = findViewById(R.id.tv_login);
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        Edt_email = findViewById(R.id.edittext_register_email);
//        Edt_password = findViewById(R.id.edittext_register_password);
//        Edt_confirm_password = findViewById(R.id.edittext_register_confirm_password);
//        Edt_register_user_birthday = findViewById(R.id.edittext_register_user_birthday);
//        Edt_register_user_name = findViewById(R.id.edittext_register_user_name);
//
//        Cb_register_confirm = findViewById(R.id.cb_register_confirm);
//
//        Btn_register = findViewById(R.id.btn_register);
//
//        tiplayout_email = findViewById(R.id.tiplayout_register_email);
//        tiplayout_pass = findViewById(R.id.tiplayout_register_password);
//        tiplayout_confirm_pass = findViewById(R.id.tiplayout_register_confirm_password);
//        tiplayout_register_user_name = findViewById(R.id.tiplayout_register_user_name);
//        tiplayout_register_user_birthday = findViewById(R.id.tiplayout_register_user_birthday);
//
//        progressDialog = new ProgressDialog(this);
//    }
//
//    private void initListener() {
//        Tv_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i1 = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(i1);
//            }
//        });
//
//        btnRegisterOnClick();
//    }
//
//    public void btnRegisterOnClick() {
//        Btn_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (TextUtils.isEmpty(Edt_register_user_name.getText().toString())) {
//                    tiplayout_register_user_name.setError(getString(R.string.error_not_be_empty));
//                } else {
//                    tiplayout_register_user_name.setErrorEnabled(false);
//                }
//                if (TextUtils.isEmpty(Edt_register_user_birthday.getText().toString())) {
//                    tiplayout_register_user_birthday.setError(getString(R.string.error_not_be_empty));
//                } else {
//                    tiplayout_register_user_birthday.setErrorEnabled(false);
//                }
//                if (TextUtils.isEmpty(Edt_email.getText().toString())) {
//                    tiplayout_email.setError(getString(R.string.error_not_be_empty));
//                } else {
//                    tiplayout_email.setErrorEnabled(false);
//                }
//                if (TextUtils.isEmpty(Edt_password.getText().toString())) {
//                    tiplayout_pass.setError(getString(R.string.error_not_be_empty));
//                } else {
//                    tiplayout_pass.setErrorEnabled(false);
//                }
//                if (TextUtils.isEmpty(Edt_confirm_password.getText().toString())) {
//                    tiplayout_confirm_pass.setError(getString(R.string.error_not_be_empty));
//                } else {
//                    tiplayout_confirm_pass.setErrorEnabled(false);
//                }
//                if (!Cb_register_confirm.isChecked()) {
//                    Toast.makeText(RegisterActivity.this, R.string.error_agree, Toast.LENGTH_LONG).show();
//                }
//
//                String email = Edt_email.getText().toString().trim();
//
//                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//                if (!email.matches(emailPattern)) {
//                    tiplayout_email.setError(getString(R.string.error_email_1));
//                }
//
//                if (Edt_password.getText().toString().trim().length() >= 6) {
//                    if (!Edt_email.getText().toString().trim().equalsIgnoreCase("")
//                            && !Edt_password.getText().toString().trim().equalsIgnoreCase("")
//                            && !Edt_register_user_name.getText().toString().trim().equalsIgnoreCase("")
//                            && !Edt_register_user_birthday.getText().toString().trim().equalsIgnoreCase("")
//                            && !Edt_confirm_password.getText().toString().trim().equalsIgnoreCase("")
//                            && email.matches(emailPattern)
//                            && Cb_register_confirm.isChecked()) {
//                        if (Edt_password.getText().toString().trim().equals(Edt_confirm_password.getText().toString().trim())) {
//                            createAccount();
//                        } else {
//                            tiplayout_confirm_pass.setError(getString(R.string.error_password_1));
//                        }
//                    }
//                } else {
//                    tiplayout_pass.setError(getString(R.string.error_password_more_chr));
//                }
//            }
//        });
//    }
//
//
//    private void createAccount() {
//
//        final String strEmail = Edt_email.getText().toString().trim();
//        String strPass = Edt_password.getText().toString().trim();
//        final String strName = Edt_register_user_name.getText().toString().trim();
//        final String strBirthday = Edt_register_user_birthday.getText().toString().trim();
//
//        progressDialog.setMessage(getString(R.string.progressDialog_register_loading));
//        progressDialog.show();
//
//        UserService userService = ApiService.createService(UserService.class);
//        Call<ApiResponse> call = userService.signup(strEmail, strPass, strEmail, strBirthday);
//
//
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                progressDialog.dismiss();
//                if (response.isSuccessful()) {
//                    ApiResponse<User> res = response.body();
//                    if (res.getStatus() == Status.SUCCESS) {
//                        Toast.makeText(getApplication(), res.getMessage(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        finishAffinity();
//                    } else {
//                        Toast.makeText(getApplication(), res.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//
//    public void pickDate() {
//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, day);
//                updateLabel();
//            }
//        };
//        Edt_register_user_birthday.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(RegisterActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//    }
//
//    private void updateLabel() {
//        String myFormat = "dd/MM/yyyy";
//        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//        Edt_register_user_birthday.setText(dateFormat.format(myCalendar.getTime()));
//    }
//
//}
package com.example.RealFilm.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.RealFilm.R;
import com.example.RealFilm.model.ApiResponse;
import com.example.RealFilm.model.Status;
import com.example.RealFilm.model.User;
import com.example.RealFilm.service.ApiService;
import com.example.RealFilm.service.UserService;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView Tv_login;
    private Button Btn_register;
    private EditText Edt_email, Edt_password, Edt_confirm_password, Edt_register_user_birthday, Edt_register_user_name;
    private TextInputLayout tiplayout_email, tiplayout_pass, tiplayout_confirm_pass, tiplayout_register_user_name, tiplayout_register_user_birthday;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private CheckBox Cb_register_confirm;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUi();
        pickDate();
        initListener();
    }

    private void initUi() {
        Tv_login = findViewById(R.id.tv_login);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Edt_email = findViewById(R.id.edittext_register_email);
        Edt_password = findViewById(R.id.edittext_register_password);
        Edt_confirm_password = findViewById(R.id.edittext_register_confirm_password);
        Edt_register_user_birthday = findViewById(R.id.edittext_register_user_birthday);
        Edt_register_user_name = findViewById(R.id.edittext_register_user_name);

        Cb_register_confirm = findViewById(R.id.cb_register_confirm);

        Btn_register = findViewById(R.id.btn_register);

        tiplayout_email = findViewById(R.id.tiplayout_register_email);
        tiplayout_pass = findViewById(R.id.tiplayout_register_password);
        tiplayout_confirm_pass = findViewById(R.id.tiplayout_register_confirm_password);
        tiplayout_register_user_name = findViewById(R.id.tiplayout_register_user_name);
        tiplayout_register_user_birthday = findViewById(R.id.tiplayout_register_user_birthday);

        progressDialog = new ProgressDialog(this);
    }

    private void initListener() {
        Tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i1);
            }
        });
        // Thêm lắng nghe sự kiện cho các trường EditText
        Edt_register_user_name.addTextChangedListener(new MyTextWatcher(Edt_register_user_name));
        Edt_register_user_birthday.addTextChangedListener(new MyTextWatcher(Edt_register_user_birthday));
        Edt_email.addTextChangedListener(new MyTextWatcher(Edt_email));
        Edt_password.addTextChangedListener(new MyTextWatcher(Edt_password));
        Edt_confirm_password.addTextChangedListener(new MyTextWatcher(Edt_confirm_password));

        // Thêm lắng nghe sự kiện khi trường EditText mất focus
        Edt_register_user_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validateName();
                }
            }
        });
        Edt_register_user_birthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validateBirthday();
                }
            }
        });
        Edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validateEmail();
                }
            }
        });
        Edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validatePassword();
                }
            }
        });
        Edt_confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validateConfirmPassword();
                }
            }
        });



        btnRegisterOnClick();
    }

    // Kiểm tra dữ liệu trên các trường và hiển thị thông báo lỗi khi cần
    private void validateName() {
        if (TextUtils.isEmpty(Edt_register_user_name.getText().toString())) {
            tiplayout_register_user_name.setError(getString(R.string.error_not_be_empty));
        } else {
            tiplayout_register_user_name.setErrorEnabled(false);
        }
    }

    private void validateBirthday() {
        if (TextUtils.isEmpty(Edt_register_user_birthday.getText().toString())) {
            tiplayout_register_user_birthday.setError(getString(R.string.error_not_be_empty));
        } else {
            tiplayout_register_user_birthday.setErrorEnabled(false);
        }
    }

    private void validateEmail() {
        String email = Edt_email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (TextUtils.isEmpty(email)) {
            tiplayout_email.setError(getString(R.string.error_not_be_empty));
        } else if (!email.matches(emailPattern)) {
            tiplayout_email.setError(getString(R.string.error_email_1));
        } else {
            tiplayout_email.setErrorEnabled(false);
        }
    }

    private void validatePassword() {
        if (TextUtils.isEmpty(Edt_password.getText().toString())) {
            tiplayout_pass.setError(getString(R.string.error_not_be_empty));
        } else {
            tiplayout_pass.setErrorEnabled(false);
        }
    }

    private void validateConfirmPassword() {
        if (TextUtils.isEmpty(Edt_confirm_password.getText().toString())) {
            tiplayout_confirm_pass.setError(getString(R.string.error_not_be_empty));
        } else {
            tiplayout_confirm_pass.setErrorEnabled(false);
        }
    }

    // Lớp lắng nghe sự kiện để xử lý việc kiểm tra dữ liệu trên các trường
    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edittext_register_user_name:
                    validateName();
                    break;
                case R.id.edittext_register_user_birthday:
                    validateBirthday();
                    break;
                case R.id.edittext_register_email:
                    validateEmail();
                    break;
                case R.id.edittext_register_password:
                    validatePassword();
                    break;
                case R.id.edittext_register_confirm_password:
                    validateConfirmPassword();
                    break;
            }
        }
    }

    public void btnRegisterOnClick() {
        Btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(Edt_register_user_name.getText().toString())) {
                    tiplayout_register_user_name.setError(getString(R.string.error_not_be_empty));
                } else {
                    tiplayout_register_user_name.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(Edt_register_user_birthday.getText().toString())) {
                    tiplayout_register_user_birthday.setError(getString(R.string.error_not_be_empty));
                } else {
                    tiplayout_register_user_birthday.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(Edt_email.getText().toString())) {
                    tiplayout_email.setError(getString(R.string.error_not_be_empty));
                } else {
                    tiplayout_email.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(Edt_password.getText().toString())) {
                    tiplayout_pass.setError(getString(R.string.error_not_be_empty));
                } else {
                    tiplayout_pass.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(Edt_confirm_password.getText().toString())) {
                    tiplayout_confirm_pass.setError(getString(R.string.error_not_be_empty));
                } else {
                    tiplayout_confirm_pass.setErrorEnabled(false);
                }
                if (!Cb_register_confirm.isChecked()) {
                    Toast.makeText(RegisterActivity.this, R.string.error_agree, Toast.LENGTH_LONG).show();
                }

                String email = Edt_email.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (!email.matches(emailPattern)) {
                    tiplayout_email.setError(getString(R.string.error_email_1));
                }

                if (Edt_password.getText().toString().trim().length() >= 6) {
                    if (!Edt_email.getText().toString().trim().equalsIgnoreCase("")
                            && !Edt_password.getText().toString().trim().equalsIgnoreCase("")
                            && !Edt_register_user_name.getText().toString().trim().equalsIgnoreCase("")
                            && !Edt_register_user_birthday.getText().toString().trim().equalsIgnoreCase("")
                            && !Edt_confirm_password.getText().toString().trim().equalsIgnoreCase("")
                            && email.matches(emailPattern)
                            && Cb_register_confirm.isChecked()) {
                        if (Edt_password.getText().toString().trim().equals(Edt_confirm_password.getText().toString().trim())) {
                            if (isUserSixteenOrOlder()) {
                                createAccount();
                            } else {
                                tiplayout_register_user_birthday.setError(getString(R.string.error_age_requirement));
                            }
                        } else {
                            tiplayout_confirm_pass.setError(getString(R.string.error_password_1));
                        }
                    }
                } else {
                    tiplayout_pass.setError(getString(R.string.error_password_more_chr));
                }
            }
        });
    }

    private boolean isUserSixteenOrOlder() {
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < myCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age >= 16;
    }

    private void createAccount() {
        final String strEmail = Edt_email.getText().toString().trim();
        String strPass = Edt_password.getText().toString().trim();
        final String strName = Edt_register_user_name.getText().toString().trim();
        final String strBirthday = Edt_register_user_birthday.getText().toString().trim();

        progressDialog.setMessage(getString(R.string.progressDialog_register_loading));
        progressDialog.show();

        UserService userService = ApiService.createService(UserService.class);
        Call<ApiResponse> call = userService.signup(strEmail, strPass, strEmail, strBirthday);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    ApiResponse<User> res = response.body();
                    if (res.getStatus() == Status.SUCCESS) {
                        Toast.makeText(getApplication(), res.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        Toast.makeText(getApplication(), res.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplication(), getString(R.string.error_registration), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pickDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        Edt_register_user_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        Edt_register_user_birthday.setText(dateFormat.format(myCalendar.getTime()));
    }

}
