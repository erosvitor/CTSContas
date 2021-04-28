package br.com.ctseducare.ctscontas.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.ctseducare.ctscontas.database.DatabaseUtils;
import br.com.ctseducare.ctscontas.model.Subcategory;

public class SubcategoryDao {

    private Context context;

    public SubcategoryDao(Context context) {
        this.context = context;
    }

    public List<Subcategory> findAll() {
        List<Subcategory> subcategories = new ArrayList<>();

        String[] columns = {"code", "description"};

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.query("subcategories", columns, null, null, null, null, "description", null);

        while (cursor.moveToNext()) {
            Subcategory subcategory = new Subcategory();
            subcategory.setCode(cursor.getInt(0));
            subcategory.setDescription(cursor.getString(1));
            subcategories.add(subcategory);
        }

        cursor.close();
        db.close();

        return subcategories;
    }

    public List<Subcategory> findByCategoryId(int categoryId) {
        List<Subcategory> subcategories = new ArrayList<>();

        String[] columns = {"code", "description"};

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.query("subcategories", columns, "code_category=?", new String[] {String.valueOf(categoryId)}, null, null, "description", null);

        while (cursor.moveToNext()) {
            Subcategory subcategory = new Subcategory();
            subcategory.setCode(cursor.getInt(0));
            subcategory.setDescription(cursor.getString(1));

            subcategories.add(subcategory);
        }

        cursor.close();
        db.close();

        return subcategories;
    }

    public Subcategory findById(int id) {
        String[] columns = {"description"};

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.query("subcategories", columns, "code = ?", new String[] {String.valueOf(id)}, null, null, null, null);

        Subcategory subcategory = null;
        if (cursor.moveToNext()) {
            subcategory = new Subcategory();
            subcategory.setCode(id);
            subcategory.setDescription(cursor.getString(0));
        }

        cursor.close();
        db.close();

        return subcategory;
    }

}
