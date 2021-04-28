package br.com.ctseducare.ctscontas.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.ctseducare.ctscontas.database.DatabaseUtils;
import br.com.ctseducare.ctscontas.model.Category;

public class CategoryDao {

    private Context context;

    public CategoryDao(Context context) {
        this.context = context;
    }

    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();

        String[] columns = {"code", "description"};

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.query("categories", columns, null, null, null, null, "description", null);

        while (cursor.moveToNext()) {
            Category category = new Category();
            category.setCode(cursor.getInt(0));
            category.setDescription(cursor.getString(1));
            categories.add(category);
        }

        cursor.close();
        db.close();

        return categories;
    }

    public Category findById(int id) {
        String[] columns = {"description"};

        SQLiteDatabase db = DatabaseUtils.getDatabase(context).getReadableDatabase();
        Cursor cursor = db.query("categories", columns, "code = ?", new String[] {String.valueOf(id)}, null, null, null, null);

        Category category = null;
        if (cursor.moveToNext()) {
            category = new Category();
            category.setCode(id);
            category.setDescription(cursor.getString(0));
        }

        cursor.close();
        db.close();

        return category;
    }


}
