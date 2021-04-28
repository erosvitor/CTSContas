package br.com.ctseducare.ctscontas.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.ctseducare.ctscontas.R;
import br.com.ctseducare.ctscontas.adapter.ListViewPaymentsPerCategoryAdapter;
import br.com.ctseducare.ctscontas.dao.AccountDao;
import br.com.ctseducare.ctscontas.dao.CategoryDao;
import br.com.ctseducare.ctscontas.dao.SubcategoryDao;
import br.com.ctseducare.ctscontas.dto.PaymentCategoryDTO;
import br.com.ctseducare.ctscontas.model.Account;
import br.com.ctseducare.ctscontas.model.Category;
import br.com.ctseducare.ctscontas.model.Subcategory;
import br.com.ctseducare.ctscontas.utils.CTSConstants;

public class MainActivity extends AppCompatActivity {

    private final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 500;

    private AccountDao accountDao = new AccountDao(this);
    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        Spinner fldMonth = findViewById(R.id.fldMonth);
        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.months));
        fldMonth.setAdapter(monthsAdapter);
        fldMonth.setSelection(currentMonth);
        fldMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                loadPaymentsByMonth();
                loadPaymentsByCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> years = new ArrayList<>();
        for (int year = 2015; year < 2030; year++) {
            years.add(String.valueOf(year));
        }
        Spinner fldYear = findViewById(R.id.fldYear);
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        fldYear.setAdapter(yearsAdapter);
        fldYear.setSelection(currentYear-2015);
        fldYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadPaymentsByMonth();
                loadPaymentsByCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loadPaymentsByMonth();
        loadPaymentsByCategory();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActivityNewAccount();
            }
        });

    }

    private void loadPaymentsByMonth() {
        Spinner fldMonth = findViewById(R.id.fldMonth);
        int month = (Integer)fldMonth.getSelectedItemPosition()+1;

        Spinner fldYear = findViewById(R.id.fldYear);
        int year = Integer.parseInt((String)fldYear.getSelectedItem());

        double paymentsByMonth = accountDao.getPaymentsByMonth(month, year);

        TextView lblPaymentsMonth = findViewById(R.id.lblTotalPaymentsByMonth);
        lblPaymentsMonth.setText(nf.format(paymentsByMonth));
    }

    private void loadPaymentsByCategory() {
        Spinner fldMonth = findViewById(R.id.fldMonth);
        int month = (Integer)fldMonth.getSelectedItemPosition()+1;

        Spinner fldYear = findViewById(R.id.fldYear);
        int year = Integer.parseInt((String)fldYear.getSelectedItem());

        List<PaymentCategoryDTO> pc = accountDao.getTotalPaymentByCategory(month, year);
        ListViewPaymentsPerCategoryAdapter adapter = new ListViewPaymentsPerCategoryAdapter(pc, this);
        ListView listViewPaymentsCategory = findViewById(R.id.listviewPaymentsCategory);
        listViewPaymentsCategory.setAdapter(adapter);
    }

    //----------------------------------------------------------------------------------------------------
    // MENU
    //----------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mmiListAccounts:
                showActivityListAccounts();
                return true;
            case R.id.mmiBackup:
                confirmBackup();
                return true;
            case R.id.mmiAbout:
                showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //----------------------------------------------------------------------------------------------------
    // NEW ACCOUNT
    //----------------------------------------------------------------------------------------------------
    private void showActivityNewAccount() {
        Intent intent = new Intent(MainActivity.this, AccountEditActivity.class);
        startActivityForResult(intent, CTSConstants.REQUEST_CODE_ACCOUNT_EDIT);
    }

    //----------------------------------------------------------------------------------------------------
    // LIST ACCOUNTS
    //----------------------------------------------------------------------------------------------------
    private void showActivityListAccounts() {
        Intent intent = new Intent(MainActivity.this, AccountListActivity.class);
        startActivityForResult(intent, CTSConstants.REQUEST_CODE_ACCOUNT_LIST_OPEN);
    }

    //----------------------------------------------------------------------------------------------------
    // ACTIVITY RESULT
    //----------------------------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( (requestCode == CTSConstants.REQUEST_CODE_ACCOUNT_EDIT && resultCode == CTSConstants.RESULT_CODE_ACCOUNT_EDIT_CLOSED) ||
             (requestCode == CTSConstants.REQUEST_CODE_ACCOUNT_LIST_OPEN && resultCode == CTSConstants.RESULT_CODE_ACCOUNT_LIST_CLOSED) ) {
            loadPaymentsByMonth();
            loadPaymentsByCategory();
        }
    }

    //----------------------------------------------------------------------------------------------------
    // CONFIRM BACKUP
    //----------------------------------------------------------------------------------------------------
    private void confirmBackup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.dialog_backup_title);
        builder.setMessage(R.string.dialog_backup_message);
        builder.setPositiveButton(R.string.button_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkPermissionToWriteExternalStorage();
                    }
                });
        builder.setNegativeButton(R.string.button_no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // CHECK PERMISSION
    private void checkPermissionToWriteExternalStorage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            // Permission has already been granted
            doBackup();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doBackup();
            } else {
                Toast toast = Toast.makeText(this, R.string.dialog_backup_result_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void doBackup() {
        File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!exportDir.exists()) {
            if (!exportDir.mkdirs()) {
                Toast toast = Toast.makeText(this, R.string.dialog_backup_createdir_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String sufix = sdf.format(new Date());

        PrintWriter pw = null;
        try {
            // Backup 'Accounts'
            File file = new File(exportDir, "accounts-" + sufix + ".csv");
            file.createNewFile();
            pw = new PrintWriter(new FileWriter(file));
            pw.println("code,type,code_category,code_subcategory,due_date,due_value,payment_date,payment_value,description");
            List<Account> accounts = new AccountDao(this).findAll();
            for (Account account : accounts) {
                pw.println(account.getCode() + "," +
                           account.getCategory() + "," +
                           account.getSubcategory() + "," +
                           account.getDueDate() + "," +
                           account.getDueValue() + "," +
                           account.getPaymentDate() + "," +
                           account.getPaymentValue() + "," +
                           account.getDescription()
                        );
            }
            pw.flush();

            // Backup 'Categories'
            file = new File(exportDir, "categories-" + sufix + ".csv");
            file.createNewFile();
            pw = new PrintWriter(new FileWriter(file));
            pw.println("code,description");
            List<Category> categories = new CategoryDao(this).findAll();
            for (Category category : categories) {
                pw.println(category.getCode() + "," +
                           category.getDescription()
                );
            }
            pw.flush();

            // Backup 'Subcategories'
            file = new File(exportDir, "subcategories-" + sufix + ".csv");
            file.createNewFile();
            pw = new PrintWriter(new FileWriter(file));
            pw.println("code,description");
            List<Subcategory> subcategories = new SubcategoryDao(this).findAll();
            for (Subcategory subcategory : subcategories) {
                pw.println(subcategory.getCode() + "," +
                           subcategory.getDescription()
                );
            }
            pw.flush();

        } catch(Exception e) {
            Toast toast = Toast.makeText(this, R.string.dialog_backup_result_error, Toast.LENGTH_SHORT);
            toast.show();
            return;
        } finally {
            if (pw != null) {
                pw.close();
            }
        }

        Toast toast = Toast.makeText(this, R.string.dialog_backup_result_ok, Toast.LENGTH_SHORT);
        toast.show();
    }

    //----------------------------------------------------------------------------------------------------
    // ABOUT
    //----------------------------------------------------------------------------------------------------
    private void showAbout() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

}
