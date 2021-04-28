package br.com.ctseducare.ctscontas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.ctseducare.ctscontas.database.DatabaseUtils;
import br.com.ctseducare.ctscontas.dto.PaymentCategoryDTO;
import br.com.ctseducare.ctscontas.model.Account;
import br.com.ctseducare.ctscontas.dto.AccountDTO;

public class AccountDao {

    private Context context;

    public AccountDao(Context context) {
        this.context = context;
    }

    public void insertOrUpdate(Account account) {
        ContentValues values = new ContentValues();
        values.put("type", account.getType());
        values.put("code_category", account.getCategory());
        values.put("code_subcategory", account.getSubcategory());
        values.put("due_date", account.getDueDate());
        values.put("due_value", account.getDueValue());
        values.put("payment_date", account.getPaymentDate());
        values.put("payment_value", account.getPaymentValue());
        values.put("description", account.getDescription());

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getWritableDatabase();
        try {
            if (account.getCode() == 0) {
                db.insert("accounts", null, values);
            } else {
                db.update("accounts", values, "code = ?", new String[] {String.valueOf(account.getCode())});
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AccountDTO> list() {
        List<AccountDTO> accounts = new ArrayList<>();

        String sql = "SELECT " +
                     "  a.code, " +
                     "  a.type, " +
                     "  c.description, " +
                     "  sc.description, " +
                     "  a.due_date, " +
                     "  a.due_value, " +
                     "  a.payment_date, " +
                     "  a.payment_value, " +
                     "  a.description " +
                    "FROM " +
                     "  accounts a " +
                     "INNER JOIN " +
                     "  categories c ON (c.code = a.code_category) " +
                     "INNER JOIN " +
                     "  subcategories sc ON (sc.code = a.code_subcategory) " +
                     "ORDER BY " +
                     "  a.payment_date DESC";

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            AccountDTO account = new AccountDTO();
            account.code = cursor.getInt(0);
            account.type = cursor.getString(1);
            account.category = cursor.getString(2);
            account.subcategory = cursor.getString(3);
            account.dueDate = convertDateToDateBrazil(cursor.getString(4));
            account.dueValue = cursor.getDouble(5);
            account.paymentDate = convertDateToDateBrazil(cursor.getString(6));
            account.paymentValue = cursor.getDouble(7);
            account.description = cursor.getString(8);

            accounts.add(account);
        }

        cursor.close();
        db.close();

        return accounts;
    }

    public AccountDTO listById(int id) {
        AccountDTO account = null;

        String sql = "SELECT " +
                "  a.code, " +
                "  a.type, " +
                "  c.description, " +
                "  sc.description, " +
                "  a.due_date, " +
                "  a.due_value, " +
                "  a.payment_date, " +
                "  a.payment_value, " +
                "  a.description " +
                "FROM " +
                "  accounts a " +
                "INNER JOIN " +
                "  categories c ON (c.code = a.code_category) " +
                "INNER JOIN " +
                "  subcategories sc ON (sc.code = a.code_subcategory) " +
                "WHERE a.code = ?";

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[] { String.valueOf(id)});

        if (cursor.moveToNext()) {
            account = new AccountDTO();
            account.code = cursor.getInt(0);
            account.type = cursor.getString(1);
            account.category = cursor.getString(2);
            account.subcategory = cursor.getString(3);
            account.dueDate = convertDateToDateBrazil(cursor.getString(4));
            account.dueValue = cursor.getDouble(5);
            account.paymentDate = convertDateToDateBrazil(cursor.getString(6));
            account.paymentValue = cursor.getDouble(7);
            account.description = cursor.getString(8);
        }

        cursor.close();
        db.close();

        return account;
    }


    public void deleteById(int id) {
        String sql = "DELETE FROM accounts WHERE code = " + id;
        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getWritableDatabase();
        db.execSQL(sql);
    }

    public Account findById(int id) {
        String[] columns = {"code", "type", "code_category", "code_subcategory", "due_date", "due_value", "payment_date", "payment_value", "description"};

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.query("accounts", columns, "code = ?", new String[] {String.valueOf(id)}, null, null, null);
        cursor.moveToNext();

        Account account = new Account();
        account.setCode(cursor.getInt(0));
        account.setType(cursor.getString(1));
        account.setCategory(cursor.getInt(2));
        account.setSubcategory(cursor.getInt(3));
        account.setDueDate(convertDateToDateBrazil(cursor.getString(4)));
        account.setDueValue(cursor.getDouble(5));
        account.setPaymentDate(convertDateToDateBrazil(cursor.getString(6)));
        account.setPaymentValue(cursor.getDouble(7));
        account.setDescription(cursor.getString(8));

        cursor.close();
        db.close();

        return account;
    }

    public List<Account> findAll() {
        String[] columns = {"code", "type", "code_category", "code_subcategory", "due_date", "due_value", "payment_date", "payment_value", "description"};

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.query("accounts", columns, null, null, null, null, null);

        List<Account> accounts = new ArrayList<>();
        while (cursor.moveToNext()) {
            Account account = new Account();
            account.setCode(cursor.getInt(0));
            account.setType(cursor.getString(1));
            account.setCategory(cursor.getInt(2));
            account.setSubcategory(cursor.getInt(3));
            account.setDueDate(convertDateToDateBrazil(cursor.getString(4)));
            account.setDueValue(cursor.getDouble(5));
            account.setPaymentDate(convertDateToDateBrazil(cursor.getString(6)));
            account.setPaymentValue(cursor.getDouble(7));
            account.setDescription(cursor.getString(8));
            accounts.add(account);
        }

        cursor.close();
        db.close();

        return accounts;
    }

    public double getPaymentsByMonth(int month, int year) {
        String sql = "SELECT SUM(payment_value) " +
                     "FROM accounts " +
                     "WHERE CAST(STRFTIME('%m', payment_date) AS INTEGER) = " + month +
                     " AND CAST(STRFTIME('%Y', payment_date) AS INTEGER) = " + year;

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        double totalPaymentMonth = cursor.getDouble(0);

        cursor.close();
        db.close();

        return totalPaymentMonth;
    }

    public List<PaymentCategoryDTO> getTotalPaymentByCategory(int month, int year) {
        String sql = "SELECT c.description, SUM(a.payment_value) " +
                "FROM categories c " +
                "LEFT JOIN accounts a ON (a.code_category = c.code) " +
                "  AND (CAST(STRFTIME('%m', a.payment_date) AS INTEGER) = " + month + ") " +
                "  AND (CAST(STRFTIME('%Y', a.payment_date) AS INTEGER) = " + year + ") " +
                "GROUP BY c.description";

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<PaymentCategoryDTO> payments = new ArrayList<>();
        while (cursor.moveToNext()) {
            PaymentCategoryDTO pc = new PaymentCategoryDTO();
            pc.category = cursor.getString(0);
            pc.value = cursor.getDouble(1);
            payments.add(pc);
        }

        cursor.close();
        db.close();

        return payments;
    }

    private String convertDateToDateBrazil(String date) {
        String result = "";
        if (!date.isEmpty()) {
            result = date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0, 4);
        }
        return result;
    }

}