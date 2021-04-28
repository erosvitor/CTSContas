package br.com.ctseducare.ctscontas.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.ctseducare.ctscontas.R;
import br.com.ctseducare.ctscontas.adapter.ListViewAccountListAdapter;
import br.com.ctseducare.ctscontas.controller.AccountController;
import br.com.ctseducare.ctscontas.dao.AccountDao;
import br.com.ctseducare.ctscontas.utils.CTSConstants;
import br.com.ctseducare.ctscontas.dto.AccountDTO;

public class AccountListActivity extends AppCompatActivity {

    private List<AccountDTO> accounts;
    private ListViewAccountListAdapter adapter;
    private ListView listViewAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.title_screen_account_list);

        setContentView(R.layout.activity_account_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        accounts = new AccountController(this).list();
        adapter = new ListViewAccountListAdapter(accounts, this);

        listViewAccounts = findViewById(R.id.listviewAccounts);
        listViewAccounts.setAdapter(adapter);
        registerForContextMenu(listViewAccounts);
    }

    //----------------------------------------------------------------------------------------------------
    // Menu Home
    //----------------------------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(CTSConstants.RESULT_CODE_ACCOUNT_LIST_CLOSED);
            this.finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    //----------------------------------------------------------------------------------------------------
    // Popup menu for ListView
    //----------------------------------------------------------------------------------------------------
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listviewAccounts) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.popup_menu_account_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        AccountDTO accountVO = (AccountDTO)listViewAccounts.getItemAtPosition(info.position);
        switch(item.getItemId()) {
            case R.id.miPopupMenuAccountListEdit:
                editAccountEdit(accountVO);
                return true;
            case R.id.miPopupMenuAccountListDelete:
                confirmAccountDelete(accountVO);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void editAccountEdit(final AccountDTO accountVO) {
        Intent intent = new Intent(AccountListActivity.this, AccountEditActivity.class);
        intent.putExtra("accountCodeForEdition", accountVO.code);
        startActivityForResult(intent, CTSConstants.REQUEST_CODE_ACCOUNT_EDIT);
    }

    private void confirmAccountDelete(final AccountDTO accountVO) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.dialog_delete_account_title);
        builder.setMessage(R.string.dialog_delete_account_message);
        builder.setPositiveButton(R.string.button_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccount(accountVO);
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

    private void deleteAccount(AccountDTO accountVO) {
        new AccountDao(this).deleteById(accountVO.code);
        accounts.remove(accountVO);
        adapter.notifyDataSetChanged();
    }

    //----------------------------------------------------------------------------------------------------
    // Popup menu for ListView
    //----------------------------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CTSConstants.REQUEST_CODE_ACCOUNT_EDIT && resultCode == CTSConstants.RESULT_CODE_ACCOUNT_EDIT_OK) {
            int accountCodeEdited = data.getIntExtra("accountCodeEdited", -1);
            if (accountCodeEdited > -1) {
                AccountDTO accountVO = new AccountDao(this).listById(accountCodeEdited);
                int position = adapter.getPosition(accountVO);
                adapter.getItem(position).category = accountVO.category;
                adapter.getItem(position).subcategory = accountVO.subcategory;
                adapter.getItem(position).dueDate = accountVO.dueDate;
                adapter.getItem(position).dueValue = accountVO.dueValue;
                adapter.getItem(position).paymentDate = accountVO.paymentDate;
                adapter.getItem(position).paymentValue = accountVO.paymentValue;
                adapter.getItem(position).description = accountVO.description;
                adapter.notifyDataSetChanged();
            }

            adapter.notifyDataSetChanged();
        }
    }

}
