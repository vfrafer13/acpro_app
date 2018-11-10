package com.example.vanessa.myapplication;

import android.content.ContentProviderOperation;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText displayName;
    EditText phone;
    String name;
    String number;
    ArrayList<ContentProviderOperation> ops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        toolbar = (Toolbar) findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        ContactActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        ops = new ArrayList<>();

        setTitle("Contacto");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View view) {
        addContact();
    }

    private void addContact(){
        displayName = findViewById(R.id.editTxtNewContact);
        phone = findViewById(R.id.editTxtPhoneContact);

        name = displayName.getText().toString();
        number = phone.getText().toString();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        );

        //------------------------------------------------------ Names
        if(name != null)
        {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            name).build()
            );
        }

        //------------------------------------------------------ Mobile Number
        if(number != null)
        {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build()
            );
        }

        // Asking the Contact provider to create a new contact
        try
        {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Necesita permisos la aplicación", Toast.LENGTH_SHORT).show();
            finish();
        }

        Toast.makeText(getApplicationContext(), "Se ha guardado el contacto " + name ,Toast.LENGTH_SHORT).show();
        finish();
    }
}
