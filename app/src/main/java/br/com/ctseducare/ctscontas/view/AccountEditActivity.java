package br.com.ctseducare.ctscontas.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import br.com.ctseducare.ctscontas.R;
import br.com.ctseducare.ctscontas.adapter.SpinnerCategoryAdapter;
import br.com.ctseducare.ctscontas.adapter.SpinnerSubcategoryAdapter;
import br.com.ctseducare.ctscontas.controller.AccountController;
import br.com.ctseducare.ctscontas.dao.AccountDao;
import br.com.ctseducare.ctscontas.dao.CategoryDao;
import br.com.ctseducare.ctscontas.dao.SubcategoryDao;
import br.com.ctseducare.ctscontas.enumerator.AccountTypeEnum;
import br.com.ctseducare.ctscontas.model.Account;
import br.com.ctseducare.ctscontas.model.Category;
import br.com.ctseducare.ctscontas.model.Subcategory;
import br.com.ctseducare.ctscontas.utils.CTSConstants;

public class AccountEditActivity extends AppCompatActivity {

    private int accountCodeForEdition;

    private RadioButton fldTypeFix;
    private RadioButton fldTypeVariable;
    private Spinner fldCategory;
    private Spinner fldSubcategory;
    private TextView lblDueDate;
    private EditText fldDueDate;
    private TextView lblDueValue;
    private EditText fldDueValue;
    private EditText fldPaymentDate;
    private EditText fldPaymentValue;
    private EditText fldDescription;

    private AccountController accountController;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountCodeForEdition = getIntent().getIntExtra("accountCodeForEdition", -1);

        if (accountCodeForEdition == -1) {
            setTitle(R.string.title_screen_account_new);
        } else {
            setTitle(R.string.title_screen_account_edit);
        }

        setContentView(R.layout.activity_account_edit);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        this.fldTypeFix = findViewById(R.id.fldAccountEditTypeFix);
        this.fldTypeVariable = findViewById(R.id.fldAccountEditTypeVariable);
        this.fldCategory = findViewById(R.id.fldAccountEditCategory);
        this.fldSubcategory = findViewById(R.id.fldAccountEditSubcategory);
        this.lblDueDate = findViewById(R.id.lblAccountEditDueDate);
        this.fldDueDate = findViewById(R.id.fldAccountEditDueDate);
        this.lblDueValue = findViewById(R.id.lblAccountEditDueValue);
        this.fldDueValue = findViewById(R.id.fldAccountEditDueValue);
        this.fldPaymentDate = findViewById(R.id.fldAccountEditPaymentDate);
        this.fldPaymentValue = findViewById(R.id.fldAccountEditPaymentValue);
        this.fldDescription = findViewById(R.id.fldAccountEditDescription);

        this.fldTypeFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblDueDate.setVisibility(View.VISIBLE);
                fldDueDate.setVisibility(View.VISIBLE);
                lblDueValue.setVisibility(View.VISIBLE);
                fldDueValue.setVisibility(View.VISIBLE);
            }
        });
        this.fldTypeVariable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblDueDate.setVisibility(View.GONE);
                fldDueDate.setVisibility(View.GONE);
                lblDueValue.setVisibility(View.GONE);
                fldDueValue.setVisibility(View.GONE);
            }
        });

        CategoryDao categoryDao = new CategoryDao(this);
        List<Category> categories = categoryDao.findAll();
        categories.add(0, new Category(0, getString(R.string.item_select)));
        SpinnerCategoryAdapter categoryAdapter = new SpinnerCategoryAdapter(categories, this);
        this.fldCategory.setAdapter(categoryAdapter);
        this.fldCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                if (itemPosition > 0) {
                    Category category = (Category)adapterView.getSelectedItem();
                    loadSubcategoriesByCategoryId(category.getCode());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.fldDueDate.setClickable(true);
        this.fldDueDate.setFocusable(false);
        this.fldDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(view.getContext(), dueDateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        this.fldPaymentDate.setClickable(true);
        this.fldPaymentDate.setFocusable(false);
        this.fldPaymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(view.getContext(), paymentDateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button btnSave = findViewById(R.id.btnAccountEditSaveAccount);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmSaveAccount();
            }
        });

        this.accountController = new AccountController(this);

        this.fldCategory.setFocusable(true);
        this.fldCategory.setFocusableInTouchMode(true);
        this.fldCategory.requestFocus();

        if (accountCodeForEdition > -1) {
            Account account = new AccountDao(this).findById(accountCodeForEdition);
            if (account.getType().equals(AccountTypeEnum.FIX.getDescription())) {
                this.fldTypeFix.performClick();
            } else {
                this.fldTypeVariable.performClick();
            }

            Category category = categoryDao.findById(account.getCategory());
            int positionCategory = categoryAdapter.getPosition(category);
            this.fldCategory.setSelection(positionCategory);

            // TIP: The subcategory will be selected on item selected event from categories spinner

            this.fldDueDate.setText(account.getDueDate());
            this.fldDueValue.setText(String.valueOf(account.getDueValue()));
            this.fldPaymentDate.setText(account.getPaymentDate());
            this.fldPaymentValue.setText(String.valueOf(account.getPaymentValue()));
            this.fldDescription.setText(account.getDescription());
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(CTSConstants.RESULT_CODE_ACCOUNT_EDIT_CLOSED);
            this.finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void loadSubcategoriesByCategoryId(int categoryId) {
        SubcategoryDao subcategoryDao = new SubcategoryDao(this);
        List<Subcategory> subcategories = subcategoryDao.findByCategoryId(categoryId);
        SpinnerSubcategoryAdapter subcategoryAdapter = new SpinnerSubcategoryAdapter(subcategories, this);
        if (subcategories.size() > 0) {
            subcategories.add(0, new Subcategory(0, 0, getString(R.string.item_select)));
        }
        this.fldSubcategory.setAdapter(subcategoryAdapter);

        if (accountCodeForEdition > -1) {
            Account account = new AccountDao(this).findById(accountCodeForEdition);
            Subcategory subcategory = subcategoryDao.findById(account.getSubcategory());
            int position = subcategoryAdapter.getPosition(subcategory);
            this.fldSubcategory.setSelection(position);
        }
    }

    private void confirmSaveAccount() {
        if (!this.fldTypeFix.isChecked() && !this.fldTypeVariable.isChecked()) {
            Toast toast = Toast.makeText(this, R.string.field_required_account_type, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (this.fldCategory.getSelectedItemPosition() == 0) {
            Toast toast = Toast.makeText(this, R.string.field_required_account_category, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (this.fldSubcategory.getSelectedItemPosition() == 0) {
            Toast toast = Toast.makeText(this, R.string.field_required_account_subcategory, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (this.fldTypeFix.isChecked() && this.fldDueDate.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, R.string.field_required_account_duedate, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (this.fldTypeFix.isChecked() && this.fldDueValue.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, R.string.field_required_account_duevalue, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (this.fldTypeVariable.isChecked() && this.fldPaymentDate.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, R.string.field_required_account_paymentdate, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (this.fldTypeVariable.isChecked() && this.fldPaymentValue.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, R.string.field_required_account_paymentvalue, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.dialog_save_account_title);
        builder.setMessage(R.string.dialog_save_account_message);
        builder.setPositiveButton(R.string.button_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveAccount();
                        clearFields();
                        fldCategory.requestFocus();
                    }
                });
        builder.setNegativeButton(R.string.button_no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearFields();
                    }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveAccount() {
        Account account = new Account();

        if (accountCodeForEdition == -1) {
            account.setCode(0);
        } else {
            account.setCode(accountCodeForEdition);
        }

        account.setType(this.fldTypeFix.isChecked() ? AccountTypeEnum.FIX.getDescription() : AccountTypeEnum.VARIABLE.getDescription());
        account.setCategory(((Category)this.fldCategory.getSelectedItem()).getCode());
        account.setSubcategory(((Subcategory)this.fldSubcategory.getSelectedItem()).getCode());

        if (this.fldTypeFix.isChecked()) {
            String dateBrazil = this.fldDueDate.getText().toString();
            String date = dateBrazil.substring(6, 10) + "-" + dateBrazil.substring(3,5) + "-" + dateBrazil.substring(0,2);
            account.setDueDate(date);
            account.setDueValue(Double.parseDouble(this.fldDueValue.getText().toString()));
        } else {
            account.setDueDate("");
            account.setDueValue(0.0);
        }

        if (this.fldPaymentDate.getText().toString().isEmpty()) {
            account.setPaymentDate("");
            account.setPaymentValue(0.0);
        } else {
            String dateBrazil = this.fldPaymentDate.getText().toString();
            String date = dateBrazil.substring(6, 10) + "-" + dateBrazil.substring(3,5) + "-" + dateBrazil.substring(0,2);
            account.setPaymentDate(date);
            account.setPaymentValue(Double.parseDouble(this.fldPaymentValue.getText().toString()));
        }

        account.setDescription(this.fldDescription.getText().toString());

        accountController.insertOrUpdate(account);

        if (accountCodeForEdition > -1) {
            Intent intent = new Intent();
            intent.putExtra("accountCodeEdited", accountCodeForEdition);
            setResult(CTSConstants.RESULT_CODE_ACCOUNT_EDIT_OK, intent);
            finish();
        }
    }

    private void clearFields() {
        this.fldCategory.setSelection(0);
        this.fldSubcategory.setSelection(0);
        this.fldDueDate.setText("");
        this.fldDueValue.setText("");
        this.fldPaymentDate.setText("");
        this.fldPaymentValue.setText("");
        this.fldDescription.setText("");
    }

    private DatePickerDialog.OnDateSetListener dueDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int _year, int _month, int _day) {
            String year = String.valueOf(_year);
            String month = _month++ < 10 ? ("0" + _month) : String.valueOf(_month);
            String day = _day < 10 ? ("0" + _day) : String.valueOf(_day);

            String date = day + "/" + (month) + "/" + year;
            fldDueDate.setText(date);
        }
    };

    private DatePickerDialog.OnDateSetListener paymentDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int _year, int _month, int _day) {
            String year = String.valueOf(_year);
            String month = _month++ < 10 ? ("0" + _month) : String.valueOf(_month);
            String day = _day < 10 ? ("0" + _day) : String.valueOf(_day);

            String date = day + "/" + (month) + "/" + year;
            fldPaymentDate.setText(date);
        }
    };

}
