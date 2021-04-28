package br.com.ctseducare.ctscontas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseStructure extends SQLiteOpenHelper {

    DatabaseStructure(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        sql = "CREATE TABLE categories (" +
              "  code INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "  description TEXT " +
              ")";
        db.execSQL(sql);

        sql = "CREATE TABLE subcategories (" +
                "  code INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  code_category INTEGER, " +
                "  description TEXT, " +
                "  FOREIGN KEY(code_category) REFERENCES categories(code) " +
                ")";
        db.execSQL(sql);

        db.execSQL("INSERT INTO categories VALUES (1, 'Habitação')");
        db.execSQL("INSERT INTO subcategories VALUES (1, 1, 'IPTU')");
        db.execSQL("INSERT INTO subcategories VALUES (2, 1, 'Prestação')");
        db.execSQL("INSERT INTO subcategories VALUES (3, 1, 'Aluguel')");
        db.execSQL("INSERT INTO subcategories VALUES (4, 1, 'Condomínio')");
        db.execSQL("INSERT INTO subcategories VALUES (5, 1, 'Seguro')");
        db.execSQL("INSERT INTO subcategories VALUES (6, 1, 'Gás')");
        db.execSQL("INSERT INTO subcategories VALUES (7, 1, 'Luz')");
        db.execSQL("INSERT INTO subcategories VALUES (8, 1, 'Água')");
        db.execSQL("INSERT INTO subcategories VALUES (9, 1, 'Extras')");

        db.execSQL("INSERT INTO categories VALUES (2, 'Telefone')");
        db.execSQL("INSERT INTO subcategories VALUES (10, 2, 'Fixo')");
        db.execSQL("INSERT INTO subcategories VALUES (11, 2, 'Celular')");

        db.execSQL("INSERT INTO categories VALUES (3, 'Alimentação')");
        db.execSQL("INSERT INTO subcategories VALUES (12, 3, 'Mercado')");
        db.execSQL("INSERT INTO subcategories VALUES (13, 3, 'Padaria')");
        db.execSQL("INSERT INTO subcategories VALUES (14, 3, 'Açougue')");
        db.execSQL("INSERT INTO subcategories VALUES (15, 3, 'Lanches')");
        db.execSQL("INSERT INTO subcategories VALUES (16, 3, 'Marmitex')");
        db.execSQL("INSERT INTO subcategories VALUES (17, 3, 'Extras')");

        db.execSQL("INSERT INTO categories VALUES (4, 'Saúde')");
        db.execSQL("INSERT INTO subcategories VALUES (18, 4, 'Plano de saúde')");
        db.execSQL("INSERT INTO subcategories VALUES (19, 4, 'Plano dentário')");
        db.execSQL("INSERT INTO subcategories VALUES (20, 4, 'Medicamentos')");
        db.execSQL("INSERT INTO subcategories VALUES (21, 4, 'Extras')");

        db.execSQL("INSERT INTO categories VALUES (5, 'Educação')");
        db.execSQL("INSERT INTO subcategories VALUES (22, 5, 'Mensalidade')");
        db.execSQL("INSERT INTO subcategories VALUES (23, 5, 'Transporte')");
        db.execSQL("INSERT INTO subcategories VALUES (24, 5, 'Lanche')");
        db.execSQL("INSERT INTO subcategories VALUES (25, 5, 'Curso')");
        db.execSQL("INSERT INTO subcategories VALUES (26, 5, 'Extras')");

        db.execSQL("INSERT INTO categories VALUES (6, 'Tarifas Bancárias')");
        db.execSQL("INSERT INTO subcategories VALUES (27, 6, 'Manutenção C/C')");
        db.execSQL("INSERT INTO subcategories VALUES (28, 6, 'Juros e IOF')");
        db.execSQL("INSERT INTO subcategories VALUES (29, 6, 'DOC e TED')");

        db.execSQL("INSERT INTO categories VALUES (7, 'Veículo')");
        db.execSQL("INSERT INTO subcategories VALUES (30, 7, 'Financiamento')");
        db.execSQL("INSERT INTO subcategories VALUES (31, 7, 'Seguro')");
        db.execSQL("INSERT INTO subcategories VALUES (32, 7, 'Combustível')");
        db.execSQL("INSERT INTO subcategories VALUES (33, 7, 'Lavagem')");
        db.execSQL("INSERT INTO subcategories VALUES (34, 7, 'Manutenção')");
        db.execSQL("INSERT INTO subcategories VALUES (35, 7, 'Estacionamento')");
        db.execSQL("INSERT INTO subcategories VALUES (36, 7, 'Extras')");

        db.execSQL("INSERT INTO categories VALUES (8, 'Entretenimento')");
        db.execSQL("INSERT INTO subcategories VALUES (37, 8, 'Internet')");
        db.execSQL("INSERT INTO subcategories VALUES (38, 8, 'TV a cabo')");
        db.execSQL("INSERT INTO subcategories VALUES (39, 8, 'Cinema')");
        db.execSQL("INSERT INTO subcategories VALUES (40, 8, 'Teatro')");
        db.execSQL("INSERT INTO subcategories VALUES (41, 8, 'Extras')");

        db.execSQL("INSERT INTO categories VALUES (9, 'Transporte')");
        db.execSQL("INSERT INTO subcategories VALUES (42, 9, 'Uber')");
        db.execSQL("INSERT INTO subcategories VALUES (43, 9, 'Táxi')");
        db.execSQL("INSERT INTO subcategories VALUES (44, 9, 'Ônibus')");

        db.execSQL("INSERT INTO categories VALUES (10, 'PET')");
        db.execSQL("INSERT INTO subcategories VALUES (45, 10, 'Areia')");
        db.execSQL("INSERT INTO subcategories VALUES (46, 10, 'Comida')");
        db.execSQL("INSERT INTO subcategories VALUES (47, 10, 'Extras')");

        db.execSQL("INSERT INTO categories VALUES (11, 'Aposentadoria')");
        db.execSQL("INSERT INTO subcategories VALUES (48, 11, 'INSS')");
        db.execSQL("INSERT INTO subcategories VALUES (49, 11, 'Privada')");

        db.execSQL("INSERT INTO categories VALUES (12, 'Mesada')");
        db.execSQL("INSERT INTO subcategories VALUES (50, 12, 'Marido')");
        db.execSQL("INSERT INTO subcategories VALUES (51, 12, 'Esposa')");
        db.execSQL("INSERT INTO subcategories VALUES (52, 12, 'Filhos')");

        db.execSQL("INSERT INTO categories VALUES (13, 'Vestuário')");
        db.execSQL("INSERT INTO subcategories VALUES (53, 13, 'Roupa')");
        db.execSQL("INSERT INTO subcategories VALUES (54, 13, 'Calçado')");
        db.execSQL("INSERT INTO subcategories VALUES (55, 13, 'Extras')");

        db.execSQL("INSERT INTO categories VALUES (14, 'Salão')");
        db.execSQL("INSERT INTO subcategories VALUES (56, 14, 'Cabeleireiro')");
        db.execSQL("INSERT INTO subcategories VALUES (57, 14, 'Manicure')");
        db.execSQL("INSERT INTO subcategories VALUES (58, 14, 'Sombrancelha')");
        db.execSQL("INSERT INTO subcategories VALUES (59, 14, 'Depilação')");
        db.execSQL("INSERT INTO subcategories VALUES (60, 14, 'Extras')");

        db.execSQL("INSERT INTO categories VALUES (15, 'Esportes')");
        db.execSQL("INSERT INTO subcategories VALUES (61, 15, 'Academia')");
        db.execSQL("INSERT INTO subcategories VALUES (62, 15, 'Futebol')");
        db.execSQL("INSERT INTO subcategories VALUES (63, 15, 'Volei')");
        db.execSQL("INSERT INTO subcategories VALUES (64, 15, 'Basquete')");
        db.execSQL("INSERT INTO subcategories VALUES (65, 15, 'Ballet')");
        db.execSQL("INSERT INTO subcategories VALUES (66, 15, 'Ginastica')");
        db.execSQL("INSERT INTO subcategories VALUES (67, 15, 'Natação')");
        db.execSQL("INSERT INTO subcategories VALUES (68, 15, 'Hidroginastica')");
        db.execSQL("INSERT INTO subcategories VALUES (69, 15, 'Extras')");

        db.execSQL("INSERT INTO categories VALUES (16, 'Extras')");
        db.execSQL("INSERT INTO subcategories VALUES (70, 16, 'Presentes')");
        db.execSQL("INSERT INTO subcategories VALUES (71, 16, 'Doações')");


        sql = "CREATE TABLE accounts (" +
                     "  code INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "  type TEXT, " +
                     "  code_category INTEGER, " +
                     "  code_subcategory INTEGER, " +
                     "  due_date TEXT, " +
                     "  due_value REAL, " +
                     "  payment_date TEXT, " +
                     "  payment_value REAL, " +
                     "  description TEXT " +
                     ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
